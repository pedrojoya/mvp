package es.iessaladillo.pedrojoya.pr209.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(primaryKeys = {"alumId", "asigId"},
        foreignKeys = {
                @ForeignKey(entity = Alumno.class, parentColumns = "id", childColumns = "alumId"),
                @ForeignKey(entity = Asignatura.class, parentColumns = "id", childColumns =
                        "asigId")
        })
public class AsignaturaAlumno {

    private String alumId;
    private String asigId;

    public String getAlumId() {
        return alumId;
    }

    public void setAlumId(String alumId) {
        this.alumId = alumId;
    }

    public String getAsigId() {
        return asigId;
    }

    public void setAsigId(String asigId) {
        this.asigId = asigId;
    }

}
