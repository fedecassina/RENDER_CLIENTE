package domain.models.entities.clienteAPI;

import lombok.Getter;
import lombok.Setter;

public class Resultado {
    @Getter
    @Setter
    public class ApiResponse<T> {
        private int codigoDeEstado;
        private String error;
        private boolean exito;
        private T resultado;
    }
}
