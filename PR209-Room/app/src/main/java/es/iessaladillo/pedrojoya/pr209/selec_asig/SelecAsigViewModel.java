package es.iessaladillo.pedrojoya.pr209.selec_asig;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import es.iessaladillo.pedrojoya.pr209.App;
import es.iessaladillo.pedrojoya.pr209.db.entities.SelecAsigTuple;
import es.iessaladillo.pedrojoya.pr209.repository.Repository;

public class SelecAsigViewModel extends AndroidViewModel {

    private final Repository mRepository;
    private LiveData<List<SelecAsigTuple>> mSelecAsigTuples;

    public SelecAsigViewModel(Application application) {
        super(application);
        mRepository = ((App) application).getRepository();
    }

    public LiveData<List<SelecAsigTuple>> loadSelecAsigTuples(String idAlumno) {
        if (mSelecAsigTuples == null) {
            mSelecAsigTuples = mRepository.getSelecAsigTuples(idAlumno);
        }
        return mSelecAsigTuples;
    }

    public void saveSelecAsigTuples(String idAlumno, List<SelecAsigTuple> selecAsigTuples) {
        mRepository.saveSelecAsigTuples(idAlumno, selecAsigTuples);
    }

}
