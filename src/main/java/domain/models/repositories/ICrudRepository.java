package domain.models.repositories;

import java.util.List;

public interface ICrudRepository {
    List buscarTodos();
    Object buscar(Long id);
    void guardar(Object o);
    void actualizar(Object o);
    void eliminar(Object o);
}