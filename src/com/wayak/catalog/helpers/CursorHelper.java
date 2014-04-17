package com.wayak.catalog.helpers;

import android.database.Cursor;

public final class CursorHelper {
	public static String getString(Cursor cursorRow, String name){
		
		int columnIndex=cursorRow.getColumnIndex(name);
		if (!cursorRow.isNull(columnIndex)) {
			return cursorRow.getString(columnIndex);
		}
		return null;
	} 
	public static int getInt(Cursor cursorRow, String name){
		int columnIndex=cursorRow.getColumnIndex(name);
		if (!cursorRow.isNull(columnIndex)) {
			return cursorRow.getInt(columnIndex);
		}
		return 0;
	}
	public static double getDouble(Cursor cursorRow, String name){
		int columnIndex=cursorRow.getColumnIndex(name);
		if (!cursorRow.isNull(columnIndex)) {
			return cursorRow.getDouble(columnIndex);
		}
		return 0;
	}
	
	public static float getFloat(Cursor cursorRow, String name){
		int columnIndex=cursorRow.getColumnIndex(name);
		if (!cursorRow.isNull(columnIndex)) {
			return cursorRow.getFloat(columnIndex);
		}
		return 0;
	}
	
	public static long getLong(Cursor cursorRow, String name){
		int columnIndex=cursorRow.getColumnIndex(name);
		if (!cursorRow.isNull(columnIndex)) {
			return cursorRow.getLong(columnIndex);
		}
		return 0;
	}
	public static short getShort(Cursor cursorRow, String name){
		int columnIndex=cursorRow.getColumnIndex(name);
		if (!cursorRow.isNull(columnIndex)) {
			return cursorRow.getShort(columnIndex);
		}
		return 0;
	}
}
