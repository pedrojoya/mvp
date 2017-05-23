package es.iessaladillo.pedrojoya.pr209.detalle;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

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
import es.iessaladillo.pedrojoya.pr209.base.AppCompatLifecycleActivity;
import es.iessaladillo.pedrojoya.pr209.db.entities.Alumno;


@SuppressWarnings({"WeakerAccess", "unused", "CanBeFinal"})
public class DetalleActivity extends AppCompatLifecycleActivity {

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

    private DetalleViewModel mViewModel;
    private Random mAleatorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        ButterKnife.bind(this);
        ActivityStarter.fill(this, savedInstanceState);
        mAleatorio = new Random();
        mViewModel = ViewModelProviders.of(this).get(DetalleViewModel.class);
        initVistas();
        if (idAlumno != null) {
            mViewModel.loadAlumno(idAlumno).observe(this, this::showAlumno);
        }
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
            if (idAlumno == null) {
                mViewModel.insertAlumno(
                        new Alumno(UUID.randomUUID().toString(), txtNombre.getText().toString(),
                                txtDireccion.getText().toString(), urlFoto));
            } else {
                mViewModel.updateAlumno(new Alumno(idAlumno, txtNombre.getText().toString(),
                        txtDireccion.getText().toString(), urlFoto));
            }
            finish();
        }
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

    public void showAlumno(Alumno alumno) {
        urlFoto = alumno.getUrlFoto();
        alumnoToVistas(alumno);
    }

    private void alumnoToVistas(Alumno alumno) {
        txtNombre.setText(alumno.getNombre());
        txtDireccion.setText(alumno.getDireccion());
        mostrarFoto(alumno.getUrlFoto());
    }

    private String generateRandomFoto() {
        return "http://lorempixel.com/200/200/abstract/" + (mAleatorio.nextInt(7) + 1) + "/";
    }

}
