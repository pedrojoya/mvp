package es.iessaladillo.pedrojoya.pr158.repository;

import es.iessaladillo.pedrojoya.pr158.db.entities.Alumno;
import es.iessaladillo.pedrojoya.pr158.db.entities.Asignatura;
import es.iessaladillo.pedrojoya.pr158.detalle.DetalleContract;
import es.iessaladillo.pedrojoya.pr158.main.MainContract;
import es.iessaladillo.pedrojoya.pr158.selec_asig.SelecAsigContract;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class Repository implements MainContract.Usecase, DetalleContract.Usecase, SelecAsigContract.Usecase {

    private final Realm mRealm;

    public Repository() {
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public RealmResults<Alumno> getAlumnos() {
        return mRealm.where(Alumno.class).findAllSortedAsync("nombre");
    }

    @Override
    public void onDestroy() {
        mRealm.close();
    }

    @Override
    public RealmResults<Asignatura> getAsignaturas() {
        return mRealm.where(Asignatura.class).findAllSorted("nombre");
    }

    @Override
    public Alumno getAlumno(String idAlumno) {
        return mRealm.where(Alumno.class).equalTo("id", idAlumno).findFirst();
    }

    @Override
    public void updateAlumno(Alumno alumno, RealmList<Asignatura> asignaturasSeleccionadas) {
        mRealm.executeTransaction(realm -> {
            alumno.setAsignaturas(asignaturasSeleccionadas);
        });
    }

    @Override
    public void saveAlumno(Alumno alumno) {
        mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(alumno));
    }

    @Override
    public void addAlumno(Alumno alumno) {
        mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(alumno));
    }

    @Override
    public void updateAlumno(Alumno alumno, String nombre, String direccion, String urlFoto) {
        mRealm.executeTransaction(realm -> {
            alumno.setNombre(nombre);
            alumno.setDireccion(direccion);
            alumno.setUrlFoto(urlFoto);
        });
    }

    @Override
    public void eliminarAlumno(final Alumno alumno) {
        mRealm.executeTransaction(realm -> alumno.deleteFromRealm());
    }

}
