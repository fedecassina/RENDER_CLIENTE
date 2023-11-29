package domain.models.entities.integracionAPI;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;
import domain.models.entities.integracionAPI.datos.ListadoDeProvincias;
import domain.models.entities.integracionAPI.datos.ListadoDeMunicipios;
import domain.models.entities.integracionAPI.datos.Provincia;

public class ServicioGeoref {
    private static ServicioGeoref instancia = null;
    private static int maximaCantidadRegistrosDefault = 200;
    private static final String urlApi = "https://apis.datos.gob.ar/georef/api/";
    private Retrofit retrofit;

    private ServicioGeoref() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlApi)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServicioGeoref instancia(){
        if(instancia== null){
            instancia = new ServicioGeoref();
        }
        return instancia;
    }

    public ListadoDeProvincias listadoDeProvincias() throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoDeProvincias> requestProvinciasArgentinas = georefService.provincias();
        Response<ListadoDeProvincias> responseProvinciasArgentinas = requestProvinciasArgentinas.execute();
        return responseProvinciasArgentinas.body();
    }

    public ListadoDeMunicipios listadoDeMunicipiosDeProvincia(Provincia provincia) throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoDeMunicipios> requestListadoDeMunicipios = georefService.municipios(provincia.id, "id, nombre", maximaCantidadRegistrosDefault);
        Response<ListadoDeMunicipios> responseListadoDeMunicipios = requestListadoDeMunicipios.execute();
        return responseListadoDeMunicipios.body();
    }

}
