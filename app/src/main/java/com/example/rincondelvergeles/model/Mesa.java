package com.example.rincondelvergeles.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Mesa implements Parcelable {

    long id;
    String mesa;
    long ocupada;

    public Mesa(long id, String mesa, long ocupada) {
        this.id = id;
        this.mesa = mesa;
        this.ocupada = ocupada;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public long getOcupada() {
        return ocupada;
    }

    public void setOcupada() {
        this.ocupada = 1;
    }

    public void setLibre() {
        this.ocupada = 0;
    }

    protected Mesa(Parcel in) {
        id = in.readLong();
        mesa = in.readString();
        ocupada = in.readLong();
    }

    public static final Creator<Mesa> CREATOR = new Creator<Mesa>() {
        @Override
        public Mesa createFromParcel(Parcel in) {
            return new Mesa(in);
        }

        @Override
        public Mesa[] newArray(int size) {
            return new Mesa[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(mesa);
        dest.writeLong(ocupada);
    }

    @Override
    public String toString() {
        return "Mesa{" +
                "id=" + id +
                ", mesa='" + mesa + '\'' +
                ", ocupada=" + ocupada +
                '}';
    }
}

