package es.iessaladillo.pedrojoya.pr209.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;

import es.iessaladillo.pedrojoya.pr209.db.AppDatabase;
import es.iessaladillo.pedrojoya.pr209.db.entities.Alumno;
import es.iessaladillo.pedrojoya.pr209.db.entities.Asignatura;
import es.iessaladillo.pedrojoya.pr209.db.entities.AsignaturaAlumno;
import es.iessaladillo.pedrojoya.pr209.db.entities.SelecAsigTuple;

public class RepositoryImpl implements Repository {

    private final AppDatabase db;

    public RepositoryImpl(AppDatabase db) {
        this.db = db;
    }

    @Override
    public LiveData<List<Alumno>> getAlumnos() {
        return db.alumnoDao().getAlumnos();
    }

    @Override
    public void deleteAlumno(Alumno alumno) {
        db.alumnoDao().delete(alumno);
    }

    @Override
    public LiveData<List<Asignatura>> getAsignaturas() {
        return db.asignaturaDao().getAsignaturas();
    }

    @Override
    public LiveData<Alumno> getAlumno(String idAlumno) {
        return db.alumnoDao().getAlumno(idAlumno);
    }
    @Override
    public void updateAlumno(Alumno alumno) {
        db.alumnoDao().update(alumno);
    }

    @Override
    public void insertAlumno(Alumno alumno) {
        db.alumnoDao().insert(alumno);
    }

    @Override
    public LiveData<List<SelecAsigTuple>> getSelecAsigTuples(String idAlumno) {
        return db.asignaturaAlumnoDao().getSelecAsigTuples(idAlumno);
    }

    @Override
    public void saveSelecAsigTuples(String idAlumno, List<SelecAsigTuple> selecAsigTuples) {
        db.beginTransaction();
        try {
            db.asignaturaAlumnoDao().deleteAsignaturasAlumno(idAlumno);
            for (SelecAsigTuple tuple : selecAsigTuples) {
                db.asignaturaAlumnoDao().insert(new AsignaturaAlumno(idAlumno, tuple.id));
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }


}
