package domain.models.entities.lectorCSV;

import domain.models.entities.servicio.Entidad;
import domain.models.entities.usuario.Persona;
import domain.models.repositories.RepositorioDeUsuarios;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVParserEntidad {

    public static List<EntidadPrestadora> parseCSV(InputStream inputStream) {
        List<EntidadPrestadora> entidades = new ArrayList<>();
        RepositorioDeUsuarios repositorioDeUsuarios = new RepositorioDeUsuarios();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 3) { // Ahora tienes tres campos en cada línea
                    String nombre = fields[0].trim();
                    String direccion = fields[1].trim();
                    String nombre_usuario = fields[2].trim();

                    Persona representante = repositorioDeUsuarios.buscarPorNombre(nombre_usuario);

                    EntidadPrestadora entidad = new EntidadPrestadora(nombre, direccion);
                    entidad.setRepresentante(representante);

                    entidades.add(entidad);
                } else {
                    // Puedes manejar de alguna manera los registros CSV incorrectos o incompletos
                    // Por ejemplo, registrando un mensaje de error o ignorándolos.
                    // Aquí podrías agregar una lógica de manejo de errores.
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Maneja cualquier excepción que pueda ocurrir durante la lectura o procesamiento
        }

        return entidades;
    }
}


