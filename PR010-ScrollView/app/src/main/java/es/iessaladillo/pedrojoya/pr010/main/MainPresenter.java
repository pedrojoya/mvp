package es.iessaladillo.pedrojoya.pr010.main;

import android.text.TextUtils;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.iessaladillo.pedrojoya.pr010.R;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private SimpleDateFormat mFormateadorFechas;

    public MainPresenter(MainContract.View view) {
        this.mView = view;
        mFormateadorFechas = new SimpleDateFormat("HH:mm:ss",
                Locale.getDefault());
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
    public void doEnviar(String mensaje) {
        mView.addMensaje(mensaje, mFormateadorFechas.format(new Date()));
    }

}
