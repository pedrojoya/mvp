package es.iessaladillo.pedrojoya.pr207.main;

import android.databinding.DataBindingUtil;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr207.databinding.ActivityMainBinding;

public class MainPresenter {

    public void txtNombreAfterTextChanged(TextView textView) {
        // Se obtiene el binding
        ActivityMainBinding binding = DataBindingUtil.findBinding(textView);
        binding.btnGuardar.setText(textView.getText().toString());
    }

}
