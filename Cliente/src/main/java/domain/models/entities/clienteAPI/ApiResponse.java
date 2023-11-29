package domain.models.entities.clienteAPI;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApiResponse<T> {
    private int codigoDeEstado;
    private String error;
    private boolean exito;
    private T resultado;
}
