package es.iessaladillo.pedrojoya.pr209.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.iessaladillo.pedrojoya.pr209.R;
import es.iessaladillo.pedrojoya.pr209.base.AppCompatLifecycleActivity;
import es.iessaladillo.pedrojoya.pr209.db.entities.Alumno;
import es.iessaladillo.pedrojoya.pr209.detalle.DetalleActivityStarter;
import es.iessaladillo.pedrojoya.pr209.selec_asig.SelecAsigActivityStarter;

public class MainActivity extends AppCompatLifecycleActivity implements AlumnosAdapter
        .OnItemClickListener, AlumnosAdapter.OnEmptyStateListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lstAlumnos)
    RecyclerView lstAlumnos;
    @BindView(R.id.lblNoHayAlumnos)
    TextView lblNoHayAlumnos;
    @BindView(R.id.fabAccion)
    FloatingActionButton fabAccion;

    private MainViewModel mViewModel;

    private LiveData<List<Alumno>> mData;

    private AlumnosAdapter mAdaptador;
    private View mFotoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        configToolbar();
        configRecyclerView();
        mViewModel.loadAlumnos().observe(this, alumnos -> mAdaptador.setData(alumnos));
    }

    private void configToolbar() {
        setSupportActionBar(toolbar);
    }

    private void configRecyclerView() {
        lstAlumnos.setHasFixedSize(true);
        lstAlumnos.setLayoutManager(
                new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        lstAlumnos.setItemAnimator(new DefaultItemAnimator());
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
        itemTouchHelper.attachToRecyclerView(lstAlumnos);
        lstAlumnos.setAdapter(mAdaptador);
    }

    @OnClick(R.id.fabAccion)
    public void agregar() {
        navigateToDetalleActivity();
    }

    @Override
    public void onItemClick(View view, Alumno alumno, int position) {
        mFotoView = view.findViewById(R.id.imgFoto);
        navigateToDetalleActivity(alumno.getId());
    }

    @Override
    public void onItemIconClick(View view, Alumno alumno, int position) {
        navigateToAsignaturasAlumnoActivity(alumno.getId());
    }

    public void navigateToDetalleActivity() {
        DetalleActivityStarter.start(this, getString(R.string.agregar_alumno));
    }

    public void navigateToDetalleActivity(String idAlumno) {
        DetalleActivityStarter.start(this, getString(R.string.actualizar_alumno), idAlumno);
    }

    public void navigateToAsignaturasAlumnoActivity(String idAlumno) {
        SelecAsigActivityStarter.start(this, idAlumno);
    }

    @Override
    public void onEmptyState(boolean empty) {
        lblNoHayAlumnos.setVisibility(empty ? View.VISIBLE : View.INVISIBLE);
    }

}
