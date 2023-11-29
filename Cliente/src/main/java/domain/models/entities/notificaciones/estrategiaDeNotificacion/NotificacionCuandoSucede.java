package domain.models.entities.notificaciones.estrategiaDeNotificacion;

import domain.models.entities.incidentes.Incidente;
import domain.models.entities.notificaciones.medioDeNotificacion.MedioNotificacion;
import domain.models.entities.usuario.Persona;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class NotificacionCuandoSucede implements NotificacionStrategy{
    private MedioNotificacion medioNotificacion;
    public String name = "CuandoSucede";
    @Override
    public void notificar(Incidente incidente, Persona unaPersona) {
        // Implementación de la notificación inmediata utilizando el medio de notificación
        unaPersona.getMedioDeRecibirNotificacion().enviarNotificacion("Mensaje: " + incidente.getDescripcion());
    }

    @Override
    public void notificarCierre(Incidente incidente, Persona unaPersona) {
        unaPersona.getMedioDeRecibirNotificacion().enviarNotificacion("El incidente ha sido resuelto");
    }

    @Override
    public void notificarRevision(Incidente incidente, Persona unaPersona){
        unaPersona.getMedioDeRecibirNotificacion().enviarNotificacion("Se requiere revision de incidente");
    }
    @Override
    public String obtenerNombre(){
        return name;
    }
}
