package domain.models.entities.mains;
import domain.models.entities.lectorCSV.EntidadPrestadora;
import domain.models.entities.lectorCSV.OrganismoDeControl;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class Main_Carga_Masiva_CSV {
    public static void main(String[] args) {
        // Ruta del archivo CSV
        String csvEntidadesPrestadoras = "src/main/java/domain/lectorCSV/EntidadesPrestadoras.csv";
        String csvOrganismoDeControl = "src/main/java/domain/lectorCSV/OrganismosDeControl.csv";

        // Lista para almacenar las entidades prestadoras
        List<EntidadPrestadora> entidadesPrestadoras = new ArrayList<>();
        List<OrganismoDeControl> organismosDeControl = new ArrayList<>();

        try (Reader reader = new FileReader(csvEntidadesPrestadoras);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            for (CSVRecord csvRecord : csvParser) {
                String nombre = csvRecord.get(0);
                String direccion = csvRecord.get(1);

                // Crear una instancia de EntidadPrestadora
                EntidadPrestadora entidad = new EntidadPrestadora(nombre, direccion);
                entidadesPrestadoras.add(entidad);
            }
            System.out.println("ENTIDADES PRESTADORAS: ");
            // Realizar operaciones con las entidades prestadoras
            for (EntidadPrestadora entidad : entidadesPrestadoras) {
                System.out.println("--------------------------------------");
                System.out.println("Nombre: " + entidad.getNombre());
                System.out.println("Dirección: " + entidad.getDireccion());
                System.out.println("--------------------------------------");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Reader reader = new FileReader(csvOrganismoDeControl);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            for (CSVRecord csvRecord : csvParser) {
                String nombre = csvRecord.get(0);
                String direccion = csvRecord.get(1);

                // Crear una instancia de Organismo de Control
                OrganismoDeControl entidad = new OrganismoDeControl(nombre, direccion);
                organismosDeControl.add(entidad);
            }
            System.out.println("ORGANISMOS DE CONTROL: ");
            // Realizar operaciones con las entidades prestadoras
            for (OrganismoDeControl entidad : organismosDeControl) {
                System.out.println("--------------------------------------");
                System.out.println("Nombre: " + entidad.getNombre());
                System.out.println("Dirección: " + entidad.getDireccion());
                System.out.println("--------------------------------------");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
