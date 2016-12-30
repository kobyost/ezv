package com.koby.EazyVacation.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * This class represents the Hotel in the sql way.(table of hotels)
 * Created by קובי on 09/08/2016.
 */
public class HotelSql {

    // Table name
    final static String HOTEL_TABLE = "hotels1";
    //Column i'd
    final static String HOTEL_TABLE_ID = "_id";
    //Column hotel name
    final static String HOTEL_TABLE_HOTEL_NAME = "hotelName";
    //Column hotel phone
    final static String HOTEL_TABLE_PHONE = "phone";
    //Column hotel address

    final static String HOTEL_TABLE_ADDRESS = "address";
    //Column hotel link
    final static String HOTEL_TABLE_LINK = "link";
    //Column hotel imageName
    final static String HOTEL_TABLE_IMAGE_NAME = "imageName";

    static public void create(SQLiteDatabase db) {
        db.execSQL("create table " + HOTEL_TABLE + " (" +
                        HOTEL_TABLE_ID + " TEXT PRIMARY KEY," +
                        HOTEL_TABLE_HOTEL_NAME + " TEXT," +
                        HOTEL_TABLE_PHONE + " TEXT," +
                        HOTEL_TABLE_ADDRESS + " TEXT," +
                        HOTEL_TABLE_LINK + " TEXT," +
                        HOTEL_TABLE_IMAGE_NAME + " TEXT);"
        );

    }

    /**
     * This method deletes the table.
     *
     * @param db
     */
    public static void drop(SQLiteDatabase db) {
        db.execSQL("drop table " + HOTEL_TABLE + ";");
    }

    /**
     * This method returns all the hotels in the sql database.
     *
     * @param db
     * @return List<Hotel>
     */
    public static List<Hotel> getAllHotels(SQLiteDatabase db) {
        Cursor cursor = db.query(HOTEL_TABLE, null, null, null, null, null, null);
        List<Hotel> hotels = new LinkedList<Hotel>();

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(HOTEL_TABLE_ID);
            int hotelNameIndex = cursor.getColumnIndex(HOTEL_TABLE_HOTEL_NAME);
            int phoneIndex = cursor.getColumnIndex(HOTEL_TABLE_PHONE);
            int addressIndex = cursor.getColumnIndex(HOTEL_TABLE_ADDRESS);
            int linkIndex = cursor.getColumnIndex(HOTEL_TABLE_LINK);
            int imageNameIndex = cursor.getColumnIndex(HOTEL_TABLE_IMAGE_NAME);
            do {
                String id = cursor.getString(idIndex);
                String hotelName = cursor.getString(hotelNameIndex);
                String phone = cursor.getString(phoneIndex);
                String address = cursor.getString(addressIndex);
                String imageName = cursor.getString(imageNameIndex);
                String link = cursor.getString(linkIndex);
                Hotel hotel = new Hotel(id, hotelName, phone, address, imageName, link);
                Log.d("HotelSql :", imageName);
                hotels.add(hotel);
            } while (cursor.moveToNext());
        }
        return hotels;
    }

    /**
     * This method returns an hotel by the hotel id from the sql database.
     *
     * @param db
     * @param id
     * @return Hotel
     */

    public static Hotel getHotelById(SQLiteDatabase db, String id) {
        String where = HOTEL_TABLE_ID + " = ?";
        String[] args = {id};
        Cursor cursor = db.query(HOTEL_TABLE, null, where, args, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(HOTEL_TABLE_ID);
            int hotelNameIndex = cursor.getColumnIndex(HOTEL_TABLE_HOTEL_NAME);
            int phoneIndex = cursor.getColumnIndex(HOTEL_TABLE_PHONE);
            int addressIndex = cursor.getColumnIndex(HOTEL_TABLE_ADDRESS);
            int linkIndex = cursor.getColumnIndex(HOTEL_TABLE_LINK);
            int imageNameIndex = cursor.getColumnIndex(HOTEL_TABLE_IMAGE_NAME);
            String _id = cursor.getString(idIndex);
            String hotelName = cursor.getString(hotelNameIndex);
            String phone = cursor.getString(phoneIndex);
            String address = cursor.getString(addressIndex);
            String link = cursor.getString(linkIndex);
            String imageName = cursor.getString(imageNameIndex);

            Hotel hotel = new Hotel(_id, hotelName, phone, address, imageName, link);
            return hotel;
        }
        return null;
    }

    /**
     * This method adds an hotel to the sql database
     *
     * @param db
     * @param hotel
     */
    public static void add(SQLiteDatabase db, Hotel hotel) {
        ContentValues values = new ContentValues();
        values.put(HOTEL_TABLE_ID, hotel.getId());
        values.put(HOTEL_TABLE_HOTEL_NAME, hotel.getHotelName());
        values.put(HOTEL_TABLE_PHONE, hotel.getPhone());
        values.put(HOTEL_TABLE_ADDRESS, hotel.getAddress());
        values.put(HOTEL_TABLE_IMAGE_NAME, hotel.getImageName());
        values.put(HOTEL_TABLE_LINK, hotel.getSite());

        db.insertWithOnConflict(HOTEL_TABLE, HOTEL_TABLE_ID, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    /**
     * This method returns the last update date of a table.
     *
     * @param db
     * @return
     */
    public static String getLastUpdateDate(SQLiteDatabase db) {
        return LastUpdateSql.getLastUpdate(db, HOTEL_TABLE);
    }

    /**
     * This method sets the last update date of a table.
     *
     * @param db
     * @param date
     */
    public static void setLastUpdateDate(SQLiteDatabase db, String date) {
        LastUpdateSql.setLastUpdate(db, HOTEL_TABLE, date);
    }

}
