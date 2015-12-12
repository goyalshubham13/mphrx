package com.mphrx.android.application;

import android.app.Application;

import com.mphrx.android.constants.Constants;
import com.mphrx.android.helpers.SQLiteDBHelper;

/**
 * Created by Shubham Goyal on 12-12-2015.
 * Application
 */
public class MphrxApp extends Application {

    private SQLiteDBHelper dbHelper;
    // declare all the sqlite query to initiate sqlite db
    private String[] dbCreateQueries = {
            "create table go_detail (goDetailId integer primary key autoincrement, itemIndex integer, webUrl text, description text);"
    };

    @Override
    public void onCreate() {
        super.onCreate();

        // initialize db helper class
        dbHelper = SQLiteDBHelper.getInstance(getApplicationContext(), Constants.DB.DB_NAME, Constants.DB.DB_VERSION);
        //set list of tables
        dbHelper.setTables(Constants.DB.tables);
        //set queries to get executed (DDLs)
        dbHelper.setDBCreateQuery(dbCreateQueries);
        //initiate db
        dbHelper.getWritableDatabase();

    }
}
