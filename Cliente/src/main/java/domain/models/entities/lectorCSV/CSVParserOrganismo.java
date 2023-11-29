package domain.models.entities.lectorCSV;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVParserOrganismo {

    public static List<OrganismoDeControl> parseCSV(InputStream inputStream) {
        List<OrganismoDeControl> organismos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 2) {
                    String nombre = fields[0].trim();
                    String direccion = fields[1].trim();
                    OrganismoDeControl organismo = new OrganismoDeControl(nombre, direccion);
                    organismos.add(organismo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Maneja cualquier excepción que pueda ocurrir durante la lectura o procesamiento
        }

        return organismos;
    }
}

