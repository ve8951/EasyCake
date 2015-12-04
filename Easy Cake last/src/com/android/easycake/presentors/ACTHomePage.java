package com.android.easycake.presentors;

import com.android.easycake.R;
import com.android.easycake.models.MODELUser;
import com.android.easycake.utilities.UTILSSessionVariables;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
public class ACTHomePage extends SuperActivity {

	TextView _usernameTV;
	Button _headerCartDetailsBT;
	TextView welcomeUser;

	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage);
_headerCartDetailsBT=(Button)findViewById(R.id.shopping);
welcomeUser=(TextView)findViewById(R.id.welcomeuser);
		
		super.updateHeaderCartDetails(_headerCartDetailsBT,welcomeUser);

		_usernameTV=(TextView)findViewById(R.id.welcomeuser);

		MODELUser modeluser=new MODELUser();

		modeluser= UTILSSessionVariables.modelUserSessionVar;


		_usernameTV.setText("Welcome "+modeluser.get_firstName().toString().toUpperCase());

		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new ImageAdapter(this));

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				switch (position) {
				case 0:
					startActivity(new Intent(ACTHomePage.this,ACTListStores.class));
					break;

				case 1:
					startActivity(new Intent(ACTHomePage.this,ACTListCities.class));
					break;

				case 2:
					startActivity(new Intent(ACTHomePage.this,ACTProfile.class));
					break;

				case 3:
					startActivity(new Intent(ACTHomePage.this,ACTShoppingCart.class));
					break;

				case 4:
					startActivity(new Intent(ACTHomePage.this,ACTFavourites.class));
					break;

				case 5:
					startActivity(new Intent(ACTHomePage.this,ACTListMyCartsHistory.class));
					break;

				}
			}
		});
	}

	public class ImageAdapter extends BaseAdapter {
		public ImageAdapter(Context c) {
		}

		public int getCount() {
			return 6;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		// create a new ImageView for each item referenced by the Adapter
		public View getView(int position, View convertView, ViewGroup parent) {
			View v;

		

			LayoutInflater li = getLayoutInflater();
			v = li.inflate(R.layout.griditem, null);
			
			
			ImageView iv = (ImageView)v.findViewById(R.id.home_image);
			iv.setBackgroundResource(mPicIds[position]);

			TextView tv = (TextView)v.findViewById(R.id.home_text);
			tv.setText(labels[position]);
			
			return v;

		}

		// references to our images
		private Integer[] mPicIds = {
				R.drawable.store, 
				R.drawable.flavour,
				R.drawable.profile,
				R.drawable.shoppingcart,
				R.drawable.favorites,
				R.drawable.trackadelivery, 


		};
		
		private String[] labels={"Search by Store","Search by Flavours","Profile","Shopping Cart","Favorites","Track a Delivery"};
				
	}

	public void easyGo(View v){

		super.easyGo(v, this);

	}
	@Override
	protected void onResume() {
		super.updateHeaderCartDetails(_headerCartDetailsBT,welcomeUser);
		super.onResume();
	}

}

