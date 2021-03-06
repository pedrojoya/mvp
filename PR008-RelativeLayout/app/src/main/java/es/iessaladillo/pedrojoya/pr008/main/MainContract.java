package es.iessaladillo.pedrojoya.pr008.main;

import es.iessaladillo.pedrojoya.pr008.base.BasePresenter;

@SuppressWarnings("unused")
public interface MainContract {

    interface Presenter extends BasePresenter<View> {
        void doAceptar(String usuario, String clave);

        void doCancelar();
    }

    interface View {
        void resetForm();

        void showLoginCorrecto(String usuario);

        void showLoginIncorrecto();
    }

}
