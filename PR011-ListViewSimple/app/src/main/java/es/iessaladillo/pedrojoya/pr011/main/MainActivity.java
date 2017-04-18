package es.iessaladillo.pedrojoya.pr011.main;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr011.R;
import es.iessaladillo.pedrojoya.pr011.components.MessageManager.MessageManager;
import es.iessaladillo.pedrojoya.pr011.components.MessageManager.ToastMessageManager;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private static final String STATE_LISTA = "estadoLista";
    private static final String STATE_LISTA_CONTENIDO = "contenidoLista";

    private ArrayAdapter<String> mAdaptador;
    private Parcelable mEstadoLista;
    private ArrayList<String> mListaContenido;

    private EditText txtNombre;
    private ImageButton btnAgregar;
    private ListView lstAlumnos;

    private MainContract.Presenter mPresenter;
    private MessageManager mMessageManager;

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
        tintButton(btnAgregar);
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
    }

    // btnAgregar activo o no dependiendo de si hay nombre.
    private void checkDatos() {
        btnAgregar.setEnabled(isFormValid());
    }

    // Retorna si el formulario es válido.
    private boolean isFormValid() {
        return !TextUtils.isEmpty(txtNombre.getText().toString();
    }

    // Al hacer click sobre un elemento de la lista.
    @Override
    public void onItemClick(AdapterView<?> lst, View v, int position, long id) {
        // Se elimina el elemento sobre el que se ha pulsado.
        eliminarAlumno(lst.getItemAtPosition(position));
    }

    // Al hacer click sobre el botón.
    @Override
    public void onClick(View v) {
        // Dependiendo de la vista pulsada.
        switch (v.getId()) {
            case R.id.btnAgregar:
                String nombre = txtNombre.getText().toString();
                if (!TextUtils.isEmpty(nombre)) {
                    agregarAlumno(nombre);
                }
                break;
            default:
        }
    }

    // Agrega un alumno a la lista.
    private void agregarAlumno(String nombre) {
        // Se agrega el alumno.
        mAdaptador.add(nombre);
        // Se pone en blanco txtNombre.
        txtNombre.setText("");
        checkDatos();
    }

    // Elimina un alumno de la lista.
    private void eliminarAlumno(Object item) {
        mAdaptador.remove((String) item);
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
    protected void onResume() {
        super.onResume();
        // Se retaura el estado de la lista.
        if (mEstadoLista != null) {
            lstAlumnos.onRestoreInstanceState(mEstadoLista);
        }
    }

    private void tintButton(@NonNull ImageButton btn) {
        ColorStateList colores = ContextCompat.getColorStateList(this, R.color.btn_color);
        Drawable d = DrawableCompat.wrap(btn.getDrawable());
        DrawableCompat.setTintList(d, colores);
        btn.setImageDrawable(d);
    }

}
