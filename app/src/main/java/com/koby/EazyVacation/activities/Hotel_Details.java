package com.koby.EazyVacation.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.koby.EazyVacation.R;
import com.koby.EazyVacation.model.Hotel;
import com.koby.EazyVacation.model.Model;

/**
 * This class is the Hotel_Details activity which is responsible for showing each hotel details in the hotels list activity.
 */
public class Hotel_Details extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel__details);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        String hotelId = (String) getIntent().getExtras().get("HOTEL_ID");
        final TextView hotelName = (TextView) findViewById(R.id.hotelName_textView);
        final TextView address = (TextView) findViewById(R.id.address_textView);
        final TextView phone = (TextView) findViewById(R.id.phone_textView);
        final TextView site = (TextView) findViewById(R.id.hotel_web_site);
        final ImageView image_hotel = (ImageView) findViewById(R.id.hotel_imageView);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.sdProgressBar);
        final ProgressBar imageProgressBar = (ProgressBar) findViewById(R.id.sdImageProgressBar);

        progressBar.setVisibility(View.VISIBLE);
        //extracting the hotel information from the server.
        Model.instance().getHotelById(hotelId, new Model.GetHotel() {
            @Override
            public void onResult(Hotel hotel) {
                //presenting the information
                hotelName.setText(hotel.getHotelName());
                address.setText(hotel.getAddress());
                phone.setText(hotel.getPhone());
                site.setText((hotel.getSite()));
                site.setMovementMethod(LinkMovementMethod.getInstance());
                if (hotel.getImageName() != null) {
                    imageProgressBar.setVisibility(View.VISIBLE);
                    Model.instance().loadImage(hotel.getImageName(), new Model.LoadImageListener() {
                        @Override
                        public void onResult(Bitmap imageBmp) {
                            if (image_hotel != null) {
                                image_hotel.setImageBitmap(imageBmp);
                                imageProgressBar.setVisibility(View.GONE);
                            }
                        }

                    });
                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancel() {

            }
        });


    }

}
