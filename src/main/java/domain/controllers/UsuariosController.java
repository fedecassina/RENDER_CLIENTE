package domain.controllers;

import domain.models.entities.informe.Informe;
import domain.models.entities.lectorCSV.EntidadPrestadora;
import domain.models.entities.notificaciones.estrategiaDeNotificacion.NotificacionCuandoSucede;
import domain.models.entities.notificaciones.estrategiaDeNotificacion.NotificacionSinApuro;
import domain.models.entities.notificaciones.medioDeNotificacion.NotificadorMAIL;
import domain.models.entities.notificaciones.medioDeNotificacion.NotificadorWPP;
import domain.models.entities.ranking.Ranking;
import domain.models.entities.roles.Rol;
import domain.models.entities.usuario.Persona;
import domain.models.repositories.RepositorioDeCargaEntidades;
import domain.models.repositories.RepositorioDeRoles;
import domain.models.repositories.RepositorioDeUsuarios;
import domain.server.init.Initializer;
import domain.server.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuariosController extends Controller implements ICrudViewsHandler {
    private RepositorioDeUsuarios repositorioDeUsuarios;
    private RepositorioDeRoles repositorioDeRoles;
    private RepositorioDeCargaEntidades repositorioDeCargaEntidades = new RepositorioDeCargaEntidades();

    public UsuariosController(RepositorioDeUsuarios repositorioDeUsuarios, RepositorioDeRoles repositorioDeRoles) {
        this.repositorioDeUsuarios = repositorioDeUsuarios;
        this.repositorioDeRoles = repositorioDeRoles;
    }

    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<Persona> usuarios = this.repositorioDeUsuarios.buscarTodos();
        model.put("usuarios", usuarios);
        context.render("usuarios/administracion.hbs", model);
    }

    @Override
    public void show(Context context) {
        Persona persona = (Persona) this.repositorioDeUsuarios.buscar(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("persona", persona);
        context.render("usuarios/edicion.hbs", model);
    }

    @Override
    public void create(Context context) {
        /* Usuario usuarioLogueado = super.usuarioLogueado(context);

        if(usuarioLogueado == null || !usuarioLogueado.getRol().tenesPermiso("crear_servicios")) {
            throw new AccessDeniedException();
        } */

        Persona persona = null;
        Map<String, Object> model = new HashMap<>();
        model.put("persona", persona);
        context.render("usuarios/creacion.hbs", model);
    }

    @Override
    public void save(Context context) {
        Persona persona = new Persona();
        this.asignarParametros(persona, context);
        this.repositorioDeUsuarios.guardar(persona);
        context.status(HttpStatus.CREATED);
        context.redirect("/usuarios");
    }

    public void cambiarMedio(Context context){
        Persona usuario = super.usuarioLogueado(context);
        if("Wpp".equals(usuario.getMedioDeRecibirNotificacion().obtenerNombre())){
            NotificadorMAIL notificadorMAIL = new NotificadorMAIL();
            usuario.setMedioDeRecibirNotificacion(notificadorMAIL);
        }else{
            NotificadorWPP notificadorWPP = new NotificadorWPP();
            usuario.setMedioDeRecibirNotificacion(notificadorWPP);
        }
        this.repositorioDeUsuarios.actualizar(usuario);
        context.redirect("/configuracion");
    }

    public byte[] ranking(Context context, String rankingDeseado) throws IOException {
        Persona usuario = super.usuarioLogueado(context);
        EntidadPrestadora entidadPrestadora = repositorioDeCargaEntidades.buscarPorUsuario(usuario);
        Ranking ranking = new Ranking();
        Informe informe = new Informe();
        byte[] contenido = new byte[0];

        switch (rankingDeseado){
            case "incidentes":{
                String rankingIncidentes = ranking.mayorCantidadDeIncidentes(entidadPrestadora);
                informe.exportarAPDF(rankingIncidentes,"src/main/resources/public/rankingIncidentes.pdf");
                contenido = Files.readAllBytes(Paths.get("src/main/resources/public/rankingIncidentes.pdf"));
                break;
            }
            case "cierres":{
                String rankingCierres = ranking.mayorPromedioDeCierres(entidadPrestadora);
                informe.exportarAPDF(rankingCierres,"src/main/resources/public/rankingCierres.pdf");
                contenido = Files.readAllBytes(Paths.get("src/main/resources/public/rankingCierres.pdf"));
                break;
            }
            case "impacto":{
                String rankingImpacto = ranking.mayorImpactoDeLasProblematicas(entidadPrestadora);
                informe.exportarAPDF(rankingImpacto,"src/main/resources/public/rankingImpacto.pdf");
                contenido = Files.readAllBytes(Paths.get("src/main/resources/public/rankingImpacto.pdf"));
                break;
            }
        }

        return contenido;
    }
    private byte[] readFromResource(String path) throws IOException {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(path)) {
            if (is != null) {
                return is.readAllBytes();
            } else {
                throw new FileNotFoundException("File not found: " + path);
            }
            }
    }
    public void showIncidentes(Context context) throws IOException {
        byte[] contenido = this.ranking(context,"incidentes");
        context.result(contenido).contentType("application/pdf");
    }

    public void showImpacto(Context context) throws IOException {
        byte[] contenido = this.ranking(context,"impacto");
        context.result(contenido).contentType("application/pdf");
    }
    public void showCierres(Context context) throws IOException {
        byte[] contenido = this.ranking(context,"cierres");
        context.result(contenido).contentType("application/pdf");
    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {
        Persona persona = (Persona) this.repositorioDeUsuarios.buscar(Long.parseLong(context.pathParam("id")));
        this.repositorioDeUsuarios.eliminar(persona);
        context.redirect("/usuarios");
    }

    private void asignarParametros(Persona persona, Context context) {
        persona.setNombre(context.formParam("nombre"));
        persona.setApellido(context.formParam("apellido"));
        persona.setCorreo(context.formParam("correo"));
        persona.setContrasenia(context.formParam("contrasenia"));
        persona.setNombreDeUsuario(context.formParam("nombreDeUsuario"));

        if ("Sin apuro".equals(context.formParam("estrategiaDeNotificacion"))) {
            NotificacionSinApuro notificicacionSinApuro = new NotificacionSinApuro();
            persona.setFormaDeRecibirNotificacion(notificicacionSinApuro);
        } else {
            NotificacionCuandoSucede notificacionCuandoSucede = new NotificacionCuandoSucede();
            persona.setFormaDeRecibirNotificacion(notificacionCuandoSucede);
        }

        if ("Whatsapp".equals(context.formParam("medioDeNotificacion"))) {
            NotificadorWPP notificadorWPP = new NotificadorWPP();
            persona.setMedioDeRecibirNotificacion(notificadorWPP);
        } else {
            NotificadorMAIL notificadorMAIL = new NotificadorMAIL();
            persona.setMedioDeRecibirNotificacion(notificadorMAIL);
        }

        switch (context.formParam("rol")) {
            case "Administrador":
                persona.setRol((Rol) Initializer.buscarRolPorNombre("Administrador"));
                // persona.setRol((Rol) this.repositorioDeRoles.buscar(8L));
                break;
            case "Prestador":
                persona.setRol((Rol) Initializer.buscarRolPorNombre("Prestador"));
                // persona.setRol((Rol) this.repositorioDeRoles.buscar(10L));
                break;
            default:
                persona.setRol((Rol) Initializer.buscarRolPorNombre("Comun"));
                // persona.setRol((Rol) this.repositorioDeRoles.buscar(9L));
        }
    }

    public void mostrarPerfil(Context context){
        Persona persona = usuarioLogueado(context);
        Map<String, Object> model = new HashMap<>();
        model.put("usuario", persona);
        context.render("usuarios/perfil.hbs", model);
    }

    public void cambiarForma(Context context){
        Persona usuario = super.usuarioLogueado(context);

        if ("SinApuro".equals(usuario.getFormaDeRecibirNotificacion().obtenerNombre())){
            NotificacionCuandoSucede notificacionCuandoSucede = new NotificacionCuandoSucede();
            usuario.setFormaDeRecibirNotificacion(notificacionCuandoSucede);
        } else {
            NotificacionSinApuro notificicacionSinApuro = new NotificacionSinApuro();
            usuario.setFormaDeRecibirNotificacion(notificicacionSinApuro);
        }
        this.repositorioDeUsuarios.actualizar(usuario);
        context.redirect("/configuracion");
    }

}

