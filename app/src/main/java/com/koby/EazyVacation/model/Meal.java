package com.koby.EazyVacation.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class represents a Meal in the room service menu
 * Created by קובי on 13/08/2016.
 */
public class Meal implements Parcelable {
    String mealName;
    String imageName;
    String price;
    Boolean check;
    String lastUpdated;

    /**
     * Meal  Parametrized constructor
     *
     * @param mealName
     * @param imageName
     * @param price
     * @param check
     */
    public Meal(String mealName, String imageName, String price, Boolean check) {
        this.mealName = mealName;
        this.imageName = imageName;
        this.price = price;
        this.check = check;
    }


    public Meal() {

    }


    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(mealName);
        dest.writeString(imageName);
        dest.writeString(price);
        dest.writeString(lastUpdated);
    }

    protected Meal(Parcel in) {
        mealName = in.readString();
        imageName = in.readString();
        price = in.readString();
        lastUpdated = in.readString();
    }

    public static final Creator<Meal> CREATOR = new Creator<Meal>() {
        @Override
        public Meal createFromParcel(Parcel in) {
            return new Meal(in);
        }

        @Override
        public Meal[] newArray(int size) {
            return new Meal[size];
        }
    };

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
