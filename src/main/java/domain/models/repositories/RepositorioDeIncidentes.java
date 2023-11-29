package domain.models.repositories;
import domain.models.entities.incidentes.Incidente;
import domain.models.entities.usuario.Comunidad;
import domain.server.Server;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
public class RepositorioDeIncidentes implements WithSimplePersistenceUnit, ICrudRepository{

    EntityManager entityManager = Server.createEntityManager();
    private RepositorioDeComunidades repositorioDeComunidades = new RepositorioDeComunidades();

    @Override
    public List buscarTodos() {
        return entityManager.createQuery("from " + Incidente.class.getName()).getResultList();
    }

    public List buscarPorComunidad(Comunidad comunidad) {

        /*Long comunidadId = comunidad.getId();

        Comunidad comunidad = repositorioDeComunidades.buscar(comunidadId);*/

        List<Incidente> incidentes = new ArrayList<>();

        /*String jpql = "SELECT c.listaDeIncidentes FROM " + Comunidad.class.getName() + " c WHERE c.id = :comunidadId";
        List<Incidente> incidentes = entityManager()
                .createQuery(jpql, Incidente.class)
                .setParameter("comunidadId", comunidadId) // Reemplaza comunidadId con el ID de la comunidad deseada
                .getResultList();*/

        return incidentes;
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
