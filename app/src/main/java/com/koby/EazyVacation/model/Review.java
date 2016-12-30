package com.koby.EazyVacation.model;

/**
 * This class represents a review that a guest sends in the Review  activity to the hotel .
 * Created by קובי on 14/06/2016.
 */
public class Review {
    String name;
    float stars;
    String review;


    public Review(String name, float stars, String review) {
        this.name = name;
        this.stars = stars;
        this.review = review;
    }

    public Review() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
