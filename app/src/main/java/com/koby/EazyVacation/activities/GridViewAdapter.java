package com.koby.EazyVacation.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koby.EazyVacation.R;
import com.koby.EazyVacation.model.Model;

import java.util.ArrayList;

/**
 * This class is the GridViewAdapter.
 * It is responsible for the gridView inside the  imageGallery activity.
 * Created by קובי on 07/08/2016.
 */

public class GridViewAdapter extends ArrayAdapter<String> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<String> data = new ArrayList<String>();
    ViewHolder holder;

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<String> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;


        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.text);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }


        String item = data.get(position);
        holder.imageTitle.setText(item);
        if (item != null)
            Log.d("tag", "bitmap exists");
        Model.instance().loadImage(item, new Model.LoadImageListener() {
            @Override
            public void onResult(Bitmap imageBmp) {
                holder.image.setImageBitmap(imageBmp);
            }
        });

        return row;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }
}




