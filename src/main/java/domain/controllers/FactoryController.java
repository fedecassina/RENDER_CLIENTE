package domain.controllers;

import domain.models.repositories.*;
import domain.models.repositories.RepositorioDeComunidades;

public class FactoryController {

    public static Object controller(String nombre) {
        Object controller = null;
        switch (nombre) {
            case "Incidentes": controller = new IncidentesController(new RepositorioDeIncidentes()); break;
            case "Usuarios": controller = new UsuariosController(new RepositorioDeUsuarios(), new RepositorioDeRoles()); break;
            case "Servicios": controller = new ServiciosController(new RepositorioDeServicios()); break;
            case "Comunidades": controller = new ComunidadesController(new RepositorioDeComunidades(),new RepositorioDeUsuarios()); break;
            case "Entidades": controller = new EntidadesController(new RepositorioDeEntidades()); break;
            case "Sesiones": controller = new SesionesController(new RepositorioDeUsuarios(), new RepositorioDeRoles()); break;
            case "CargaMasiva": controller = new CargaMasivaOrganismoController(new RepositorioDeCargaOrganismos()); break;
            case "CargaMasivaEntidades": controller = new CargaMasivaEntidadController(new RepositorioDeCargaEntidades()); break;
            case "Establecimientos": controller = new EstablecimientosController(new RepositorioDeEstablecimientos()); break;
        }
        return controller;
    }
}