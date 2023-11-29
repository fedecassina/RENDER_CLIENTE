package domain.models.entities.roles;

import io.javalin.security.RouteRole;

public enum TipoRol implements RouteRole {
    ADMINISTRADOR,
    NORMAL,
}
