package domain.controllers;

import domain.models.entities.incidentes.Incidente;
import domain.models.entities.servicio.Entidad;
import domain.models.entities.servicio.Establecimiento;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.usuario.Persona;
import domain.models.repositories.RepositorioDeEntidades;
import domain.models.repositories.RepositorioDeEstablecimientos;
import domain.models.repositories.RepositorioDeIncidentes;
import domain.models.repositories.RepositorioDeServicios;
import domain.server.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.*;

public class EstablecimientosController extends Controller implements ICrudViewsHandler {

    private RepositorioDeEstablecimientos repositorioDeEstablecimientos;
    private RepositorioDeEntidades repositorioDeEntidades = new RepositorioDeEntidades();

    public EstablecimientosController(RepositorioDeEstablecimientos repositorioDeEstablecimientos) {
        this.repositorioDeEstablecimientos = repositorioDeEstablecimientos;
    }

    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {

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
        context.redirect("/configuracion");
    }

    @Override
    public void save(Context context) {
        Establecimiento establecimiento = new Establecimiento();
        this.asignarParametros(establecimiento, context);
        this.repositorioDeEstablecimientos.guardar(establecimiento);
        context.status(HttpStatus.CREATED);
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

    public void asignarParametros(Establecimiento establecimiento, Context context) {
        if (!Objects.equals(context.formParam("nombre"), "")) {
            establecimiento.setNombre(context.formParam("nombre"));
            System.out.println(context.formParam("nombre"));
        }
        if (!Objects.equals(context.formParam("entidad"), "")) {
            Entidad entidad = repositorioDeEntidades.buscarEntidadPorNombre(context.formParam("entidad"));
            establecimiento.setEntidad(entidad);
        }

    }

}
