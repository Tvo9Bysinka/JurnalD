package com.example.jurnald;

import android.os.Parcel;
import android.os.Parcelable;

public class Using_adapter_3_string_dobavleni9 implements Parcelable {

    Integer id;
    String pole_1,pole_2,pole_3;
    public Using_adapter_3_string_dobavleni9(Integer id, String pole_1, String pole_2,String pole_3) {
        this.id = id;
        this.pole_1 = pole_1;
        this.pole_2 = pole_2;
        this.pole_3 = pole_3;
    }

    public Using_adapter_3_string_dobavleni9(Parcel in) {
        id = in.readInt();
        pole_1 = in.readString();
        pole_2 = in.readString();
        pole_3 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.id);
        out.writeString(this.pole_1);
        out.writeString(this.pole_2);
        out.writeString(this.pole_3);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPole_1() {
        return pole_1;
    }

    public void setPole_1(String pole_1) {
        this.pole_1 = pole_1;
    }

    public String getPole_2() {
        return pole_2;
    }

    public void setPole_2(String pole_2) {
        this.pole_2 = pole_2;
    }

    public String getPole_3() {
        return pole_3;
    }

    public void setPole_3(String pole_3) {
        this.pole_3 = pole_3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Using_adapter_3_string_dobavleni9> CREATOR = new Creator<Using_adapter_3_string_dobavleni9>() {
        @Override
        public Using_adapter_3_string_dobavleni9 createFromParcel(Parcel source) {
            return new Using_adapter_3_string_dobavleni9(source);
        }

        @Override
        public Using_adapter_3_string_dobavleni9[] newArray(int size) {
            return new Using_adapter_3_string_dobavleni9[size];
        }
    };
}