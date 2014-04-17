package com.wayak.catalog;

import com.wayak.catalog.db.ProductsDataSource;
import com.wayak.catalog.models.Product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProductActivity extends Activity implements OnClickListener{
	private EditText nameTXT;
	private EditText descriptionTXT;
	private EditText priceTXT;
	private EditText sizeTXT;
	private TextView codeTXT;
	
	private Product model;
	private int productID;
	private ProductsDataSource dataSource;
	

	public static String EXTRA_PRODUCT_ID="id";
	public static String EXTRA_PRODUCT_CODE="code";
	public static String EXTRA_REQUEST="request";
	public static final int REQUEST_NEW=0;
	public static final int REQUEST_VIEW=1;
	public static final int REQUEST_EDIT=2;
	public static final int REQUEST_CODE=3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		dataSource=ProductsDataSource.get(getApplicationContext());
		setContentView(R.layout.activity_product);
		((Button)findViewById(R.id.saveBTN)).setOnClickListener(this);
		((Button)findViewById(R.id.cancelBTN)).setOnClickListener(this);
		nameTXT=(EditText) findViewById(R.id.nameTXT);
		descriptionTXT=(EditText) findViewById(R.id.descriptionTXT);
		priceTXT=(EditText) findViewById(R.id.priceTXT);
		sizeTXT=(EditText) findViewById(R.id.sizeTXT);
		codeTXT=(TextView) findViewById(R.id.codeTXT);
		Bundle extras=getIntent().getExtras();
		if (extras!=null) {
			int request=extras.getInt(EXTRA_REQUEST);
			switch (request) {
			case REQUEST_NEW:
				model=new Product();
				break;
			case REQUEST_VIEW:
				productID=extras.getInt(EXTRA_PRODUCT_ID);
				dataSource.open();
				model=dataSource.getProduct(productID);
				dataSource.close();
				setData();
				break;
			case REQUEST_EDIT:
				productID=extras.getInt(EXTRA_PRODUCT_ID);
				dataSource.open();
				model=dataSource.getProduct(productID);
				dataSource.close();
				setData();
				break;
			case REQUEST_CODE:
				long code=extras.getLong(EXTRA_PRODUCT_CODE);
				dataSource.open();
				model=dataSource.getProductByCode(code);
				dataSource.close();
				if (model==null) {
					model=new Product();
					model.code=code;
				}
				setData();
				break;
			default:
				model=new Product();
				break;
			}
		}else{
			model=new Product();
		}
		
		
		setResult(RESULT_CANCELED);
	}
	
	private void setData(){
		this.codeTXT.setText(model.code+"");
		this.descriptionTXT.setText(model.description);
		this.nameTXT.setText(model.name);
		this.priceTXT.setText(model.price+"");
		this.sizeTXT.setText(model.size);
		this.codeTXT.setText(model.code+"");
	}

	@Override
	public void onClick(View v) {
		int id=v.getId();
		switch (id) {
		case R.id.saveBTN:
			save();
			break;
		case R.id.cancelBTN:
			finish();
			break;
		default:
			break;
		}
		
	}
	
	private void save(){
		if (nameTXT.getText().toString().length()>0) {
			model.name=nameTXT.getText().toString();
		}else{
			Toast.makeText(getApplicationContext(), R.string.error_msg_1, Toast.LENGTH_LONG).show();
			return;
		}
		if (descriptionTXT.getText().toString().length()>0) {
			model.description=descriptionTXT.getText().toString();
		}else{
			Toast.makeText(getApplicationContext(), R.string.error_msg_1, Toast.LENGTH_LONG).show();
			return;
		}
		if (priceTXT.getText().toString().length()>0) {
			model.price=Double.parseDouble(priceTXT.getText().toString());
		}else{
			Toast.makeText(getApplicationContext(), R.string.error_msg_1, Toast.LENGTH_LONG).show();
			return;
		}
		if (sizeTXT.getText().toString().length()>0) {
			model.size=sizeTXT.getText().toString();
		}else{
			Toast.makeText(getApplicationContext(), R.string.error_msg_1, Toast.LENGTH_LONG).show();
			return;
		}
		dataSource.open();
		dataSource.saveProduct(model);
		dataSource.close();
		setResult(RESULT_OK);
		finish();
	}
	
}
