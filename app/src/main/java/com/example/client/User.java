package com.example.client;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

public class User implements Parcelable {
    private String firstname, lastName, ip;

    public User(String name, String lastName, String ip) {
        this.firstname = name;
        this.lastName = lastName;
        this.ip = ip;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public String getIp() {
        return ip;
    }

    public String getJson(){
        return new Gson().toJson(this);
    }

    

    protected User(Parcel in) {
        firstname = in.readString();
        lastName = in.readString();
        ip = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(firstname);
        parcel.writeString(lastName);
        parcel.writeString(ip);
    }
}
