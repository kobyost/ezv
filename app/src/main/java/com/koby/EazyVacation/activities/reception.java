package com.koby.EazyVacation.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.koby.EazyVacation.R;
import com.koby.EazyVacation.model.Model;

/**
 * This class is the reception activity which represents the Front desk screen.
 * the front desk provide many services, such as:maintenance,wifi,wake up call,late checkOut,call a taxi, clean room.
 */
public class reception extends Activity {
    String wifi_pw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reception);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().hide();
        Model.instance().getWifi(Model.instance().getVacation().getHotelName(), new Model.GetWifi() {
            @Override
            public void onResult(String wifi) {
                setWifi_pw(wifi);
            }

            @Override
            public void onCancel() {

            }
        });


        Button maintenance = (Button) findViewById(R.id.maintenance_button);
        Button wifi_button = (Button) findViewById(R.id.wifi_button);
        Button wakeUpCall_button = (Button) findViewById(R.id.wakeUpcall_button);
        Button lateCheckOut_button = (Button) findViewById(R.id.lateCheckOut_button);
        Button callTaxi_button = (Button) findViewById(R.id.callTaxi_button);
        Button cleanRoom_button = (Button) findViewById(R.id.cleanRoom_button);

        //Opens the Maintnance screen
        maintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Maintnance.class);
                startActivity(intent);
            }
        });
        //Opens the wifi screen
        wifi_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Wifi_activity.class);
                intent.putExtra("wifi", wifi_pw);
                startActivity(intent);

            }
        });
        //Opens the WakeUp call  screen
        wakeUpCall_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), wakeUPCall_activity.class);
                startActivity(intent);
            }
        });
        //Opens the Call a taxi screen
        callTaxi_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), call_Taxi_Activity.class);
                startActivity(intent);
            }
        });
        //Opens the LateCheckOut screen
        lateCheckOut_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), lateCheckOut_Activity.class);
                startActivity(intent);
            }
        });
        //Opens the Clean room screen
        cleanRoom_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CleanRoom_Activity.class);
                startActivity(intent);
            }
        });


    }

    /**
     * Called when the user clicks the phone button
     */
    public void callDesk(View view) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        Button button = (Button) findViewById(view.getId());
        System.out.print("tel:" + button.getHint().toString());
        String phoneNumber = button.getHint().toString().substring(6);
        callIntent.setData(Uri.parse("tel:" + (phoneNumber)));

        startActivity(callIntent);
    }

    public String getWifi_pw() {
        return wifi_pw;
    }

    public void setWifi_pw(String wifi_pw) {
        this.wifi_pw = wifi_pw;
    }


}
