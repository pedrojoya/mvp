package es.iessaladillo.pedrojoya.pr207.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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
        initVistas();
    }

    private void initVistas() {
        mBinding.btnGuardar.setOnClickListener(view -> guardar());
    }

    private void guardar() {
        if (mValidator.validate()) {
            Toast.makeText(this, R.string.guardando, Toast.LENGTH_SHORT).show();
        }
    }

}
