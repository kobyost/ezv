package com.koby.EazyVacation.activities;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.koby.EazyVacation.R;
import com.koby.EazyVacation.model.HotelEvent;
import com.koby.EazyVacation.model.Info;
import com.koby.EazyVacation.model.Model;
import com.koby.EazyVacation.model.Vacation;

/**
 * This class is the MainActivity which is the main screen of the app.
 * In this activity the user can choose which kind of service does he want.
 * He can pick front desk,room service,information,image gallery,reviews.
 */
public class MainActivity extends Activity {

    Vacation vacation;
    boolean flag = true;
    String hotelName;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // findHotelName();
        vacation = getIntent().getParcelableExtra("vacation");
        if (vacation != null)
            hotelName = vacation.getHotelName();
        getInfoData();
        setContentView(R.layout.activity_main);
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.makeText(getApplicationContext(), "Welcome to " + hotelName, Toast.LENGTH_SHORT).show();
        if (Model.instance().getFlag()) {
            Model.instance().setFlag(false);
            listenForEvents();
        }


        Button front_Desk_button = (Button) findViewById(R.id.front_desk);
        Button room_Service = (Button) findViewById(R.id.room_Service);
//      Button spa=(Button)findViewById(R.id.spa);
//      Button attractions=(Button)findViewById(R.id.attractions);
        Button information = (Button) findViewById(R.id.information);
        Button review = (Button) findViewById(R.id.review_button);
        Button gallery = (Button) findViewById(R.id.gallery_button);

        room_Service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RoomService.class);
                startActivity(intent);
            }
        });
        // ! will be addresed later !
//        spa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent  = new Intent(getApplicationContext(), Under_construction.class);
//                startActivity(intent);
//            }
//        });
        ///Later ! will be addresed later.
//        attractions.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent  = new Intent(getApplicationContext(), Under_construction.class);
//                startActivity(intent);
//            }
//        });

        //opens the information activty
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(intent);
            }
        });
        //opens the reception activty
        front_Desk_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), reception.class);
                startActivity(intent);
            }
        });
        //opens the reviews activty
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Reviews_activity.class);
                startActivity(intent);
            }
        });
        //opens the image gallery activity
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ImageGallery.class);
                startActivity(intent);
            }
        });


    }

    /**
     * This method listens for events from the hotel.
     * If the hotel sends an event this method will create a notification message about the event.
     */
    public void listenForEvents() {


        Model.instance().getHotelEvent(hotelName, new Model.GetEvent() {
            @Override
            public void onResult(HotelEvent event) {
                createNotification(event);
            }

            @Override
            public void onCancel() {
                Log.d("main activity", "no child found");

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;
        //noinspection SimplifiableIfStatement
        switch (id) {

            case R.id.log_out:
                Model.instance().logout();
                intent = new Intent(getApplicationContext(), first_screen.class);
                startActivity(intent);

                return true;
            case R.id.my_vacation:

                intent = new Intent(getApplicationContext(), VacationActivity.class);
                // intent.putExtra("vacation",vacation);
                startActivity(intent);
                return true;

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (Model.instance().getUserId() != null)
            super.onBackPressed();
        else {
            Intent intent = new Intent(this, first_screen.class);
            startActivity(intent);
        }


    }

    /**
     * This method is responsible for the creation of an hotel event notification message.
     *
     * @param hotelEvent
     */
    public void createNotification(HotelEvent hotelEvent) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.logo_new)
                .setContentText(hotelEvent.getSummary());
        // Creates an explicit intent for an Activity in your app
        // Intent resultIntent = new Intent(getApplicationContext(),MainActivity.class);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

// Sets a title for the Inbox in expanded layout
        inboxStyle.setBigContentTitle("Easy Vacation");
        inboxStyle.addLine(hotelEvent.getSubject());
        inboxStyle.addLine("Time: " + hotelEvent.getDate() + " " + hotelEvent.getStartTime() + "-" + hotelEvent.getEndTime());
        inboxStyle.addLine(hotelEvent.getSummary());
        //inboxStyle.addLine("Time:17/5/16 16:00--17:00");
// Moves the expanded layout object into the notification object.
        mBuilder.setStyle(inboxStyle);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
//        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        int mId = 1;
        mNotificationManager.notify(mId, mBuilder.build());
//        startActivity(resultIntent);

    }

    /**
     * This method is responsible for receiving the hotel information from the servers db.
     */
    private void getInfoData() {
        intent = new Intent(getApplicationContext(), Information.class);
        Model.instance().getInfolById("1", hotelName, new Model.GetInfoListener() {
            @Override
            public void onResult(Info info) {
                Log.d("Inside getinfo", info.toString());

                intent.putExtra("info", info);

            }

            @Override
            public void onCancel() {

            }
        });
    }
}
