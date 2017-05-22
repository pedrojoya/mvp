package es.iessaladillo.pedrojoya.pr208.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<Integer> contador = new MutableLiveData<>();

    public MainActivityViewModel() {
        contador.setValue(0);
    }

    public LiveData<Integer> getContador() {
        return contador;
    }

    public void increment() {
        contador.setValue(contador.getValue() + 1);
    }
}
