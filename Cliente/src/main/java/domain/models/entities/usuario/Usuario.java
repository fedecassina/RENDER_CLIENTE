package domain.models.entities.usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usuario {

    private String nombreUsuario;
    private String contrasenia;

    public Usuario(String nombreUsuario, String contrasenia) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
    }
}
