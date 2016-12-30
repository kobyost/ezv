package com.koby.EazyVacation.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.koby.EazyVacation.R;
import com.koby.EazyVacation.model.Model;
import com.koby.EazyVacation.model.Vacation;

/**
 * This class represents the second screen Activity and this will be the home screen after the  the user loges in.
 * In the activity there are 1 fragment:Home_screen_fragment.
 * The activity implements 1 interface :Delegate.
 */
public class second_screen extends Activity implements Home_screen_fragment.Delegate {
    Intent intent;
    FragmentTransaction transaction;
    Home_screen_fragment home_screen_fragment;
    Bitmap imageBitmap = null;
    ImageView imageView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);
        //initialize home screen fargment.
        home_screen_fragment = new Home_screen_fragment();
        transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.second_screen_container, home_screen_fragment, "y");
        transaction.show(home_screen_fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.log_out:
                Model.instance().logout();
                Toast.makeText(getApplicationContext(), "You have logged out", Toast.LENGTH_SHORT).show();
                finish();
                return true;


        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }

    // This method is called Once the vacation obj has returned ,save vacation on the intent and start the MainActivity
    @Override
    public void inVacation(Vacation vacation) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("vacation", vacation);
        startActivity(intent);

    }

    //this method starts the HotelList activity
    @Override
    public void viewHotelList() {
        Intent intent = new Intent(getApplicationContext(), HotelList.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        Model.instance().logout();
        Toast.makeText(getApplicationContext(), "You have logged out", Toast.LENGTH_SHORT).show();
        finish();
    }

}
