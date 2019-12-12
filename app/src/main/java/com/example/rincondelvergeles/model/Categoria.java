package com.example.rincondelvergeles.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Categoria implements Parcelable {

    long id;
    String nombre;

    public Categoria(long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    protected Categoria(Parcel in) {
        id = in.readLong();
        nombre = in.readString();
    }

    public static final Creator<Categoria> CREATOR = new Creator<Categoria>() {
        @Override
        public Categoria createFromParcel(Parcel in) {
            return new Categoria(in);
        }

        @Override
        public Categoria[] newArray(int size) {
            return new Categoria[size];
        }
    };

    public long getId() {
        return id;
    }

    public Categoria setId(long id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public Categoria setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(nombre);
    }
}

