package es.iessaladillo.pedrojoya.pr209.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;

import es.iessaladillo.pedrojoya.pr209.db.entities.AsignaturaAlumno;

@Dao
public interface AsignaturaAlumnoDao {

    @Insert
    void insert(AsignaturaAlumno asignatura);

    @Delete
    void delete(AsignaturaAlumno asignatura);

}
