package com.koby.EazyVacation.model;

/**
 * This class represents a ticket ( a request) that the hotel receives from the guest.
 * Created by קובי on 11/06/2016.
 */
public class Ticket {
    String date;
    String time;
    String type;
    String guest;
    String room;
    String summary;

    /**
     * Parametrized Constructor.
     * @param date
     * @param time
     * @param type
     * @param room
     * @param guest
     * @param summary
     */
    public Ticket(String date, String time, String type, String room, String guest, String summary) {
        this.date = date;
        this.time = time;
        this.type = type;
        this.room = room;
        this.guest = guest;
        this.summary = summary;
    }

    /**
     * Default constructor of Ticket.
     */
    public Ticket() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
