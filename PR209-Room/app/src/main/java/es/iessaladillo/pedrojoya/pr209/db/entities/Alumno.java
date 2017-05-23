package es.iessaladillo.pedrojoya.pr209.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Alumno {

    @PrimaryKey
    private String id;
    private String nombre;
    private String direccion;
    private String urlFoto;
    private long timestamp;

    @Ignore
    public Alumno(Alumno alumno) {
        id = alumno.getId();
        nombre = alumno.getNombre();
        direccion = alumno.getDireccion();
        urlFoto = alumno.getUrlFoto();
    }

    @Ignore
    public Alumno(String id) {
        this.id = id;
    }

    @Ignore
    public Alumno(String id, String nombre, String direccion, String urlFoto, long timestamp) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.urlFoto = urlFoto;
        this.timestamp = timestamp;
    }

    public Alumno() { }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
