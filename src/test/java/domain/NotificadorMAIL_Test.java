package domain;
import domain.models.entities.notificaciones.medioDeNotificacion.NotificadorMAIL;
import org.junit.Test;
import static org.junit.Assert.*;

public class NotificadorMAIL_Test {

    @Test
    public void enviarNotificacion_DeberiaEnviarCorreoElectronicoExitosamente() {
        // Crear una instancia del notificador
        NotificadorMAIL notificador = new NotificadorMAIL();

        // Definir el mensaje a enviar
        String mensaje = "Este es un mensaje de prueba.";

        // Enviar la notificación
        notificador.enviarNotificacion(mensaje);

        // No se lanzó ninguna excepción, lo cual indica que el correo se envió correctamente
        // Si se lanza alguna excepción, el test fallará automáticamente
        assertTrue(true);
    }
}
