package com.koby.EazyVacation.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.koby.EazyVacation.R;

/**
 * This class is the Room service activity which is responsible for presenting the menu of the room service.
 */
public class RoomService extends Activity {

    static final int ORDER_MEAL_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_service);
        Button lunch = (Button) findViewById(R.id.lunch);
        Button breakfast = (Button) findViewById(R.id.breakfast);
        Button dinner = (Button) findViewById(R.id.dinner);

        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MealsList.class);
                intent.putExtra("mealType", "Breakfast");
                startActivityForResult(intent, ORDER_MEAL_REQUEST);
            }
        });


        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MealsList.class);
                intent.putExtra("mealType", "Lunch");
                startActivityForResult(intent, ORDER_MEAL_REQUEST);
            }
        });


        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MealsList.class);
                intent.putExtra("mealType", "Dinner");
                startActivityForResult(intent, ORDER_MEAL_REQUEST);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == ORDER_MEAL_REQUEST) {
            if (resultCode == RESULT_OK) {
                finish();
            }
        }
    }

}
