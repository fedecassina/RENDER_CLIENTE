package domain.models.entities.servicio;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import domain.models.entities.incidentes.Incidente;
import domain.models.entities.integracionAPI.datos.Municipio;
import domain.models.entities.persistente.Persistente;
import domain.models.entities.usuario.Comunidad;
import domain.models.entities.usuario.Persona;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name="establecimientos")
public class Establecimiento extends Persistente {

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "establecimiento")
    private List<Servicio> ListaServicios = new ArrayList<>();

    @OneToMany
    private List<Incidente> lista_incidentes = new ArrayList<>();

    @JsonManagedReference
    @Transient //TODO
    private List<Incidente> lista_incidentesTotales = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "establecimiento_id", referencedColumnName = "id")
    private Entidad entidad;

    @Transient //TODO
    private Municipio localizacion;

    //private Ubicacion ubicacion; TODO ver tema ubicacion y localizacion

    public Establecimiento (String nombre, List<Servicio> servicios, Municipio localizacion) {
        this.nombre = nombre;
        this.ListaServicios = servicios;
        this.localizacion = localizacion;
    }

    public Establecimiento() {

    }

    public List<Incidente> getLista_incidentes(){
        return lista_incidentes;
    }

    public List<Long> obtenerTiemposIncidentes() {
        List<Long> tiempos = new ArrayList<>();

        for (Incidente incidente : lista_incidentes) {
            Date fechaInicio = incidente.getFechaInicio();
            Date fechaCierre = incidente.getFechaCierre();

            if (fechaCierre != null) {
                long tiempo = incidente.tiempo(fechaInicio, fechaCierre);
                tiempos.add(tiempo);
            }
        }

        return tiempos;
    }


    public void cargaIncidentes(Incidente incidente){//CARGAR SOLO LOS QUE NO ESTEN CARGADOS
        lista_incidentesTotales.add(incidente);
        if(incidente.getServicio().habilitado != false){
            lista_incidentes.add(incidente);
            incidente.getServicio().habilitado = false;
        }
    }
    public List<Incidente> incidenteSemanales(){

    return (List<Incidente>) this.lista_incidentes.stream().filter(incidente -> incidente.perteneceSemanaActual(LocalDateTime.now()));
    }

    public Integer cantidadIncidentesSemanales(){
        int cantidad = this.incidenteSemanales().size();
        return Integer.valueOf(cantidad);
    }
    //public void mayorPromedioDeCierres() {}


    public List<Persona> personasAfectadas(Servicio servicio) {
        return extraerPersonasAfectadasDeComunidades(
                extraerComunidadesDePersonas(
                        extraerPersonasDeIncidentes(
                                incidentesDelServicio(servicio)
                        )
                )
        );
    }

    public List<Incidente> incidentesDelServicio(Servicio servicio) {
        return lista_incidentesTotales.stream()
                .filter(incidente -> incidente.getServicio().equals(servicio))
                .collect(Collectors.toList());
    }

    public List<Persona> extraerPersonasDeIncidentes(List<Incidente> listaIncidentes) {
        return listaIncidentes.stream()
                .map(Incidente::getPersona)
                .collect(Collectors.toList());
    }

    public List<Comunidad> extraerComunidadesDePersonas(List<Persona> listaPersonas) {
        return listaPersonas.stream()
                .flatMap(persona -> persona.getListComunidades().stream())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Persona> extraerPersonasAfectadasDeComunidades(List<Comunidad> listaComunidades) {
        return listaComunidades.stream()
                .flatMap(comunidad -> comunidad.getMiembros().stream())
                .filter(Persona::getAfectado)
                .distinct()
                .collect(Collectors.toList());
    }

    public Integer devolverNumeroAfectados ()
    {
        return (extraerPersonasAfectadasDeComunidades(extraerComunidadesDePersonas(extraerPersonasDeIncidentes(lista_incidentes)))).size();
    }
}
