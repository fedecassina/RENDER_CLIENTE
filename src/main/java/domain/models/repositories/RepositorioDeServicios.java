package domain.models.repositories;

import domain.models.entities.incidentes.Incidente;
import domain.models.entities.servicio.Servicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class RepositorioDeServicios  implements WithSimplePersistenceUnit, ICrudRepository{
    @Override
    public List buscarTodos() {
        return entityManager().createQuery("from " + Servicio.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id) {
        return entityManager().find(Servicio.class, id);
    }

    public Servicio buscarPorNombre(String nombre) {
        TypedQuery<Servicio> query = entityManager().createQuery("SELECT s FROM Servicio s WHERE s.nombre = :nombre", Servicio.class);
        query.setParameter("nombre", nombre);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Devuelve null si no se encuentra ninguna entidad con el nombre dado
        }
    }

    public List buscarServiciosDeInteres(String id) {
        String query = "SELECT SP FROM SERVICIOS_PERSONAS SP INNER JOIN SERVICIOS S ON SP.SERVICIOS.ID = S.ID WHERE S.ID=" + id;
        return entityManager().createQuery(query).getResultList();

    }



    @Override
    public void guardar(Object o) {
        EntityTransaction tx = entityManager().getTransaction();
        if(!tx.isActive())
            tx.begin();

        entityManager().persist(o);
        tx.commit();
    }

    @Override
    public void actualizar(Object o) {
        EntityTransaction tx = entityManager().getTransaction();
        if(!tx.isActive())
            tx.begin();

        entityManager().merge(o);
        tx.commit();
    }

    @Override
    public void eliminar(Object o) {
        EntityTransaction tx = entityManager().getTransaction();
        if(!tx.isActive())
            tx.begin();

        entityManager().remove(o);
        tx.commit();
    }

}
