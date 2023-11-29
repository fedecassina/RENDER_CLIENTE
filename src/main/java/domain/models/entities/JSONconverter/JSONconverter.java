package domain.models.entities.JSONconverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import domain.models.entities.incidentes.Incidente;

import java.io.IOException;
import java.util.List;

public class JSONconverter {
    public static String toJson(List<Incidente> incidentes) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(incidentes);
    }

    public static List<Incidente> fromJson(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType type = objectMapper.getTypeFactory().constructCollectionType(List.class, Incidente.class);
        return objectMapper.readValue(json, type);
    }
}

