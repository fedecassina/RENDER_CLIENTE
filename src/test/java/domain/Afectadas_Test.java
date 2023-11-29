package domain;

import domain.models.entities.incidentes.Incidente;
import domain.models.entities.integracionAPI.datos.Municipio;
import domain.models.entities.servicio.Establecimiento;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.usuario.Comunidad;
import domain.models.entities.usuario.Persona;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Afectadas_Test {
    private List<Servicio> serviciosSubteA;
    private List<Comunidad> comunidadesP1;
    private List<Comunidad> comunidadesP2;
    private List<Comunidad> comunidadesP3;
    private List<Comunidad> comunidadesP4;
    private List<Persona> miembrosC1;
    private List<Persona> miembrosC2;
    //private List<Persona> miembrosC1;
    @BeforeEach
    public void setup(){
        serviciosSubteA = new ArrayList<>();
        comunidadesP1 = new ArrayList<>();
        comunidadesP2 = new ArrayList<>();
        comunidadesP3 = new ArrayList<>();
        comunidadesP4= new ArrayList<>();
        miembrosC1= new ArrayList<>();
        miembrosC2= new ArrayList<>();
        //miembrosC3= new ArrayList<>();
    }


    @Test
    public void listaDeAfectados(){
        Municipio caballito = new Municipio("Caballito");

        Servicio escaleraMecanica = new Servicio();
        serviciosSubteA.add(escaleraMecanica);

        Establecimiento subteA = new Establecimiento("Subte A", serviciosSubteA, caballito);

        Persona persona1 = new Persona("Creador",  comunidadesP1,null,null, caballito, true);
        Persona persona2 = new Persona("Persona 1",  comunidadesP2,null,null, caballito, false);
        Persona persona3 = new Persona("Persona 2" , comunidadesP3,null,null, caballito, true);
        Persona persona4 = new Persona("Persona 3",  comunidadesP4,null,null, caballito, false);

        Comunidad comunidad1 = new Comunidad("Comunidad 1");
        Comunidad comunidad2 = new Comunidad("Comunidad 2");
        Comunidad comunidad3 = new Comunidad("Comunidad 3");

        comunidad1.miembros.add(persona1);
        comunidad2.miembros.add(persona1);
        comunidadesP1.add(comunidad1);
        comunidadesP1.add(comunidad2);


        comunidad2.miembros.add(persona2);
        comunidadesP2.add(comunidad2);

        comunidad3.miembros.add(persona3);
        comunidadesP3.add(comunidad3);

        comunidad3.miembros.add(persona4);
        comunidadesP4.add(comunidad3);

        Incidente incidenteCreado1 = persona1.crearIncidente("se rompieron las escaleras",escaleraMecanica, subteA);
        Incidente incidenteCreado2 = persona2.crearIncidente("se rompieron las escaleras",escaleraMecanica, subteA);
        Incidente incidenteCreado3 = persona3.crearIncidente("se rompieron las escaleras",escaleraMecanica, subteA);

        subteA.cargaIncidentes(incidenteCreado1);
        subteA.cargaIncidentes(incidenteCreado2);
        subteA.cargaIncidentes(incidenteCreado3);

        Assertions.assertIterableEquals(subteA.personasAfectadas(escaleraMecanica), Arrays.asList(persona1, persona3));

    }

    // ESTABLECIMIENTO --> SERVICIO / LISTA INCIDENTES
    // INCIDENTE --> SERVICIO --> LISTA DE INCIDENTES ASOCIADOS A UN SERVICIO
    // LISTA DE INCIDENTES ASOCIADOS A UN SERVICIO --> LISTA DE PERSONAS
    // LISTA PERSONAS --> LISTA DE COMUNIDAES
    // LISTA DE COMUNIDADES --> LISTA DE PERSONAS
    // LISTA DE AFECTADOS

        // EL ESTABLECIMIENTO TIENE QUE TENER LOS 3 INCIDENTES EN LA LISTA Y EL SERVICIO

        // PERSONA 1 --> COMUNIDAD 1/2 --> AFECTADO  (TRUE)
        // PERSONA 2 --> COMUNIDAD 2 -->   OBSERVADOR (TRUE)
        // PERSONA 3 --> COMUNIDAD 3 -->   AFECTADO (TRUE)
        // PERSONA 4 --> COMUNIDAD 3 -->   OBSERVADOR (TRUE)

        // INCIDENTE 1 --> ESTABLECIMIENTO / SERVICIO / PERSONA 1
        // INCIDENTE 2 --> ESTABLECIMIENTO / SERVICIO / PERSONA 2
        // INCIDENTE 3 --> ESTABLECIMIENTO / SERVICIO / PERSONA 3

        // COMUNIDAD 1 --> TIENE QUE TENER A LA PERSONA 1
        // COMUNIDAD 2 --> TIENE QUE TENER A LA PERSONA 1 Y 2
        // COMUNIDAD 3 --> TIENE QUE TENER A LA PERSONA 1 Y 3

        // si yo pido la lista al establecimiento del servicio --> [persona1,persona3]


}
