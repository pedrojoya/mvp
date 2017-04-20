package es.iessaladillo.pedrojoya.pr207.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.ilhasoft.support.validation.Validator;
import es.iessaladillo.pedrojoya.pr207.R;
import es.iessaladillo.pedrojoya.pr207.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private Validator mValidator;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mValidator = new Validator(mBinding);
        mValidator.enableFormValidationMode();
        mBinding.setValidator(mValidator);
        mBinding.setPresenter(this);
        checkFormValid();
    }

    public void btnGuardarOnClick(View view) {
        guardar();
    }

    public boolean txtPasswordOnEditorAction() {
        guardar();
        return true;
    }

    private void guardar() {
        List<View> vistas = new ArrayList<>();
        vistas.add(mBinding.txtNombre);
        if (!TextUtils.isEmpty(mBinding.txtTelefono.getText())) {
            vistas.add(mBinding.txtTelefono);
        }
        if (!TextUtils.isEmpty(mBinding.txtEmail.getText())) {
            vistas.add(mBinding.txtEmail);
        }
        vistas.add(mBinding.txtPassword);
        if (mValidator.validate(vistas)) {
            Toast.makeText(this, R.string.guardando, Toast.LENGTH_SHORT).show();
        }
    }

    // Llamado por DataBinding cuando cambia el texto de algun EditText.
    public void checkFormValid() {
        mBinding.btnGuardar.setEnabled(
                !TextUtils.isEmpty(mBinding.txtNombre.getText()) && !TextUtils.isEmpty(
                        mBinding.txtPassword.getText()));
    }

    public boolean txtPasswordOnEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            btnGuardarOnClick(mBinding.btnGuardar);
            return true;
        }
        return false;
    }

}
