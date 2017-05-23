package es.iessaladillo.pedrojoya.pr209.selec_asig;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import activitystarter.ActivityStarter;
import activitystarter.Arg;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.iessaladillo.pedrojoya.pr209.R;
import es.iessaladillo.pedrojoya.pr209.base.AppCompatLifecycleActivity;
import es.iessaladillo.pedrojoya.pr209.db.entities.Alumno;
import es.iessaladillo.pedrojoya.pr209.db.entities.SelecAsigTuple;

public class SelecAsigActivity extends AppCompatLifecycleActivity {

    @BindView(R.id.lstAsignaturas)
    ListView lstAsignaturas;
    @BindView(R.id.lblEmptyView)
    TextView lblEmptyView;

    @Arg
    String idAlumno;

    private Alumno mAlumno;

    private ArrayAdapter<SelecAsigTuple> mAdaptador;
    private SelecAsigViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selec_asig);
        mViewModel = ViewModelProviders.of(this).get(SelecAsigViewModel.class);
        ButterKnife.bind(this);
        ActivityStarter.fill(this, savedInstanceState);
        initVistas();
        mViewModel.loadSelecAsigTuples(idAlumno).observe(this, selecAsigTuples -> {
            mAdaptador.clear();
            mAdaptador.addAll(selecAsigTuples);
            for (int i = 0; i < selecAsigTuples.size(); i++) {
                if (selecAsigTuples.get(i).alumId != null) {
                    lstAsignaturas.setItemChecked(i, true);
                }
            }
        });
    }

    private void initVistas() {
        lstAsignaturas.setEmptyView(lblEmptyView);
        lstAsignaturas.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        mAdaptador = new ArrayAdapter<SelecAsigTuple>(this, android.R.layout
                .simple_list_item_multiple_choice, new ArrayList<>());
        lstAsignaturas.setAdapter(mAdaptador);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        guardar();
        finish();
    }

    private void guardar() {
        mViewModel.saveSelecAsigTuples(idAlumno, getElementosSeleccionados());
    }

    private List<SelecAsigTuple> getElementosSeleccionados() {
        List<SelecAsigTuple> datos = new ArrayList<>();
        SparseBooleanArray selec = lstAsignaturas.getCheckedItemPositions();
        for (int i = 0; i < selec.size(); i++) {
            // Si está seleccionado.
            if (selec.valueAt(i)) {
                // Se añade al resultado.
                datos.add((SelecAsigTuple) lstAsignaturas.getItemAtPosition(selec.keyAt(i)));
            }
        }
        return datos;
    }

}
