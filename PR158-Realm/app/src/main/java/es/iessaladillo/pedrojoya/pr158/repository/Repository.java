package es.iessaladillo.pedrojoya.pr158.repository;

import es.iessaladillo.pedrojoya.pr158.db.entities.Alumno;
import es.iessaladillo.pedrojoya.pr158.main.MainContract;
import io.realm.Realm;
import io.realm.RealmResults;

public class Repository implements MainContract.Usecase {

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
    public void eliminarAlumno(final Alumno alumno) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                alumno.deleteFromRealm();
            }
        });
    }

}
