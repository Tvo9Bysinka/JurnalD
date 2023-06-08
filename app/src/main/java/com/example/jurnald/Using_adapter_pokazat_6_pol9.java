package com.example.jurnald;

import android.os.Parcel;
import android.os.Parcelable;

public class Using_adapter_pokazat_6_pol9 implements Parcelable {
    String name_1_pole,name_2_pole,name_3_pole,name_4_pole,name_5_pole,name_6_pole;
    Integer id,id_2_pole,id_3_pole,id_4_pole;

    public Using_adapter_pokazat_6_pol9(Integer id,
                                        String name_1_pole, String name_2_pole,String name_3_pole,
                                        String name_4_pole,String name_5_pole,String name_6_pole,
                                        Integer id_2_pole, Integer id_3_pole, Integer id_4_pole) {


        this.id = id;
        this.id_2_pole = id_2_pole;
        this.id_3_pole = id_3_pole;
        this.id_4_pole = id_4_pole;
        this.name_1_pole = name_1_pole;
        this.name_2_pole = name_2_pole;
        this.name_3_pole = name_3_pole;
        this.name_4_pole = name_4_pole;
        this.name_5_pole = name_5_pole;
        this.name_6_pole = name_6_pole;
    }

    public Using_adapter_pokazat_6_pol9(Parcel in) {
        id = in.readInt();
        id_2_pole = in.readInt();
        id_3_pole = in.readInt();
        id_4_pole = in.readInt();
        name_1_pole= in.readString();
        name_2_pole= in.readString();
        name_3_pole= in.readString();
        name_4_pole= in.readString();
        name_5_pole= in.readString();
        name_6_pole= in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.id);
        out.writeInt(this.id_2_pole);
        out.writeInt(this.id_3_pole);
        out.writeInt(this.id_4_pole);
        out.writeString(this.name_1_pole);
        out.writeString(this.name_2_pole);
        out.writeString(this.name_3_pole);
        out.writeString(this.name_4_pole);
        out.writeString(this.name_5_pole);
        out.writeString(this.name_6_pole);


    }

    public String getName_1_pole() {
        return name_1_pole;
    }

    public void setName_1_pole(String name_1_pole) {
        this.name_1_pole = name_1_pole;
    }

    public String getName_2_pole() {
        return name_2_pole;
    }

    public void setName_2_pole(String name_2_pole) {
        this.name_2_pole = name_2_pole;
    }

    public String getName_3_pole() {
        return name_3_pole;
    }

    public void setName_3_pole(String name_3_pole) {
        this.name_3_pole = name_3_pole;
    }

    public String getName_4_pole() {
        return name_4_pole;
    }

    public void setName_4_pole(String name_4_pole) {
        this.name_4_pole = name_4_pole;
    }

    public String getName_5_pole() {
        return name_5_pole;
    }

    public void setName_5_pole(String name_5_pole) {
        this.name_5_pole = name_5_pole;
    }

    public String getName_6_pole() {
        return name_6_pole;
    }

    public void setName_6_pole(String name_6_pole) {
        this.name_6_pole = name_6_pole;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_2_pole() {
        return id_2_pole;
    }

    public void setId_2_pole(Integer id_2_pole) {
        this.id_2_pole = id_2_pole;
    }

    public Integer getId_3_pole() {
        return id_3_pole;
    }

    public void setId_3_pole(Integer id_3_pole) {
        this.id_3_pole = id_3_pole;
    }

    public Integer getId_4_pole() {
        return id_4_pole;
    }

    public void setId_4_pole(Integer id_4_pole) {
        this.id_4_pole = id_4_pole;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Using_adapter_pokazat_6_pol9> CREATOR = new Creator<Using_adapter_pokazat_6_pol9>() {
        @Override
        public Using_adapter_pokazat_6_pol9 createFromParcel(Parcel source) {
            return new Using_adapter_pokazat_6_pol9(source);
        }

        @Override
        public Using_adapter_pokazat_6_pol9[] newArray(int size) {
            return new Using_adapter_pokazat_6_pol9[size];
        }
    };
}