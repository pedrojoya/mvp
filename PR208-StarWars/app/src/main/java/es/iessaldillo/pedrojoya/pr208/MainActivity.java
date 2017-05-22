package es.iessaldillo.pedrojoya.pr208;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import es.iessaldillo.pedrojoya.pr208.api.StarWarsApi;
import es.iessaldillo.pedrojoya.pr208.api.models.Respuesta;
import es.iessaldillo.pedrojoya.pr208.api.models.Result;
import es.iessaldillo.pedrojoya.pr208.models.Personaje;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Button btnConsultar;
    private EditText txtConsulta;
    private StarWarsApi mClienteAPI;
    private TextView lblResultado;
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCompositeDisposable = new CompositeDisposable();
        btnConsultar = (Button) findViewById(R.id.btnConsultar);
        btnConsultar.setOnClickListener(view -> consultar());
        txtConsulta = (EditText) findViewById(R.id.txtConsulta);
        lblResultado = (TextView) findViewById(R.id.lblResultado);
        // Obtene el cliente de ataque a la API.
        mClienteAPI = ((App) getApplication()).getStarWarsApiClient();
    }

    private void consultar() {
        lblResultado.setText("");
/*
        Disposable disposable = mClienteAPI.buscarPersona(txtConsulta.getText().toString())
                .subscribeOn(Schedulers.io())
                .flatMap(respuesta -> Observable.fromIterable(respuesta.getResults()))
                .map(result -> result.getName())
                .reduce((name1, name2) -> name1 + ", " + name2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cadena -> lblResultado.setText(cadena), this::mostrarError);
        mCompositeDisposable.add(disposable);
*/
        Disposable disposable = mClienteAPI.buscarPersona(txtConsulta.getText().toString())
                .subscribeOn(Schedulers.io())
                .concatMap(respuesta -> Observable.fromIterable(respuesta.getResults()))
                .concatMap(result -> mClienteAPI.getPlaneta(
                        Uri.parse(result.getHomeworld()).getLastPathSegment())
                        .map(planeta -> new Personaje(result.getName(), planeta.getName())))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(personaje -> lblResultado.append(
                        personaje.getNombre() + " de " + personaje.getPlaneta() + "\n"),
                        this::mostrarError);
        mCompositeDisposable.add(disposable);

    }

    private void mostrarPersonaje(String name) {
        lblResultado.setText(name);
    }

    private void mostrarRespuesta(Respuesta respuesta) {
        if (respuesta.getResults() != null && respuesta.getResults().size() > 0) {
            Result primerResultado = respuesta.getResults().get(0);
            lblResultado.setText(primerResultado.getName());
        }
    }

    private void mostrarError(Throwable error) {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    // TODO Ver https://newfivefour.com/android-rxjava-wait-for-network-calls-finish.html

}
