package com.example.jurnald;

import android.os.Parcel;
import android.os.Parcelable;

public class Using_adapter_pokazat_daty_propyskov implements Parcelable {
    String fio_stud,opis,data,ficsation;
    Integer id,id_zapisi_jurnala;

    public Using_adapter_pokazat_daty_propyskov(Integer id,Integer id_zapisi_jurnala,
                                        String fio_stud, String opis,String ficsation,String data)
                                         {


        this.id = id;
        this.id_zapisi_jurnala = id_zapisi_jurnala;
        this.fio_stud = fio_stud;
        this.opis = opis;
        this.data = data;
        this.ficsation = ficsation;
    }

    public Using_adapter_pokazat_daty_propyskov(Parcel in) {
        id = in.readInt();
        id_zapisi_jurnala = in.readInt();
        fio_stud= in.readString();
        opis= in.readString();
        data= in.readString();
        ficsation= in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.id);
        out.writeInt(this.id_zapisi_jurnala);
        out.writeString(this.fio_stud);
        out.writeString(this.opis);
        out.writeString(this.data);
        out.writeString(this.ficsation);


    }

    public String getFio_stud() {
        return fio_stud;
    }

    public void setFio_stud(String fio_stud) {
        this.fio_stud = fio_stud;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFicsation() {
        return ficsation;
    }

    public void setFicsation(String ficsation) {
        this.ficsation = ficsation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_zapisi_jurnala() {
        return id_zapisi_jurnala;
    }

    public void setId_zapisi_jurnala(Integer id_zapisi_jurnala) {
        this.id_zapisi_jurnala = id_zapisi_jurnala;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Using_adapter_pokazat_daty_propyskov> CREATOR = new Creator<Using_adapter_pokazat_daty_propyskov>() {
        @Override
        public Using_adapter_pokazat_daty_propyskov createFromParcel(Parcel source) {
            return new Using_adapter_pokazat_daty_propyskov(source);
        }

        @Override
        public Using_adapter_pokazat_daty_propyskov[] newArray(int size) {
            return new Using_adapter_pokazat_daty_propyskov[size];
        }
    };
}