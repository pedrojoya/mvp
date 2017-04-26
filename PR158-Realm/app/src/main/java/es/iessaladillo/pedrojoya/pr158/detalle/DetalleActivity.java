package es.iessaladillo.pedrojoya.pr158.detalle;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.transition.Fade;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Random;
import java.util.UUID;

import activitystarter.ActivityStarter;
import activitystarter.Arg;
import activitystarter.Optional;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import es.iessaladillo.pedrojoya.pr158.R;
import es.iessaladillo.pedrojoya.pr158.db.entities.Alumno;
import es.iessaladillo.pedrojoya.pr158.utils.SharedTransitionsUtils;


@SuppressWarnings({"WeakerAccess", "unused", "CanBeFinal"})
public class DetalleActivity extends AppCompatActivity implements DetalleContract.View {

    public static final String TN_FOTO = "transition_foto";
    private static final long ENTER_TRANSITION_DURATION_MILIS = 500;

    private static final String STATE_INDICES_ASIGNATURAS_SELECCIONADAS =
            "indicesAsignaturasSeleccionadas";

    @BindView(R.id.imgFoto)
    ImageView imgFoto;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.txtNombre)
    TextInputEditText txtNombre;
    @BindView(R.id.txtDireccion)
    TextInputEditText txtDireccion;
    @BindView(R.id.txtAsignaturas)
    TextInputEditText txtAsignaturas;
    @BindView(R.id.fabAccion)
    FloatingActionButton fabAccion;

    @Arg
    String title;
    @Arg
    @Optional
    String idAlumno;
    @Arg
    @Optional
    String urlFoto;

    private DetallePresenter mPresenter;
    private Random mAleatorio;
    private Alumno mAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedTransitionsUtils.requestContentTransitionsFeature(getWindow());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        ButterKnife.bind(this);
        ActivityStarter.fill(this, savedInstanceState);
        configTransitions();
        mPresenter = new DetallePresenter(this);
        mAleatorio = new Random();
        initVistas();
        mPresenter.loadAlumno(idAlumno);
    }

    private void initVistas() {
        setTitle(title);
        configToolbar();
        configAsignaturas();
        ViewCompat.setTransitionName(imgFoto, TN_FOTO);
    }

    private void configAsignaturas() {
        txtAsignaturas.setFocusable(true);
        txtAsignaturas.setClickable(true);
        txtAsignaturas.setInputType(InputType.TYPE_NULL);
        txtAsignaturas.setKeyListener(null);
        txtAsignaturas.setOnFocusChangeListener(
                (view, b) -> mPresenter.selectAsignaturas(idAlumno, mAlumno));
        //txtAsignaturas.setOnClickListener(v -> mPresenter.selectAsignaturas(idAlumno, mAlumno));
    }

    @OnClick(R.id.imgFoto)
    public void cambiarFoto(View view) {
        urlFoto = generateRandomFoto();
        mostrarFoto(urlFoto);
    }

    private void configToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setExpandedTitleColor(
                ContextCompat.getColor(this, android.R.color.transparent));
        collapsingToolbarLayout.setCollapsedTitleTextColor(
                ContextCompat.getColor(this, android.R.color.white));
    }

    @OnEditorAction(R.id.txtDireccion)
    public boolean onDatosIntroducidos(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            guardar();
            return true;
        }
        return false;
    }

    @OnClick(R.id.fabAccion)
    public void guardar() {
        if (!TextUtils.isEmpty(txtNombre.getText().toString()) && !TextUtils.isEmpty(
                txtDireccion.getText().toString())) {
            mPresenter.doGuardar(idAlumno, mAlumno, txtNombre.getText().toString(),
                    txtDireccion.getText().toString(), urlFoto);
            ActivityCompat.finishAfterTransition(DetalleActivity.this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        ActivityStarter.save(this, outState);
        super.onSaveInstanceState(outState);
    }

    // Se configuran las transiciones de la actividad.
    private void configTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Transición de entrada.
            Fade enterTransition = new Fade(Fade.IN);
            enterTransition.excludeTarget(android.R.id.statusBarBackground, true);
            enterTransition.excludeTarget(android.R.id.navigationBarBackground, true);
            enterTransition.excludeTarget(R.id.appbar, true);
            enterTransition.setDuration(ENTER_TRANSITION_DURATION_MILIS);
            getWindow().setEnterTransition(enterTransition);
            // Se pospone la animación de entrada hasta que la imagen
            // esté disponible.
            //supportPostponeEnterTransition();
            // Transición de retorno.
            getWindow().setReturnTransition(new Fade(Fade.OUT));
        }
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
    }


    private void mostrarFoto(String urlFoto) {
        Picasso.with(this).load(this.urlFoto).placeholder(R.drawable.placeholder).error(
                R.drawable.placeholder).fit().noFade().centerCrop().into(imgFoto, new Callback() {
            @Override
            public void onSuccess() {
                supportStartPostponedEnterTransition();
            }

            @Override
            public void onError() {
                supportStartPostponedEnterTransition();
            }
        });
    }


    @Override
    public void showAlumno(Alumno alumno) {
        mAlumno = alumno;
        alumnoToVistas(alumno);
    }

    private void alumnoToVistas(Alumno alumno) {
        txtNombre.setText(alumno.getNombre());
        txtDireccion.setText(alumno.getDireccion());
        txtAsignaturas.setText(TextUtils.join(", ", alumno.getAsignaturas()));
        mostrarFoto(alumno.getUrlFoto());
    }

    @Override
    public void showNewAlumno() {
        mAlumno = new Alumno(UUID.randomUUID().toString());
        if (TextUtils.isEmpty(urlFoto)) {
            urlFoto = generateRandomFoto();
        }
        mostrarFoto(urlFoto);
        txtAsignaturas.setEnabled(false);
    }

    @Override
    public void navigateToAsignaturasAlumnoActivity(String idAlumno) {
        // TODO.
        Toast.makeText(this, "Ir a selección de asignaturas", Toast.LENGTH_SHORT).show();
    }

    private String generateRandomFoto() {
        return "http://lorempixel.com/200/200/abstract/" + (mAleatorio.nextInt(7) + 1) + "/";
    }

}
