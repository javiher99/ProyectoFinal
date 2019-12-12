package com.example.rincondelvergeles.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Comanda implements Parcelable {

    long id;
    long idFactura;
    long idProducto;
    String nombreProducto;
    long idEmpleado;
    long unidades;
    long entregada;
    double precio;

    public Comanda(long id, long idFactura, long idProducto, String nombreProducto, long idEmpleado, long unidades, long entregada, double precio) {
        this.id = id;
        this.idFactura = idFactura;
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.idEmpleado = idEmpleado;
        this.unidades = unidades;
        this.entregada = entregada;
        this.precio = precio;
    }

    protected Comanda(Parcel in) {
        id = in.readLong();
        idFactura = in.readLong();
        idProducto = in.readLong();
        nombreProducto = in.readString();
        idEmpleado = in.readLong();
        unidades = in.readLong();
        entregada = in.readLong();
        precio = in.readDouble();
    }

    public static final Creator<Comanda> CREATOR = new Creator<Comanda>() {
        @Override
        public Comanda createFromParcel(Parcel in) {
            return new Comanda(in);
        }

        @Override
        public Comanda[] newArray(int size) {
            return new Comanda[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(long idFactura) {
        this.idFactura = idFactura;
    }

    public long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public long getUnidades() {
        return unidades;
    }

    public void setUnidades(long unidades) {
        this.unidades = unidades;
    }

    public long getEntregada() {
        return entregada;
    }

    public void setEntregada(long entregada) {
        this.entregada = entregada;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeLong(idFactura);
        parcel.writeLong(idProducto);
        parcel.writeString(nombreProducto);
        parcel.writeLong(idEmpleado);
        parcel.writeLong(unidades);
        parcel.writeLong(entregada);
        parcel.writeDouble(precio);
    }
}

