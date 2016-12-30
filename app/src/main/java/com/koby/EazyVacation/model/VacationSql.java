package com.koby.EazyVacation.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

/**
 * This class represents the Vacation class in the sql way.(table of Vacations)
 * Created by קובי on 09/08/2016.
 */
public class VacationSql {

    final static String VACATION_TABLE = "vacation";
    final static String VACATION_TABLE_ID = "_id";
    final static String VACATION_TABLE_HOTEL_NAME = "hotelName";
    final static String VACATION_TABLE_ROOM_NUMBER = "roomNumber";
    final static String VACATION_TABLE_CHECK_IN = "checkIn";
    final static String VACATION_TABLE_CHECK_OUT = "checkOut";


    static public void create(SQLiteDatabase db) {
        db.execSQL("create table " + VACATION_TABLE + " (" +
                        VACATION_TABLE_ID + " TEXT PRIMARY KEY," +
                        VACATION_TABLE_HOTEL_NAME + " TEXT," +
                        VACATION_TABLE_ROOM_NUMBER + " TEXT," +
                        VACATION_TABLE_CHECK_IN + " TEXT," +
                        VACATION_TABLE_CHECK_OUT + " TEXT);"
        );
    }

    public static void drop(SQLiteDatabase db) {
        db.execSQL("drop table " + VACATION_TABLE + ";");
    }

    public static List<Vacation> getAllVacations(SQLiteDatabase db) {
        Cursor cursor = db.query(VACATION_TABLE, null, null, null, null, null, null);
        List<Vacation> vacations = new LinkedList<Vacation>();

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(VACATION_TABLE_ID);
            int hotelNameIndex = cursor.getColumnIndex(VACATION_TABLE_HOTEL_NAME);
            int roomNumberIndex = cursor.getColumnIndex(VACATION_TABLE_ROOM_NUMBER);
            int checkInIndex = cursor.getColumnIndex(VACATION_TABLE_CHECK_IN);
            int checkOutIndex = cursor.getColumnIndex(VACATION_TABLE_CHECK_OUT);
            do {
                String id = cursor.getString(idIndex);
                String hotelName = cursor.getString(hotelNameIndex);
                String roomNumber = cursor.getString(roomNumberIndex);
                String checkIn = cursor.getString(checkInIndex);
                String checkOut = cursor.getString(checkOutIndex);
                Vacation vacation = new Vacation(id, hotelName, roomNumber, checkIn, checkOut);
                vacations.add(vacation);
            } while (cursor.moveToNext());
        }
        return vacations;
    }

    public static Vacation getVacationById(SQLiteDatabase db, String id) {
        String where = VACATION_TABLE_ID + " = ?";
        String[] args = {id};
        Cursor cursor = db.query(VACATION_TABLE, null, where, args, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(VACATION_TABLE_ID);
            int hotelNameIndex = cursor.getColumnIndex(VACATION_TABLE_HOTEL_NAME);
            int roomNumberIndex = cursor.getColumnIndex(VACATION_TABLE_ROOM_NUMBER);
            int checkInIndex = cursor.getColumnIndex(VACATION_TABLE_CHECK_IN);
            int checkOutIndex = cursor.getColumnIndex(VACATION_TABLE_CHECK_OUT);
            String _id = cursor.getString(idIndex);
            String hotelName = cursor.getString(hotelNameIndex);
            String roomNumber = cursor.getString(roomNumberIndex);
            String checkIn = cursor.getString(checkInIndex);
            String checkOut = cursor.getString(checkOutIndex);
            Vacation vacation = new Vacation(_id, hotelName, roomNumber, checkIn, checkOut);
            return vacation;
        }
        return null;
    }

    public static void add(SQLiteDatabase db, Vacation vacation) {
        ContentValues values = new ContentValues();
        values.put(VACATION_TABLE_ID, vacation.getId());
        values.put(VACATION_TABLE_HOTEL_NAME, vacation.getHotelName());
        values.put(VACATION_TABLE_ROOM_NUMBER, vacation.getRoomNumber());
        values.put(VACATION_TABLE_CHECK_IN, vacation.getCheckIn());
        values.put(VACATION_TABLE_CHECK_OUT, vacation.getCheckOut());

        db.insertWithOnConflict(VACATION_TABLE, VACATION_TABLE_ID, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static String getLastUpdateDate(SQLiteDatabase db) {
        return LastUpdateSql.getLastUpdate(db, VACATION_TABLE);
    }

    public static void setLastUpdateDate(SQLiteDatabase db, String date) {
        LastUpdateSql.setLastUpdate(db, VACATION_TABLE, date);
    }
}
