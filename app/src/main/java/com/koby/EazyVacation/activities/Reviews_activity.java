package com.koby.EazyVacation.activities;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.koby.EazyVacation.R;
import com.koby.EazyVacation.model.Model;
import com.koby.EazyVacation.model.Review;

/**
 * This class is the Reviews_activity which is responsible for users reviews about the hotel.
 * Each review will be sent to the hotel
 */
public class Reviews_activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews_activity);

        final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        final EditText reviewInfo = (EditText) findViewById(R.id.review_info);
        Button submit = (Button) findViewById(R.id.submit_button_review);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float rating = ratingBar.getRating();
                String userName = Model.instance().getUser().getfName() + " " + Model.instance().getUser().getlName();
                String hotelName = Model.instance().getVacation().getHotelName();
                //adding the review to the servers db.
                Model.instance().addReview(new Review(userName, rating, reviewInfo.getText().toString()), hotelName);
                Toast.makeText(getApplicationContext(), "Submitted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

}
