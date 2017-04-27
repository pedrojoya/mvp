package es.iessaladillo.pedrojoya.pr158.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.iessaladillo.pedrojoya.pr158.R;
import es.iessaladillo.pedrojoya.pr158.db.entities.Alumno;
import es.iessaladillo.pedrojoya.pr158.detalle.DetalleActivityStarter;
import es.iessaladillo.pedrojoya.pr158.selec_asig.SelecAsigActivityStarter;
import es.iessaladillo.pedrojoya.pr158.utils.SharedTransitionsUtils;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements MainContract.View, AlumnosAdapter
        .OnItemClickListener, AlumnosAdapter.OnEmptyStateListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lstAlumnos)
    RecyclerView lstAlumnos;
    @BindView(R.id.lblNoHayAlumnos)
    TextView lblNoHayAlumnos;
    @BindView(R.id.fabAccion)
    FloatingActionButton fabAccion;

    private AlumnosAdapter mAdaptador;
    private RealmResults<Alumno> mData;
    private MainPresenter mPresenter;
    private View mFotoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedTransitionsUtils.requestContentTransitionsFeature(getWindow());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new MainPresenter(this);
        mData = mPresenter.getDatos();
        initVistas();
    }

    private void initVistas() {
        configToolbar();
        configRecyclerView();
    }

    private void configToolbar() {
        setSupportActionBar(toolbar);
    }

    private void configRecyclerView() {
        lstAlumnos.setHasFixedSize(true);
        lstAlumnos.setLayoutManager(
                new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        lstAlumnos.setItemAnimator(new DefaultItemAnimator());
        mAdaptador = new AlumnosAdapter(mData);
        mAdaptador.setOnItemClickListener(this);
        mAdaptador.setOnEmptyStateListener(this);
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
                        mPresenter.doEliminarAlumno(mData.get(viewHolder.getAdapterPosition()));
                    }
                });
        itemTouchHelper.attachToRecyclerView(lstAlumnos);
        lstAlumnos.setAdapter(mAdaptador);
    }

    @OnClick(R.id.fabAccion)
    public void agregar() {
        mPresenter.doAgregar();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onItemClick(View view, Alumno alumno, int position) {
        mFotoView = view.findViewById(R.id.imgFoto);
        mPresenter.doDetalle(alumno);
    }

    @Override
    public void onItemIconClick(View view, Alumno alumno, int position) {
        mPresenter.showAsignaturas(alumno);
    }

    @Override
    protected void onDestroy() {
        mAdaptador.onDestroy();
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void navigateToDetalleActivity() {
        DetalleActivityStarter.start(this, getString(R.string.agregar_alumno));
    }

    @Override
    public void navigateToDetalleActivity(String idAlumno) {
        DetalleActivityStarter.start(this, getString(R.string.actualizar_alumno), idAlumno);
    }

    @Override
    public void navigateToAsignaturasAlumnoActivity(String idAlumno) {
        SelecAsigActivityStarter.start(this, idAlumno);
    }

    @Override
    public void onEmptyState(boolean empty) {
        lblNoHayAlumnos.setVisibility(empty ? View.VISIBLE : View.INVISIBLE);
    }

}
