package es.iessaladillo.pedrojoya.pr149.main;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import es.iessaladillo.pedrojoya.pr149.R;
import es.iessaladillo.pedrojoya.pr149.utils.ValidationUtils;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout tilTelefono;
    private TextInputEditText txtTelefono;
    private TextInputLayout tilEmail;
    private TextInputEditText txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        setupValidacionTelefono();
        setupValidacionEmail();
    }

    // Configura la validación del teléfono.
    private void setupValidacionTelefono() {
        tilTelefono = (TextInputLayout) findViewById(R.id.tilTelefono);
        txtTelefono = (TextInputEditText) findViewById(R.id.txtTelefono);
        if (txtTelefono != null && tilTelefono != null) {
            txtTelefono.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    checkTelefonoValido();
                }
            });
        }
    }

    private void checkTelefonoValido() {
        if (!TextUtils.isEmpty(txtTelefono.getText().toString())) {
            if (!ValidationUtils.isValidPhoneNumber(txtTelefono.getText().toString())) {
                tilTelefono.setError("No es un número de teléfono válido");
            } else {
                tilTelefono.setErrorEnabled(false);
            }
        } else {
            tilTelefono.setErrorEnabled(false);
        }
    }

    // Configura la validación del email.
    private void setupValidacionEmail() {
        tilEmail = (TextInputLayout) findViewById(R.id.tilEmail);
        txtEmail = (TextInputEditText) findViewById(R.id.txtEmail);
        if (txtEmail != null && tilEmail != null) {
            txtEmail.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    checkEmailValido();
                }
            });
        }
    }

    private void checkEmailValido() {
        if (!TextUtils.isEmpty(txtEmail.getText().toString())) {
            if (!ValidationUtils.isValidEmail(txtEmail.getText().toString())) {
                tilEmail.setError("Debe tener el formato usuario@dominio.tipo");
            } else {
                tilEmail.setErrorEnabled(false);
            }
        } else {
            tilEmail.setErrorEnabled(false);
        }
    }

}
