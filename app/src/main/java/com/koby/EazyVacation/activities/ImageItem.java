package com.koby.EazyVacation.activities;

import android.graphics.Bitmap;

/**
 * This class is the ImageItem which represents an image item inside the Image gallery activity.
 * Created by קובי on 07/08/2016.
 */
public class ImageItem {
    private Bitmap image;
    private String title;

    public ImageItem() {
    }

    public ImageItem(Bitmap image, String title) {
        super();
        this.image = image;
        this.title = title;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}