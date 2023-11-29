package domain.server;

import domain.controllers.*;
import static io.javalin.apibuilder.ApiBuilder.*;

public class Router {


    public static void init() {
        Server.app().get("/", ctx -> {
            ctx.render("usuarios/pantalla-inicial.hbs");
        });

        Server.app().get("/incidentes/crear-incidente", ctx -> {
            ctx.render("incidentes/crear-incidente.hbs");
        });

        Server.app().get("/entidades/apertura-entidad", ctx -> {
            ctx.render("entidades/apertura-entidad.hbs");
        });

        Server.app().get("/servicios/crear-servicio", ctx -> {
            ctx.render("servicios/crear-servicio.hbs");
        });

        Server.app().get("/comunidades/crear-comunidad", ctx -> {
            ctx.render("comunidades/crear-comunidad.hbs");
        });

        Server.app().get("/carga-masiva-organismos", ctx -> {
            ctx.render("carga-masiva-organismos.hbs");
        });
        Server.app().get("/carga-masiva-entidades", ctx -> {
            ctx.render("carga-masiva-entidades.hbs");
        });
        Server.app().get("/establecimientos/crear", ctx -> {
            ctx.render("establecimientos/crear-establecimiento.hbs");
        });

        Server.app().routes(() -> {
            post("incidentes/crear", ((IncidentesController) FactoryController.controller("Incidentes"))::create);
            get("incidentes/editar/{id}", ((IncidentesController) FactoryController.controller("Incidentes"))::show);
            get("incidentes/cerrar/{id}", ((IncidentesController) FactoryController.controller("Incidentes"))::update);
            get("incidentes", ((IncidentesController) FactoryController.controller("Incidentes"))::showAll);

            get("usuarios", ((UsuariosController) FactoryController.controller("Usuarios"))::index);
            get("usuarios/crear", ((UsuariosController) FactoryController.controller("Usuarios"))::create);
            post("usuarios", ((UsuariosController) FactoryController.controller("Usuarios"))::save);
            get("usuarios/editar/{id}", ((UsuariosController) FactoryController.controller("Usuarios"))::show);
            get("usuarios/eliminar/{id}", ((UsuariosController) FactoryController.controller("Usuarios"))::delete);
            get("perfil", ((UsuariosController) FactoryController.controller("Usuarios"))::mostrarPerfil);
            post("medioDeNotificacion", ((UsuariosController) FactoryController.controller("Usuarios"))::cambiarMedio);
            post("formaDeNotificacion", ((UsuariosController) FactoryController.controller("Usuarios"))::cambiarForma);
            get("rankingIncidentes", ((UsuariosController) FactoryController.controller("Usuarios"))::showIncidentes);
            get("rankingImpacto", ((UsuariosController) FactoryController.controller("Usuarios"))::showImpacto);
            get("rankingCierres", ((UsuariosController) FactoryController.controller("Usuarios"))::showCierres);


            get("comunidades", ((ComunidadesController) FactoryController.controller("Comunidades"))::index);
            get("comunidades/miscomunidades", ((ComunidadesController) FactoryController.controller("Comunidades"))::indexPerfil);
            post("comunidades/crear", ((ComunidadesController) FactoryController.controller("Comunidades"))::create);
            get("comunidades/{id}", ((ComunidadesController) FactoryController.controller("Comunidades"))::show);
            get("comunidades/miembros/{id}", ((ComunidadesController) FactoryController.controller("Comunidades"))::comunidadMiembros);
            get("comunidades/entrar-comunidad/{id}", ((ComunidadesController) FactoryController.controller("Comunidades"))::entrarComunidad);
            get("comunidades/salir-comunidad/{id}", ((ComunidadesController) FactoryController.controller("Comunidades"))::salirComunidad);
            get("comunidades/miembros/sacar/{comunidadId}/{personaId}", ((ComunidadesController) FactoryController.controller("Comunidades"))::sacarDeComunidad);
            get("comunidades/miembros/hacer-admin/{comunidadId}/{personaId}", ((ComunidadesController) FactoryController.controller("Comunidades"))::hacerAdminDeComunidad);

        //  get("comunidades/editar/{id}", ((ComunidadesController) FactoryController.controller("Comunidades"))::update);

            post("entidades/crear", ((EntidadesController) FactoryController.controller("Entidades"))::create);
            get("entidades", ((EntidadesController) FactoryController.controller("Entidades"))::showAll);
            get("entidades/editar/{id}", ((EntidadesController) FactoryController.controller("Entidades"))::show);
            get("entidades/misentidadesinteres", ((EntidadesController) FactoryController.controller("Entidades"))::indexPerfil);
            post("entidades/agregarInteres/{id}", ((EntidadesController) FactoryController.controller("Entidades"))::agregarInteres);
            post("entidades/sacarInteres/{id}", ((EntidadesController) FactoryController.controller("Entidades"))::sacarInteres);
            get("entidad/{id}", ((EntidadesController) FactoryController.controller("Entidades"))::show);
            get("entidadInteres/{id}", ((EntidadesController) FactoryController.controller("Entidades"))::showInteres);


            get("servicios/misservicios", ((ServiciosController) FactoryController.controller("Servicios"))::indexPerfil);
            get("servicios", ((ServiciosController) FactoryController.controller("Servicios"))::index);
            post("servicios/crear", ((ServiciosController) FactoryController.controller("Servicios"))::create);
            get("servicios/{id}", ((ServiciosController) FactoryController.controller("Servicios"))::show);

            get("iniciar-sesion", ((SesionesController) FactoryController.controller("Sesiones"))::show);
            post("iniciar-sesion", ((SesionesController) FactoryController.controller("Sesiones"))::login);
            get("inicio-sesion-incorrecto", ((SesionesController) FactoryController.controller("Sesiones"))::showIncorrecto);
            get("registrarse", ((SesionesController) FactoryController.controller("Sesiones"))::create);
            post("registrarse", ((SesionesController) FactoryController.controller("Sesiones"))::save);

            post("carga-masiva-organismos", ((CargaMasivaOrganismoController) FactoryController.controller("CargaMasiva"))::create);
            post("carga-masiva-entidades", ((CargaMasivaEntidadController) FactoryController.controller("CargaMasivaEntidades"))::create);

            post("establecimientos/crear", ((EstablecimientosController) FactoryController.controller("Establecimientos"))::create);

            get("configuracion", ((SesionesController) FactoryController.controller("Sesiones"))::config);
        });



    }

}