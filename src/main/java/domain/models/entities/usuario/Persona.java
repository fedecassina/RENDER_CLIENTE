package domain.models.entities.usuario;

import domain.models.entities.incidentes.Incidente;
import domain.models.entities.integracionAPI.datos.Municipio;
import domain.models.entities.notificaciones.estrategiaDeNotificacion.NotificacionStrategy;
import domain.models.entities.notificaciones.medioDeNotificacion.MedioNotificacion;
import domain.models.entities.converters.EstrategiaNotificacionConverter;
import domain.models.entities.converters.MedioDeNotificacionConverter;
import domain.models.entities.persistente.Persistente;
import domain.models.entities.roles.Rol;
import domain.models.entities.servicio.Entidad;
import domain.models.entities.servicio.Establecimiento;
import domain.models.entities.servicio.Servicio;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="personas")
@Getter
@Setter
public class Persona extends Persistente {

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "nombreDeUsuario")
    private String nombreDeUsuario;

    @Column(name = "contrasenia" )
    private String contrasenia;
    
    @ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "id")
    private Rol rol;

    @Column(name = "correo")
    private String correo;

    @Column(name = "afectado")
    private Boolean afectado;

    @ManyToMany
    @JoinColumn(name = "comunidad_id", referencedColumnName = "id")
    private List<Comunidad> listComunidades;

    @ManyToMany
    private List<Servicio> listServiciosDeInteres ;

    @ManyToMany
    private List<Entidad> listEntidadesDeInteres;

    @Convert(converter = MedioDeNotificacionConverter.class)
    @Column(name = "medioDeNotificacion")
    private MedioNotificacion medioDeRecibirNotificacion; // WPP O MAIL

    @Convert(converter = EstrategiaNotificacionConverter.class)
    @Column(name = "formaDeNotificacion")
    private NotificacionStrategy formaDeRecibirNotificacion ; // CUANDO SUCEDE O SIN APURO

    @ElementCollection
    @CollectionTable(name = "persona_horarios", joinColumns = @JoinColumn(name = "persona_id"))
    @Column(name= "horario")
    private List<String> horariosDePreferencia;

    @ElementCollection
    @CollectionTable(name = "persona_horarios_Cierre", joinColumns = @JoinColumn(name = "persona_id"))
    @Column(name= "horarioCierre")
    private List<String> horariosDePreferenciaCierre;

    @Transient //TODO
    private Municipio localizacionActual;

    public Persona(String nombre, List<Comunidad> listComunidades, MedioNotificacion medioDeRecibirNotificacion, NotificacionStrategy formaDeRecibirNotificacion, Municipio localizacionActual, Boolean afectado) {
        this.nombre = nombre;
        this.listComunidades = listComunidades;
        this.medioDeRecibirNotificacion = medioDeRecibirNotificacion;
        this.formaDeRecibirNotificacion = formaDeRecibirNotificacion;
        this.localizacionActual = localizacionActual;
        this.afectado = afectado;
    }

    public Persona() {
        listComunidades = new ArrayList<>();
        listEntidadesDeInteres = new ArrayList<>();
        listServiciosDeInteres = new ArrayList<>();
        horariosDePreferencia = new ArrayList<>();
        horariosDePreferenciaCierre = new ArrayList<>();
    }

    public Persona(String nombre,Boolean afectado) {
        this.nombre = nombre;
        listComunidades = new ArrayList<>();
        listEntidadesDeInteres = new ArrayList<>();
        listServiciosDeInteres = new ArrayList<>();
        horariosDePreferencia = new ArrayList<>();
        horariosDePreferenciaCierre = new ArrayList<>();
        this.afectado = afectado;
    }

    public void actualizarLocalizacionActual(Municipio municipio){
        localizacionActual = municipio;
    }

    public Incidente crearIncidente(String observaciones, Servicio servicio, Establecimiento establecimiento){
        Incidente incidente = new Incidente(observaciones, this,servicio,establecimiento);
        return incidente;
    }

    public void abrirIncidente(Incidente incidente) {

        for (Comunidad comunidad : listComunidades) {
            comunidad.agregarIncidente(incidente);
            comunidad.informarAMiembros(incidente);
        }

    }
    public void cerrarIncidente(Incidente incidente) {
        Date fechaActual = new Date(); // Obtenemos la fecha y hora actual

        for (Comunidad comunidad : listComunidades) {
            // Cerramos el incidente con la fecha actual
            incidente.cerrar(fechaActual);

            // Realizamos las acciones en la comunidad
            comunidad.quitarIncidente(incidente);
            comunidad.informarAMiembrosCierreDeIncidente(incidente);
        }
    }

    public List<Incidente> verListaIncidentesComunidad(Comunidad comunidad) {
        List<Incidente> listaIncidentes = new ArrayList<>();

        if (listComunidades.contains(comunidad)) {
            // Obtener la lista de incidentes de la comunidad
            listaIncidentes = comunidad.getListaDeIncidentes();
        }

        return listaIncidentes;
    }

    public void agregarComunidad(Comunidad comunidad){
        listComunidades.add(comunidad);
    }

    public void quitarComunidad(Comunidad comunidad){
        listComunidades.remove(comunidad);
    }


    public void agregarInteres(Entidad entidad){
        listEntidadesDeInteres.add(entidad);
        //entidad.agregarInteresado(this);  // Añade al usuario como interesado de la entidad.
    }
    public void sacarInteres(Entidad entidad){
        listEntidadesDeInteres.remove(entidad);
        //entidad.agregarInteresado(this);  // Añade al usuario como interesado de la entidad.
    }



}
