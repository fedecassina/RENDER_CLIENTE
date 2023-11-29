package domain.models.entities.notificaciones.estrategiaDeNotificacion;

import domain.models.entities.incidentes.Incidente;
import domain.models.entities.usuario.Persona;

public interface NotificacionStrategy {
    public void notificar(Incidente incidente, Persona unaPersona);
    public void notificarCierre(Incidente incidente, Persona unaPersona);
    public void notificarRevision(Incidente incidente, Persona unaPersona);
    public String obtenerNombre();

}
