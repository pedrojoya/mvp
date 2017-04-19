package es.iessaladillo.pedrojoya.pr008.main;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr008.R;
import es.iessaladillo.pedrojoya.pr008.components.MessageManager.MessageManager;
import es.iessaladillo.pedrojoya.pr008.components.MessageManager.ToastMessageManager;
import es.iessaladillo.pedrojoya.pr008.utils.KeyboardUtils;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity implements MainContract.View {

    private EditText txtUsuario;
    private EditText txtClave;
    private Button btnAceptar;
    private Button btnCancelar;
    private TextView lblUsuario;
    private TextView lblClave;

    private MainContract.Presenter mPresenter;
    private MessageManager mMessageManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(this);
        mMessageManager = new ToastMessageManager();
        initVistas();
    }

    private void initVistas() {
        btnAceptar = (Button) findViewById(R.id.btnAceptar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        lblUsuario = (TextView) findViewById(R.id.lblUsuario);
        lblClave = (TextView) findViewById(R.id.lblClave);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtClave = (EditText) findViewById(R.id.txtClave);
        txtClave.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE && isFormValid()) {
                KeyboardUtils.hideKeyboard(MainActivity.this, textView);
                mPresenter.doAceptar(txtUsuario.getText().toString(),
                        txtClave.getText().toString());
                // Se ha gestionado el evento.
                return true;
            }
            return false;
        });
        btnAceptar.setOnClickListener(v -> mPresenter.doAceptar(txtUsuario.getText().toString(),
                txtClave.getText().toString()));
        btnCancelar.setOnClickListener(v -> mPresenter.doCancelar());
        setCambiarColorFocusListener(lblUsuario, txtUsuario);
        setCambiarColorFocusListener(lblClave, txtClave);
        setCambiarVisibilidadTextWatcher(lblUsuario, txtUsuario);
        setCambiarVisibilidadTextWatcher(lblClave, txtClave);
        // Comprobaciones iniciales.
        setColorSegunFoco(lblUsuario, true);
        checkFormularioValido();
        setTextViewVisibility(txtClave, lblClave);
        setTextViewVisibility(txtUsuario, lblUsuario);
    }

    private void setCambiarVisibilidadTextWatcher(TextView lbl, EditText txt) {
        txt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setTextViewVisibility(txt, lbl);
                checkFormularioValido();
            }

        });
    }

    private void setCambiarColorFocusListener(TextView lbl, EditText txt) {
        txt.setOnFocusChangeListener((v, hasFocus) -> setColorSegunFoco(lbl, hasFocus));
    }

    private void checkFormularioValido() {
        btnAceptar.setEnabled(isFormValid());
    }

    private boolean isFormValid() {
        return !TextUtils.isEmpty(txtUsuario.getText().toString()) && !TextUtils.isEmpty(
                txtClave.getText().toString());
    }

    private void setTextViewVisibility(EditText txt, TextView lbl) {
        lbl.setVisibility(
                TextUtils.isEmpty(txt.getText().toString()) ? View.INVISIBLE : View.VISIBLE);
    }

    private void setColorSegunFoco(TextView lbl, boolean hasFocus) {
        lbl.setTextColor(
                hasFocus ? ContextCompat.getColor(this, R.color.accent) : ContextCompat.getColor(
                        this, R.color.primary));
        lbl.setTypeface(hasFocus ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
    }

    @Override
    public void resetForm() {
        txtUsuario.setText("");
        txtClave.setText("");
    }

    @Override
    public void showLoginCorrecto(String usuario) {
        mMessageManager.showMessage(btnAceptar,
                getString(R.string.conectando_con_el_usuario, usuario));
    }

    @Override
    public void showLoginIncorrecto() {
        mMessageManager.showMessage(btnAceptar, getString(R.string.error_conectando));
    }

}
