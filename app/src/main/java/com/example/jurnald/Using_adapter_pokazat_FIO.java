package com.example.jurnald;

import android.os.Parcel;
import android.os.Parcelable;

public class Using_adapter_pokazat_FIO implements Parcelable {
    String name_pervoe_pole,name_vtoroe_pole,name_tretie_pole;
    Integer id;

    public Using_adapter_pokazat_FIO(Integer id, String name_pervoe_pole,
                                        String name_vtoroe_pole,String name_tretie_pole) {


        this.id = id;
        this.name_pervoe_pole = name_pervoe_pole;
        this.name_vtoroe_pole = name_vtoroe_pole;
        this.name_tretie_pole = name_tretie_pole;
    }

    public Using_adapter_pokazat_FIO(Parcel in) {
        id = in.readInt();
        name_pervoe_pole= in.readString();
        name_vtoroe_pole= in.readString();
        name_tretie_pole= in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.id);
        out.writeString(this.name_pervoe_pole);
        out.writeString(this.name_vtoroe_pole);
        out.writeString(this.name_tretie_pole);
    }

    public String getName_pervoe_pole() {
        return name_pervoe_pole;
    }

    public void setName_pervoe_pole(String name_pervoe_pole) {
        this.name_pervoe_pole = name_pervoe_pole;
    }

    public String getName_vtoroe_pole() {
        return name_vtoroe_pole;
    }

    public void setName_vtoroe_pole(String name_vtoroe_pole) {
        this.name_vtoroe_pole = name_vtoroe_pole;
    }

    public String getName_tretie_pole() {
        return name_tretie_pole;
    }

    public void setName_tretie_pole(String name_tretie_pole) {
        this.name_tretie_pole = name_tretie_pole;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Using_adapter_pokazat_FIO> CREATOR = new Creator<Using_adapter_pokazat_FIO>() {
        @Override
        public Using_adapter_pokazat_FIO createFromParcel(Parcel source) {
            return new Using_adapter_pokazat_FIO(source);
        }

        @Override
        public Using_adapter_pokazat_FIO[] newArray(int size) {
            return new Using_adapter_pokazat_FIO[size];
        }
    };}