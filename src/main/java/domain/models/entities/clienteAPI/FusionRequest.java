package domain.models.entities.clienteAPI;

import domain.models.entities.usuario.Comunidad;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FusionRequest {

        /*{
                "comunidad1": {
                "id": 1,
                        "establecimientos": [1, 2],
                "servicios": [1,2],
                "usuarios": [1, 2],
                "incidentes": [1, 2],
                "gradoConfianza": 0.95
                },
                "comunidad2": {
                "id": 2,
                        "establecimientos": [3, 4],
                "servicios": [3, 4],
                "usuarios": [3, 4],
                "incidentes": [3, 4],
                "gradoConfianza": 0.95
                }
        }*/


    private Comunidad comunidad1;
    private Comunidad comunidad2;
}
