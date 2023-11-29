package domain.controllers;

import domain.models.entities.roles.Permiso;
import domain.models.entities.servicio.Entidad;
import domain.models.entities.servicio.Establecimiento;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.usuario.Persona;
import domain.models.repositories.RepositorioDeEntidades;
import domain.models.repositories.RepositorioDeEstablecimientos;
import domain.models.repositories.RepositorioDeServicios;
import domain.models.repositories.RepositorioDeUsuarios;
import domain.server.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.*;
import java.util.logging.Logger;

public class ServiciosController extends Controller implements ICrudViewsHandler {

    private RepositorioDeServicios repositorioDeServicios;
    private RepositorioDeEstablecimientos repositorioDeEstablecimientos = new RepositorioDeEstablecimientos();

    public ServiciosController(RepositorioDeServicios repo) {
        this.repositorioDeServicios = repo;
    }

    @Override
    public void index(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        Persona usuarioLogueado = super.usuarioLogueado(ctx);
        List<Permiso> permisos = new ArrayList<>(usuarioLogueado.getRol().getPermisos());
        List<Servicio> servicios = this.repositorioDeServicios.buscarTodos();
        model.put("servicios", servicios);
        model.put("permisos", permisos);
        ctx.render("servicios/servicios.hbs", model);
    }

    public void indexPerfil(Context context) {
        Map<String, Object> model = new HashMap<>();

        Logger logger = Logger.getLogger(getClass().getName());

        logger.info("Llegué a index");

        List<Servicio> servicios = this.repositorioDeServicios.buscarTodos();
        servicios = filtrarServiciosPorMiembro(servicios,context);

        model.put("servicios", servicios);
        context.render("servicios/servicios-de-interes.hbs", model);
    }
    public List<Servicio> filtrarServiciosPorMiembro(List<Servicio> servicios, Context contexto) {
        List<Servicio> servicioConMiembro = new ArrayList<>();
        Persona miembro = super.usuarioLogueado(contexto);
        for (Servicio servicio : servicios) {
            if (servicio.getLista_interesados().contains(miembro)) {
                servicioConMiembro.add(servicio);
            }
        }
        return servicioConMiembro;
    }

    @Override
    public void show(Context context) {
        Map<String, Object> model = new HashMap<>();
        Servicio servicios = (Servicio) this.repositorioDeServicios.buscar(Long.parseLong(context.pathParam("id")));
        model.put("servicio", servicios);
        context.render("servicios/servicio.hbs", model);

    }
    public void showDelUsuario(Context ctx){
        Map<String, Object> model = new HashMap<>();
        List<Servicio> servicios = this.repositorioDeServicios.buscarServiciosDeInteres(ctx.pathParam("id"));
        model.put("servicios", servicios);
        ctx.render("servicios/servicios-de-interes.hbs", model);
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
        context.redirect("/servicios");
    }

    @Override
    public void save(Context context) {
        Servicio servicio = new Servicio();
        this.asignarParametros(servicio, context);
        this.repositorioDeServicios.guardar(servicio);
        context.status(HttpStatus.CREATED);

    }

    @Override
    public void edit(Context context) {
        Servicio servicio = (Servicio) this.repositorioDeServicios.buscar(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("servicio", servicio);
        context.render("servicios/servicio.hbs", model); //TODO
    }

    @Override
    public void update(Context context) {
        Servicio servicio = (Servicio) this.repositorioDeServicios.buscar(Long.parseLong(context.pathParam("id")));
        this.repositorioDeServicios.actualizar(servicio);
        context.redirect("/servicios");
    }

    @Override
    public void delete(Context context) {
        Servicio servicio = (Servicio) this.repositorioDeServicios.buscar(Long.parseLong(context.pathParam("id")));
        this.repositorioDeServicios.eliminar(servicio);
        context.redirect("/servicios");
    }

    private void asignarParametros(Servicio servicio, Context context) {
        if (!Objects.equals(context.formParam("nombre"), "")) {
            servicio.setNombre(context.formParam("nombre"));
            System.out.println(context.formParam("nombre"));
        }
        if (!Objects.equals(context.formParam("descripcion"), "")) {
            servicio.setDescripcion(context.formParam("descripcion"));
            System.out.println(context.formParam("descripcion"));
        }
        if (!Objects.equals(context.formParam("establecimiento"), "")) {
            Establecimiento establecimiento = repositorioDeEstablecimientos.buscarPorNombre(context.formParam("establecimiento"));
            servicio.setEstablecimiento(establecimiento);
        }
    }
}
