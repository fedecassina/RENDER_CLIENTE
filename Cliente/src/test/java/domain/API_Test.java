package domain;

import domain.models.entities.integracionAPI.ServicioGeoref;
import domain.models.entities.integracionAPI.datos.ListadoDeMunicipios;
import domain.models.entities.integracionAPI.datos.ListadoDeProvincias;
import domain.models.entities.integracionAPI.datos.Provincia;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

public class API_Test {
    private ServicioGeoref servicioGeoref;
    private ListadoDeProvincias listadoDeProvincias;
    private Provincia buenosAires;

    @BeforeEach
    public void init() throws IOException {
        servicioGeoref = ServicioGeoref.instancia();
        listadoDeProvincias = servicioGeoref.listadoDeProvincias();
        listadoDeProvincias.provincias.sort((p1, p2) -> p1.id >= p2.id? 1 : -1);
    }

    @Test
    public void estaBuenosAires () {
        Assertions.assertTrue(listadoDeProvincias.provincias.stream().anyMatch(provincia1 -> provincia1.nombre.equals("Buenos Aires")));
    }

    @Test
    public void estaMercedesEnBuenosAires () throws IOException {
        Optional<Provincia> posibleProvincia = listadoDeProvincias.provinciaDeId(6);
        buenosAires = posibleProvincia.get();
        ListadoDeMunicipios municipiosDeBuenosAires = servicioGeoref.listadoDeMunicipiosDeProvincia(buenosAires);
        Assertions.assertTrue(municipiosDeBuenosAires.municipios.stream().anyMatch(municipio1 -> municipio1.nombre.equals("Mercedes")));
    }

    @Test
    public void estanTodasLasProvinciasArgentinas () {
        Assertions.assertEquals(listadoDeProvincias.provincias.size(), 24);
    }

}


