package com.wayak.catalog.models;

import android.content.ContentValues;
import android.database.Cursor;

public interface Model {
	ContentValues toContentValues();
	void setFromCursor(Cursor cursor);
}
