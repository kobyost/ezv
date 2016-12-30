package com.koby.EazyVacation.activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.koby.EazyVacation.R;
import com.koby.EazyVacation.model.Info;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * This class is the Information FragmentActivity which is responsible for a specific hotel information data to be presented in the information screen.
 */
public class Information extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    Info info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        getActionBar().hide();
        Intent intent = getIntent();
        info = intent.getParcelableExtra("info");
        TextView breakfestTime = (TextView) findViewById(R.id.breakfestHours);
        TextView lunchTime = (TextView) findViewById(R.id.lunchtHours);
        TextView dinnerTime = (TextView) findViewById(R.id.dinnerJours);
        TextView poolTime = (TextView) findViewById(R.id.pool_hours);
        TextView phone = (TextView) findViewById(R.id.phoneInfo);
        TextView address = (TextView) findViewById(R.id.addressInfo);
        TextView webSiteLink = (TextView) findViewById(R.id.linkInfo);
        webSiteLink.setText(info.getLink());
        webSiteLink.setMovementMethod(LinkMovementMethod.getInstance());
        breakfestTime.setText("Breakfast: " + info.getBrekfastTime());
        lunchTime.setText("Lunch: " + info.getLunchTIME());
        dinnerTime.setText("Dinner: " + info.getDinnerTime());
        address.setText(info.getAddress());
        poolTime.setText(info.getPoolTime());
        phone.setText(info.getPhone());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        List<Address> addresses = null;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            Log.d("OnMap", info.getAddress());
            addresses = geocoder.getFromLocationName(info.getAddress(), 2);


        } catch (IOException e) {
            e.printStackTrace();
        }
        LatLng hotel = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
        mMap.addMarker(new MarkerOptions().position(hotel).title("Dan Panorama"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hotel));
        mMap.moveCamera(CameraUpdateFactory.zoomTo((float) 10));
    }
}
