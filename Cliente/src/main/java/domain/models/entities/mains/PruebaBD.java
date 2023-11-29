package domain.models.entities.mains;

import domain.models.entities.servicio.Servicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityTransaction;

public class PruebaBD implements WithSimplePersistenceUnit {
    public static void main(String[] args) {
        new PruebaBD().start();
    }

    private void start() {
        Servicio unServicio = new Servicio();
        unServicio.setNombre("Jardineria");

        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().persist(unServicio); //INSERT INTO ....
        tx.commit();
    }
/*
    private void recuperamosTodosLosServicios() {
        List<Servicio> servicios = entityManager().createQuery("from " + Servicio.class.getName()).getResultList();
        System.out.println(servicios);
    }


    private void recuperarJardineriaPorNombre() {
        Servicio unServicio = (Servicio) entityManager()
                .createQuery("from " + Servicio.class.getName() + " where nombre = :nombre")
                .setParameter("nombre", "Jardinería")
                .getSingleResult();


        System.out.println(unServicio);
    }

    private void recuperarJardineriaPorId() {
        Servicio jardineria = entityManager().find(Servicio.class, 1);
        System.out.println(jardineria);
    }
*/
}