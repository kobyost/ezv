package com.koby.EazyVacation.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class is responsible for the management and handling of all the tables in the sql cache
 * Created by קובי on 02/08/2016.
 */
public class ModelSql {

    private static final int VERSION = 17;

    Helper sqlDb;

    public ModelSql(Context context) {
        sqlDb = new Helper(context);
    }

    public Helper getSqlDb() {
        return sqlDb;
    }

    public void setSqlDb(Helper sqlDb) {
        this.sqlDb = sqlDb;
    }

    public SQLiteDatabase getWritableDB() {
        return sqlDb.getWritableDatabase();
    }

    public SQLiteDatabase getReadbleDB() {
        return sqlDb.getReadableDatabase();
    }

    class Helper extends SQLiteOpenHelper {
        public Helper(Context context) {
            super(context, "database.db", null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            HotelSql.create(db);
            MealBreakFastSql.create(db);
            MealLunchSql.create(db);
            MealDinnerSql.create(db);
            VacationSql.create(db);
            LastUpdateSql.create(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            HotelSql.drop(db);
            MealBreakFastSql.drop(db);
            MealLunchSql.drop(db);
            MealDinnerSql.drop(db);
            LastUpdateSql.drop(db);
            VacationSql.drop(db);
            onCreate(db);
        }
    }

}
