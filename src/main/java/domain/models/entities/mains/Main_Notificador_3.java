package domain.models.entities.mains;

import domain.models.entities.incidentes.Incidente;
import domain.models.entities.integracionAPI.datos.Municipio;
import domain.models.entities.notificaciones.estrategiaDeNotificacion.NotificacionCuandoSucede;
import domain.models.entities.notificaciones.medioDeNotificacion.NotificadorWPP;
import domain.models.entities.servicio.Establecimiento;
import domain.models.entities.usuario.Comunidad;
import domain.models.entities.usuario.Persona;

import java.util.ArrayList;
import java.util.List;

public class Main_Notificador_3 {
    public static void main(String[] args) {
        Municipio municipio = new Municipio("Municipio1");
        Comunidad comunidad1 = new Comunidad("Comunidad A");
        Establecimiento establecimiento = new Establecimiento("Coto", null , municipio);

        List<Comunidad> comunidades = new ArrayList<>();;
        comunidades.add(comunidad1);

        List<Incidente> listaIncidentesComunidad;
        NotificacionCuandoSucede estrategia = new NotificacionCuandoSucede();;
        NotificadorWPP medio = new NotificadorWPP();;
        String descripcionIncidente1 = "Se rompió algo";;

        Persona persona = new Persona("Fede", comunidades, medio, estrategia, municipio, null);;
        //List<String> horariosPreferencia = Arrays.asList("0 0 8 ? * MON-FRI", "0 0 12 ? * MON-FRI");
        //persona.setHorariosDePreferencia(horariosPreferencia);

        comunidad1.miembros.add(persona);
        //Persona persona2;

        Incidente incidenteCreado = persona.crearIncidente("INCIDENTE CREADO: DEBERIA LLEGAR WPP Y AL INSTANTE",null,null);
        incidenteCreado.setEstablecimiento(establecimiento);

        persona.abrirIncidente(incidenteCreado);

        comunidad1.informarAMiembrosRevisionPorCercania();

        persona.cerrarIncidente(incidenteCreado);
    }
}
