package domain.models.entities.notificaciones.medioDeNotificacion;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class NotificadorWPP implements MedioNotificacion {
    private String name = "Wpp";
    // Credenciales de Twilio
    private static final String ACCOUNT_SID = "ACb17cf0d2e1ea02470395f0a1a255bc2a";
    private static final String AUTH_TOKEN = "aabc515c6c5895151b6ea19a785d3c1b";

    // Número de teléfono de Twilio
    private static final String TWILIO_PHONE_NUMBER = "whatsapp:+14155238886";
    private static final String recipientPhoneNumber = "whatsapp:+5491130334051";
    @Override
    public void enviarNotificacion(String mensaje) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        PhoneNumber from = new PhoneNumber(TWILIO_PHONE_NUMBER);
        PhoneNumber to = new PhoneNumber(recipientPhoneNumber);

        Message message = Message.creator(to, from, mensaje).create();

        System.out.println("Mensaje de WhatsApp enviado correctamente: " + message.getSid());
    }

    public String obtenerNombre(){
        return name;
    }

}
