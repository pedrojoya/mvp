package es.iessaladillo.pedrojoya.pr207.utils;

import android.databinding.BindingAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import br.com.ilhasoft.support.validation.Validator;

public class DataBindingAdapters {

    @BindingAdapter("app:onTextChangedValidator")
    public static void setOnTextChangedValidator(EditText txt, Validator validator) {
        txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                validator.validate(txt);
            }
        });
    }

}
