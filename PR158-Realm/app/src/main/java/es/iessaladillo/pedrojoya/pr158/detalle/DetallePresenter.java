package es.iessaladillo.pedrojoya.pr158.detalle;

import es.iessaladillo.pedrojoya.pr158.db.entities.Alumno;
import es.iessaladillo.pedrojoya.pr158.repository.Repository;
import io.realm.RealmResults;

public class DetallePresenter implements DetalleContract.Presenter {

    private DetalleContract.View mView;
    private final DetalleContract.Usecase mUseCase;

    public DetallePresenter(DetalleContract.View view) {
        this.mView = view;
        this.mUseCase = new Repository();
    }

    @Override
    public void onViewAttach(DetalleContract.View view) {
        mView = view;
    }

    @Override
    public void onViewDetach() {
        mView = null;
    }

    @Override
    public void onDestroy() {
        mUseCase.onDestroy();
    }

    @Override
    public DetalleContract.View getView() {
        return mView;
    }

    @Override
    public RealmResults<Alumno> getDatos() {
        return mUseCase.getAlumnos();
    }

    @Override
    public void doAgregar() {
        mView.navigateToDetalleActivity();
    }

    @Override
    public void doDetalle(Alumno alumno) {
        mView.navigateToDetalleActivity(alumno.getId());
    }

    @Override
    public void doEliminarAlumno(Alumno alumno) {
        mUseCase.eliminarAlumno(alumno);
    }

}
