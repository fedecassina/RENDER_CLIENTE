package domain.models.entities.clienteAPI;
import domain.models.entities.JSONconverter.ComunidadJsonConverter;
import domain.models.entities.usuario.Comunidad;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClienteRestFusion {
    /*private static void enviarSolicitudAnalizarFusion(String url, List<Comunidad> comunidades, ComunidadJsonConverter converter) {
        try {
            // Crear el cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Crear el cuerpo de la solicitud
            SugerenciaRequest sugerenciaRequest = new SugerenciaRequest();
            sugerenciaRequest.setComunidades(comunidades);

            // Convertir el objeto a JSON usando el converter
            String jsonBody = converter.convertirAJson(sugerenciaRequest);

            // Crear la solicitud HTTP POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            // Enviar la solicitud y obtener la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Imprimir la respuesta
            System.out.println("Respuesta de analizar fusiones:");
            System.out.println(response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public String enviarSolicitudFusionarComunidades(String url, Comunidad comunidad1, Comunidad comunidad2, ComunidadJsonConverter converter) {
        try {
            // Crear el cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Crear el cuerpo de la solicitud
            FusionRequest fusionRequest = new FusionRequest();
            fusionRequest.setComunidad1(comunidad1);
            fusionRequest.setComunidad2(comunidad2);

            // Convertir el objeto a JSON usando el converter
            String jsonBody = converter.convertirAJson(fusionRequest);

            // Crear la solicitud HTTP POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            // Enviar la solicitud y obtener la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Imprimir la respuesta
            System.out.println("Respuesta de fusionar comunidades:");
            System.out.println(response.body());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
