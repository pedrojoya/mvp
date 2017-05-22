package es.iessaladillo.pedrojoya.pr208.ui.main;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import es.iessaladillo.pedrojoya.pr208.BR;

public class MainActivityBindingVariable extends BaseObservable {

    private int contador;

    @Bindable
    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
        notifyPropertyChanged(BR.contador);
    }

}
