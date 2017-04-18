package es.iessaladillo.pedrojoya.pr011.main;

import es.iessaladillo.pedrojoya.pr011.base.BasePresenter;

@SuppressWarnings("unused")
public interface MainContract {

    interface Presenter extends BasePresenter<View> {
        void doAgregar(String nombre);

        void doOnAlumnoClicked(String item);
    }

    interface View {
    }

}
