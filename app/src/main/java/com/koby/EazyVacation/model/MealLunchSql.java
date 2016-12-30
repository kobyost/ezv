package com.koby.EazyVacation.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

/**
 * This class represents the Meal of type lunch in the sql way.(table of  lunch meals)
 * Created by קובי on 14/08/2016.
 */
public class MealLunchSql {

    final static String MEAL_TABLE = "mealsLunch1";
    final static String MEAL_TABLE_ID = "mealName";
    final static String MEAL_TABLE_IMAGE_NAME = "imageName";
    final static String MEAL_TABLE_PRICE = "price";
    final static String MEAL_TABLE_CHECKABLE = "checkable";


    static public void create(SQLiteDatabase db) {
        db.execSQL("create table " + MEAL_TABLE + " (" +
                MEAL_TABLE_ID + " TEXT PRIMARY KEY," +
                MEAL_TABLE_IMAGE_NAME + " TEXT," +
                MEAL_TABLE_PRICE + " TEXT," +
                MEAL_TABLE_CHECKABLE + " BOOLEAN);");
    }

    public static void drop(SQLiteDatabase db) {
        db.execSQL("drop table " + MEAL_TABLE + ";");
    }

    public static List<Meal> getAllMeals(SQLiteDatabase db) {
        Cursor cursor = db.query(MEAL_TABLE, null, null, null, null, null, null);
        List<Meal> meals = new LinkedList<Meal>();

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(MEAL_TABLE_ID);
            int imageNameIndex = cursor.getColumnIndex(MEAL_TABLE_IMAGE_NAME);
            int priceIndex = cursor.getColumnIndex(MEAL_TABLE_PRICE);
            int checkableIndex = cursor.getColumnIndex(MEAL_TABLE_CHECKABLE);
            do {
                String id = cursor.getString(idIndex);
                String imageName = cursor.getString(imageNameIndex);
                String price = cursor.getString(priceIndex);
                int checkable = cursor.getInt(checkableIndex); //0 false / 1 true
                Meal meal = new Meal(id, imageName, price, checkable == 1);
                meals.add(meal);
            } while (cursor.moveToNext());
        }
        return meals;
    }

    public static Meal getMealById(SQLiteDatabase db, String id) {
        String where = MEAL_TABLE_ID + " = ?";
        String[] args = {id};
        Cursor cursor = db.query(MEAL_TABLE, null, where, args, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(MEAL_TABLE_ID);
            int imageNameIndex = cursor.getColumnIndex(MEAL_TABLE_IMAGE_NAME);
            int priceIndex = cursor.getColumnIndex(MEAL_TABLE_PRICE);
            int checkableIndex = cursor.getColumnIndex(MEAL_TABLE_CHECKABLE);
            ;

            String _id = cursor.getString(idIndex);
            String imageName = cursor.getString(imageNameIndex);
            String price = cursor.getString(priceIndex);
            int checkable = cursor.getInt(checkableIndex); //0 false / 1 true
            Meal meal = new Meal(_id, imageName, price, checkable == 1);
            return meal;
        }
        return null;
    }

    public static void add(SQLiteDatabase db, Meal meal) {
        ContentValues values = new ContentValues();
        values.put(MEAL_TABLE_ID, meal.getMealName());
        values.put(MEAL_TABLE_IMAGE_NAME, meal.getImageName());
        values.put(MEAL_TABLE_PRICE, meal.getPrice());
        if (meal.getCheck()) {
            values.put(MEAL_TABLE_CHECKABLE, 1);
        } else {
            values.put(MEAL_TABLE_CHECKABLE, 0);
        }
        db.insertWithOnConflict(MEAL_TABLE, MEAL_TABLE_ID, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static String getLastUpdateDate(SQLiteDatabase db) {
        return LastUpdateSql.getLastUpdate(db, MEAL_TABLE);
    }

    public static void setLastUpdateDate(SQLiteDatabase db, String date) {
        LastUpdateSql.setLastUpdate(db, MEAL_TABLE, date);
    }
}
