package es.iessaladillo.pedrojoya.pr209.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(primaryKeys = {"alumId", "asigId"},
        indices = {@Index("alumId"), @Index("asigId")},
        foreignKeys = {
                @ForeignKey(entity = Alumno.class, parentColumns = "id", childColumns = "alumId", onDelete = CASCADE),
                @ForeignKey(entity = Asignatura.class, parentColumns = "id", childColumns =
                        "asigId")
        })
public class AsignaturaAlumno {

    private String alumId;
    private String asigId;

    @Ignore
    public AsignaturaAlumno(String alumId, String asigId) {
        this.alumId = alumId;
        this.asigId = asigId;
    }

    public AsignaturaAlumno() {
    }

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
