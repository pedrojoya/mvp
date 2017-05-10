package starwars.es.iessaldillo.pedrojoya.starwars;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import starwars.es.iessaldillo.pedrojoya.starwars.api.StarWarsApi;
import starwars.es.iessaldillo.pedrojoya.starwars.api.models.Respuesta;
import starwars.es.iessaldillo.pedrojoya.starwars.api.models.Result;

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
        Disposable disposable = mClienteAPI.buscarPersona(txtConsulta.getText().toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::mostrarRespuesta, this::mostrarError);
        mCompositeDisposable.add(disposable);
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

}
