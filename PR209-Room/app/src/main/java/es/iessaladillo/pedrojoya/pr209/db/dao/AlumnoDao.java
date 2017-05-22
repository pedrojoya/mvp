package es.iessaladillo.pedrojoya.pr209.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import es.iessaladillo.pedrojoya.pr209.db.entities.Alumno;
import es.iessaladillo.pedrojoya.pr209.db.entities.Asignatura;

@Dao
public interface AlumnoDao {

    @Query("SELECT * FROM Alumno")
    LiveData<List<Alumno>> getAlumnos();

    @Query("SELECT * FROM Alumno WHERE id = :alumnoId")
    Alumno getAlumno(String alumnoId);

    @Query("SELECT Asignatura.id, Asignatura.nombre FROM AsignaturaAlumno "
            + "INNER JOIN Asignatura ON AsignaturaAlumno.asigId = Asignatura.id "
            + "INNER JOIN Alumno ON AsignaturaAlumno.alumId = Alumno.id "
            + "WHERE Alumno.id LIKE :alumnoId")
    LiveData<List<Asignatura>> getAsignaturasAlumno(String alumnoId);

    @Insert
    void insert(Alumno alumno);

    @Update
    void update(Alumno alumno);

    @Delete
    void delete(Alumno alumno);

}
