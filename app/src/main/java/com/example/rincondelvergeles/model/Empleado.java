package com.example.rincondelvergeles.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Empleado implements Parcelable {

    long id;
    String login;
    String pass;

    public Empleado(long id, String login, String pass) {
        this.id = id;
        this.login = login;
        this.pass = pass;
    }

    protected Empleado(Parcel in) {
        id = in.readLong();
        login = in.readString();
        pass = in.readString();
    }

    public static final Creator<Empleado> CREATOR = new Creator<Empleado>() {
        @Override
        public Empleado createFromParcel(Parcel in) {
            return new Empleado(in);
        }

        @Override
        public Empleado[] newArray(int size) {
            return new Empleado[size];
        }
    };

    public long getId() {
        return id;
    }

    public Empleado setId(long id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public Empleado setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPass() {
        return pass;
    }

    public Empleado setPass(String pass) {
        this.pass = pass;
        return this;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(login);
        dest.writeString(pass);
    }
}

