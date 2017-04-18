package es.iessaladillo.pedrojoya.pr206.editalumno;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import activitystarter.ActivityStarter;
import activitystarter.Arg;
import activitystarter.MakeActivityStarter;
import es.iessaladillo.pedrojoya.pr206.R;
import es.iessaladillo.pedrojoya.pr206.model.Alumno;

@MakeActivityStarter(includeStartForResult = true)
public class EditAlumnoActivity extends AppCompatActivity implements EditAlumnoContract.View {

    private static final String EXTRA_NOMBRE = "nombre";
    private static final String EXTRA_EDAD = "edad";

    private EditText txtNombre;
    private EditText txtEdad;
    private Button btnAceptar;

    @SuppressWarnings({"CanBeFinal", "WeakerAccess"})
    @Arg
    Alumno mAlumno;
    private EditAlumnoContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);
        // Se obtienen los extras del intent con el que se ha llamado la actividad.
        ActivityStarter.fill(this);
        mPresenter = new EditAlumnoPresenter(this, mAlumno, savedInstanceState == null);
        initVistas();
    }

    private void initVistas() {
        btnAceptar = (Button) findViewById(R.id.btnAceptar);
        txtNombre = (EditText) this.findViewById(R.id.txtNombre);
        txtEdad = (EditText) this.findViewById(R.id.txtEdad);
        btnAceptar.setOnClickListener(v -> {
            int edad;
            try {
                edad = Integer.parseInt(txtEdad.getText().toString());
            } catch (NumberFormatException e) {
                edad = Alumno.DEFAULT_EDAD;
            }
            mPresenter.doRetornarDatos(txtNombre.getText().toString(), edad);
        });
        txtNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnAceptar.setEnabled(isFormularioCorrecto());
            }
        });
        txtEdad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnAceptar.setEnabled(isFormularioCorrecto());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onViewAttach(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // Retorna si el formulario tiene datos correctos.
    private boolean isFormularioCorrecto() {
        return !TextUtils.isEmpty(txtNombre.getText().toString()) && !TextUtils.isEmpty(
                txtEdad.getText().toString()) && Integer.parseInt(txtEdad.getText().toString())
                <= Alumno.MAX_EDAD;
    }

    @Override
    public void returnIntent(Intent resultIntent) {
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void showDatos(Alumno alumno) {
        txtNombre.setText(alumno.getNombre());
        txtEdad.setText(String.valueOf(alumno.getEdad()));
    }

    public static Intent createResultIntent(String nombre, int edad) {
        // Se crea un nuevo intent sin acción ni destinatario y se le agregan
        // los extras con los datos introducidos.
        Intent intent = new Intent();
        intent.putExtra(EXTRA_NOMBRE, nombre);
        intent.putExtra(EXTRA_EDAD, edad);
        return intent;
    }

    // Retorna un alumno creado a partir de los extras del intent.
    public static Alumno getAlumnoFromResultIntent(@NonNull Intent intent) {
        String nombre = intent.getStringExtra(EXTRA_NOMBRE);
        int edad = intent.getIntExtra(EXTRA_EDAD, Alumno.DEFAULT_EDAD);
        return new Alumno(nombre, edad);
    }

}
