package es.iessaladillo.pedrojoya.pr206.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import activitystarter.ActivityStarter;
import activitystarter.Arg;
import activitystarter.MakeActivityStarter;
import es.iessaladillo.pedrojoya.pr206.R;
import es.iessaladillo.pedrojoya.pr206.components.MessageManager.MessageManager;
import es.iessaladillo.pedrojoya.pr206.components.MessageManager.ToastMessageManager;
import es.iessaladillo.pedrojoya.pr206.editalumno.EditAlumnoActivity;
import es.iessaladillo.pedrojoya.pr206.editalumno.EditAlumnoActivityStarter;
import es.iessaladillo.pedrojoya.pr206.model.Alumno;

@MakeActivityStarter
public class MainActivity extends AppCompatActivity implements MainContract.View {

    private static final int RC_ALUMNO = 1;

    private TextView lblDatos;
    @SuppressWarnings("FieldCanBeLocal")
    private Button btnSolicitar;

    private MainContract.Presenter mPresenter;
    private MessageManager mMessageManager;

    @SuppressWarnings("WeakerAccess")
    @Arg
    Alumno mAlumno;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityStarter.fill(this, savedInstanceState);
        mPresenter = new MainPresenter(this);
        mMessageManager = new ToastMessageManager();
        if (mAlumno == null) {
            mAlumno = new Alumno("", Alumno.DEFAULT_EDAD);
        }
        initVistas();
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
        MainActivityStarter.save(this, outState);
    }

    // Cuando llega una respuesta.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Si el resultado es satisfactorio y el código de petición el adecuado.
        if (requestCode == RC_ALUMNO && resultCode == RESULT_OK) {
            // Se obtienen los extras del intent retornado y se envían al presentador.
            mPresenter.onSolicitarDatosResult(EditAlumnoActivity.getAlumnoFromResultIntent(data));
        }
    }

    @Override
    public void navigateToSolicitar(Alumno alumno) {
        EditAlumnoActivityStarter.startForResult(this, alumno, RC_ALUMNO);
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
