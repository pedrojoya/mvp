package es.iessaladillo.pedrojoya.pr158.selec_asig;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import activitystarter.ActivityStarter;
import activitystarter.Arg;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.iessaladillo.pedrojoya.pr158.R;
import es.iessaladillo.pedrojoya.pr158.db.entities.Alumno;
import es.iessaladillo.pedrojoya.pr158.db.entities.Asignatura;
import io.realm.RealmList;
import io.realm.RealmResults;

public class SelecAsigActivity extends AppCompatActivity implements SelecAsigContract.View {

    @BindView(R.id.lstAsignaturas)
    ListView lstAsignaturas;
    @BindView(R.id.lblEmptyView)
    TextView lblEmptyView;

    @Arg
    String idAlumno;

    private Alumno mAlumno;

    private SelecAsigPresenter mPresenter;
    private ArrayAdapter<Asignatura> mAdaptador;
    private RealmResults<Asignatura> mAsignaturas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selec_asig);
        ButterKnife.bind(this);
        ActivityStarter.fill(this, savedInstanceState);
        mPresenter = new SelecAsigPresenter(this);
        initVistas();
        mPresenter.loadAsignaturas(idAlumno);
    }

    private void initVistas() {
        lstAsignaturas.setEmptyView(lblEmptyView);
        lstAsignaturas.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
    }

    private boolean esDelAlumno(String idAsignatura) {
        for (Asignatura asigAlum : mAlumno.getAsignaturas()) {
            if (asigAlum.getId() == idAsignatura) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        mPresenter.doGuardar(mAlumno, getElementosSeleccionados(lstAsignaturas));
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private RealmList<Asignatura> getElementosSeleccionados(ListView lst) {
        // ArrayList resultado.
        RealmList<Asignatura> datos = new RealmList<>();
        // Se obtienen los elementos seleccionados de la lista.
        SparseBooleanArray selec = lst.getCheckedItemPositions();
        for (int i = 0; i < selec.size(); i++) {
            // Si está seleccionado.
            if (selec.valueAt(i)) {
                // Se añade al resultado.
                datos.add((Asignatura) lst.getItemAtPosition(selec.keyAt(i)));
            }
        }
        // Se retorna el resultado.
        return datos;
    }

    @Override
    public void showAsignaturas(RealmResults<Asignatura> asignaturas, Alumno alumno) {
        mAsignaturas = asignaturas;
        mAlumno = alumno;
        mAdaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice,
                mAsignaturas);
        lstAsignaturas.setAdapter(mAdaptador);
        Asignatura asignatura;
        for (int i = 0; i < lstAsignaturas.getCount(); i++) {
            asignatura = (Asignatura) lstAsignaturas.getItemAtPosition(i);
            // Si la asignatura está entre las del alumnos
            if (mAlumno.getAsignaturas().contains(asignatura)) {
                lstAsignaturas.setItemChecked(i, true);
            }
        }
    }

}
