package com.wayak.catalog.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppSQLiteHelper extends SQLiteOpenHelper {
	private static final String DB_FILE="catalog.db";
	private static final int DB_VERSION=2;
	
	public AppSQLiteHelper(Context context) {
		super(context, DB_FILE, null, DB_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(Schema.Products.QUERY_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		db.execSQL(Schema.Products.QUERY_UPDATE);
	}

}
