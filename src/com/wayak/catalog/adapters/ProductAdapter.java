package com.wayak.catalog.adapters;

import java.util.ArrayList;
import java.util.List;

import com.wayak.catalog.R;
import com.wayak.catalog.models.Product;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

public class ProductAdapter extends ArrayAdapter<Product> {
	
	List<Product> products=new ArrayList<Product>();
	List<Product> allProducts=new ArrayList<Product>();
	ProductFilter filter=null;
	Context ctx;
	LayoutInflater inflater;
	public ProductAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		ctx=context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// TODO Auto-generated constructor stub
	}
	

	public void remove(int pos) {
		products.remove(pos);
		notifyDataSetChanged();
	}
	
	@Override
	public Filter getFilter() {
		if (filter==null) {
			filter=new ProductFilter();
		}
		return filter;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return products.size();
	}
	
	@Override
	public Product getItem(int position) {
		// TODO Auto-generated method stub
		return products.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return products.get(position).hashCode();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Product product=getItem(position);
		ProductHolder holder;
		if (convertView==null) {
			convertView=inflater.inflate(R.layout.product_list_item, null);
			holder=new ProductHolder();
			holder.nameTXT=(TextView) convertView.findViewById(R.id.nameTXT);
			holder.priceTXT=(TextView) convertView.findViewById(R.id.priceTXT);
			holder.sizeTXT=(TextView) convertView.findViewById(R.id.sizeTXT);
			holder.codeTXT=(TextView) convertView.findViewById(R.id.codeTXT);
			convertView.setTag(holder);
		}else{
			holder=(ProductHolder) convertView.getTag();
		}
		
		holder.nameTXT.setText(product.name);
		holder.priceTXT.setText("$"+product.price);
		holder.sizeTXT.setText(product.size);
		holder.codeTXT.setText(product.code+"");
		return convertView;
	}
	
	public void addAll(List<Product> data){
		products.clear();
		allProducts.clear();
		products=data;
		allProducts=data;
		notifyDataSetChanged();
		
	}
	
	private class ProductFilter extends Filter{
		
		
		
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			String constraintSt=constraint.toString().toLowerCase();
			FilterResults result=new FilterResults();
			if (constraint == null || constraint.length() == 0) {
				result.values = allProducts;
				result.count = allProducts.size();
		    }
		    else {
		        List<Product> productsResult = new ArrayList<Product>();
		        for (Product p : allProducts) {
		        	if (p.name.toLowerCase().contains(constraintSt) || (p.code+"").toLowerCase().contains(constraintSt)) {
		        		productsResult.add(p);
					}
		        	result.values = productsResult;
		        	result.count = productsResult.size();
		        }
		
		    }
			return result;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults result) {
			 if (result.count == 0)
			        notifyDataSetInvalidated();
			    else {
			        products=(List<Product>) result.values;
			        notifyDataSetChanged();
			    }
			
		}
		
	}
	
	private class ProductHolder{
		TextView codeTXT;
		TextView nameTXT;
		TextView priceTXT;
		TextView sizeTXT;
	}
}
