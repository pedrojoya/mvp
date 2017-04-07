package es.iessaladillo.pedrojoya.pr010.main;

import es.iessaladillo.pedrojoya.pr010.base.BasePresenter;

@SuppressWarnings("unused")
public interface MainContract {

    interface Presenter extends BasePresenter<View> {
        void doEnviar(String texto);
    }

    interface View {
        void addMensaje(String mensaje, String hora);
    }

}
