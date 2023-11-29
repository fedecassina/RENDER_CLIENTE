package domain;

import domain.models.entities.lectorCSV.EntidadPrestadora;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CargaCSV_Test {
    String csvEntidadesPrestadoras;
    List<EntidadPrestadora> entidadesPrestadoras;

    @BeforeEach
    public void init() throws IOException {
        csvEntidadesPrestadoras = "src/main/java/domain/lectorCSV/EntidadesPrestadoras.csv";
        entidadesPrestadoras = new ArrayList<>();

        try (Reader reader = new FileReader(csvEntidadesPrestadoras);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            for (CSVRecord csvRecord : csvParser) {
                String nombre = csvRecord.get(0);
                String direccion = csvRecord.get(1);

                // Crear una instancia de EntidadPrestadora
                EntidadPrestadora entidad = new EntidadPrestadora(nombre, direccion);
                entidadesPrestadoras.add(entidad);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void seCargoLaEntidadMaxiconsumo () {
        EntidadPrestadora maxiconsumo = new EntidadPrestadora("Maxiconsumo", "San Martin 29");
        Assertions.assertTrue(entidadesPrestadoras.stream().anyMatch(entidad1 -> entidad1.getNombre().equals("Maxiconsumo") && entidad1.getDireccion().equals("San Martin 29")));
    }

}
