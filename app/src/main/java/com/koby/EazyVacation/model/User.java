package com.koby.EazyVacation.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * This class represents a registered user for the application
 * Created by קובי on 08/06/2016.
 */
public class User implements Parcelable {

    String email;
    String passWord;
    String confirmPassword;
    String fName;
    String lName;
    String id;
    String phoneNumber;
    Boolean updatesAndNews;
    String lastUpdated;

    /**
     * Parametrized Constructor for a user
     *
     * @param email
     * @param passWord
     * @param confirmPassword
     * @param fName
     * @param lName
     * @param id
     * @param phoneNumber
     * @param updatesAndNews
     */
    public User(String email, String passWord, String confirmPassword, String fName, String lName, String id, String phoneNumber, Boolean updatesAndNews) {
        this.email = email;
        this.passWord = passWord;
        this.confirmPassword = confirmPassword;
        this.fName = fName;
        this.lName = lName;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.updatesAndNews = updatesAndNews;
    }


    public User(User other) {
        this.email = other.getEmail();
        this.passWord = other.getPassWord();
        this.confirmPassword = other.getConfirmPassword();
        this.fName = other.getfName();
        this.lName = other.getlName();
        this.id = other.getId();
        this.phoneNumber = other.getPhoneNumber();
        this.updatesAndNews = other.getUpdatesAndNews();

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

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public User() {
        this.updatesAndNews = false;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getUpdatesAndNews() {
        return updatesAndNews;
    }

    public void setUpdatesAndNews(Boolean updatesAndNews) {
        this.updatesAndNews = updatesAndNews;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(passWord);
        dest.writeString(confirmPassword);
        dest.writeString(fName);
        dest.writeString(lName);
        dest.writeString(id);
        dest.writeString(phoneNumber);
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    protected User(Parcel in) {
        email = in.readString();
        passWord = in.readString();
        confirmPassword = in.readString();
        fName = in.readString();
        lName = in.readString();
        id = in.readString();
        phoneNumber = in.readString();
    }
}
