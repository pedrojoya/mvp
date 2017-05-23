package es.iessaladillo.pedrojoya.pr209.detalle;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import es.iessaladillo.pedrojoya.pr209.App;
import es.iessaladillo.pedrojoya.pr209.db.entities.Alumno;
import es.iessaladillo.pedrojoya.pr209.repository.Repository;

public class DetalleViewModel extends AndroidViewModel {

    private final Repository mRepository;
    private LiveData<Alumno> mAlumno;

    public DetalleViewModel(Application application) {
        super(application);
        mRepository = ((App) application).getRepository();
    }

    public LiveData<Alumno> loadAlumno(String idAlumno) {
        if (mAlumno == null) {
            mAlumno = mRepository.getAlumno(idAlumno);
        }
        return mAlumno;
    }

    public void insertAlumno(Alumno alumno) {
        mRepository.insertAlumno(alumno);
    }

    public void updateAlumno(Alumno alumno) {
        mRepository.updateAlumno(alumno);
    }

}
