package domain.models.repositories;

import domain.models.entities.roles.Rol;
import domain.models.entities.usuario.Persona;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class RepositorioDeRoles implements WithSimplePersistenceUnit, ICrudRepository {
    @Override
    public List buscarTodos() {
        return null;
    }

    @Override
    public Object buscar(Long id) {
        return entityManager().find(Rol.class, id);
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
