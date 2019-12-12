package com.example.rincondelvergeles.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Producto implements Parcelable {

    long id;
    String nombre;
    double precio;
    String destino;
    long categoria;

    public Producto(long id, String nombre, double precio, String destino, long categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.destino = destino;
        this.categoria = categoria;
    }

    protected Producto(Parcel in) {
        id = in.readLong();
        nombre = in.readString();
        precio = in.readDouble();
        destino = in.readString();
        categoria = in.readLong();
    }

    public static final Creator<Producto> CREATOR = new Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel in) {
            return new Producto(in);
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };

    public long getId() {
        return id;
    }

    public Producto setId(long id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public Producto setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public double getPrecio() {
        return precio;
    }

    public Producto setPrecio(double precio) {
        this.precio = precio;
        return this;
    }

    public String getDestino() {
        return destino;
    }

    public Producto setDestino(String destino) {
        this.destino = destino;
        return this;
    }

    public long getCategoria() {
        return categoria;
    }

    public Producto setCategoria(long categoria) {
        this.categoria = categoria;
        return this;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", destino='" + destino + '\'' +
                ", categoria=" + categoria +
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
        dest.writeDouble(precio);
        dest.writeString(destino);
        dest.writeLong(categoria);
    }
}

