package es.iessaladillo.pedrojoya.pr209.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import es.iessaladillo.pedrojoya.pr209.db.dao.AlumnoDao;
import es.iessaladillo.pedrojoya.pr209.db.dao.AsignaturaAlumnoDao;
import es.iessaladillo.pedrojoya.pr209.db.dao.AsignaturaDao;
import es.iessaladillo.pedrojoya.pr209.db.entities.Alumno;
import es.iessaladillo.pedrojoya.pr209.db.entities.Asignatura;
import es.iessaladillo.pedrojoya.pr209.db.entities.AsignaturaAlumno;

@Database(entities = {Alumno.class, Asignatura.class, AsignaturaAlumno.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AlumnoDao alumnoDao();
    public abstract AsignaturaDao asignaturaDao();
    public abstract AsignaturaAlumnoDao asignaturaAlumnoDao();
}
