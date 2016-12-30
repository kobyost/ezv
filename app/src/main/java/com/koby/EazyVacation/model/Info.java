package com.koby.EazyVacation.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This classs represents the information of an hotel from the managment sysytem.
 * The info will be presented in the information activity.
 * Created by קובי on 12/08/2016.
 */
public class Info implements Parcelable {
    String id;
    String brekfastTime;
    String lunchTIME;
    String dinnerTime;
    String poolTime;
    String address;
    String phone;
    String link;
    String lastUpdated;

    /**
     * Default constructor
     */
    public Info() {
    }


    public String getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public String toString() {
        return "Info{" +
                "id='" + id + '\'' +
                ", brekfastTime='" + brekfastTime + '\'' +
                ", lunchTIME='" + lunchTIME + '\'' +
                ", dinnerTime='" + dinnerTime + '\'' +
                ", poolTime='" + poolTime + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", link='" + link + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                '}';
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * Paremerized constructor
     *
     * @param id
     * @param brekfastTime
     * @param lunchTIME
     * @param dinnerTime
     * @param poolTime
     * @param address
     * @param phone
     * @param link
     */
    public Info(String id, String brekfastTime, String lunchTIME, String dinnerTime, String poolTime, String address, String phone, String link) {
        this.id = id;
        this.brekfastTime = brekfastTime;
        this.lunchTIME = lunchTIME;
        this.dinnerTime = dinnerTime;
        this.poolTime = poolTime;
        this.address = address;
        this.phone = phone;
        this.link = link;
    }

    /**
     * Copy constructor
     *
     * @param other
     */
    public Info(Info other) {
        this.id = other.getId();
        this.brekfastTime = other.getBrekfastTime();
        this.lunchTIME = other.getLunchTIME();
        this.dinnerTime = other.getDinnerTime();
        this.poolTime = other.getPoolTime();
        this.address = other.getAddress();
        this.phone = other.getPhone();
        this.link = other.getLink();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrekfastTime() {
        return brekfastTime;
    }

    public void setBrekfastTime(String brekfastTime) {
        this.brekfastTime = brekfastTime;
    }

    public String getLunchTIME() {
        return lunchTIME;
    }

    public void setLunchTIME(String lunchTIME) {
        this.lunchTIME = lunchTIME;
    }

    public String getDinnerTime() {
        return dinnerTime;
    }

    public void setDinnerTime(String dinnerTime) {
        this.dinnerTime = dinnerTime;
    }

    public String getPoolTime() {
        return poolTime;
    }

    public void setPoolTime(String poolTime) {
        this.poolTime = poolTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(brekfastTime);
        dest.writeString(lunchTIME);
        dest.writeString(dinnerTime);
        dest.writeString(poolTime);
        dest.writeString(address);
        dest.writeString(phone);
        dest.writeString(link);
        dest.writeString(lastUpdated);

    }

    protected Info(Parcel in) {
        id = in.readString();
        brekfastTime = in.readString();
        lunchTIME = in.readString();
        dinnerTime = in.readString();
        poolTime = in.readString();
        address = in.readString();
        phone = in.readString();
        link = in.readString();
        lastUpdated = in.readString();

    }

    public static final Creator<Info> CREATOR = new Creator<Info>() {
        @Override
        public Info createFromParcel(Parcel in) {
            return new Info(in);
        }

        @Override
        public Info[] newArray(int size) {
            return new Info[size];
        }
    };
}
