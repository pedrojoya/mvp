package es.iessaladillo.pedrojoya.pr206.main;

import es.iessaladillo.pedrojoya.pr206.base.BasePresenter;
import es.iessaladillo.pedrojoya.pr206.model.Alumno;

@SuppressWarnings("unused")
public interface MainContract {

    interface Presenter extends BasePresenter<View> {
        void doSolicitarDatos(Alumno alumno);

        void onSolicitarDatosResult(Alumno alumno);
    }

    interface View {
        void navigateToSolicitar(Alumno alumno);

        void showDatos(Alumno alumno);

        void showMensajeExito();
    }

}
