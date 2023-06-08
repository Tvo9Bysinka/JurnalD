package com.example.jurnald;

import android.os.Parcel;
import android.os.Parcelable;

public class Using_adapter_pokazat_4_pol9 implements Parcelable {
    String name_pervoe_pole,name_vtoroe_pole,name_tretie_pole,name_4tvertoe_pole;
    Integer id,id_vtoroe_pole,id_tretie_pole,id_4tvertoe_pole;

    public Using_adapter_pokazat_4_pol9(Integer id, String name_pervoe_pole,
                                        String name_vtoroe_pole,String name_tretie_pole,String name_4tvertoe_pole,
                                        Integer id_vtoroe_pole, Integer id_tretie_pole, Integer id_4tvertoe_pole) {

        //      spisok_predmeta.add(new Using_adapter_dobavleni9_predmeta(x.getId(), x.getName_group(), y.getName(), x.getId_napravlenie()));

        this.id = id;
        this.id_vtoroe_pole = id_vtoroe_pole;
        this.id_tretie_pole = id_tretie_pole;
        this.id_4tvertoe_pole = id_4tvertoe_pole;
        this.name_pervoe_pole = name_pervoe_pole;
        this.name_vtoroe_pole = name_vtoroe_pole;
        this.name_tretie_pole = name_tretie_pole;
        this.name_4tvertoe_pole = name_4tvertoe_pole;
    }

    public Using_adapter_pokazat_4_pol9(Parcel in) {
        id = in.readInt();
        id_vtoroe_pole = in.readInt();
        id_tretie_pole = in.readInt();
        id_4tvertoe_pole = in.readInt();
        name_pervoe_pole= in.readString();
        name_vtoroe_pole= in.readString();
        name_tretie_pole= in.readString();
        name_4tvertoe_pole= in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.id);
        out.writeInt(this.id_vtoroe_pole);
        out.writeInt(this.id_tretie_pole);
        out.writeInt(this.id_4tvertoe_pole);
        out.writeString(this.name_pervoe_pole);
        out.writeString(this.name_vtoroe_pole);
        out.writeString(this.name_tretie_pole);
        out.writeString(this.name_4tvertoe_pole);

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

    public String getName_4tvertoe_pole() {
        return name_4tvertoe_pole;
    }

    public void setName_4tvertoe_pole(String name_4tvertoe_pole) {
        this.name_4tvertoe_pole = name_4tvertoe_pole;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_vtoroe_pole() {
        return id_vtoroe_pole;
    }

    public void setId_vtoroe_pole(Integer id_vtoroe_pole) {
        this.id_vtoroe_pole = id_vtoroe_pole;
    }

    public Integer getId_tretie_pole() {
        return id_tretie_pole;
    }

    public void setId_tretie_pole(Integer id_tretie_pole) {
        this.id_tretie_pole = id_tretie_pole;
    }

    public Integer getId_4tvertoe_pole() {
        return id_4tvertoe_pole;
    }

    public void setId_4tvertoe_pole(Integer id_4tvertoe_pole) {
        this.id_4tvertoe_pole = id_4tvertoe_pole;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Using_adapter_pokazat_4_pol9> CREATOR = new Creator<Using_adapter_pokazat_4_pol9>() {
        @Override
        public Using_adapter_pokazat_4_pol9 createFromParcel(Parcel source) {
            return new Using_adapter_pokazat_4_pol9(source);
        }

        @Override
        public Using_adapter_pokazat_4_pol9[] newArray(int size) {
            return new Using_adapter_pokazat_4_pol9[size];
        }
    };
}