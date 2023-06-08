package com.example.jurnald;

import android.os.Parcel;
import android.os.Parcelable;

public class Using_adapter_dvapol9_dobavleni9 implements Parcelable {
    String name;
    Integer id;
    Integer propyski;
    public Using_adapter_dvapol9_dobavleni9(Integer id, String name,Integer propyski) {
        this.id = id;
        this.name = name;
        this.propyski=propyski;
    }

    public Using_adapter_dvapol9_dobavleni9(Parcel in) {
        id = in.readInt();
        name = in.readString();
        propyski=in.readInt();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.id);
        out.writeString(this.name);
        out.writeInt(this.propyski);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPropyski() {
        return propyski;
    }

    public void setPropyski(Integer propyski) {
        this.propyski = propyski;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Using_adapter_dvapol9_dobavleni9> CREATOR = new Creator<Using_adapter_dvapol9_dobavleni9>() {
        @Override
        public Using_adapter_dvapol9_dobavleni9 createFromParcel(Parcel source) {
            return new Using_adapter_dvapol9_dobavleni9(source);
        }

        @Override
        public Using_adapter_dvapol9_dobavleni9[] newArray(int size) {
            return new Using_adapter_dvapol9_dobavleni9[size];
        }
    };
}