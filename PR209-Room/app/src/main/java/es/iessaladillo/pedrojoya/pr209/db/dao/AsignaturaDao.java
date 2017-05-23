package es.iessaladillo.pedrojoya.pr209.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import es.iessaladillo.pedrojoya.pr209.db.entities.Asignatura;

@Dao
public interface AsignaturaDao {

    @Query("SELECT * FROM Asignatura")
    LiveData<List<Asignatura>> getAsignaturas();

    @Query("SELECT * FROM Asignatura WHERE id = :asignaturaId")
    LiveData<Asignatura> getAsignatura(String asignaturaId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Asignatura asignatura);

    @Update
    int update(Asignatura asignatura);

    @Delete
    int delete(Asignatura asignatura);

}
