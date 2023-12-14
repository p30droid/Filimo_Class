package com.navin.filimo.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Hello implements Parcelable {
    private String name;
    private String family;
    private int age;
    private int weight;
    private String description;

    protected Hello(Parcel in) {
        name = in.readString();
        family = in.readString();
        age = in.readInt();
        weight = in.readInt();
        description = in.readString();
    }

    public static final Creator<Hello> CREATOR = new Creator<Hello>() {
        @Override
        public Hello createFromParcel(Parcel in) {
            return new Hello(in);
        }

        @Override
        public Hello[] newArray(int size) {
            return new Hello[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(family);
        parcel.writeInt(age);
        parcel.writeInt(weight);
        parcel.writeString(description);
    }
}
