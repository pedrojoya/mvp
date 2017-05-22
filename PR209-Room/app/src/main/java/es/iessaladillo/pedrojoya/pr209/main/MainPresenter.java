package es.iessaladillo.pedrojoya.pr209.main;

import es.iessaladillo.pedrojoya.pr209.db.entities.Alumno;
import es.iessaladillo.pedrojoya.pr209.repository.Repository;
import io.realm.RealmResults;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private final MainContract.Usecase mUseCase;

    public MainPresenter(MainContract.View view) {
        this.mView = view;
        this.mUseCase = new Repository();
    }

    @Override
    public void onViewAttach(MainContract.View view) {
        mView = view;
    }

    @Override
    public void onViewDetach() {
        mView = new MainContract.View() {
            @Override
            public void navigateToDetalleActivity() { }

            @Override
            public void navigateToDetalleActivity(String idAlumno) { }

            @Override
            public void navigateToAsignaturasAlumnoActivity(String idAlumno) { }

        };
    }

    @Override
    public void onDestroy() {
        mUseCase.onDestroy();
    }

    @Override
    public MainContract.View getView() {
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

    @Override
    public void showAsignaturas(Alumno alumno) {
        mView.navigateToAsignaturasAlumnoActivity(alumno.getId());
    }

}
