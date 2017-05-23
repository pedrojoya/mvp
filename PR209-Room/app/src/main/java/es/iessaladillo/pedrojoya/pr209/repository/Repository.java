package es.iessaladillo.pedrojoya.pr209.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;

import es.iessaladillo.pedrojoya.pr209.db.entities.Alumno;
import es.iessaladillo.pedrojoya.pr209.db.entities.Asignatura;
import es.iessaladillo.pedrojoya.pr209.db.entities.SelecAsigTuple;

public interface Repository {

    LiveData<List<Alumno>> getAlumnos();
    void deleteAlumno(Alumno alumno);

    LiveData<List<Asignatura>> getAsignaturas();

    LiveData<Alumno> getAlumno(String idAlumno);
    void updateAlumno(Alumno alumno);
    void insertAlumno(Alumno alumno);

    LiveData<List<SelecAsigTuple>> getSelecAsigTuples(String idAlumno);
    void saveSelecAsigTuples(String idAlumno, List<SelecAsigTuple> selecAsigTuples);
}
