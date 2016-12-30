package com.koby.EazyVacation.activities;

import android.app.Application;
import android.content.Context;

/**This class is responsible for the application context
 * Created by RaZr0 on 06/06/2016.
 */
public class MyApplication extends Application {
    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}