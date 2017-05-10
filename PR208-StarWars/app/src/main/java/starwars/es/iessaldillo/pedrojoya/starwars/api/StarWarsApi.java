package starwars.es.iessaldillo.pedrojoya.starwars.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import starwars.es.iessaldillo.pedrojoya.starwars.api.models.Respuesta;

public interface StarWarsApi {

    @GET("people")
    Observable<Respuesta> buscarPersona(@Query("search") String texto);

    @GET("planets/{idPlaneta}")
    Observable<Respuesta> getPlaneta(@Path("idPlaneta") String idPlaneta);

}
