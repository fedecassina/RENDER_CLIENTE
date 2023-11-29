package domain;
import org.junit.Test;

import domain.models.entities.notificaciones.medioDeNotificacion.NotificadorWPP;
public class NotificadorWPP_Test {
    @Test
    public void testEnviarNotificacion() {
        NotificadorWPP notificador = new NotificadorWPP();
        String mensaje = "Hola, esto es una prueba de envío de WhatsApp";

        notificador.enviarNotificacion(mensaje);
    }
}
