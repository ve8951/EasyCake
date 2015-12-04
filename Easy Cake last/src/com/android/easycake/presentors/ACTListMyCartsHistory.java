package com.android.easycake.presentors;

import java.util.ArrayList;
import java.util.List;

import com.android.easycake.R;
import com.android.easycake.dal.DALCart;
import com.android.easycake.dal.DALOrderStatus;
import com.android.easycake.models.MODELCartProduct;
import com.android.easycake.models.MODELDeliveryStatus;
import com.android.easycake.models.MODELUserCart;
import com.android.easycake.utilities.UTILSSessionVariables;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ACTListMyCartsHistory extends SuperListActivity{

	List<MODELUserCart> _cartdetails=new ArrayList<MODELUserCart>();
	//List<MODELCities> _cityid=new ArrayList<MODELCities>();
	SQLiteDatabase db;

	Button _headerCartDetailsBT;
	TextView welcomeUser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trackdelivery);

		_headerCartDetailsBT=(Button)findViewById(R.id.shopping);
		welcomeUser=(TextView)findViewById(R.id.welcomeuser);
		super.updateHeaderCartDetails(_headerCartDetailsBT,welcomeUser);

		db=openOrCreateDatabase("EasyCake", MODE_PRIVATE, null);
		MODELCartProduct modelciies=new MODELCartProduct();
		_cartdetails=DALCart.retriveuserCarts(db, UTILSSessionVariables.modelUserSessionVar.get_userId());

		Toast.makeText(getBaseContext(), "am innnnnnn", Toast.LENGTH_SHORT).show();
		setListAdapter(new MyListAdapter(this));
	}



	public class MyListAdapter  extends ArrayAdapter{
		Activity context;


		public MyListAdapter(Activity context) {

			super(context,R.layout.rowlistdeliverystatus, _cartdetails);
			this.context=context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflator=context.getLayoutInflater();
			View row=inflator.inflate(R.layout.rowlistdeliverystatus,null);


			TextView _cartdateTV=(TextView)row.findViewById(R.id.cartdateTV);	
			TextView _carttimeTV=(TextView)row.findViewById(R.id.carttimeTV);
			TextView _cartproductNoTV=(TextView)row.findViewById(R.id.cartproductnoTV);
			TextView _cartpriceTV=(TextView)row.findViewById(R.id.cartpriceTV);

			MODELDeliveryStatus modelDeliveryStatus=new MODELDeliveryStatus();
			modelDeliveryStatus=DALOrderStatus.getStatusById(db,_cartdetails.get(position).get_status() );

			_cartdateTV.setText(_cartdetails.get(position).get_dateOfCreation());
			_carttimeTV.setText(_cartdetails.get(position).get_timeOfCreation());
			_cartproductNoTV.setText(modelDeliveryStatus.get_statusValue());
			_cartpriceTV.setText(_cartdetails.get(position).get_cartprice());




			return(row);
		}

	}
//	protected void onListItemClick(ListView l, View v, int position, long id) {
//
//
//		Toast.makeText(getBaseContext(), _cartdetails.get(position).get_shoppingCartId(), Toast.LENGTH_SHORT).show();
//		Intent i=new Intent(ACTListMyCartsHistory.this,ACTListCartProdHistory.class);
//		i.putExtra("cart_id", _cartdetails.get(position).get_shoppingCartId());
//		startActivity(i);
//
//
//	}

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
