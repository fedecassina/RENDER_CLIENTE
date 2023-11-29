package domain.models.entities.usuario;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import domain.models.entities.incidentes.Incidente;
import domain.models.entities.persistente.Persistente;
import lombok.Getter;
import lombok.Setter;
import domain.models.entities.servicio.Establecimiento;
import domain.models.entities.servicio.Servicio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "comunidades")
@Getter
@Setter
public class Comunidad extends Persistente {

    @JsonIgnore
    @Column(name = "descripcion")
    private String descripcion;

    @JsonIgnore
    @Column(name = "nombre")
    private String nombre;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "comunidad_miembros", // Nombre de la tabla de relación para miembros
            joinColumns = @JoinColumn(name = "comunidad_id"),
            inverseJoinColumns = @JoinColumn(name = "miembro_id")
    )
    public List<Persona> miembros;


    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "comunidad_administradores", // Nombre de la tabla de relación para administradores
            joinColumns = @JoinColumn(name = "comunidad_id"),
            inverseJoinColumns = @JoinColumn(name = "administrador_id")
    )
    public List<Persona> administradores;

    @JsonIgnore
    @ManyToOne
    public Persona creador;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "comunidad_id", referencedColumnName = "id")
    public List<Incidente> listaDeIncidentes;

    @JsonIgnore
    @Transient
    private List<Establecimiento> establecimientos;

    @JsonIgnore
    @Transient
    private List<Servicio> servicios;

    @Transient
    @JsonProperty("incidentes")
    private List<Long> listaDeIncidentesLong;

    @Transient
    @JsonProperty("usuarios")
    private List<Long> MiembrosLong;

    @Transient
    @JsonProperty("establecimientos")
    private List<Long> establecimientosLong;

    @Transient
    @JsonProperty("servicios")
    private List<Long> serviciosLong;

    @Transient
    @JsonProperty("propuestasAnteriores")
    private List<PropuestaAnterior> propuestas;

    @Transient
    private double gradoConfianza;


    public Comunidad(String nombre) {
        this.nombre = nombre;
        this.listaDeIncidentes = new ArrayList<>();
        this.miembros = new ArrayList<>();
        this.establecimientos = new ArrayList<>();
        this.servicios = new ArrayList<>();
    }

    public Comunidad() {
        miembros = new ArrayList<>();
        listaDeIncidentes = new ArrayList<>();
        this.administradores = new ArrayList<>();
        this.establecimientos = new ArrayList<>();
        this.servicios = new ArrayList<>();
    }

    public void agregarIncidente(Incidente incidente) {
        listaDeIncidentes.add(incidente);
    }

    public void quitarIncidente(Incidente incidente){
        listaDeIncidentes.remove(incidente);
    }

    public void agregarEstablecimiento(Establecimiento establecimiento) {
        establecimientos.add(establecimiento);
    }

    public void quitarEstablecimiento(Establecimiento establecimiento){
        establecimientos.remove(establecimiento);
    }

    public void agregarServicio(Servicio servicio) {
        servicios.add(servicio);
    }

    public void quitarServicio(Servicio servicio){
        servicios.remove(servicio);
    }


    public void informarAMiembros(Incidente incidente){

        for (Persona persona: miembros) {
            persona.getFormaDeRecibirNotificacion().notificar(incidente, persona);
        }

    }

    public void informarAMiembrosRevisionPorCercania(){

        for(Incidente incidente : listaDeIncidentes){
            for (Persona persona: miembros) {
                if(persona.getLocalizacionActual() == incidente.getEstablecimiento().getLocalizacion()){
                    persona.getFormaDeRecibirNotificacion().notificarRevision(incidente, persona);
                }
            }
        }

    }

    public void informarAMiembrosCierreDeIncidente(Incidente incidente){
        for (Persona persona: miembros) {
            persona.getFormaDeRecibirNotificacion().notificarCierre(incidente, persona);
        }
    }

    public void agregarMiembros(Persona... personas){
        miembros.addAll(List.of(personas));
    }

    public void quitarMiembro(Persona... personas){
        miembros.removeAll(List.of(personas));
    }

    @Transient
    @JsonAlias({"MiembrosLong", "usuarios"})
    public List<Long> getMiembrosLong() {
        return miembros != null ? miembros.stream().map(Persona::getId).collect(Collectors.toList()) : null;
    }

    @Transient
    @JsonAlias({"listaDeIncidentesLong", "incidentes"})
    public List<Long> getListaDeIncidentesLong() {
        return listaDeIncidentes != null ? listaDeIncidentes.stream().map(Incidente::getId).collect(Collectors.toList()) : null;
    }

    @Transient
    @JsonAlias({"establecimientosLong", "establecimientos"})
    public List<Long> getEstablecimientosLong() {
        return establecimientos != null ? establecimientos.stream().map(Establecimiento::getId).collect(Collectors.toList()) : null;
    }


    @Transient
    public List<Long> getServiciosLong() {
        return servicios != null ? servicios.stream().map(Servicio::getId).collect(Collectors.toList()) : null;
    }

    public double getGradoConfianza(){
        return this.gradoConfianza;
    }

    public List<PropuestaAnterior> getPropuestas(){
        return this.propuestas;
    }

    public List<Persona> getMiembros() {
        return miembros;
    }

    public List<Incidente> getListaDeIncidentes() {
        return listaDeIncidentes;
    }

    public List<Establecimiento> getEstablecimientos() {
        return establecimientos;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public boolean esAdmin(Persona admin) {
        return administradores.contains(admin);
    }

    public void agregarAdmins(Persona... personas){
        administradores.addAll(List.of(personas));
    }

}
