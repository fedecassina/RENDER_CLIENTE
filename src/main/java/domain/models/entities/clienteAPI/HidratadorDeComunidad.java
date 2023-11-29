package domain.models.entities.clienteAPI;

import domain.models.entities.incidentes.Incidente;
import domain.models.entities.servicio.Entidad;
import domain.models.entities.servicio.Establecimiento;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.usuario.Comunidad;
import domain.models.entities.usuario.Persona;
import domain.models.entities.usuario.Usuario;
import domain.models.repositories.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HidratadorDeComunidad {
    RepositorioDeServicios repositorioDeServicios = new RepositorioDeServicios();
    RepositorioDeUsuarios repositorioDeUsuarios = new RepositorioDeUsuarios();
    RepositorioDeEstablecimientos repositorioDeEstablecimientos = new RepositorioDeEstablecimientos();
    RepositorioDeIncidentes repositorioDeIncidentes = new RepositorioDeIncidentes();
    public Comunidad hidratarComunidad(Respuesta respuesta, Comunidad comunidadAnterior){
        Comunidad comunidad = new Comunidad();

        List<Establecimiento> establecimientos = respuesta.getEstablecimientos()
                .stream()
                .map(id -> repositorioDeEstablecimientos.buscar(id))
                .filter(Objects::nonNull) // Filtrar elementos no nulos para evitar ClassCastException
                .map(Establecimiento.class::cast) // Realizar el casting
                .collect(Collectors.toList());

        System.out.println("serv" + respuesta.getServicios());
        System.out.println("servStream" + respuesta.getServicios().stream());

        List<Servicio> servicios = respuesta.getServicios()
                .stream()
                .map(id -> repositorioDeServicios.buscar(id))
                .filter(Objects::nonNull) // Filtrar elementos no nulos para evitar ClassCastException
                .map(Servicio.class::cast) // Realizar el casting
                .collect(Collectors.toList());

        /*List<Persona> usuarios = respuesta.getUsuarios()
                .stream()
                .map(id -> repositorioDeUsuarios.buscar(id))
                .filter(Objects::nonNull) // Filtrar elementos no nulos para evitar ClassCastException
                .map(Persona.class::cast) // Realizar el casting
                .collect(Collectors.toList());*/

        //comunidad.setMiembros(usuarios);
        comunidad.setEstablecimientos(establecimientos);
        comunidad.setServicios(servicios);
        comunidad.setListaDeIncidentes(comunidadAnterior.getListaDeIncidentes());
        comunidad.setGradoConfianza(respuesta.getGradoConfianza());

        return comunidad;

    }

}
