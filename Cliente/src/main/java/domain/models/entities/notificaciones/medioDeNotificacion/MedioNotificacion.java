package domain.models.entities.notificaciones.medioDeNotificacion;

import lombok.Getter;
import lombok.Setter;

public interface MedioNotificacion {

    public void enviarNotificacion(String mensaje);
    public String obtenerNombre();
}
