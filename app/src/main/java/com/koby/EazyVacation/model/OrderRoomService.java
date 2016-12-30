package com.koby.EazyVacation.model;

/**This class represents an order from a room service order that the client sends to the hotel.
 * Created by קובי on 14/08/2016.
 */
public class OrderRoomService {
    String roomNumber;
    String date;
    String summary;
    String totalPrice;
    String lastUpdated;


    /**
     * Parametrized Constructor for OrderRoomService
     * @param roomNumber
     * @param date
     * @param summary
     * @param totalPrice
     */
    public OrderRoomService(String roomNumber, String date, String summary, String totalPrice) {
        this.roomNumber = roomNumber;
        this.date = date;
        this.summary = summary;
        this.totalPrice = totalPrice;
    }

    public OrderRoomService() {

    }

    public String getRoomNumber() {

        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
