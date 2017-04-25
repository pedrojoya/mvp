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
import es.iessaladillo.pedrojoya.pr158.detalle.DetalleActivity;
import es.iessaladillo.pedrojoya.pr158.utils.SharedTransitionsUtils;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements AlumnosAdapter.OnItemClickListener {

    private static final int RC_DETALLE = 1;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lstAlumnos)
    RecyclerView lstAlumnos;
    @BindView(R.id.lblNoHayAlumnos)
    TextView lblNoHayAlumnos;
    @BindView(R.id.fabAccion)
    FloatingActionButton fabAccion;

    private AlumnosAdapter mAdaptador;
    private Realm mRealm;
    private RecyclerView.AdapterDataObserver mObservador;
    private RealmResults<Alumno> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedTransitionsUtils.requestContentTransitionsFeature(getWindow());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // Se obtiene la instancia de Realm.
        mRealm = Realm.getDefaultInstance();
        initVistas();
    }

    private void initVistas() {
        configToolbar();
        configRecyclerView();
    }

    private void configToolbar() {
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.fabAccion)
    public void agregar() {
        DetalleActivity.startForResult(MainActivity.this, RC_DETALLE);
    }

    private void configRecyclerView() {
        lstAlumnos.setHasFixedSize(true);
        lstAlumnos.setLayoutManager(
                new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        lstAlumnos.setItemAnimator(new DefaultItemAnimator());
        mData = getAlumnos();
        mAdaptador = new AlumnosAdapter(mRealm, mData);
        mAdaptador.setOnItemClickListener(MainActivity.this);
        mObservador = new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                checkAdapterIsEmpty();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                checkAdapterIsEmpty();
            }
        };
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
                        // Se elimina el elemento
                        mAdaptador.removeItem(viewHolder.getAdapterPosition());
                        checkAdapterIsEmpty();
                    }
                });
        itemTouchHelper.attachToRecyclerView(lstAlumnos);
        lstAlumnos.setAdapter(mAdaptador);
        checkAdapterIsEmpty();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAdapterIsEmpty();
    }

    private RealmResults<Alumno> getAlumnos() {
        return mRealm.where(Alumno.class).findAllSorted("nombre");
    }

    private void checkAdapterIsEmpty() {
        lblNoHayAlumnos.setVisibility(
                mAdaptador.getItemCount() == 0 ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onItemClick(View view, Alumno alumno, int position) {
        DetalleActivity.startForResult(this, RC_DETALLE, alumno.getId(),
                view.findViewById(R.id.imgFoto));
    }

    @Override
    protected void onDestroy() {
        mAdaptador.onDestroy();
        mRealm.close();
        super.onDestroy();
    }

}
