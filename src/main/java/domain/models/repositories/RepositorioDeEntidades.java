package domain.models.repositories;

import domain.models.entities.incidentes.Incidente;
import domain.models.entities.servicio.Entidad;
import domain.server.Server;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import domain.models.entities.servicio.Establecimiento;
import domain.models.entities.servicio.Servicio;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class RepositorioDeEntidades implements WithSimplePersistenceUnit, ICrudRepository{
    EntityManager entityManager = Server.createEntityManager();
    @Override
    public List buscarTodos() {
        return entityManager.createQuery("from " + Entidad.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id) {
        return entityManager.find(Entidad.class, id);
    }

    public Establecimiento buscarPorNombre(String nombre) {
        TypedQuery<Establecimiento> query = entityManager.createQuery("SELECT e FROM Establecimiento e WHERE e.nombre = :nombre", Establecimiento.class);
        query.setParameter("nombre", nombre);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Devuelve null si no se encuentra ninguna entidad con el nombre dado
        }
    }

    public Entidad buscarEntidadPorNombre(String nombre) {
        TypedQuery<Entidad> query = entityManager.createQuery("SELECT e FROM Entidad e WHERE e.nombre = :nombre", Entidad.class);
        query.setParameter("nombre", nombre);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Devuelve null si no se encuentra ninguna entidad con el nombre dado
        }
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
