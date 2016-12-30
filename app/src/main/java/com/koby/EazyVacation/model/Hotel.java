package com.koby.EazyVacation.model;

/**
 * Created by koby on 02/06/2016.
 * This is the Hotel class contains all the hotel details.
 */

public class Hotel {

    String id;
    String hotelName;
    String phone;
    String address;
    String imageName;
    String site;
    String lastUpdated;

    /**
     * This method returns the data member lastUpdated
     *
     * @return String lastUpdated
     */
    public String getLastUpdated() {
        return lastUpdated;
    }

    /**
     * This method sets the lastUpdated dataMember
     *
     * @param lastUpdated
     */
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * Hotel  default Constructor
     */
    public Hotel() {
    }

    /**
     * The Hotel Parametrized Constructor
     *
     * @param id
     * @param hotelName
     * @param phone
     * @param address
     * @param imageName
     * @param site
     */
    public Hotel(String id, String hotelName, String phone, String address, String imageName, String site) {
        this.id = id;
        this.hotelName = hotelName;
        this.phone = phone;
        this.address = address;
        this.imageName = imageName;
        this.site = site;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;

    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }


}
