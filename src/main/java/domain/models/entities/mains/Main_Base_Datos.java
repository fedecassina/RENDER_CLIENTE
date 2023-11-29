package domain.models.entities.mains;

import domain.models.entities.incidentes.Incidente;
import domain.models.entities.notificaciones.estrategiaDeNotificacion.NotificacionCuandoSucede;
import domain.models.entities.notificaciones.medioDeNotificacion.NotificadorWPP;
import domain.models.entities.servicio.Entidad;
import domain.models.entities.servicio.Establecimiento;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.usuario.Comunidad;
import domain.models.entities.usuario.Persona;
import domain.models.entities.lectorCSV.EntidadPrestadora;
import domain.models.entities.lectorCSV.OrganismoDeControl;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityTransaction;
import java.util.Collections;

public class Main_Base_Datos implements WithSimplePersistenceUnit {
    public static void main(String[] args) {
        new Main_Base_Datos().start();
    }

    private void start() {

        Persona unaPersona = new Persona();
        NotificadorWPP notificador = new NotificadorWPP();
        NotificacionCuandoSucede cuandoSucede = new NotificacionCuandoSucede();
        Comunidad unaComunidad = new Comunidad();
        Servicio unServicio = new Servicio();
        Entidad unaEntidad = new Entidad();
        Incidente unIncidente = new Incidente();
        Establecimiento unEstablecimiento = new Establecimiento();
        OrganismoDeControl unOrganismo = new OrganismoDeControl();
        EntidadPrestadora unaEntidadPrestadora = new EntidadPrestadora();

        unaComunidad.setNombre("Grupo 3");

        unaPersona.setNombre("Juan");
        unaPersona.setApellido("Perez");
        unaPersona.setCorreo("juancito@gmail.com");
        unaPersona.setContrasenia("Juan1234");
        unaPersona.setAfectado(Boolean.TRUE);

        unServicio.setNombre("Jardineria");
        unServicio.setHabilitado(Boolean.TRUE);
        unServicio.setDescripcion("Cortar Pasto");

        unIncidente.setDescripcion("Se rompio la escalera");
        unIncidente.setAbierto(Boolean.TRUE);

        unaEntidadPrestadora.setDireccion("Thames 1550");
        unaEntidadPrestadora.setNombre("Trenes Argentinos");

        unaEntidad.setNombre("Linea Mitre");

        unOrganismo.setNombre("CNRT");
        unOrganismo.setDireccion("Brandsen 805");

        unEstablecimiento.setNombre("Retiro");

        unaPersona.setListComunidades(Collections.singletonList(unaComunidad));
        unaPersona.setHorariosDePreferencia(Collections.singletonList("0 32 20 ? * *"));
        unaPersona.setHorariosDePreferenciaCierre(Collections.singletonList("0 33 20 ? * *"));
        unaPersona.setListServiciosDeInteres(Collections.singletonList(unServicio));
        unaPersona.setListEntidadesDeInteres(Collections.singletonList(unaEntidad));

        unaComunidad.setMiembros(Collections.singletonList(unaPersona));
        unaComunidad.setListaDeIncidentes(Collections.singletonList(unIncidente));

        unServicio.setEstablecimiento(unEstablecimiento);
        unServicio.setLista_interesados(Collections.singletonList(unaPersona));

        unIncidente.setEstablecimiento(unEstablecimiento);
        unIncidente.setPersona(unaPersona);

        unaEntidad.setLista_interesados(Collections.singletonList(unaPersona));
        unaEntidad.setSucursales(Collections.singletonList(unEstablecimiento));

        unEstablecimiento.setListaServicios(Collections.singletonList(unServicio));
        unEstablecimiento.setLista_incidentes(Collections.singletonList(unIncidente));

        unaEntidadPrestadora.setRepresentante(unaPersona);
        unaEntidadPrestadora.setEntidades(Collections.singletonList(unaEntidad));

        unOrganismo.setRepresentante(unaPersona);
        unOrganismo.setLista_entidades_prestadoras(Collections.singletonList(unaEntidadPrestadora));

        unIncidente.setServicio(unServicio);
        unIncidente.setFechaInicio(new java.util.Date(122,11,18));
        unIncidente.setFechaCierre(new java.util.Date(123,11,18));// Año 122 corresponde a 2022, el mes se indexa desde 0 (0 = enero, 11 = diciembre)

        unaPersona.setMedioDeRecibirNotificacion(notificador);
        unaPersona.setFormaDeRecibirNotificacion(cuandoSucede);

        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();

        entityManager().persist(unServicio); //INSERT INTO ....
        entityManager().persist(unaEntidad);
        entityManager().persist(unaComunidad);
        entityManager().persist(unIncidente);
        entityManager().persist(unaEntidadPrestadora);
        entityManager().persist(unOrganismo);
        entityManager().persist(unEstablecimiento);
        entityManager().persist(unaPersona);
        tx.commit();
    }

}