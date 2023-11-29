package domain.controllers;

import domain.models.entities.roles.Permiso;
import domain.models.entities.usuario.Comunidad;
import domain.models.entities.usuario.Persona;
import domain.models.repositories.RepositorioDeComunidades;
import domain.models.repositories.RepositorioDeUsuarios;
import domain.server.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.*;

public class ComunidadesController extends Controller implements ICrudViewsHandler {

    private RepositorioDeComunidades repositorioDeComunidades;
    private RepositorioDeUsuarios repositorioDeUsuarios;

    public ComunidadesController(RepositorioDeComunidades repositorioDeComunidades, RepositorioDeUsuarios repositorioDeUsuarios) {
        this.repositorioDeComunidades = repositorioDeComunidades;
        this.repositorioDeUsuarios = repositorioDeUsuarios;
    }

    @Override
    public void index(Context context) {
        Persona usuarioLogueado = super.usuarioLogueado(context);
        List<Permiso> permisos = new ArrayList<>(usuarioLogueado.getRol().getPermisos());
        Map<String, Object> model = new HashMap<>();
        List<Comunidad> comunidades = this.repositorioDeComunidades.buscarTodos();
        model.put("comunidades", comunidades);
        model.put("permisos", permisos);
        context.render("comunidades/comunidades-general.hbs", model);
    }

    public void indexPerfil(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<Comunidad> comunidades = this.repositorioDeComunidades.buscarTodos();
        comunidades = filtrarComunidadesPorMiembro(comunidades,context);
        model.put("comunidades", comunidades);
        context.render("comunidades/comunidades-persona.hbs", model);
    }
    public List<Comunidad> filtrarComunidadesPorMiembro(List<Comunidad> comunidades, Context contexto) {
        List<Comunidad> comunidadesConMiembro = new ArrayList<>();
        Persona miembro = super.usuarioLogueado(contexto);
        for (Comunidad comunidad : comunidades) {
            if (comunidad.getMiembros().contains(miembro)) {
                comunidadesConMiembro.add(comunidad);
            }
        }
        return comunidadesConMiembro;
    }

    public void comunidadMiembros(Context context) {
        Comunidad comunidad = (Comunidad) this.repositorioDeComunidades.buscar(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        List<Persona> miembros = comunidad.getMiembros();
        model.put("miembros", miembros);
        model.put("comunidad", comunidad);
        context.render("comunidades/miembros-comunidad.hbs", model);
    }
    public void entrarComunidad(Context context) {
        Comunidad comunidad = (Comunidad) this.repositorioDeComunidades.buscar(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        Persona nuevoMiembro = usuarioLogueado(context);
        if(comunidad.getMiembros().contains(nuevoMiembro)){
            context.redirect("/comunidades/miscomunidades");
        }
        else{
            nuevoMiembro.agregarComunidad(comunidad);
            comunidad.agregarMiembros(nuevoMiembro);
            repositorioDeUsuarios.actualizar(nuevoMiembro);
            repositorioDeComunidades.actualizar(comunidad);
            context.redirect("/comunidades/miscomunidades");
        }
    }

    public void salirComunidad(Context context) {
        Comunidad comunidad = (Comunidad) this.repositorioDeComunidades.buscar(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        Persona miembroASacar = usuarioLogueado(context);
        if(comunidad.getMiembros().contains(miembroASacar)){
            miembroASacar.quitarComunidad(comunidad);
            comunidad.quitarMiembro(miembroASacar);
            repositorioDeUsuarios.actualizar(miembroASacar);
            repositorioDeComunidades.actualizar(comunidad);
            context.redirect("/comunidades/miscomunidades");
        }
        else{
             context.redirect("/comunidades/miscomunidades");
        }
    }
    public void sacarDeComunidad(Context context){
        long comunidadId = Long.parseLong(context.pathParam("comunidadId"));
        long personaId = Long.parseLong(context.pathParam("personaId"));
        Comunidad comunidad = (Comunidad) this.repositorioDeComunidades.buscar(comunidadId);
        Map<String, Object> model = new HashMap<>();
        Persona miembroASacar = (Persona) repositorioDeUsuarios.buscar(personaId);
        miembroASacar.quitarComunidad(comunidad);
        comunidad.quitarMiembro(miembroASacar);
        repositorioDeUsuarios.actualizar(miembroASacar);
        repositorioDeComunidades.actualizar(comunidad);
        context.render("comunidades/miembros-comunidad.hbs", model);
    }
    public void hacerAdminDeComunidad(Context context){
        long comunidadId = Long.parseLong(context.pathParam("comunidadId"));
        long personaId = Long.parseLong(context.pathParam("personaId"));
        Comunidad comunidad = (Comunidad) this.repositorioDeComunidades.buscar(comunidadId);
        Map<String, Object> model = new HashMap<>();
        Persona nuevoAdmin = (Persona) repositorioDeUsuarios.buscar(personaId);
        comunidad.agregarAdmins(nuevoAdmin);
        repositorioDeComunidades.actualizar(comunidad);
        context.render("comunidades/miembros-comunidad.hbs", model);
    }

    @Override
    public void show(Context context) {
        Comunidad comunidad = (Comunidad) this.repositorioDeComunidades.buscar(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("comunidad", comunidad);
        if(comunidad.esAdmin(usuarioLogueado(context)))
            context.render("comunidades/informacion-comunidad.hbs", model);
        else //TODO --> CAMBIAR ESTE PARA QUE SEAN HBS DIFERENTES
            context.render("comunidades/informacion-comunidad-noAdmin.hbs", model);
    }

    @Override
    public void create(Context context) {

        this.save(context);
        context.redirect("/comunidades");
    }

    public void save(Context context) {
        Comunidad comunidad = new Comunidad();
        this.asignarParametros(comunidad, context);
        comunidad.agregarMiembros(usuarioLogueado(context));
        comunidad.setCreador(usuarioLogueado(context));
        comunidad.agregarAdmins(usuarioLogueado(context));
        this.repositorioDeComunidades.guardar(comunidad);
        context.status(HttpStatus.CREATED);
        //context.redirect("/comunidades");
    }

    @Override
    public void edit(Context context) {
        Comunidad comunidad = (Comunidad) this.repositorioDeComunidades.buscar(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("comunidad", comunidad);
        context.render("comunidades/comunidad.hbs", model);
    }

    @Override
    public void update(Context context) {
        Comunidad comunidad = (Comunidad) this.repositorioDeComunidades.buscar(Long.parseLong(context.pathParam("id")));
        // Realizar la actualización de la comunidad según sea necesario
        // ...

        this.repositorioDeComunidades.actualizar(comunidad);
        context.redirect("/comunidades/crear-comunidad");
    }

    @Override
    public void delete(Context context) {
        Comunidad comunidad = (Comunidad) this.repositorioDeComunidades.buscar(Long.parseLong(context.pathParam("id")));
        this.repositorioDeComunidades.eliminar(comunidad);
        context.redirect("/comunidades");
    }

    private void asignarParametros(Comunidad comunidad, Context context) {
        if (!Objects.equals(context.formParam("nombre"), "")) {
            comunidad.setNombre(context.formParam("nombre"));
        }
        if (!Objects.equals(context.formParam("descripcion"), "")) {
            comunidad.setDescripcion(context.formParam("descripcion"));
        }

        // Asignar otros parámetros de la comunidad según sea necesario
        // ...
    }
}
