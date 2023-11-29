package domain.models.repositories;

import domain.models.entities.incidentes.Incidente;
import domain.models.entities.servicio.Establecimiento;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.usuario.Persona;
import domain.server.Server;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class RepositorioDeUsuarios implements WithSimplePersistenceUnit, ICrudRepository {

    EntityManager entityManager = Server.createEntityManager();

    @Override
    public List buscarTodos() {
        return entityManager.createQuery("from " + Persona.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id) {
        return entityManager.find(Persona.class, id);
    }

    public String buscarContrasenia(String correo) {
        TypedQuery<Persona> query = entityManager.createQuery("SELECT p FROM Persona p WHERE p.correo = :correo", Persona.class);
        query.setParameter("correo", correo);

        try {
            Persona usuarioActual = query.getSingleResult();
            return usuarioActual.getContrasenia();
        } catch (NoResultException e) {
            return null; // Devuelve null si no se encuentra ninguna entidad con el nombre dado
        }
    }

    public Long buscarId(String correo) {
        TypedQuery<Persona> query = entityManager.createQuery("SELECT p FROM Persona p WHERE p.correo = :correo", Persona.class);
        query.setParameter("correo", correo);

        try {
            Persona usuarioActual = query.getSingleResult();
            return usuarioActual.getId();
        } catch (NoResultException e) {
            return null; // Devuelve null si no se encuentra ninguna entidad con el nombre dado
        }
    }

    public Persona buscarPorNombre(String nombre) {
        TypedQuery<Persona> query = entityManager.createQuery("SELECT s FROM Persona s WHERE s.nombreDeUsuario = :nombreDeUsuario", Persona.class);
        query.setParameter("nombreDeUsuario", nombre);

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
