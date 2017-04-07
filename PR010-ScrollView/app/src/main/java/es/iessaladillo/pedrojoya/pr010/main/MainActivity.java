package es.iessaladillo.pedrojoya.pr010.main;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.iessaladillo.pedrojoya.pr010.R;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private EditText txtMensaje;
    private TextView lblTexto;
    private ImageView btnEnviar;
    private NestedScrollView scvTexto;

    private MainContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(this);
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        lblTexto = (TextView) findViewById(R.id.lblTexto);
        txtMensaje = (EditText) findViewById(R.id.txtMensaje);
        btnEnviar = (ImageView) findViewById(R.id.btnEnviar);
        scvTexto = (NestedScrollView) findViewById(R.id.scvTexto);
        configMensajeEditText();
        btnEnviar.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(txtMensaje.getText().toString())) {
                mPresenter.doEnviar(txtMensaje.getText().toString());
            }
        });
        // Se realizan las comprobaciones iniciales.
        checkDatos();
        hacerScroll(scvTexto, View.FOCUS_DOWN);
    }

    private void configMensajeEditText() {
        txtMensaje.setHorizontallyScrolling(false);
        txtMensaje.setMaxLines(getResources().getInteger(R.integer.txtMensaje_maxLines));
        txtMensaje.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        txtMensaje.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE && !TextUtils.isEmpty(txtMensaje.getText().toString())) {
                mPresenter.doEnviar(txtMensaje.getText().toString());
            }
            return false;
        });
        txtMensaje.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Se activa o desactiva btnEnviar.
                checkDatos();
            }

        });
    }

    // Hace scroll en el ScrollView en la dirección indicada.
    @SuppressWarnings("SameParameterValue")
    private void hacerScroll(final NestedScrollView scv, final int focus) {
        // Es necesario que se haga en el futuro para que se calcule
        // correctamente el final.
        scv.post(new Runnable() {
            @Override
            public void run() {
                scv.fullScroll(focus);
                // scv.scrollTo(0, scv.getBottom());
                txtMensaje.requestFocus();
            }
        });
    }

    // Activa o desasctiva btnEnviar dependiendo de si hay datos.
    private void checkDatos() {
        btnEnviar.setEnabled(!TextUtils.isEmpty(txtMensaje.getText()));
    }

    @Override
    public void addMensaje(String mensaje, String hora) {
        // Se agrega el mensaje.
        lblTexto.append(getString(R.string.mensaje_hora, hora, mensaje));
        // Se limpia el cuadro de texto.
        txtMensaje.setText("");
        // Se mueve el scroll al final para ver el último mensaje.
        hacerScroll(scvTexto, View.FOCUS_DOWN);
    }

}
