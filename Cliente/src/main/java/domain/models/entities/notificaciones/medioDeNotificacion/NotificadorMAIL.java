package domain.models.entities.notificaciones.medioDeNotificacion;

import lombok.Getter;
import lombok.Setter;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
@Getter
@Setter
public class NotificadorMAIL implements MedioNotificacion {
    private String name = "Mail";

    @Override
    public void enviarNotificacion(String mensaje) {
        final String username = "cassinafede@gmail.com"; // Reemplaza con tu dirección de correo electrónico
        final String password = "hhbxlgfeqbitzfqv"; // Reemplaza con tu contraseña de correo electrónico

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "*");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("cassinafede@gmail.com"));
            message.setSubject("Prueba");
            message.setText(mensaje);

            Transport.send(message);
            System.out.println("Sent message successfully.");
        } catch (MessagingException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
    public String obtenerNombre(){
        return name;
    }
}