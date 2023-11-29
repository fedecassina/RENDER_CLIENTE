package domain.models.repositories;

import domain.models.entities.incidentes.Incidente;
import domain.models.entities.lectorCSV.EntidadPrestadora;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.usuario.Persona;
import domain.server.Server;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class RepositorioDeCargaEntidades implements WithSimplePersistenceUnit, ICrudRepository{

    EntityManager entityManager = Server.createEntityManager();
    @Override
    public List buscarTodos() {
        return entityManager.createQuery("from " + Incidente.class.getName()).getResultList();
    }

    public EntidadPrestadora buscarPorUsuario(Persona persona) {

        Long idPersona = persona.getId();

        TypedQuery<EntidadPrestadora> query = entityManager.createQuery("SELECT e FROM EntidadPrestadora e WHERE e.representante.id = :id", EntidadPrestadora.class);
        query.setParameter("id", idPersona);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Devuelve null si no se encuentra ninguna entidad con el nombre dado
        }
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
