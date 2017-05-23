package es.iessaladillo.pedrojoya.pr209.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.List;

import es.iessaladillo.pedrojoya.pr209.R;
import es.iessaladillo.pedrojoya.pr209.base.AppCompatLifecycleActivity;
import es.iessaladillo.pedrojoya.pr209.databinding.ActivityMainBinding;
import es.iessaladillo.pedrojoya.pr209.db.entities.Alumno;
import es.iessaladillo.pedrojoya.pr209.detalle.DetalleActivityStarter;
import es.iessaladillo.pedrojoya.pr209.selec_asig.SelecAsigActivityStarter;

public class MainActivity extends AppCompatLifecycleActivity implements AlumnosAdapter
        .OnItemClickListener, AlumnosAdapter.OnEmptyStateListener {

    private MainViewModel mViewModel;

    private LiveData<List<Alumno>> mData;

    private AlumnosAdapter mAdaptador;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setPresenter(this);
        configToolbar();
        configRecyclerView();
        mViewModel.loadAlumnos().observe(this, alumnos -> {
            if (alumnos != null) {
                // TODO Hacerlo con DiffUtil.
                mAdaptador.setData(alumnos);
            }
        });
    }

    private void configToolbar() {
        setSupportActionBar(mBinding.toolbar);
    }

    private void configRecyclerView() {
        mBinding.lstAlumnos.setHasFixedSize(true);
        mBinding.lstAlumnos.setLayoutManager(
                new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        mBinding.lstAlumnos.setItemAnimator(new DefaultItemAnimator());
        mAdaptador = new AlumnosAdapter(this, this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        mViewModel.deleteAlumno(
                                mAdaptador.getItem(viewHolder.getAdapterPosition()));
                    }
                });
        itemTouchHelper.attachToRecyclerView(mBinding.lstAlumnos);
        mBinding.lstAlumnos.setAdapter(mAdaptador);
    }

    public void navigateToDetalleActivity() {
        DetalleActivityStarter.start(this, getString(R.string.agregar_alumno));
    }

    @Override
    public void onItemClick(View view, Alumno alumno, int position) {
        navigateToDetalleActivity(alumno.getId());
    }

    @Override
    public void onItemIconClick(View view, Alumno alumno, int position) {
        navigateToAsignaturasAlumnoActivity(alumno.getId());
    }

    private void navigateToDetalleActivity(String idAlumno) {
        DetalleActivityStarter.start(this, getString(R.string.actualizar_alumno), idAlumno);
    }

    private void navigateToAsignaturasAlumnoActivity(String idAlumno) {
        SelecAsigActivityStarter.start(this, idAlumno);
    }

    @Override
    public void onEmptyState(boolean empty) {
        mBinding.lblNoHayAlumnos.setVisibility(empty ? View.VISIBLE : View.INVISIBLE);
    }

}
