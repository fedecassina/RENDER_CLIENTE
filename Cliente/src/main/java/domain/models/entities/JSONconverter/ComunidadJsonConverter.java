package domain.models.entities.JSONconverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.models.entities.incidentes.Incidente;
import domain.models.entities.clienteAPI.FusionRequest;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.usuario.Comunidad;
import domain.models.entities.usuario.Persona;
import domain.models.entities.servicio.Establecimiento;

import java.util.List;
import java.util.stream.Collectors;

public class ComunidadJsonConverter {

    private final ObjectMapper objectMapper;

    public ComunidadJsonConverter() {
        this.objectMapper = new ObjectMapper();
    }

    public String convertirAJson(FusionRequest fusionRequest) {
        try {
            return objectMapper.writeValueAsString(transformarAIds(fusionRequest));
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir FusionRequest a JSON", e);
        }
    }

    public FusionRequest transformarAIds(FusionRequest fusionRequest) {
        Comunidad comunidad1 = fusionRequest.getComunidad1();
        Comunidad comunidad2 = fusionRequest.getComunidad2();

        // Transformar IDs en las comunidades antes de crear el FusionRequest
        comunidad1 = transformarAIds(comunidad1);
        comunidad2 = transformarAIds(comunidad2);

        fusionRequest.setComunidad1(comunidad1);
        fusionRequest.setComunidad2(comunidad2);

        return fusionRequest;
    }

    private Comunidad transformarAIds(Comunidad comunidad) {
        List<Long> idUsuarios = comunidad.getMiembros().stream()
                .map(Persona::getId)
                .collect(Collectors.toList());
        comunidad.setMiembrosLong(idUsuarios);

        List<Long> idIncidentes = comunidad.getListaDeIncidentes().stream()
                .map(Incidente::getId)
                .collect(Collectors.toList());
        comunidad.setListaDeIncidentesLong(idIncidentes);

        List<Long> servicios = comunidad.getServicios().stream()
                .map(Servicio::getId)
                .collect(Collectors.toList());
        comunidad.setServiciosLong(servicios);

        List<Long> establecimientos = comunidad.getEstablecimientos().stream()
                .map(Establecimiento::getId)
                .collect(Collectors.toList());
        comunidad.setEstablecimientosLong(establecimientos);

        return comunidad;
    }

    // Métodos obtenerPersonasDesdeIds y obtenerIdsDesdeIncidentes no mostrados por brevedad
}

