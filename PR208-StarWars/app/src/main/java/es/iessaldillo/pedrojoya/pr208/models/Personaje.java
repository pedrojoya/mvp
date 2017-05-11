package es.iessaldillo.pedrojoya.pr208.models;

public class Personaje {

    private String nombre;
    private String planeta;

    public Personaje(String nombre, String planeta) {
        this.nombre = nombre;
        this.planeta = planeta;
    }

    public static Personaje newInstance(String nombre, String planeta) {
        return new Personaje(nombre, planeta);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPlaneta() {
        return planeta;
    }

    public void setPlaneta(String planeta) {
        this.planeta = planeta;
    }
}
