package es.iessaladillo.pedrojoya.pr206.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Alumno implements Parcelable {

    public static final int DEFAULT_EDAD = 18;
    public static final int MAX_EDAD = 130;

    private final String nombre;
    private final int edad;

    @SuppressWarnings("SameParameterValue")
    public Alumno(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeInt(this.edad);
    }

    private Alumno(Parcel in) {
        this.nombre = in.readString();
        this.edad = in.readInt();
    }

    public static final Parcelable.Creator<Alumno> CREATOR = new Parcelable.Creator<Alumno>() {
        @Override
        public Alumno createFromParcel(Parcel source) {
            return new Alumno(source);
        }

        @Override
        public Alumno[] newArray(int size) {
            return new Alumno[size];
        }
    };
}
