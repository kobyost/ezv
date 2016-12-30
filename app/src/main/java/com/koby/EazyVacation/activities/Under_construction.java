package com.koby.EazyVacation.activities;

import android.os.Bundle;
import android.app.Activity;

import com.koby.EazyVacation.R;

/**
 * This class is used for showing the construction screen while an activity is under construction.
 */
public class Under_construction extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_under_construction);
        getActionBar().hide();
    }

}
