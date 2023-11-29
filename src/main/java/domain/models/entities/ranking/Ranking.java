package domain.models.entities.ranking;

import domain.models.entities.lectorCSV.EntidadPrestadora;
import domain.models.entities.servicio.Entidad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ranking {

    public String mayorPromedioDeCierres(EntidadPrestadora entidadPrestadora) {
        List<Entidad> entidades = new ArrayList<>(entidadPrestadora.getEntidades()); // Create a mutable copy

        Collections.sort(entidades, (entidad1, entidad2) ->
                Float.compare(entidad1.promedioDeCierre(), entidad2.promedioDeCierre()));

        return imprimirRanking(entidades);
    }


    public String mayorCantidadDeIncidentes(EntidadPrestadora entidadPrestadora) {
        List<Entidad> entidades = new ArrayList<>(entidadPrestadora.getEntidades()); // Crear una copia mutable

        Collections.sort(entidades, (entidad1, entidad2) ->
                Integer.compare(entidad1.incidentesSemanales(), entidad2.incidentesSemanales()));

        return imprimirRanking(entidades);
    }



    public String mayorImpactoDeLasProblematicas(EntidadPrestadora entidadPrestadora) {
        List<Entidad> entidades = new ArrayList<>(entidadPrestadora.getEntidades()); // Crear una copia mutable

        Collections.sort(entidades, (entidad1, entidad2) -> {
            try {
                // Invertir el orden de comparación para ordenar de mayor a menor
                return Double.compare(entidad2.obtenerNumeroRanking(), entidad1.obtenerNumeroRanking());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return imprimirRanking(entidades);
    }

    public String imprimirRanking(List<Entidad> entidades) {
        StringBuilder sb = new StringBuilder();
        int contador = 1;

        for (Entidad entidad : entidades) {
            sb.append(contador).append(". ").append(entidad.getNombre()).append(System.lineSeparator());
            contador++;
        }

        return sb.toString();
    }

}
