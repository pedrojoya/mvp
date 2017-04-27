package es.iessaladillo.pedrojoya.pr158.selec_asig;

import es.iessaladillo.pedrojoya.pr158.base.BasePresenter;
import es.iessaladillo.pedrojoya.pr158.db.entities.Alumno;
import es.iessaladillo.pedrojoya.pr158.db.entities.Asignatura;
import io.realm.RealmList;
import io.realm.RealmResults;

@SuppressWarnings("unused")
public interface SelecAsigContract {

    interface Presenter extends BasePresenter<View> {
        void loadAsignaturas(String idAlumno);

        void doGuardar(Alumno mAlumno, RealmList<Asignatura> asignaturasSeleccionadas);
    }

    interface View {
        void showAsignaturas(RealmResults<Asignatura> asignaturas, Alumno alumno);
    }

    interface Usecase {
        void onDestroy();

        RealmResults<Asignatura> getAsignaturas();

        Alumno getAlumno(String idAlumno);

        void updateAlumno(Alumno alumno, RealmList<Asignatura> asignaturasSeleccionadas);
    }

}
