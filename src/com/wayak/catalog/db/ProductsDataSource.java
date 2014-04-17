package com.wayak.catalog.db;




import java.util.ArrayList;
import java.util.List;

import com.wayak.catalog.models.Product;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



public class ProductsDataSource implements DataSource {
	private Context ctx;
	private SQLiteDatabase db;
	private AppSQLiteHelper appSQLiteHelper;
	private static ProductsDataSource instance=null;
	private String TABLE_NAME=Schema.Products.TABLE_NAME;
	
	private ProductsDataSource(Context context){
		this.ctx=context;
		appSQLiteHelper=new AppSQLiteHelper(this.ctx);
	}
	public static ProductsDataSource get(Context context){
		instance= instance == null ? new ProductsDataSource(context) : instance;
		return instance;
	}
	@Override
	public void open() {
		this.db=appSQLiteHelper.getWritableDatabase();
	}

	@Override
	public void close() {
		this.appSQLiteHelper.close();
	}
	
	public void saveProduct(Product model){
		db.replace(TABLE_NAME, null, model.toContentValues());
	}
	
	public List<Product> getAll(){
		List<Product> result=new ArrayList<Product>();
		Cursor cursor=db.query(TABLE_NAME, null, null, null, null, null, Schema.Products.KEY_NAME);
		
		if (cursor.moveToFirst()) {
			do {
				Product product=new Product();
				product.setFromCursor(cursor);
				result.add(product);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return result;
	}
	
	public Product getProduct(int id){
		
		Cursor cursor=db.query(TABLE_NAME, null, Schema.Products.KEY_ID+"="+id, null, null, null, null);
		if (cursor.moveToFirst()) {
			Product result=new Product();
			result.setFromCursor(cursor);
			cursor.close();
			return result;
		}
		cursor.close();
		return null;
	}
	
	public Product getProductByCode(long code){
		Cursor cursor=db.query(TABLE_NAME, null, Schema.Products.KEY_CODE+"="+code, null, null, null, null);
		if (cursor.moveToFirst()) {
			Product result=new Product();
			result.setFromCursor(cursor);
			cursor.close();
			return result;
		}
		cursor.close();
		return null;
	}
	
	public void deleteProduct(int id){
		db.delete(TABLE_NAME, Schema.Products.KEY_ID+" = "+id, null);
	}
}
