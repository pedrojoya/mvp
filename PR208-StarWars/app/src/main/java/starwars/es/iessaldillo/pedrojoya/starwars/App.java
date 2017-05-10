package starwars.es.iessaldillo.pedrojoya.starwars;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.readystatesoftware.chuck.ChuckInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import starwars.es.iessaldillo.pedrojoya.starwars.api.StarWarsApi;

public class App extends Application {

    private StarWarsApi clienteAPI;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        buildStarWarsApiClient();
    }

    private void buildStarWarsApiClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new ChuckInterceptor(getApplicationContext()))
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://swapi.co/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        clienteAPI = retrofit.create(StarWarsApi.class);
    }

    public StarWarsApi getStarWarsApiClient() {
        return clienteAPI;
    }
}
