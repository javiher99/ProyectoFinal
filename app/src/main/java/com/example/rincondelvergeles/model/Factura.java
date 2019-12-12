package com.example.rincondelvergeles.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Factura implements Parcelable {

    long id;
    String horaInicio;
    String horaCierre;
    double total;
    long mesa;
    long idEmpleadoInicio;
    long idEmpleadoFinal;

    public Factura(long id, String horaInicio, String horaCierre, double total, long mesa, long idEmpleadoInicio, long idEmpleadoFinal) {
        this.id = id;
        this.horaInicio = horaInicio;
        this.horaCierre = horaCierre;
        this.total = total;
        this.mesa = mesa;
        this.idEmpleadoInicio = idEmpleadoInicio;
        this.idEmpleadoFinal = idEmpleadoFinal;
    }

    protected Factura(Parcel in) {
        id = in.readLong();
        horaInicio = in.readString();
        horaCierre = in.readString();
        total = in.readDouble();
        mesa = in.readLong();
        idEmpleadoInicio = in.readLong();
        idEmpleadoFinal = in.readLong();
    }

    public static final Creator<Factura> CREATOR = new Creator<Factura>() {
        @Override
        public Factura createFromParcel(Parcel in) {
            return new Factura(in);
        }

        @Override
        public Factura[] newArray(int size) {
            return new Factura[size];
        }
    };

    public long getId() {
        return id;
    }

    public Factura setId(long id) {
        this.id = id;
        return this;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public Factura setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
        return this;
    }

    public String getHoraCierre() {
        return horaCierre;
    }

    public Factura setHoraCierre(String horaCierre) {
        this.horaCierre = horaCierre;
        return this;
    }

    public double getTotal() {
        return total;
    }

    public Factura setTotal(double total) {
        this.total = total;
        return this;
    }

    public long getMesa() {
        return mesa;
    }

    public Factura setMesa(long mesa) {
        this.mesa = mesa;
        return this;
    }

    public long getIdEmpleadoInicio() {
        return idEmpleadoInicio;
    }

    public Factura setIdEmpleadoInicio(long idEmpleadoInicio) {
        this.idEmpleadoInicio = idEmpleadoInicio;
        return this;
    }

    public long getIdEmpleadoFinal() {
        return idEmpleadoFinal;
    }

    public Factura setIdEmpleadoFinal(long idEmpleadoFinal) {
        this.idEmpleadoFinal = idEmpleadoFinal;
        return this;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "id=" + id +
                ", horaInicio='" + horaInicio + '\'' +
                ", horaCierre='" + horaCierre + '\'' +
                ", total=" + total +
                ", mesa=" + mesa +
                ", idEmpleadoInicio=" + idEmpleadoInicio +
                ", idEmpleadoFinal=" + idEmpleadoFinal +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(horaInicio);
        dest.writeString(horaCierre);
        dest.writeDouble(total);
        dest.writeLong(mesa);
        dest.writeLong(idEmpleadoInicio);
        dest.writeLong(idEmpleadoFinal);
    }
}

