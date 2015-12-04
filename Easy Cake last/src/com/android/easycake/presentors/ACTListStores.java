package com.android.easycake.presentors;

import java.util.ArrayList;
import java.util.List;

import com.android.easycake.R;
import com.android.easycake.dal.DALStoreSearch;
import com.android.easycake.models.MODELCity;
import com.android.easycake.models.MODELStore;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ACTListStores extends SuperListActivity {

	List<MODELCity> cityData = new ArrayList<MODELCity>();
	List<MODELStore> storeData=new ArrayList<MODELStore>();
	String[] cityname;
	String[] storename;
	SQLiteDatabase db;
	DALStoreSearch dalStoreSearch = new DALStoreSearch();
	Spinner city;
	String cityForIntent;
	Button _headerCartDetailsBT;
	TextView welcomeUser;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.liststores);

		_headerCartDetailsBT=(Button)findViewById(R.id.shopping);
		welcomeUser=(TextView) findViewById(R.id.welcomeuser);

		super.updateHeaderCartDetails(_headerCartDetailsBT,welcomeUser);
		db = openOrCreateDatabase("EasyCake", MODE_PRIVATE, null);

		cityPopulate();


	}



	public void cityPopulate() {
		cityData = dalStoreSearch.getCities(db);
		Spinner city = (Spinner) findViewById(R.id.city);
		cityname = new String[cityData.size()];
		for (int i = 0; i < cityData.size(); i++) {
			cityname[i] = cityData.get(i).get_storeCityName();
		}

		city.setAdapter(new ArrayAdapter(getBaseContext(),android.R.layout.simple_dropdown_item_1line, cityname));

		city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {



				Toast.makeText(getBaseContext(),
						cityData.get(pos).get_storeCityID(),
						Toast.LENGTH_SHORT).show();

				storespopulate(pos);
				cityForIntent=cityData.get(pos).get_storeCityName();

			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}




	public class MyListAdapter  extends ArrayAdapter{
		Activity context;

		public MyListAdapter(Activity context) {
			super(context,R.layout.rowliststores,storename);
			this.context=context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflator=context.getLayoutInflater();
			View row=inflator.inflate(R.layout.rowliststores, null);


			TextView name=(TextView)row.findViewById(R.id.storeTextview);	


			name.setText(storename[position]);




			return(row);
		}

	}

	protected void onListItemClick(ListView l, View v, int position, long id) {

		//storeData.get(position).get_storeID()
		Intent i=new Intent(ACTListStores.this,ACTListStoreBranches.class);
		i.putExtra("storePass",storeData.get(position).get_storeID());
		i.putExtra("storeName", storeData.get(position).get_storeName());
		i.putExtra("cityName", cityForIntent);
		startActivity(i);

		//Toast.makeText(getBaseContext(), storeData.get(position).get_storeID(), Toast.LENGTH_SHORT).show();
	}






	public void storespopulate(int pos)
	{

		storeData=dalStoreSearch.getStoresByCity(db,cityData.get(pos).get_storeCityID());
		storename=new String[storeData.size()];
		for(int j=0;j<storeData.size();j++)
		{
			storename[j]=storeData.get(j).get_storeName();
		}

		setListAdapter(new MyListAdapter(ACTListStores.this));

	}

	@Override
	protected void onPause() {
		db.close();
		super.onPause();
	}

	public void easyGo(View v){

		super.easyGo(v, ACTListStores.this);

	}

	@Override
	protected void onResume() {
		db=openOrCreateDatabase("EasyCake", MODE_PRIVATE, null);
		super.updateHeaderCartDetails(_headerCartDetailsBT,welcomeUser);
		super.onResume();
	}
}
