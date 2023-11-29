package domain.models.entities.notificaciones.estrategiaDeNotificacion;

import domain.models.entities.incidentes.Incidente;
import domain.models.entities.notificaciones.medioDeNotificacion.MedioNotificacion;
import domain.models.entities.usuario.Persona;
import lombok.Getter;
import lombok.Setter;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;
import java.util.List;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.CronScheduleBuilder.*;

@Getter
@Setter
public class NotificacionSinApuro implements NotificacionStrategy{
    private MedioNotificacion medioNotificacion;
    private String name = "SinApuro";
    @Override
    public void notificar(Incidente incidente, Persona unaPersona) {

        try {
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();

            List<String> horariosDePreferencia = unaPersona.getHorariosDePreferencia();

            for (String horario : horariosDePreferencia) {
                CronExpression cronExpression = new CronExpression(horario);
                String cronSchedule = cronExpression.getCronExpression();

                JobDetail job = newJob(NotificacionJob.class)
                        .withIdentity("notificacionJob-" + horario, "grupoNotificacion")
                        .build();

                job.getJobDataMap().put("incidente", incidente);
                job.getJobDataMap().put("medioNotificacion", unaPersona.getMedioDeRecibirNotificacion());

                Trigger trigger = newTrigger()
                        .withIdentity("notificacionTrigger-" + horario, "grupoNotificacion")
                        .withSchedule(cronSchedule(cronSchedule))
                        .build();

                scheduler.scheduleJob(job, trigger);
            }

            scheduler.start();
        } catch (SchedulerException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notificarCierre(Incidente incidente, Persona unaPersona) {
        try {
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();

            List<String> horariosDePreferenciaCierre = unaPersona.getHorariosDePreferenciaCierre();

            for (String horario : horariosDePreferenciaCierre) {
                CronExpression cronExpression = new CronExpression(horario);
                String cronSchedule = cronExpression.getCronExpression();

                JobDetail job = newJob(NotificacionCierreJob.class) // Utilizamos NotificacionCierreJob
                        .withIdentity("notificacionCierreJob-" + horario, "grupoNotificacion")
                        .build();

                job.getJobDataMap().put("incidente", incidente);
                job.getJobDataMap().put("medioNotificacion", unaPersona.getMedioDeRecibirNotificacion());

                Trigger trigger = newTrigger()
                        .withIdentity("notificacionCierreTrigger-" + horario, "grupoNotificacion")
                        .withSchedule(cronSchedule(cronSchedule))
                        .build();

                scheduler.scheduleJob(job, trigger);
            }

            scheduler.start();
        } catch (SchedulerException | ParseException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String obtenerNombre(){
        return name;
    }
    @Override
    public void notificarRevision(Incidente incidente, Persona unaPersona){
        unaPersona.getMedioDeRecibirNotificacion().enviarNotificacion("Se requiere revision de incidente"); //TODO
    }

}



