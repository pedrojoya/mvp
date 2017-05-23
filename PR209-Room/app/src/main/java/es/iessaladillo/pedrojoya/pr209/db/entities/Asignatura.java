package es.iessaladillo.pedrojoya.pr209.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Asignatura {

    @PrimaryKey
    private String id;
    private String nombre;

    @Ignore
    public Asignatura(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Asignatura() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return id;
    }

}
