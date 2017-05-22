package es.iessaldillo.pedrojoya.pr208.api;

import es.iessaldillo.pedrojoya.pr208.api.models.World;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import es.iessaldillo.pedrojoya.pr208.api.models.Respuesta;

public interface StarWarsApi {

    @GET("people")
    Observable<Respuesta> buscarPersona(@Query("search") String texto);

    @GET("planets/{idPlaneta}")
    Observable<World> getPlaneta(@Path("idPlaneta") String idPlaneta);

}
