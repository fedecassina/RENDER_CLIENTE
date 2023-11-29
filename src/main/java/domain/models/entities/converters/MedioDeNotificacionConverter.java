package domain.models.entities.converters;

import domain.models.entities.notificaciones.medioDeNotificacion.MedioNotificacion;
import domain.models.entities.notificaciones.medioDeNotificacion.NotificadorMAIL;
import domain.models.entities.notificaciones.medioDeNotificacion.NotificadorWPP;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MedioDeNotificacionConverter implements AttributeConverter<MedioNotificacion,String> {
    @Override
    public String convertToDatabaseColumn(MedioNotificacion medioDeNotificacion) {
        String medioEnBase = null;

        if(medioDeNotificacion.obtenerNombre() == "Wpp") {
            medioEnBase = "wpp";
        }
        else if(medioDeNotificacion.obtenerNombre() =="Mail") {
            medioEnBase = "email";
        }
        return medioEnBase;
    }

    @Override
    public MedioNotificacion convertToEntityAttribute(String s) {
        MedioNotificacion medio = new NotificadorWPP();

        if(s.equals("wpp")) {
            medio = new NotificadorWPP();
        }
        else if(s.equals("email")) {
            medio = new NotificadorMAIL();
        }
        return medio;
    }
}
