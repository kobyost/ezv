package com.koby.EazyVacation.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * This class represents a vacation in  a certain hotel .
 * Holds the required information: id,hotel name,room number,check in and check out date.
 * Created by koby on 02/06/2016.
 */
public class Vacation implements Parcelable {
    String id;
    String hotelName;
    String roomNumber;
    String checkIn;
    String checkOut;

    @Override
    public String toString() {
        return "Vacation{" +
                "hotelName='" + hotelName + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", checkIn='" + checkIn + '\'' +
                ", checkOut='" + checkOut + '\'' +
                '}';
    }

    /**
     * Parametrized Constructor of Vacation
     *
     * @param id
     * @param hotelName
     * @param roomNumber
     * @param checkIn
     * @param checkOut
     */
    public Vacation(String id, String hotelName, String roomNumber, String checkIn, String checkOut) {
        this.id = id;
        this.hotelName = hotelName;
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    /**
     * Default constructor of Vacation
     */
    public Vacation() {

    }

    public Vacation(Vacation other_vaction) {
        this.hotelName = other_vaction.getHotelName();
        this.roomNumber = other_vaction.getRoomNumber();
        this.checkIn = other_vaction.getCheckIn();
        this.checkOut = other_vaction.getCheckOut();
    }

    protected Vacation(Parcel in) {

        hotelName = in.readString();
        roomNumber = in.readString();
        checkIn = in.readString();
        checkOut = in.readString();
    }

    public static final Creator<Vacation> CREATOR = new Creator<Vacation>() {
        @Override
        public Vacation createFromParcel(Parcel in) {
            return new Vacation(in);
        }

        @Override
        public Vacation[] newArray(int size) {
            return new Vacation[size];
        }
    };

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hotelName);
        dest.writeString(roomNumber);
        dest.writeString(checkIn);
        dest.writeString(checkOut);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

