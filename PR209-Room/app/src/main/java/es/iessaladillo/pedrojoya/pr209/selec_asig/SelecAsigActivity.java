package es.iessaladillo.pedrojoya.pr209.selec_asig;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import activitystarter.ActivityStarter;
import activitystarter.Arg;
import es.iessaladillo.pedrojoya.pr209.R;
import es.iessaladillo.pedrojoya.pr209.base.AppCompatLifecycleActivity;
import es.iessaladillo.pedrojoya.pr209.databinding.ActivitySelecAsigBinding;
import es.iessaladillo.pedrojoya.pr209.db.entities.SelecAsigTuple;

public class SelecAsigActivity extends AppCompatLifecycleActivity {

    @Arg
    String idAlumno;

    private ArrayAdapter<SelecAsigTuple> mAdaptador;
    private SelecAsigViewModel mViewModel;
    private ActivitySelecAsigBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SelecAsigViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_selec_asig);
        ActivityStarter.fill(this, savedInstanceState);
        initVistas();
        mViewModel.loadSelecAsigTuples(idAlumno).observe(this, selecAsigTuples -> {
            mAdaptador.clear();
            if (selecAsigTuples != null) {
                mAdaptador.addAll(selecAsigTuples);
                for (int i = 0; i < selecAsigTuples.size(); i++) {
                    if (selecAsigTuples.get(i).alumId != null) {
                        mBinding.lstAsignaturas.setItemChecked(i, true);
                    }
                }
            }
        });
    }

    private void initVistas() {
        mBinding.lstAsignaturas.setEmptyView(mBinding.lblEmptyView);
        mBinding.lstAsignaturas.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        mAdaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice,
                new ArrayList<>());
        mBinding.lstAsignaturas.setAdapter(mAdaptador);
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
        SparseBooleanArray selec = mBinding.lstAsignaturas.getCheckedItemPositions();
        for (int i = 0; i < selec.size(); i++) {
            if (selec.valueAt(i)) {
                datos.add(
                        (SelecAsigTuple) mBinding.lstAsignaturas.getItemAtPosition(selec.keyAt(i)));
            }
        }
        return datos;
    }

}
