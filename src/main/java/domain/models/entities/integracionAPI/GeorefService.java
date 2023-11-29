package domain.models.entities.integracionAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import domain.models.entities.integracionAPI.datos.ListadoDeProvincias;
import domain.models.entities.integracionAPI.datos.ListadoDeMunicipios;

public interface GeorefService {

    @GET("provincias")
    Call<ListadoDeProvincias> provincias();

    @GET("municipios")
    Call<ListadoDeMunicipios> municipios(@Query("provincia") int idProvincia);

    @GET("municipios")
    Call<ListadoDeMunicipios> municipios(@Query("provincia") int idProvincia, @Query("campos") String campos);

    @GET("municipios")
    Call<ListadoDeMunicipios> municipios(@Query("provincia") int idProvincia, @Query("campos") String campos, @Query("max") int max);

}
