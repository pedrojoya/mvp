package es.iessaladillo.pedrojoya.pr158.db.entities;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

// Modelo Alumno.
@SuppressWarnings({"WeakerAccess", "unused"})
public class Alumno extends RealmObject {

    @PrimaryKey
    @Required
    private String id;
    @Required
    private String nombre;
    private String direccion;
    private String urlFoto;
    private RealmList<Asignatura> asignaturas;

    private long timestamp;

    public Alumno(Alumno alumno) {
        id = alumno.getId();
        nombre = alumno.getNombre();
        direccion = alumno.getDireccion();
        urlFoto = alumno.getUrlFoto();
        asignaturas = alumno.getAsignaturas();
    }

    public Alumno(String id) {
        this.id = id;
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

    public RealmList<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(RealmList<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }

}
