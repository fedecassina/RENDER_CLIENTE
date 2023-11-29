package domain.models.entities.mains;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.models.entities.clienteAPI.ApiResponse;
import domain.models.entities.clienteAPI.HidratadorDeComunidad;
import domain.models.entities.clienteAPI.Respuesta;
import domain.models.entities.incidentes.Incidente;
import domain.models.entities.JSONconverter.ComunidadJsonConverter;
import domain.models.entities.clienteAPI.ClienteRestFusion;
import domain.models.entities.servicio.Establecimiento;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.usuario.Comunidad;
import domain.models.entities.usuario.Persona;
import domain.models.repositories.RepositorioDeComunidades;
import domain.models.repositories.RepositorioDeEstablecimientos;
import domain.models.repositories.RepositorioDeServicios;

public class MainPruebaIntegracion {
    public static void main(String[] args) throws JsonProcessingException {

        RepositorioDeEstablecimientos repositorioDeEstablecimientos = new RepositorioDeEstablecimientos();
        RepositorioDeServicios repositorioDeServicios = new RepositorioDeServicios();
        RepositorioDeComunidades repositorioDeComunidades = new RepositorioDeComunidades();
        Comunidad comunidad1 = (Comunidad) repositorioDeComunidades.buscar(15L);
        Comunidad comunidad2 = (Comunidad) repositorioDeComunidades.buscar(16L);

        comunidad1.agregarServicio((Servicio) repositorioDeServicios.buscar(21L));
        comunidad2.agregarServicio((Servicio) repositorioDeServicios.buscar(21L));

        comunidad1.agregarEstablecimiento((Establecimiento) repositorioDeEstablecimientos.buscar(20L));
        comunidad2.agregarEstablecimiento((Establecimiento) repositorioDeEstablecimientos.buscar(20L));

        // Crear una instancia de ComunidadJsonConverter
        ComunidadJsonConverter converter = new ComunidadJsonConverter();

        ClienteRestFusion cliente = new ClienteRestFusion();

        String json = cliente.enviarSolicitudFusionarComunidades("http://localhost:8082/api/fusionar-comunidades",comunidad1, comunidad2, converter);
        ObjectMapper objectMapper = new ObjectMapper();
        ApiResponse<Respuesta> apiResponse = objectMapper.readValue(json, new TypeReference<ApiResponse<Respuesta>>() {});
        Respuesta respuesta = apiResponse.getResultado();

        HidratadorDeComunidad hidratadorDeComunidad = new HidratadorDeComunidad();
        Comunidad comunidadHidratada = hidratadorDeComunidad.hidratarComunidad(respuesta, comunidad1);

        System.out.println("Establecimientos: ");
        comunidadHidratada.getEstablecimientos().stream().map(Establecimiento::getNombre).forEach(System.out::println);

        System.out.println("Servicios: ");
        comunidadHidratada.getServicios().stream().map(Servicio::getNombre).forEach(System.out::println);

        System.out.println("Incidentes: ");
        comunidadHidratada.getListaDeIncidentes().stream().map(Incidente::getDescripcion).forEach(System.out::println);

    }


}

