package es.iessaladillo.pedrojoya.pr209.detalle;

import es.iessaladillo.pedrojoya.pr209.base.BasePresenter;
import es.iessaladillo.pedrojoya.pr209.db.entities.Alumno;
import es.iessaladillo.pedrojoya.pr209.db.entities.Asignatura;
import io.realm.RealmResults;

@SuppressWarnings("unused")
public interface DetalleContract {

    interface Presenter extends BasePresenter<View> {
        void loadAlumno(String idAlumno);

        void doGuardar(String idAlumno, Alumno alumno, String nombre, String direccion,
                String urlFoto);

        void selectAsignaturas(String idAlumno, Alumno mAlumno);
    }

    interface View {
        void showAlumno(Alumno alumno);

        void showNewAlumno();

        void navigateToAsignaturasAlumnoActivity(String idAlumno);
    }

    interface Usecase {
        void onDestroy();

        RealmResults<Asignatura> getAsignaturas();

        Alumno getAlumno(String idAlumno);

        void addAlumno(Alumno alumno);

        void updateAlumno(Alumno alumno, String nombre, String direccion, String urlFoto);
    }

}
