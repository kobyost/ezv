package com.koby.EazyVacation.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.koby.EazyVacation.R;
import com.koby.EazyVacation.model.Meal;
import com.koby.EazyVacation.model.Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is the MealsList Activity.
 * It represents the meals list screen in each  type of meal on the Room service activity.
 */
public class MealsList extends Activity {

    ListView mealsList;
    List<Meal> data;
    ProgressBar progressBar;
    MyAddapter adapter;
    String mealType;
    static final int ORDER_MEAL_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals_list);
        mealsList = (ListView) findViewById(R.id.listView);
        data = new LinkedList<Meal>();
        adapter = new MyAddapter();
        mealsList.setAdapter(adapter);
        Intent intent = getIntent();
        mealType = intent.getStringExtra("mealType");
        Log.d("Tag", mealType);
        TextView headLine = (TextView) findViewById(R.id.headline);
        Button orderButton = (Button) findViewById(R.id.checkout);
        progressBar = (ProgressBar) findViewById(R.id.mealistProgressBar);
        //saves the meals in an array and sends it to the OrderDetails activity.
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Meal> mealsChecked = new ArrayList<Meal>();
                for (Meal meal : data) {
                    if (meal.getCheck()) {
                        mealsChecked.add(meal);
                    }
                }
                if (mealsChecked.size() != 0) {
                    Intent intent = new Intent(getApplicationContext(), OrderDetails.class);
                    intent.putParcelableArrayListExtra("meals", (ArrayList<? extends Parcelable>) mealsChecked);
                    startActivityForResult(intent, ORDER_MEAL_REQUEST);
                } else {
                    Toast.makeText(getApplicationContext(), "Use the check box to pick a meal !", Toast.LENGTH_SHORT).show();
                }


            }
        });
        headLine.setText(mealType);

        loadMealsData();
    }

    /**
     * This method loads the meals data from the servers db.
     */
    void loadMealsData() {
        progressBar.setVisibility(View.VISIBLE);
        Model.instance().getAllmealsAsynch(mealType, Model.instance().getVacation().getHotelName(), new Model.GetMealsListener() {
            @Override
            public void onResult(List<Meal> meals) {
                progressBar.setVisibility(View.GONE);
                data = meals;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancel() {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == ORDER_MEAL_REQUEST) {
            if (resultCode == RESULT_OK) {
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
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
                convertView = inflater.inflate(R.layout.room_service_row, null);
                Log.d("TAG", "create view:" + position);
                CheckBox checkBox1 = (CheckBox) convertView.findViewById(R.id.checkbox_meal);
                checkBox1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Meal meal = data.get((Integer) v.getTag());
                        meal.setCheck(!meal.getCheck());
                        //data.set((Integer) v.getTag(), meal);

                    }
                });


            } else {
                Log.d("TAG", "use convert view:" + position);
            }
            final ProgressBar progress = (ProgressBar) convertView.findViewById(R.id.meal_rowImageProgressBar);
            progress.setVisibility(View.VISIBLE);
            TextView mealName = (TextView) convertView.findViewById(R.id.mealName);
            final ImageView image_meal = (ImageView) convertView.findViewById(R.id.image_meal);
            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox_meal);
            TextView price = (TextView) convertView.findViewById(R.id.mealPrice);

            Meal meal = data.get(position);
            checkBox.setTag(new Integer(position));
            convertView.setTag(position);
            checkBox.setChecked(meal.getCheck());
            mealName.setText(meal.getMealName());
            price.setText(meal.getPrice() + "$");
            String imgName = meal.getImageName();


            Model.instance().loadImage(imgName, new Model.LoadImageListener() {
                @Override
                public void onResult(Bitmap imageBmp) {
                    if (imageBmp != null) {
                        image_meal.setImageBitmap(imageBmp);
                        progress.setVisibility(View.GONE);
                    }
                }
            });


            return convertView;
        }
    }
}
