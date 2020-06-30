package com.test.exchange_rate.dbHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.test.exchange_rate.model.CurrencyRate;

import java.util.ArrayList;
import java.util.List;

public class CurrencyRateDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "CurrencyRateDB.db";

    //Table name
    private static final String CURRENCY_RATE_TABLE = "currency";

    //currency rate table column name
    private static final String ID = "id";
    private static final String TYPE = "type";
    private static final String VALUE = "value";


    public CurrencyRateDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_TABLE = " CREATE TABLE " + CURRENCY_RATE_TABLE + "("
                + ID + " INTEGER PRIMARY KEY ,"
                + TYPE + " TEXT, "
                + VALUE + " TEXT ) ";

        //execute table
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("  DROP TABLE IF EXISTS  " + CURRENCY_RATE_TABLE);
        onCreate(db);
    }

    //insert currency rate from json to database
    public void addCurrencyRate(CurrencyRate currencyRate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID,currencyRate.getId());
        values.put(TYPE, currencyRate.getType());
        values.put(VALUE, currencyRate.getValue());

        // Inserting Row
        db.insert(CURRENCY_RATE_TABLE, null, values);
    }

    //get all currency rate list
    public List<CurrencyRate> getCurrencyRate() {
        List<CurrencyRate> currencyList = new ArrayList<>();

        String selectQuery = " SELECT * FROM " + CURRENCY_RATE_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CurrencyRate currencyRate = new CurrencyRate();

                currencyRate.setId(cursor.getInt(0));
                currencyRate.setType(cursor.getString(1));
                currencyRate.setValue(cursor.getString(2));

                currencyList.add(currencyRate);

            } while (cursor.moveToNext());

            cursor.close();

        }
        return currencyList;
    }
}
