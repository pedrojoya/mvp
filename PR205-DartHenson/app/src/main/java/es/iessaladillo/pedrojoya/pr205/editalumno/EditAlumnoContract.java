package es.iessaladillo.pedrojoya.pr205.editalumno;

import android.content.Intent;

import es.iessaladillo.pedrojoya.pr205.base.BasePresenter;
import es.iessaladillo.pedrojoya.pr205.model.Alumno;

@SuppressWarnings("unused")
public interface EditAlumnoContract {

    interface Presenter extends BasePresenter<View> {
        void doRetornarDatos(String nombre, int edad);
    }

    interface View {
        void returnIntent(Intent resultIntent);

        void showDatos(Alumno alumno);
    }

}
