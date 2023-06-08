package com.example.jurnald;
import android.os.Parcel;
import android.os.Parcelable;

public class Using_adater_dobavlenie implements Parcelable {
    String name;
    Integer id;

    public Using_adater_dobavlenie(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Using_adater_dobavlenie(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.id);
        out.writeString(this.name);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Using_adater_dobavlenie> CREATOR = new Creator<Using_adater_dobavlenie>() {
        @Override
        public Using_adater_dobavlenie createFromParcel(Parcel source) {
            return new Using_adater_dobavlenie(source);
        }

        @Override
        public Using_adater_dobavlenie[] newArray(int size) {
            return new Using_adater_dobavlenie[size];
        }
    };
}
