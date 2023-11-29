package domain.models.entities.notificaciones.estrategiaDeNotificacion;

import domain.models.entities.incidentes.Incidente;
import domain.models.entities.integracionAPI.datos.Municipio;
import domain.models.entities.notificaciones.medioDeNotificacion.NotificadorMAIL;
import domain.models.entities.servicio.Establecimiento;
import domain.models.entities.usuario.Comunidad;
import domain.models.entities.usuario.Persona;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        Municipio municipio = new Municipio("Municipio1");
        Comunidad comunidad1 = new Comunidad("Comunidad A");
        Establecimiento establecimiento = new Establecimiento("Coto", null , municipio);

        List<Comunidad> comunidades = new ArrayList<>();
        comunidades.add(comunidad1);

        List<Incidente> listaIncidentesComunidad;
        NotificacionSinApuro estrategia = new NotificacionSinApuro();
        NotificadorMAIL medio = new NotificadorMAIL();

        Persona persona = new Persona("Fede", comunidades, medio, estrategia, municipio, null);
        List<String> horariosPreferencia = Arrays.asList("0 32 20 ? * *");// <second> <minute> <hour> <day-of-month> <month> <day-of-week><year> <command>
        List<String> horariosPreferenciaCierre = Arrays.asList("0 33 20 ? * *");
        persona.setHorariosDePreferencia(horariosPreferencia);
        persona.setHorariosDePreferenciaCierre(horariosPreferenciaCierre);
        comunidad1.miembros.add(persona);
        //Persona persona2;

        Incidente incidenteCreado = persona.crearIncidente("INCIDENTE CREADO: DEBERIA LLEGAR MAIL Y SIN APURO",null,null);
        incidenteCreado.setEstablecimiento(establecimiento);

        persona.abrirIncidente(incidenteCreado);
        comunidad1.informarAMiembrosRevisionPorCercania();
        persona.cerrarIncidente(incidenteCreado);
    }
}
