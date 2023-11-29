package domain.models.repositories;

import domain.models.entities.incidentes.Incidente;
import domain.server.Server;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioDeCargaOrganismos implements WithSimplePersistenceUnit, ICrudRepository{

    EntityManager entityManager = Server.createEntityManager();

    @Override
    public List buscarTodos() {
        return entityManager.createQuery("from " + Incidente.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id) {
        return entityManager.find(Incidente.class, id);
    }

    @Override
    public void guardar(Object o) {
        EntityTransaction tx = entityManager.getTransaction();
        if(!tx.isActive())
            tx.begin();

        entityManager.persist(o);
        tx.commit();
    }

    @Override
    public void actualizar(Object o) {
        EntityTransaction tx = entityManager.getTransaction();
        if(!tx.isActive())
            tx.begin();

        entityManager.merge(o);
        tx.commit();
    }

    @Override
    public void eliminar(Object o) {
        EntityTransaction tx = entityManager.getTransaction();
        if(!tx.isActive())
            tx.begin();

        entityManager.remove(o);
        tx.commit();
    }

}
