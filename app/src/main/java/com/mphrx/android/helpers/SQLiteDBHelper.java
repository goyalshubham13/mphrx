package com.mphrx.android.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mphrx.android.exceptions.SQLiteDBException;

import java.util.List;
/**
 * Created by Shubham Goyal on 12-12-2015.
 */
public class SQLiteDBHelper extends SQLiteOpenHelper {

	private String[] DB_CREATE_SQL;
	private List<String> tables;
	private static SQLiteDBHelper instance;
	private SQLiteDBHelper(Context context, String dbName, int version) {
		super(context, dbName, null, version);
	}

	// get instance with dbname and version
	public static SQLiteDBHelper getInstance (Context context, String dbName, int version) {
		if(instance == null) {
			instance = new SQLiteDBHelper(context, dbName, version);
		}
		return instance;
	}

	// class must be initialize with other dbname and version first before using this method
	public static SQLiteDBHelper getInstance () {
		try {
			if(instance == null)
				throw new SQLiteDBException("Get instance by arguments first : use SQLiteDBHelper.getInstance (Context context, String name, CursorFactory factory,	int version)");
		} catch (SQLiteDBException ex) {
			ex.printStackTrace();
		}
		return instance;
	}
	
	public void setDBCreateQuery (String[] dbCreateQuery) {
		DB_CREATE_SQL = dbCreateQuery;
	}
	
	public void setTables(List<String> tables) {
		this.tables = tables;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		if(DB_CREATE_SQL.length == 0) {
			try {
				throw new SQLiteDBException("You may have missed to pass create DB query. Call setDBCreateQuery method to define your initial database.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		for (int i = 0 ; i < DB_CREATE_SQL.length ; i ++) {
			db.execSQL(DB_CREATE_SQL[i]);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (tables.size() == 0) {
			try {
				throw new SQLiteDBException("You may have missed to initialize table names. Call setTables method to define your database tables.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		for (int index = 0 ; index < tables.size(); index ++) {
			db.execSQL("DROP TABLE IF EXISTS " + tables.get(index));
		}
		onCreate(db);
	}
}
