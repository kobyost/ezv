package com.koby.EazyVacation.activities;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import com.koby.EazyVacation.R;
/**
 * This class represents the Wifi_activity which is the screen that will appear when you press the wifi service in the front desk screen(reception activity).
 */
public class Wifi_activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        getActionBar().hide();
        TextView wifiPw= (TextView) findViewById(R.id.wifiTextViewPw);
        wifiPw.setText(getIntent().getStringExtra("wifi"));

    }

}
