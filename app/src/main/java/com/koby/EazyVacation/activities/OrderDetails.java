package com.koby.EazyVacation.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.koby.EazyVacation.R;
import com.koby.EazyVacation.model.Meal;
import com.koby.EazyVacation.model.Model;
import com.koby.EazyVacation.model.OrderRoomService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * This class is the OrderDetails activity in which we show the user his room service order details and then sends the order to the hotel.
 */
public class OrderDetails extends Activity {


    StringBuffer mealsName = new StringBuffer();
    float totalPrice = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        getActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        ArrayList<Meal> meals = intent.getParcelableArrayListExtra("meals");
        if (meals == null) {
            Log.d("Tag", "meals is nullll");
        } else {
            Log.d("TAG inside order", meals.get(0).getMealName());
        }

        TextView numberOfMeals = (TextView) findViewById(R.id.numberOfMeals);
        TextView selectedMeals = (TextView) findViewById(R.id.selectedMeals);
        TextView totalPriceText = (TextView) findViewById(R.id.TotalPrice);
        Button orderButton = (Button) findViewById(R.id.submitOrder);

        for (Meal meal : meals) {
            mealsName.append(meal.getMealName() + ", ");
            totalPrice += Float.parseFloat(meal.getPrice());
        }
        numberOfMeals.setText(meals.size() + "");
        selectedMeals.setText(mealsName);
        totalPriceText.setText(totalPrice + "");
        //saves the order  on the servers db.
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderRoomService orderRoomService = new OrderRoomService(Model.instance().getVacation().getRoomNumber(), getDate(), mealsName.toString(), totalPrice + "");
                Model.instance().addRoomServiceOrder(orderRoomService, Model.instance().getVacation().getHotelName());
                AlertDialog dialog = new AlertDialog.Builder(OrderDetails.this).create();
                dialog.setTitle("Room Service");
                dialog.setMessage("Order Completed");
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OKAY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent resultIntent = new Intent();
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();

                    }
                });

                dialog.show();
            }
        });
    }

    /**
     * This method returns the current date.
     * @return String
     */
    private String getDate() {
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = null;
        date = dateFormatGmt.format(new Date()).toString();
        return date;
    }

}
