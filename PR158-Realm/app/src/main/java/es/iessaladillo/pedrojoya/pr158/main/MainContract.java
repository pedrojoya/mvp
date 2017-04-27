package es.iessaladillo.pedrojoya.pr158.main;

import es.iessaladillo.pedrojoya.pr158.base.BasePresenter;
import es.iessaladillo.pedrojoya.pr158.db.entities.Alumno;
import io.realm.RealmResults;

@SuppressWarnings("unused")
public interface MainContract {

    interface Presenter extends BasePresenter<View> {
        RealmResults<Alumno> getDatos();

        void doAgregar();

        void doDetalle(Alumno alumno);

        void doEliminarAlumno(Alumno alumno);

        void showAsignaturas(Alumno alumno);
    }

    interface View {
        void navigateToDetalleActivity();

        void navigateToDetalleActivity(String idAlumno);

        void navigateToAsignaturasAlumnoActivity(String idAlumno);
    }

    interface Usecase {
        RealmResults<Alumno> getAlumnos();

        void onDestroy();

        void eliminarAlumno(Alumno alumno);
    }

}
