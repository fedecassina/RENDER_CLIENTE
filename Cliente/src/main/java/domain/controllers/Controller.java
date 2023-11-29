package domain.controllers;

import domain.models.entities.usuario.Persona;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;


public abstract class Controller implements WithSimplePersistenceUnit {

    protected Persona usuarioLogueado(Context ctx) {
        if(ctx.sessionAttribute("usuario_id") == null)
            return null;
        return entityManager()
                .find(Persona.class, ctx.sessionAttribute("usuario_id"));
    }
}
