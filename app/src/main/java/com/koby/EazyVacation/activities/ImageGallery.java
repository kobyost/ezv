package com.koby.EazyVacation.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.koby.EazyVacation.R;
import com.koby.EazyVacation.model.Model;

import java.util.ArrayList;

/**
 * This class is the ImageGallery activity which is responsible for creating the images gallery inside the app.
 * Each image will be viewed in  a gridView,if a user wants to add an image he can do so by pressing the addImageButton and this button will open the TakePictureActivity.
 */
public class ImageGallery extends Activity {
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    ArrayList<ImageItem> data;
    static final int NEW_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);
        data = new ArrayList<ImageItem>();
        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter();
        gridView.setAdapter(gridAdapter);
        ImageButton addImageButton = (ImageButton) findViewById(R.id.addImageButton);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);
                //Create intent
                Intent intent = new Intent(ImageGallery.this, ImageDetails.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("image", item.getImage());
                //Start details activity
                startActivity(intent);
            }
        });
/**
 * the add image button listener will open the  TakePictureActivity.
 */
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TakePictureActivity.class);
                startActivityForResult(intent, NEW_IMAGE_REQUEST);
            }
        });
        loadImagesData();


    }

    /**
     * This method loads all the images data from te servers db.
     */
    void loadImagesData() {
        Model.instance().getAllImagesAsynch(new Model.GetImagesListener() {
            @Override
            public void onResult(ArrayList<ImageItem> images) {
                data = images;
                gridAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancel() {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // Check which request we're responding to
        if (requestCode == NEW_IMAGE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                loadImagesData();
            }
        }
    }

    /**
     * This class is responsible for connecting the GridView information with grid_item_layout information
     */
    class GridViewAdapter extends BaseAdapter {


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
                convertView = inflater.inflate(R.layout.grid_item_layout, null);
                Log.d("TAG", "create view:" + position);


            } else {
                Log.d("TAG", "use convert view:" + position);
            }

            TextView imageName = (TextView) convertView.findViewById(R.id.text);
            final ImageView image = (ImageView) convertView.findViewById(R.id.imageItem);
            final ProgressBar progress = (ProgressBar) convertView.findViewById(R.id.galleRrowImageProgressBar);
            progress.setVisibility(View.VISIBLE);
            final ImageItem imgItem = data.get(position);
            if (imgItem != null) {
                Log.d("Tag", "imgname is" + imgItem.getTitle());
                imageName.setText(imgItem.getTitle());
            }

            Model.instance().loadImage(imgItem.getTitle(), new Model.LoadImageListener() {
                @Override
                public void onResult(Bitmap imageBmp) {
                    if (image != null) {
                        image.setImageBitmap(imageBmp);
                        imgItem.setImage(imageBmp);
                        progress.setVisibility(View.GONE);
                    }
                }
            });


            return convertView;
        }


    }


}
