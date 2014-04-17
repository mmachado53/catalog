package com.wayak.catalog.models;

import java.io.Serializable;

import com.wayak.catalog.db.Schema;
import com.wayak.catalog.helpers.CursorHelper;

import android.content.ContentValues;
import android.database.Cursor;

public class Product implements Serializable, Model {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -383208262376684861L;
	public int id=-1;
	public String name="";
	public String description="";
	public String size="";
	public long code=-1;
	public double price=0.0;
	@Override
	public ContentValues toContentValues() {
		ContentValues result=new ContentValues();
		result.put(Schema.Products.KEY_CODE, code);
		result.put(Schema.Products.KEY_DESCRIPTION, description);
		result.put(Schema.Products.KEY_NAME, name);
		result.put(Schema.Products.KEY_SIZE, size);
		result.put(Schema.Products.KEY_PRICE, price);
		if (this.id != -1) {
			result.put(Schema.Products.KEY_ID, id);
		}
		return result;
	}
	@Override
	public void setFromCursor(Cursor cursor) {
		this.id=CursorHelper.getInt(cursor, Schema.Products.KEY_ID);
		this.code=CursorHelper.getLong(cursor, Schema.Products.KEY_CODE);
		this.description=CursorHelper.getString(cursor, Schema.Products.KEY_DESCRIPTION);
		this.name=CursorHelper.getString(cursor, Schema.Products.KEY_NAME);
		this.size=CursorHelper.getString(cursor, Schema.Products.KEY_SIZE);
		this.price=CursorHelper.getDouble(cursor, Schema.Products.KEY_PRICE);
	}
}
