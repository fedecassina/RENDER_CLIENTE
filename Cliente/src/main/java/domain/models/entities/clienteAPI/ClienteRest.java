package domain.models.entities.clienteAPI;

import domain.models.entities.servicio.Entidad;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class ClienteRest {

    public double obtenerNumeroRanking (Entidad entidad) throws IOException {
        // Configurar la URL del servidor y el puerto donde se ejecuta tu API
        String baseUrl = "http://localhost:8081"; // Reemplaza "puerto" con el puerto real de tu API

        // Crear una instancia de RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Ejecutar una solicitud POST
        String requestBody = "Cuerpo de la solicitud POST";
        requestBody = entidad.incidentesEntidad();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> postResponseEntity = restTemplate.exchange(
                baseUrl + "/api/endpoint",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // Verificar si la solicitud POST fue exitosa
        if (postResponseEntity.getStatusCode() == HttpStatus.OK) {
            String postResponseBody = postResponseEntity.getBody();
            System.out.println("Respuesta del servidor (POST): " + postResponseBody);
        } else {
            System.err.println("Error en la solicitud POST. Código de estado: " + postResponseEntity.getStatusCode());
        }
        // Ejecutar una solicitud GET
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                baseUrl + "/api/endpoint",
                HttpMethod.GET,
                null,
                String.class
        );

        // Verificar si la solicitud fue exitosa
        double numeroRanking = 0;
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            double responseBody = Double.parseDouble(responseEntity.getBody());
            numeroRanking = responseBody;
        } else {
            System.err.println("Error en la solicitud GET. Código de estado: " + responseEntity.getStatusCode());
        }

        return numeroRanking;
    }

    public static void main(String[] args) {
        // Configurar la URL del servidor y el puerto donde se ejecuta tu API
        String baseUrl = "http://localhost:8080"; // Reemplaza "puerto" con el puerto real de tu API

        // Crear una instancia de RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Ejecutar una solicitud POST
        String requestBody = "Cuerpo de la solicitud POST";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> postResponseEntity = restTemplate.exchange(
                baseUrl + "/api/endpoint",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // Verificar si la solicitud POST fue exitosa
        if (postResponseEntity.getStatusCode() == HttpStatus.OK) {
            String postResponseBody = postResponseEntity.getBody();
            System.out.println("Respuesta del servidor (POST): " + postResponseBody);
        } else {
            System.err.println("Error en la solicitud POST. Código de estado: " + postResponseEntity.getStatusCode());
        }
        // Ejecutar una solicitud GET
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                baseUrl + "/api/endpoint",
                HttpMethod.GET,
                null,
                String.class
        );

        // Verificar si la solicitud fue exitosa
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            double responseBody = Double.parseDouble(responseEntity.getBody());
        } else {
            System.err.println("Error en la solicitud GET. Código de estado: " + responseEntity.getStatusCode());
        }

    }
}
