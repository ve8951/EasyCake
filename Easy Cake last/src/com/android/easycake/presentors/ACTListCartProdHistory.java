package com.android.easycake.presentors;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnCreateContextMenuListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.easycake.R;
import com.android.easycake.dal.DALCart;
import com.android.easycake.dal.DALStoreDetailsByProductId;
import com.android.easycake.models.MODELShoppingCartProduct;
import com.android.easycake.models.MODELStoreInfoforProduct;


public class ACTListCartProdHistory extends SuperListActivity {
	List<MODELShoppingCartProduct> _productdetails;  
	String selectedcartId;
	SQLiteDatabase db;
	Button _headerCartDetailsBT;
	TextView welcomeUser;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderhistory);

		_headerCartDetailsBT=(Button)findViewById(R.id.shopping);
		welcomeUser=(TextView)findViewById(R.id.welcomeuser);

		super.updateHeaderCartDetails(_headerCartDetailsBT,welcomeUser);
		db=openOrCreateDatabase("EasyCake", MODE_PRIVATE, null);

		selectedcartId=getIntent().getExtras().getString("cart_id");

		_productdetails=DALCart.retriveOrderHistory(db, selectedcartId);

		if(_productdetails==null){
			Toast.makeText(getBaseContext(), "No Data Found", Toast.LENGTH_SHORT).show();
		}else{

			setListAdapter(new MyListAdapter(this));
		}



	}	
	public class MyListAdapter  extends ArrayAdapter{
		Activity context;


		public MyListAdapter(Activity context) {

			super(context,R.layout.rowforproductdetail,_productdetails);
			this.context=context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflator=context.getLayoutInflater();
			View row=inflator.inflate(R.layout.rowforproductdetail, null);


			MODELStoreInfoforProduct modelinfostore=DALStoreDetailsByProductId.getCategoriesByproductId(db,_productdetails.get(position).getModelProducts().get_productId());

			//ImageView prodimg=(ImageView)findViewById(R.id.producticon);
			TextView prodname=(TextView)row.findViewById(R.id.productname);	
			TextView prodweight=(TextView)row.findViewById(R.id.weightTV);
			TextView prodingrg=(TextView)row.findViewById(R.id.ingridientTV);
			TextView storename=(TextView)row.findViewById(R.id.storenameinprodlistTV);
			//TextView storeaddress=(TextView)row.findViewById(R.id.storeaddinprodlistTV);
			Button addToCartBTN=(Button)row.findViewById(R.id.productAddToCaryBTN);
			Button favouriteBTN=(Button)row.findViewById(R.id.addToFavBT);
			addToCartBTN.setVisibility(View.GONE);
			favouriteBTN.setVisibility(View.GONE);

			prodname.setText(_productdetails.get(position).getModelProducts().get_productName());
			prodingrg.setText(_productdetails.get(position).getModelProducts().get_ingredient());
			prodweight.setText(_productdetails.get(position).getModelProducts().get_price()+" / "+_productdetails.get(position).getModelProducts().get_productWeight());
			//storename.setText(modelinfostore.getModelStore().get_storeName());
			String storenameforTV=modelinfostore.getModelStore().get_storeName();
			String storeAddress=modelinfostore.getModelStoreBranch().get_address();
			storename.setText(storenameforTV+" , "+storeAddress);

			return(row);
		}

	}

	@Override
	protected void onPause() {
		db.close();
		super.onPause();
	}

	@Override
	protected void onResume() {
		db=openOrCreateDatabase("EasyCake", MODE_PRIVATE, null);
		super.updateHeaderCartDetails(_headerCartDetailsBT,welcomeUser);
		super.onResume();
	}


	public void easyGo(View v){

		super.easyGo(v, this);

	}

}
