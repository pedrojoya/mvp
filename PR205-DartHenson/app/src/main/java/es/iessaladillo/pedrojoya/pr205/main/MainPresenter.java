package es.iessaladillo.pedrojoya.pr205.main;

import es.iessaladillo.pedrojoya.pr205.model.Alumno;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    public MainPresenter(MainContract.View view) {
        this.mView = view;
    }


    @Override
    public void doSolicitarDatos(Alumno alumno) {
        mView.navigateToSolicitar(alumno);
    }

    @Override
    public void onSolicitarDatosResult(Alumno alumno) {
        mView.showDatos(alumno);
        mView.showMensajeExito();
    }

    @Override
    public void onViewAttach(MainContract.View view) {
        mView = view;
    }

    @Override
    public void onViewDetach() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public MainContract.View getView() {
        return null;
    }

}
