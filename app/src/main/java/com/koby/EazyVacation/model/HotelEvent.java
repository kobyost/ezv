package com.koby.EazyVacation.model;

/**
 * This class represents an event in the hotel
 * Created by קובי on 12/06/2016.
 */
public class HotelEvent {
    private String date;
    private String endTime;
    private String startTime;
    private String subject;
    private String summary;

    /**
     * HotelEvent Constructor
     *
     * @param startTime
     * @param date
     * @param endTime
     * @param subject
     * @param summary
     */
    public HotelEvent(String startTime, String date, String endTime, String subject, String summary) {
        this.startTime = startTime;
        this.date = date;
        this.endTime = endTime;
        this.subject = subject;
        this.summary = summary;
    }

    /**
     * Default constructor
     */
    public HotelEvent() {


    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


    @Override
    public String toString() {
        return "HotelEvent{" +
                "date='" + date + '\'' +
                ", endTime='" + endTime + '\'' +
                ", startTime='" + startTime + '\'' +
                ", subject='" + subject + '\'' +
                ", summry='" + summary + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


}
