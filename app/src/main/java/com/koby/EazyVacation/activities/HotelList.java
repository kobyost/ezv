package com.koby.EazyVacation.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.koby.EazyVacation.R;
import com.koby.EazyVacation.model.Hotel;
import com.koby.EazyVacation.model.Model;

import java.util.LinkedList;
import java.util.List;

/**
 * This class is the HotelList activity which is responsible for the hotel list .
 * Each hotel will be presented in a row, when you click on an hotel, you will get to the HotelDetails activity and there you can view the hotels information details .
 */
public class HotelList extends Activity {
    ListView hotelList;
    ProgressBar progressBar;
    List<Hotel> data;
    MyAddapter adapter;
    static final int NEW_HOTEL_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list);
        hotelList = (ListView) findViewById(R.id.listView);
        progressBar = (ProgressBar) findViewById(R.id.hotelListProgressBar);
        data = new LinkedList<Hotel>();
        adapter = new MyAddapter();
        hotelList.setAdapter(adapter);

        hotelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Hotel_Details.class);
                intent.putExtra("HOTEL_ID", data.get(position).getId());
                startActivity(intent);
            }
        });
        loadHotelsData();
    }

    /**
     * This method is responsible for loading the hotels data from the servers db.
     */
    void loadHotelsData() {
        progressBar.setVisibility(View.VISIBLE);
        Model.instance().getAllHotelsAsynch(new Model.GetHotelsListener() {
            @Override
            public void onResult(List<Hotel> hotels) {
                progressBar.setVisibility(View.GONE);
                data = hotels;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancel() {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // Check which request we're responding to
        if (requestCode == NEW_HOTEL_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                loadHotelsData();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This class is responsible for connecting the list view information with list row information
     */
    class MyAddapter extends BaseAdapter {


        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.hotel_list_row, null);
                Log.d("TAG", "create view:" + position);


            } else {
                Log.d("TAG", "use convert view:" + position);
            }
            //starts loading the row list information
            TextView hotelName = (TextView) convertView.findViewById(R.id.hotel_name_row);
            final ImageView image_hotel = (ImageView) convertView.findViewById(R.id.student_list_row_image);
            convertView.setTag(position);
            Hotel hotel = data.get(position);
            hotelName.setText(hotel.getHotelName());
            String imgName = hotel.getImageName();
            final ProgressBar progress = (ProgressBar) convertView.findViewById(R.id.rowImageProgressBar);
            progress.setVisibility(View.VISIBLE);
            Model.instance().loadImage(imgName, new Model.LoadImageListener() {
                @Override
                public void onResult(Bitmap imageBmp) {
                    if (image_hotel != null) {
                        image_hotel.setImageBitmap(imageBmp);
                        progress.setVisibility(View.GONE);
                    }
                }
            });


            return convertView;
        }
    }
}
