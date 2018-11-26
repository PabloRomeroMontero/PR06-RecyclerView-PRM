package com.iessaladillo.pablo.pr06_recyclerview_prm.data.local.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private static long counter = 0;
    private long id;
    private Avatar avatar;
    private String name;
    private String email;
    private String address;
    private int number;
    private String web;


    public User(Avatar avatar, String name, String email, String address, int number, String web) {
        this.id = ++counter;
        this.avatar = avatar;
        this.name = name;
        this.email = email;
        this.number = number;
        this.web = web;
        this.address=address;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    protected User(Parcel in) {
        this.id=in.readLong();
        this.name=in.readString();
        this.email=in.readString();
        this.number=in.readInt();
        this.address=in.readString();
        this.web=in.readString();
        this.avatar = in.readParcelable(Avatar.class.getClassLoader());
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeInt(this.number);
        dest.writeString(this.address);
        dest.writeString(this.web);
        dest.writeParcelable(this.avatar,0);
    }


    public long getId() {
        return id;
    }
}
