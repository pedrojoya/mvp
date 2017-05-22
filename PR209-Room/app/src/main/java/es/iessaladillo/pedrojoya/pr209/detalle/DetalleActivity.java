package es.iessaladillo.pedrojoya.pr209.detalle;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import es.iessaladillo.pedrojoya.pr209.R;
import es.iessaladillo.pedrojoya.pr209.db.entities.Alumno;


@SuppressWarnings({"WeakerAccess", "unused", "CanBeFinal"})
public class DetalleActivity extends AppCompatActivity implements DetalleContract.View {

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        ButterKnife.bind(this);
        ActivityStarter.fill(this, savedInstanceState);
        mPresenter = new DetallePresenter(this);
        mAleatorio = new Random();
        initVistas();
        mPresenter.loadAlumno(idAlumno);
    }

    private void initVistas() {
        setTitle(title);
        configToolbar();
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
            finish();
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

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }

    private void mostrarFoto(String urlFoto) {
        Picasso.with(this).load(urlFoto).placeholder(R.drawable.placeholder).error(
                R.drawable.placeholder).fit().noFade().centerCrop().into(imgFoto);
    }

    @Override
    public void showAlumno(Alumno alumno) {
        mAlumno = alumno;
        urlFoto = alumno.getUrlFoto();
        alumnoToVistas(alumno);
    }

    private void alumnoToVistas(Alumno alumno) {
        txtNombre.setText(alumno.getNombre());
        txtDireccion.setText(alumno.getDireccion());
        mostrarFoto(alumno.getUrlFoto());
    }

    @Override
    public void showNewAlumno() {
        mAlumno = new Alumno(UUID.randomUUID().toString());
        if (TextUtils.isEmpty(urlFoto)) {
            urlFoto = generateRandomFoto();
        }
        mostrarFoto(urlFoto);
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
