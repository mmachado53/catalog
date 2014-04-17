package com.wayak.catalog;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.wayak.catalog.adapters.ProductAdapter;
import com.wayak.catalog.db.ProductsDataSource;
import com.wayak.catalog.helpers.DialogsHelper;
import com.wayak.catalog.models.Product;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity implements TextWatcher, OnItemClickListener, OnItemLongClickListener{
	private ListView list;
	private EditText searchTXT;
	private static final int REQUEST_NEW=0;
	private ProductsDataSource dataSource;
	private List<Product> products=new ArrayList<Product>();  
	private ProductAdapter adapter;
	
	private int longClickItem=-1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchTXT=(EditText) findViewById(R.id.searchTXT);
        list=(ListView) findViewById(R.id.listView1);
        list.setOnItemClickListener(this);
        list.setOnItemLongClickListener(this);
        dataSource=ProductsDataSource.get(getApplicationContext());
        adapter=new ProductAdapter(getApplicationContext(), 0);
        list.setAdapter(adapter);
        searchTXT.addTextChangedListener(this);
        refreshData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
		case  R.id.action_add:
			add();
			break;
		case R.id.action_search:
			captureBarcode();
			break;
		default:
			break;
		}
        if (id == R.id.action_add) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

   private void add(){
	   Intent intent=new Intent(MainActivity.this,ProductActivity.class);
	   intent.putExtra(ProductActivity.EXTRA_REQUEST, ProductActivity.REQUEST_NEW);
	   startActivityForResult(intent, REQUEST_NEW);
   }
   
   private void refreshData(){
	   products.clear();
	   dataSource.open();
	   products=dataSource.getAll();
	   dataSource.close();
	   adapter.addAll(products);
	   
   }


@Override
public void afterTextChanged(Editable constraint) {
	// TODO Auto-generated method stub
	
}


@Override
public void beforeTextChanged(CharSequence constraint, int arg1, int arg2, int arg3) {
	// TODO Auto-generated method stub
	
}


@Override
public void onTextChanged(CharSequence constraint, int arg1, int arg2, int arg3) {
	adapter.getFilter().filter(constraint.toString());
	
}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	if (requestCode==REQUEST_NEW  && resultCode == RESULT_OK) {
		refreshData();
		return;
	}
	
	IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
	if (result!=null) {
		String contents = result.getContents();
		if (contents.length()>0) {
			Intent intent=new Intent(MainActivity.this,ProductActivity.class);
			 intent.putExtra(ProductActivity.EXTRA_REQUEST, ProductActivity.REQUEST_CODE);
			 intent.putExtra(ProductActivity.EXTRA_PRODUCT_CODE, Long.parseLong(contents));
			 startActivityForResult(intent, REQUEST_NEW);
		}
		Toast.makeText(getApplicationContext(), contents, Toast.LENGTH_LONG).show();
	}
	/* IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
    	 if (result!=null) {
    		 String contents = result.getContents();
    		 if (contents != null) {
    			 if(checkFuenteId("")){
    				 if (idFuente!=null) {
						idFuente.setText(contents);
					}
    				 //startReportActivity(ReportActivity.NEW, "");
    			 }
    			 //Intent intent=new Intent(MainActivity.this,ReportActivity.class);
    			//intent.putExtra("id", contents);
    			//intent.putExtra("code", ReportActivity.NEW);
    			//startActivityForResult(intent, 0);
    			 
			}
		}*/
	
	//super.onActivityResult(requestCode, resultCode, data);
}


private void captureBarcode(){
	IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
    integrator.initiateScan();
}

@Override
public void onItemClick(AdapterView<?> adapter, View view, int pos, long arg3) {
	Product p=(Product)adapter.getItemAtPosition(pos);
	int productId=p.id;
	
	 Intent intent=new Intent(MainActivity.this,ProductActivity.class);
	 intent.putExtra(ProductActivity.EXTRA_REQUEST, ProductActivity.REQUEST_VIEW);
	 intent.putExtra(ProductActivity.EXTRA_PRODUCT_ID, productId);
	
	 startActivityForResult(intent, REQUEST_NEW);
}


@Override
public boolean onItemLongClick(AdapterView<?> adapter, View view, int pos,
		long arg3) {
	longClickItem=pos;
	DialogsHelper.newOkCancelDialog(MainActivity.this, R.string.delete, R.string.alert_msg_1, DeletProductListener).show();
	return false;
}

private DialogInterface.OnClickListener DeletProductListener=new DialogInterface.OnClickListener() {
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		Product p=adapter.getItem(longClickItem);
		dataSource.open();
		dataSource.deleteProduct(p.id);
		dataSource.close();
		adapter.remove(longClickItem);
	}
};
}
