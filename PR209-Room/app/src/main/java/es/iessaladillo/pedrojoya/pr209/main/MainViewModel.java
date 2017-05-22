package es.iessaladillo.pedrojoya.pr209.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import es.iessaladillo.pedrojoya.pr209.db.entities.Alumno;

public class MainViewModel extends ViewModel {

    private LiveData<List<Alumno>> data = new MutableLiveData<>();

    public MainViewModel() {
        contador.setValue(0);
    }

    public LiveData<Integer> getContador() {
        return contador;
    }

    public void increment() {
        contador.setValue(contador.getValue() + 1);
    }
}
