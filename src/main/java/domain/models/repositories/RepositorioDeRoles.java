package domain.models.repositories;

import domain.models.entities.roles.Rol;
import domain.models.entities.usuario.Persona;
import domain.server.Server;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import java.util.List;

public class RepositorioDeRoles implements WithSimplePersistenceUnit, ICrudRepository {

    EntityManager entityManager = Server.createEntityManager();

    @Override
    public List buscarTodos() {
        return null;
    }

    @Override
    public Object buscar(Long id) {
        return entityManager.find(Rol.class, id);
    }
    public Object buscarPorNombre(String nombre) {
        return entityManager.find(Rol.class, nombre);
    }

    @Override
    public void guardar(Object o) {

    }

    @Override
    public void actualizar(Object o) {

    }

    @Override
    public void eliminar(Object o) {

    }
}
