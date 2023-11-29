package domain.models.entities.incidentes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;
import domain.models.entities.converters.LocalDateAttributeConverter;
import domain.models.entities.usuario.Persona;
import domain.models.entities.persistente.Persistente;
import domain.models.entities.servicio.Establecimiento;
import domain.models.entities.servicio.Servicio;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "incidentes")
@Getter
@Setter
public class Incidente extends Persistente {

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "abierto")
    private Boolean abierto;

    @Convert(converter = LocalDateAttributeConverter.class)
    @Column(name="fechaInicio", columnDefinition = "DATE")
    private Date fechaInicio;

    @Convert(converter = LocalDateAttributeConverter.class)
    @Column(name = "fechaCierre", columnDefinition = "DATE")
    private Date fechaCierre;

    @Column(name = "descripcion", columnDefinition = "text")
    private String descripcion;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "establecimiento_id", referencedColumnName = "id")
    private Establecimiento establecimiento;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "servicio_id", referencedColumnName = "id")
    private Servicio servicio;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "persona_id", referencedColumnName = "id")
    private Persona persona;

    public Incidente(String descripcion, Persona persona, Servicio servicio, Establecimiento establecimiento) {
        this.descripcion = descripcion;
        this.abierto = true;
        Calendar calendario = Calendar.getInstance();
        this.fechaInicio = calendario.getTime();
        this.persona = persona;
        this.servicio = servicio;
        this.establecimiento = establecimiento;
    }

    public Incidente() {

    }

    public void cerrar(Date fechaCierre) {
        if (abierto) {
            this.abierto = false;
            this.fechaCierre = fechaCierre;
        }
    }
    public long tiempo(Date fechaInicio, Date fechaCierre) {
        long tiempo = (fechaCierre.getTime() - fechaInicio.getTime()) / (60 * 60 * 1000);
        return tiempo;
    }
    public static boolean perteneceSemanaActual(LocalDateTime fecha) {
        // Obtener la fecha y hora actual
        LocalDateTime fechaHoraActual = LocalDateTime.now();

        // Obtener la fecha de inicio de la semana actual
        LocalDate inicioSemanaActual = fechaHoraActual.toLocalDate()
                .with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1);

        // Obtener la fecha de fin de la semana actual
        LocalDate finSemanaActual = inicioSemanaActual.plusDays(6);

        // Verificar si la fecha proporcionada está dentro de la semana actual
        return !fecha.isBefore(inicioSemanaActual.atStartOfDay()) && !fecha.isAfter(finSemanaActual.atTime(23, 59, 59));
    }

}
