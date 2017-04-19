package es.iessaladillo.pedrojoya.pr011.main;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr011.R;
import es.iessaladillo.pedrojoya.pr011.components.MessageManager.MessageManager;
import es.iessaladillo.pedrojoya.pr011.components.MessageManager.ToastMessageManager;
import es.iessaladillo.pedrojoya.pr011.utils.TintUtils;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private static final String STATE_LISTA = "STATE_LISTA";
    private static final String STATE_LISTA_CONTENIDO = "STATE_LISTA_CONTENIDO";

    private MainContract.Presenter mPresenter;
    private MessageManager mMessageManager;
    private ArrayAdapter<String> mAdaptador;
    private Parcelable mEstadoLista;
    private ArrayList<String> mListaContenido;

    private EditText txtNombre;
    private ImageButton btnAgregar;
    private ListView lstAlumnos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(this);
        mMessageManager = new ToastMessageManager();
        restoreInstance(savedInstanceState);
        initVistas();
    }

    private void restoreInstance(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Se obtiene el estado anterior de la lista.
            mEstadoLista = savedInstanceState.getParcelable(STATE_LISTA);
            mListaContenido = savedInstanceState.getStringArrayList(STATE_LISTA_CONTENIDO);
        } else {
            mListaContenido = new ArrayList<>();
        }
    }

    private void initVistas() {
        btnAgregar = (ImageButton) findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(v -> mPresenter.doAgregar(txtNombre.getText().toString()));
        TintUtils.tintViewWithColorStateList(btnAgregar,
                ContextCompat.getColorStateList(this, R.color.btn_color));
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtNombre.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkDatos();
            }

        });
        checkDatos();
        txtNombre.setOnEditorActionListener((v, actionId, event) -> {
            // Si se ha pulsado Done.
            if (actionId == EditorInfo.IME_ACTION_DONE && isFormValid()) {
                mPresenter.doAgregar(txtNombre.getText().toString());
                return true;
            }
            return false;
        });
        setupListView();
    }

    private void setupListView() {
        lstAlumnos = (ListView) findViewById(R.id.lstAlumnos);
        lstAlumnos.setEmptyView(findViewById(R.id.lblNoHayAlumnos));
        lstAlumnos.setOnItemClickListener(
                (parent, view, position, id) -> mPresenter.doOnAlumnoClicked(
                        (String) lstAlumnos.getItemAtPosition(position)));
        mAdaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mListaContenido);
        lstAlumnos.setAdapter(mAdaptador);
        if (mEstadoLista != null) {
            lstAlumnos.onRestoreInstanceState(mEstadoLista);
        }
    }

    private void checkDatos() {
        btnAgregar.setEnabled(isFormValid());
    }

    private boolean isFormValid() {
        return !TextUtils.isEmpty(txtNombre.getText().toString());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Se salva el estado del ListView.
        mEstadoLista = lstAlumnos.onSaveInstanceState();
        outState.putParcelable(STATE_LISTA, mEstadoLista);
        outState.putStringArrayList(STATE_LISTA_CONTENIDO, mListaContenido);
    }

    @Override
    public void agregarAlumno(String alumno) {
        mAdaptador.add(alumno);
        resetVistas();
        checkDatos();
        mMessageManager.showMessage(btnAgregar, getString(R.string.alumno_agregado, alumno));
    }

    @Override
    public void eliminarAlumno(String alumno) {
        mAdaptador.remove(alumno);
        mMessageManager.showMessage(btnAgregar, getString(R.string.alumno_eliminado, alumno));
    }

    private void resetVistas() {
        txtNombre.setText("");
    }

}
