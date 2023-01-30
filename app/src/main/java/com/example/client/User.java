package com.example.client;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

public class User implements Parcelable {
    private String firstname, lastname, ip;

    private boolean online;

    public User(String name, String lastName, String ip) {
        this.firstname = name;
        this.lastname = lastName;
        this.ip = ip;
        this.online = true;
    }

    protected User(Parcel in) {
        firstname = in.readString();
        lastname = in.readString();
        ip = in.readString();
        online = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeString(ip);
        dest.writeByte((byte) (online ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getJson(){
        return new Gson().toJson(this);
    }


}
