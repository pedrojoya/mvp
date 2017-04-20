package es.iessaladillo.pedrojoya.pr207.utils;

import android.databinding.BindingAdapter;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import br.com.ilhasoft.support.validation.Validator;

@SuppressWarnings({"WeakerAccess", "DefaultAnnotationParam"})
public class DataBindingValidatorAdapters {

    private DataBindingValidatorAdapters() {
    }

    @BindingAdapter(value = {"app:onTextChangedValidator", "app:disableValidationWhenEmpty"},
            requireAll = true)
    public static void setOnTextChangedValidator(EditText txt, Validator validator,
            boolean disableWhenEmpty) {
        txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (disableWhenEmpty && TextUtils.isEmpty(editable.toString())) {
                    validator.disableValidation(txt);
                } else {
                    validator.enableValidation(txt);
                }
                validator.validate(txt);
            }
        });
    }

}
