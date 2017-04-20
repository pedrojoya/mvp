package es.iessaladillo.pedrojoya.pr207.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import br.com.ilhasoft.support.validation.Validator;
import es.iessaladillo.pedrojoya.pr207.R;
import es.iessaladillo.pedrojoya.pr207.databinding.ActivityMainBinding;
import es.iessaladillo.pedrojoya.pr207.utils.KeyboardUtils;

public class MainActivity extends AppCompatActivity {

    private Validator mValidator;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupValidator();
        mBinding.setValidator(mValidator);
        mBinding.setPresenter(this);
        checkFormValid();
    }

    private void setupValidator() {
        mValidator = new Validator(mBinding);
        mValidator.enableFormValidationMode();
        // El teléfono y el email no son requeridos por lo que sólo se validarán si tienen datos.
        mValidator.disableValidation(mBinding.txtTelefono);
        mValidator.disableValidation(mBinding.txtEmail);
    }

    public boolean txtPasswordOnEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            guardar();
            return true;
        }
        return false;
    }

    @SuppressWarnings("WeakerAccess")
    public void guardar() {
        KeyboardUtils.hideKeyboard(this, mBinding.btnGuardar);
        if (mValidator.validate()) {
            Toast.makeText(this, R.string.guardando, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("WeakerAccess")
    public void checkFormValid() {
        mBinding.btnGuardar.setEnabled(
                !TextUtils.isEmpty(mBinding.txtNombre.getText()) && !TextUtils.isEmpty(
                        mBinding.txtPassword.getText()));
    }

}
