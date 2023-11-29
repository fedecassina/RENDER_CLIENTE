package domain.controllers;
import domain.models.entities.incidentes.Incidente;
import domain.models.entities.servicio.Establecimiento;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.usuario.Comunidad;
import domain.models.entities.usuario.Persona;
import domain.models.entities.usuario.Usuario;
import domain.models.repositories.*;
import domain.server.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.*;

public class IncidentesController extends Controller implements ICrudViewsHandler {

    private RepositorioDeIncidentes repositorioDeIncidentes;
    private RepositorioDeEstablecimientos repositorioDeEstablecimientos = new RepositorioDeEstablecimientos();
    private RepositorioDeComunidades repositorioDeComunidades = new RepositorioDeComunidades();

    public IncidentesController(RepositorioDeIncidentes repositorioDeIncidentes) {
        this.repositorioDeIncidentes = repositorioDeIncidentes;
    }

    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<Incidente> incidentes = this.repositorioDeIncidentes.buscarTodos();
        model.put("incidentes", incidentes);
        context.render("incidentes/incidentes.hbs", model);
    }

    @Override
    public void show(Context context) {
        Incidente incidente = (Incidente) this.repositorioDeIncidentes.buscar(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("incidente", incidente);
        context.render("incidentes/edicion-incidente.hbs", model);
    }

    public void showAll(Context context) {
        Map<String, Object> model = new HashMap<>();

        Persona usuario = this.usuarioLogueado(context);
        List<Comunidad> comunidades = usuario.getListComunidades();

        List<Incidente> incidentes = new ArrayList<>();

        for (Comunidad comunidad : comunidades){
            incidentes.addAll(comunidad.getListaDeIncidentes());
        }
        model.put("incidentes", incidentes);
        context.render("incidentes/incidentes.hbs", model);
    }

    @Override
    public void create(Context context) {
        /*Usuario usuarioLogueado = super.usuarioLogueado(context);

        if(usuarioLogueado == null || !usuarioLogueado.getRol().tenesPermiso("crear_incidentes")) {
            throw new AccessDeniedException();
        }*/

        this.save(context);

        //Map<String, Object> model = new HashMap<>();
        //model.put("incidente", incidente);
        context.redirect("/incidentes");
    }

    public void createU(Context context) {
        Persona usuario = new Persona();
        usuario.setNombre("Tomas");
    }

    @Override
    public void save(Context context) {
        Persona usuario = this.usuarioLogueado(context);
        List<Comunidad> comunidades = usuario.getListComunidades();
        RepositorioDeEntidades repositorioDeEntidades = new RepositorioDeEntidades();
        Establecimiento establecimiento = repositorioDeEntidades.buscarPorNombre(context.formParam("establecimiento"));

        for (Comunidad comunidad : comunidades) {
            Incidente incidente = new Incidente();
            this.asignarParametros(incidente, context, usuario, establecimiento);
            this.repositorioDeIncidentes.guardar(incidente);
            this.repositorioDeEstablecimientos.actualizar(establecimiento);
            comunidad.agregarIncidente(incidente);
            this.repositorioDeComunidades.actualizar(comunidad);
            context.status(HttpStatus.CREATED);
        }
    }

    @Override
    public void edit(Context context) {
        Incidente incidente = (Incidente) this.repositorioDeIncidentes.buscar(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("incidente", incidente);
        context.render("incidentes/incidente.hbs", model);
    }

    @Override
    public void update(Context context) {
        Incidente incidente = (Incidente) this.repositorioDeIncidentes.buscar(Long.parseLong(context.pathParam("id")));
        Calendar calendario = Calendar.getInstance();
        incidente.cerrar(calendario.getTime());
        this.repositorioDeIncidentes.actualizar(incidente);
        context.redirect("/incidentes");
    }

    @Override
    public void delete(Context context) {
        Incidente incidente = (Incidente) this.repositorioDeIncidentes.buscar(Long.parseLong(context.pathParam("id")));
        this.repositorioDeIncidentes.eliminar(incidente);
        context.redirect("/incidentes");
    }

    private void asignarParametros(Incidente incidente, Context context, Persona usuario, Establecimiento establecimiento) {

        incidente.setEstablecimiento(establecimiento);

        if(!Objects.equals(context.formParam("servicio"), "")) {
            RepositorioDeServicios repositorioDeServicios = new RepositorioDeServicios();
            Servicio servicio1 = repositorioDeServicios.buscarPorNombre(context.formParam("servicio"));
            incidente.setServicio(servicio1);
        }
        if(!Objects.equals(context.formParam("descripcion"), "")) {
            incidente.setDescripcion(context.formParam("descripcion"));
        }

        incidente.setPersona(usuario);

        Calendar calendario = Calendar.getInstance();
        incidente.setFechaInicio(calendario.getTime());


        incidente.setAbierto(Boolean.TRUE);
    }

    private void cerrar(Incidente incidente) {
        incidente.setAbierto(Boolean.FALSE);
    }
}
