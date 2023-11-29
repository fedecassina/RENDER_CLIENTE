package domain.models.entities.converters;

//import domain.Notificaciones.EstrategiaDeNotificacion.*;
import domain.models.entities.notificaciones.estrategiaDeNotificacion.NotificacionCuandoSucede;
import domain.models.entities.notificaciones.estrategiaDeNotificacion.NotificacionSinApuro;
import domain.models.entities.notificaciones.estrategiaDeNotificacion.NotificacionStrategy;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
@Converter(autoApply = true)
public class EstrategiaNotificacionConverter implements AttributeConverter<NotificacionStrategy, String>{

    @Override
    public String convertToDatabaseColumn(NotificacionStrategy attribute) {
        String medioEnBase = null;

        if(attribute.obtenerNombre()== "CuandoSucede") {
            medioEnBase = "CuandoSucede";
        }
        else if(attribute.obtenerNombre() == "SinApuro") {
            medioEnBase = "SinApuro";
        }
        return medioEnBase;
    }

    @Override
    public NotificacionStrategy convertToEntityAttribute(String s) {
        NotificacionStrategy medio = null;

        if (s.equals("CuandoSucede")) {
            medio = new NotificacionCuandoSucede();
        } else if (s.equals("SinApuro")) {
            medio = new NotificacionSinApuro();
        }
        return medio;
    }
}