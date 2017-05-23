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

    @Query("SELECT Asignatura.id, Asignatura.nombre, AsignaturaAlumno.alumId FROM "
            + "Asignatura LEFT JOIN AsignaturaAlumno ON Asignatura.id = AsignaturaAlumno.asigId "
            + "WHERE AsignaturaAlumno.alumId IS NULL OR AsignaturaAlumno.alumId = :alumnoId")
    LiveData<List<SelecAsigTuple>> getSelecAsigTuples(String alumnoId);

}
