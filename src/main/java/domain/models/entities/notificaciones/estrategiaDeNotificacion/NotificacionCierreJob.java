package domain.models.entities.notificaciones.estrategiaDeNotificacion;

import domain.models.entities.incidentes.Incidente;
import domain.models.entities.notificaciones.medioDeNotificacion.MedioNotificacion;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class NotificacionCierreJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Incidente incidente = (Incidente) context.getJobDetail().getJobDataMap().get("incidente");
        MedioNotificacion medioNotificacion = (MedioNotificacion) context.getJobDetail().getJobDataMap().get("medioNotificacion");

        medioNotificacion.enviarNotificacion("El incidente ha sido resuelto " );
    }
}
