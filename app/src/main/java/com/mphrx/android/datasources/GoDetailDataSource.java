package com.mphrx.android.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mphrx.android.helpers.SQLiteDBHelper;
import com.mphrx.android.vo.GoDetailVO;
/**
 * Created by Shubham Goyal on 12-12-2015.
 * class responsible for sqlite comminication for table go_detail
 */
public class GoDetailDataSource extends Datasource {

    private SQLiteDatabase database;
    private SQLiteDBHelper dbHelper;
    //table and columns
    public static final String GO_DETAIL_TABLE = "go_detail";
    public static final String COL_GO_DETAIL_GODETAILID = "goDetailId";
    public static final String COL_GO_DETAIL_INDEX = "itemIndex";
    public static final String COL_GO_DETAIL_WEBURL = "webUrl";
    public static final String COL_GO_DETAIL_DESCRIPTION = "description";

    // all columns
    public static final String[] goDetailColumns = {
            COL_GO_DETAIL_GODETAILID,
            COL_GO_DETAIL_INDEX,
            COL_GO_DETAIL_WEBURL,
            COL_GO_DETAIL_DESCRIPTION
    };


    public GoDetailDataSource(Context context) {
        super(context);
        dbHelper = SQLiteDBHelper.getInstance();
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close () {
        dbHelper.close();
    }

    public GoDetailVO getGoDetail (int index) {
        showProgressDialog();
        String[] selestionArgs = {String.valueOf(index)};
        Cursor cursor = database.query(GO_DETAIL_TABLE, goDetailColumns, COL_GO_DETAIL_INDEX + "=?", selestionArgs, null, null, null);
        cursor.moveToFirst();
        GoDetailVO goDetailVO = null;
        while (!cursor.isAfterLast()) {
            goDetailVO = new GoDetailVO();
            goDetailVO.setWebUrl(cursor.getString(cursor.getColumnIndex(COL_GO_DETAIL_WEBURL)));
            goDetailVO.setDescription(cursor.getString(cursor.getColumnIndex(COL_GO_DETAIL_DESCRIPTION)));
            cursor.moveToNext();
        }
        dismissProgressDialog();
        return goDetailVO;
    }

    public long insertDetail (int index, GoDetailVO goDetailVO) {
        showProgressDialog();
        ContentValues values = new ContentValues();
        values.put(COL_GO_DETAIL_INDEX, index);
        values.put(COL_GO_DETAIL_WEBURL, goDetailVO.getWebUrl());
        values.put(COL_GO_DETAIL_DESCRIPTION, goDetailVO.getDescription());
        dismissProgressDialog();
        return database.insert(GO_DETAIL_TABLE, null, values);
    }
}
