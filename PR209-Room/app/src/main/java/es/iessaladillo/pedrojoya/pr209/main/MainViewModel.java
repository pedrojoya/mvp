package es.iessaladillo.pedrojoya.pr209.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import es.iessaladillo.pedrojoya.pr209.db.entities.Alumno;
import es.iessaladillo.pedrojoya.pr209.repository.Repository;

public class MainViewModel extends ViewModel {

    private Repository mRepository;
    private LiveData<List<Alumno>> mAlumnos = new MutableLiveData<>();

    public MainViewModel(Repository repository) {
        mRepository = repository;
        mAlumnos = mRepository.loadAlumnos();
    }

    public LiveData<List<Alumno>> loadAlumnos() {
        return mAlumnos;
    }

}
