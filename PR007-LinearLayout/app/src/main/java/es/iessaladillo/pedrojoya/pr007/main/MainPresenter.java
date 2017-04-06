package es.iessaladillo.pedrojoya.pr007.main;

import android.text.TextUtils;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    public MainPresenter(MainContract.View view) {
        this.mView = view;
    }

    @Override
    public void onViewAttach(MainContract.View view) {
        mView = view;
    }

    @Override
    public void onViewDetach() {
        mView = null;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public MainContract.View getView() {
        return mView;
    }

    @Override
    public void doAceptar(String usuario, String clave) {
        if (TextUtils.equals(usuario, "baldo") && TextUtils.equals(clave, "mero")) {
            mView.showLoginCorrecto(usuario);
        } else {
            mView.showLoginIncorrecto();
        }
    }

    @Override
    public void doCancelar() {
        mView.resetForm();
    }

}
