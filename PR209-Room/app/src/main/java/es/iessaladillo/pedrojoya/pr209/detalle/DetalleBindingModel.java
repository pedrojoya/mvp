package es.iessaladillo.pedrojoya.pr209.detalle;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import es.iessaladillo.pedrojoya.pr209.BR;

public class DetalleBindingModel extends BaseObservable {

    private String nombre;
    private String direccion;
    private String urlFoto;

    @Bindable
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        notifyPropertyChanged(BR.nombre);
    }

    @Bindable
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
        notifyPropertyChanged(BR.direccion);
    }

    @Bindable
    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
        notifyPropertyChanged(BR.urlFoto);
    }

}
