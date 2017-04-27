package es.iessaladillo.pedrojoya.pr158.selec_asig;

import es.iessaladillo.pedrojoya.pr158.db.entities.Alumno;
import es.iessaladillo.pedrojoya.pr158.db.entities.Asignatura;
import es.iessaladillo.pedrojoya.pr158.repository.Repository;
import io.realm.RealmList;
import io.realm.RealmResults;

public class SelecAsigPresenter implements SelecAsigContract.Presenter {

    private SelecAsigContract.View mView;
    private final SelecAsigContract.Usecase mUseCase;

    public SelecAsigPresenter(SelecAsigContract.View view) {
        this.mView = view;
        this.mUseCase = new Repository();
    }

    @Override
    public void onViewAttach(SelecAsigContract.View view) {
        mView = view;
    }

    @Override
    public void onViewDetach() {
        mView = new SelecAsigContract.View() {
            @Override
            public void showAsignaturas(RealmResults<Asignatura> asignaturas, Alumno alumno) {

            }
        };
    }

    @Override
    public void onDestroy() {
        mUseCase.onDestroy();
    }

    @Override
    public SelecAsigContract.View getView() {
        return mView;
    }

    @Override
    public void loadAsignaturas(String idAlumno) {
        mView.showAsignaturas(mUseCase.getAsignaturas(), mUseCase.getAlumno(idAlumno));
    }

    @Override
    public void doGuardar(Alumno mAlumno, RealmList<Asignatura> asignaturasSeleccionadas) {
        mUseCase.updateAlumno(mAlumno, asignaturasSeleccionadas);
    }

}
