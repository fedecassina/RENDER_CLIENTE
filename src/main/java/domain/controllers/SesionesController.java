package domain.controllers;

import domain.models.entities.notificaciones.estrategiaDeNotificacion.NotificacionCuandoSucede;
import domain.models.entities.notificaciones.estrategiaDeNotificacion.NotificacionSinApuro;
import domain.models.entities.notificaciones.medioDeNotificacion.NotificadorMAIL;
import domain.models.entities.notificaciones.medioDeNotificacion.NotificadorWPP;
import domain.models.entities.roles.Permiso;
import domain.models.entities.roles.Rol;
import domain.models.entities.usuario.Persona;
import domain.models.repositories.RepositorioDeRoles;
import domain.models.repositories.RepositorioDeUsuarios;
import domain.server.init.Initializer;
import domain.server.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SesionesController extends Controller implements ICrudViewsHandler {

    private RepositorioDeUsuarios repositorioDeUsuarios;
    private RepositorioDeRoles repositorioDeRoles;

    public SesionesController(RepositorioDeUsuarios repositorioDeUsuarios, RepositorioDeRoles repositorioDeRoles) {
        this.repositorioDeUsuarios = repositorioDeUsuarios;
        this.repositorioDeRoles = repositorioDeRoles;
    }

    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {
        context.render("sesiones/inicio-sesion.hbs");
    }

    public void showIncorrecto(Context context) {
        context.render("sesiones/inicio-sesion-incorrecto.hbs");
    }
    @Override
    public void create(Context context) {
        context.render("sesiones/registro.hbs");
    }

    @Override
    public void save(Context context) {
        Persona persona =  new Persona();
        this.asignarParametros(persona, context);
        this.repositorioDeUsuarios.guardar(persona);
        context.status(HttpStatus.CREATED);
        context.redirect("iniciar-sesion");
    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {

    }

    public void login(Context context) {

        if (validarCredenciales(context)) {
            Long usuario_id = repositorioDeUsuarios.buscarId(context.formParam("email"));

            context.sessionAttribute("usuario_id", usuario_id);

            context.redirect("perfil");
        } else {
            context.redirect("inicio-sesion-incorrecto");
        }
    }

    private boolean validarCredenciales(Context context) {

       String contrasenia = repositorioDeUsuarios.buscarContrasenia(context.formParam("email"));
       if(contrasenia.equals(context.formParam("contrasenia"))){
           return true;
       } else{
           return false;
       }

    }

    private void asignarParametros (Persona persona, Context context) {
        persona.setNombre(context.formParam("nombre"));
        persona.setApellido(context.formParam("apellido"));
        persona.setCorreo(context.formParam("correo"));
        persona.setContrasenia(context.formParam("contrasenia"));
        persona.setNombreDeUsuario(context.formParam("nombreDeUsuario"));

        if ("Sin apuro".equals(context.formParam("estrategiaDeNotificacion"))){
            NotificacionSinApuro notificicacionSinApuro = new NotificacionSinApuro();
            persona.setFormaDeRecibirNotificacion(notificicacionSinApuro);
        } else {
            NotificacionCuandoSucede notificacionCuandoSucede = new NotificacionCuandoSucede();
            persona.setFormaDeRecibirNotificacion(notificacionCuandoSucede);
        }

        if ("Whatsapp".equals(context.formParam("medioDeNotificacion"))){
            NotificadorWPP notificadorWPP = new NotificadorWPP();
            persona.setMedioDeRecibirNotificacion(notificadorWPP);
        } else {
            NotificadorMAIL notificadorMAIL = new NotificadorMAIL();
            persona.setMedioDeRecibirNotificacion(notificadorMAIL);
        }

        // persona.setRol((Rol) this.repositorioDeRoles.buscar(9L));
        persona.setRol((Rol) Initializer.buscarRolPorNombre("Comun"));
    }

    public void config(Context context) {
        Persona usuarioLogueado = super.usuarioLogueado(context);
        List<Permiso> permisos = new ArrayList<>(usuarioLogueado.getRol().getPermisos());
        Map<String, Object> model = new HashMap<>();
        model.put("usuario", usuarioLogueado);
        model.put("permisos", permisos);
        context.render("configuracion.hbs", model);
    }
}
