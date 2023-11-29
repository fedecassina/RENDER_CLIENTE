package domain.models.entities.clienteAPI;

import domain.models.entities.servicio.Establecimiento;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.usuario.PropuestaAnterior;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Respuesta {
    private Long id;
    private List<Long> usuarios;
    private List<Long> establecimientos;
    private List<Long> servicios;
    private List<Long> incidentes;
    private List<PropuestaAnterior> propuestasAnteriores;
    private double gradoConfianza;


}

