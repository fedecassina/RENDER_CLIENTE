package domain.controllers;

import domain.models.entities.lectorCSV.EntidadPrestadora;
import domain.models.entities.roles.Permiso;
import domain.models.entities.servicio.Entidad;
import domain.models.entities.usuario.Comunidad;
import domain.models.entities.usuario.Persona;
import domain.models.repositories.RepositorioDeCargaEntidades;
import domain.models.repositories.RepositorioDeEntidades;
import domain.models.repositories.RepositorioDeUsuarios;
import domain.server.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.*;

public class EntidadesController extends Controller implements ICrudViewsHandler {

    private RepositorioDeEntidades repositorioDeEntidades;
    private RepositorioDeUsuarios repositorioDeUsuarios;
    public EntidadesController(RepositorioDeEntidades repositorioDeEntidades) {
        this.repositorioDeEntidades = repositorioDeEntidades;
    }

    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<Entidad> entidades = this.repositorioDeEntidades.buscarTodos();
        model.put("entidades", entidades);
        model.put("entidades", context.sessionAttribute("usuario_id"));
        context.render("entidades/entidades-general.hbs", model);
    }

    public void indexPerfil(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<Entidad> entidades = this.repositorioDeEntidades.buscarTodos();
        entidades = filtrarEntidadesPorMiembro(entidades,context);
        model.put("entidades", entidades);
        context.render("entidades/entidades-de-intereses.hbs", model);
    }
    public List<Entidad> filtrarEntidadesPorMiembro(List<Entidad> entidades, Context contexto) {
        List<Entidad> entidadesConMiembro = new ArrayList<>();
        Persona miembro = super.usuarioLogueado(contexto);
        for (Entidad entidad : entidades) {
            if (entidad.getLista_interesados().contains(miembro)) {
                entidadesConMiembro.add(entidad);
            }
        }
        return entidadesConMiembro;
    }


    @Override
    public void show(Context context) {
        Entidad entidad = (Entidad) this.repositorioDeEntidades.buscar(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("entidad", entidad);
        context.render("entidades/entidad.hbs", model);
    }
    public void showInteres(Context context) {
        Entidad entidad = (Entidad) this.repositorioDeEntidades.buscar(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("entidad", entidad);
        context.render("entidades/entidad-interes.hbs", model);
    }

    public void showAll(Context context) {
        Map<String, Object> model = new HashMap<>();
        Persona usuarioLogueado = super.usuarioLogueado(context);
        List<Permiso> permisos = new ArrayList<>(usuarioLogueado.getRol().getPermisos());
        List<Entidad> entidades = this.repositorioDeEntidades.buscarTodos();
        System.out.println("cantidad " + entidades.stream().count());
        model.put("entidades", entidades);
        model.put("permisos", permisos);
        context.render("entidades/entidades-general.hbs", model);
    }

    @Override
    public void create(Context context) {

        this.save(context);
        context.redirect("/entidades");
    }

    @Override
    public void save(Context context) {
        Entidad entidad = new Entidad();
        this.asignarParametros(entidad, context);
        entidad.agregarInteresado(usuarioLogueado(context));
        this.repositorioDeEntidades.guardar(entidad);
        context.status(HttpStatus.CREATED);
    }
    public void asignarEntidadPrestadora(Context context,Entidad entidad){
        Persona persona = super.usuarioLogueado(context);
        RepositorioDeCargaEntidades repositorioDeCargaEntidades = new RepositorioDeCargaEntidades();
        EntidadPrestadora entidadPrestadora = repositorioDeCargaEntidades.buscarPorUsuario(persona);
        entidadPrestadora.agregarEntidad(entidad);
        repositorioDeCargaEntidades.actualizar(entidadPrestadora);
    }

    @Override
    public void edit(Context context) {
        Entidad entidad = (Entidad) this.repositorioDeEntidades.buscar(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("entidad", entidad);
        context.render("entidades/entidades-general.hbs", model);
    }

    public void agregarInteres(Context context){
        long entidadId = Long.parseLong(context.pathParam("id"));
        Entidad entidad = (Entidad) this.repositorioDeEntidades.buscar(entidadId);
        Persona usuarioLogueado = super.usuarioLogueado(context);
        usuarioLogueado.agregarInteres(entidad);
        entidad.agregarInteresado(usuarioLogueado(context));
        repositorioDeEntidades.actualizar(entidad);
        //repositorioDeUsuarios.actualizar(usuarioLogueado);
        // Redirige a la página de intereses del usuario usando una solicitud GET
        context.redirect("/entidades/misentidadesinteres");
    }
    public void sacarInteres(Context context){
        long entidadId = Long.parseLong(context.pathParam("id"));
        Entidad entidad = (Entidad) this.repositorioDeEntidades.buscar(entidadId);
        Persona usuarioLogueado = super.usuarioLogueado(context);
        usuarioLogueado.sacarInteres(entidad);
        entidad.sacarInteresado(usuarioLogueado(context));
        repositorioDeEntidades.actualizar(usuarioLogueado);
        // Redirige a la página de intereses del usuario usando una solicitud GET
        context.redirect("/entidades/misentidadesinteres");
    }

    @Override
    public void update(Context context) {
        Entidad entidad = (Entidad) this.repositorioDeEntidades.buscar(Long.parseLong(context.pathParam("id")));
        this.repositorioDeEntidades.actualizar(entidad);
        context.redirect("/entidades");
    }

    @Override
    public void delete(Context context) {
        Entidad entidad = (Entidad) this.repositorioDeEntidades.buscar(Long.parseLong(context.pathParam("id")));
        this.repositorioDeEntidades.eliminar(entidad);
        context.redirect("/entidades");
    }

    private void asignarParametros(Entidad entidad, Context context) {
        if (!Objects.equals(context.formParam("nombre"), "")) {
            entidad.setNombre(context.formParam("nombre"));

        }
        if (!Objects.equals(context.formParam("descripcion"), "")) {
            entidad.setDescripcion(context.formParam("descripcion"));
        }
        Persona persona = super.usuarioLogueado(context);
        RepositorioDeCargaEntidades repositorioDeCargaEntidades = new RepositorioDeCargaEntidades();
        EntidadPrestadora entidadPrestadora = repositorioDeCargaEntidades.buscarPorUsuario(persona);
        entidad.setEntidadPrestadora(entidadPrestadora);
    }
}

