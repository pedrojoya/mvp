package es.iessaladillo.pedrojoya.pr209.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import es.iessaladillo.pedrojoya.pr209.db.entities.AsignaturaAlumno;
import es.iessaladillo.pedrojoya.pr209.db.entities.SelecAsigTuple;

@Dao
public interface AsignaturaAlumnoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(AsignaturaAlumno asignatura);

    @Query("DELETE FROM AsignaturaAlumno WHERE alumId = :alumnoId")
    int deleteAsignaturasAlumno(String alumnoId);

    @Query("SELECT id, nombre, alumId "
            + "FROM Asignatura INNER JOIN AsignaturaAlumno "
            + "ON Asignatura.id = AsignaturaAlumno.asigId "
            + "WHERE AsignaturaAlumno.alumId = :alumnoId " + "UNION "
            + "SELECT id, nombre, null AS alumId " + "FROM Asignatura "
            + "WHERE Asignatura.id NOT IN (" + "SELECT asigId FROM AsignaturaAlumno "
            + "WHERE alumId = :alumnoId)")
    LiveData<List<SelecAsigTuple>> getSelecAsigTuples(String alumnoId);

}
