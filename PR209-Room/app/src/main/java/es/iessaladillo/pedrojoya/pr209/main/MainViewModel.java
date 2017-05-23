package es.iessaladillo.pedrojoya.pr209.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import es.iessaladillo.pedrojoya.pr209.App;
import es.iessaladillo.pedrojoya.pr209.db.entities.Alumno;
import es.iessaladillo.pedrojoya.pr209.repository.Repository;

public class MainViewModel extends AndroidViewModel {

    private final Repository mRepository;
    private final LiveData<List<Alumno>> mAlumnos;

    public MainViewModel(Application application) {
        super(application);
        mRepository = ((App) application).getRepository();
        mAlumnos = mRepository.getAlumnos();
    }

    public LiveData<List<Alumno>> loadAlumnos() {
        return mAlumnos;
    }

    public void deleteAlumno(Alumno alumno) {
        mRepository.deleteAlumno(alumno);
    }

}
