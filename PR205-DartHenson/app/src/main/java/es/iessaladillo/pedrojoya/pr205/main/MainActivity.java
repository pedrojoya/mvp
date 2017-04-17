package es.iessaladillo.pedrojoya.pr205.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.f2prateek.dart.Dart;

import es.iessaladillo.pedrojoya.pr205.Henson;
import es.iessaladillo.pedrojoya.pr205.R;
import es.iessaladillo.pedrojoya.pr205.components.MessageManager.MessageManager;
import es.iessaladillo.pedrojoya.pr205.components.MessageManager.ToastMessageManager;
import es.iessaladillo.pedrojoya.pr205.model.Alumno;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private static final int RC_ALUMNO = 1;
    private static final String STATE_NOMBRE = "STATE_NOMBRE";
    private static final String STATE_EDAD = "STATE_EDAD";

    private TextView lblDatos;
    @SuppressWarnings("FieldCanBeLocal")
    private Button btnSolicitar;

    private MainContract.Presenter mPresenter;
    private MessageManager mMessageManager;
    @SuppressWarnings("WeakerAccess")
    Alumno mAlumno;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(this);
        mMessageManager = new ToastMessageManager();
        restoreInstance(savedInstanceState);
        initVistas();
    }

    private void restoreInstance(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mAlumno = new Alumno(savedInstanceState.getString(STATE_NOMBRE),
                    savedInstanceState.getInt(STATE_EDAD));
        } else {
            mAlumno = new Alumno("", Alumno.DEFAULT_EDAD);
        }
    }

    private void initVistas() {
        btnSolicitar = (Button) this.findViewById(R.id.btnSolicitar);
        lblDatos = (TextView) this.findViewById(R.id.lblDatos);
        if (TextUtils.isEmpty(mAlumno.getNombre())) {
            lblDatos.setText(R.string.no_disponibles);
        }
        btnSolicitar.setOnClickListener(v -> mPresenter.doSolicitarDatos(mAlumno));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_NOMBRE, mAlumno.getNombre());
        outState.putInt(STATE_EDAD, mAlumno.getEdad());
    }

    // Cuando llega una respuesta.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Si el resultado es satisfactorio y el código de petición el adecuado.
        if (requestCode == RC_ALUMNO && resultCode == RESULT_OK) {
            // Se obtienen los extros del intent retornado y se inyectan en los campos del alumno.
            Dart.inject(mAlumno, data.getExtras());
            mPresenter.onSolicitarDatosResult(mAlumno);
        }
    }

    @Override
    public void navigateToSolicitar(Alumno alumno) {
        Intent intent = Henson.with(this)
                .gotoEditAlumnoActivity()
                .edad(alumno.getEdad())
                .nombre(alumno.getNombre())
                .build();
        startActivityForResult(intent, RC_ALUMNO);
    }

    @Override
    public void showDatos(Alumno alumno) {
        mAlumno = alumno;
        lblDatos.setText(getString(R.string.datos, alumno.getNombre(), alumno.getEdad()));
    }

    @Override
    public void showMensajeExito() {
        mMessageManager.showMessage(btnSolicitar, getString(R.string.exito_al_solicitar_datos));
    }

}
