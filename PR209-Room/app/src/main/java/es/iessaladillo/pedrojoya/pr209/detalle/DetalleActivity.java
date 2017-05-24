package es.iessaladillo.pedrojoya.pr209.detalle;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import java.util.Random;
import java.util.UUID;

import activitystarter.ActivityStarter;
import activitystarter.Arg;
import activitystarter.Optional;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;
import es.iessaladillo.pedrojoya.pr209.R;
import es.iessaladillo.pedrojoya.pr209.base.AppCompatLifecycleActivity;
import es.iessaladillo.pedrojoya.pr209.databinding.ActivityDetalleBinding;
import es.iessaladillo.pedrojoya.pr209.db.entities.Alumno;

import static es.iessaladillo.pedrojoya.pr209.R.id.txtDireccion;

@SuppressWarnings({"WeakerAccess", "unused", "CanBeFinal"})
public class DetalleActivity extends AppCompatLifecycleActivity {

    @Arg
    String title;
    @Arg
    @Optional
    String idAlumno;
    @Arg
    @Optional
    String urlFoto;

    private DetalleViewModel mViewModel;
    private ActivityDetalleBinding mBinding;
    private DetalleBindingModel mBindingModel;
    private Random mAleatorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DetalleViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detalle);
        mBinding.setPresenter(this);
        mBindingModel = new DetalleBindingModel();
        mBinding.setBindingModel(mBindingModel);
        ButterKnife.bind(this);
        ActivityStarter.fill(this, savedInstanceState);
        mAleatorio = new Random();
        initVistas();
        if (idAlumno != null) {
            mViewModel.loadAlumno(idAlumno).observe(this, this::showAlumno);
        }
    }

    private void initVistas() {
        setTitle(title);
        configToolbar();
    }

    private void configToolbar() {
        setSupportActionBar(mBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mBinding.collapsingToolbarLayout.setExpandedTitleColor(
                ContextCompat.getColor(this, android.R.color.transparent));
        mBinding.collapsingToolbarLayout.setCollapsedTitleTextColor(
                ContextCompat.getColor(this, android.R.color.white));
    }

    public void cambiarFoto() {
        urlFoto = generateRandomFoto();
        mBindingModel.setUrlFoto(urlFoto);
    }

    @OnEditorAction(txtDireccion)
    public boolean onDatosIntroducidos(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            guardar();
            return true;
        }
        return false;
    }

    public void guardar() {
        if (!TextUtils.isEmpty(mBindingModel.getNombre()) && !TextUtils.isEmpty(
                mBindingModel.getDireccion())) {
            if (idAlumno == null) {
                mViewModel.insertAlumno(
                        new Alumno(UUID.randomUUID().toString(), mBindingModel.getNombre(),
                                mBindingModel.getDireccion(), urlFoto));
            } else {
                mViewModel.updateAlumno(new Alumno(idAlumno, mBindingModel.getNombre(),
                        mBindingModel.getDireccion(), urlFoto));
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

    public void showAlumno(Alumno alumno) {
        urlFoto = alumno.getUrlFoto();
        mBindingModel.setNombre(alumno.getNombre());
        mBindingModel.setDireccion(alumno.getDireccion());
        mBindingModel.setUrlFoto(alumno.getUrlFoto());
    }

    private String generateRandomFoto() {
        return "http://lorempixel.com/200/200/abstract/" + (mAleatorio.nextInt(7) + 1) + "/";
    }

}
