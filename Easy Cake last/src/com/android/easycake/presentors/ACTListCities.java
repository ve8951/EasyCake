package com.android.easycake.presentors;

import java.util.ArrayList;
import java.util.List;

import com.android.easycake.R;
import com.android.easycake.dal.DALRegistration;
import com.android.easycake.models.MODELCity;



import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ACTListCities extends SuperListActivity{

	SQLiteDatabase db;
	List<MODELCity> citydata=new ArrayList<MODELCity>();
	Spinner cities;
	String[] cityname;
	Button _goBTN;
	MODELCity modelcuCities=new MODELCity();
	Integer currentCitySpinnerPosition;
	Button _headerCartDetailsBT;
	TextView welcomeUser;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectbycity);
_headerCartDetailsBT=(Button)findViewById(R.id.shopping);
welcomeUser=(TextView)findViewById(R.id.welcomeuser);
		
		super.updateHeaderCartDetails(_headerCartDetailsBT,welcomeUser);
		
		_goBTN=(Button)findViewById(R.id.gotoflavourBTN);
		

		cityPopulate();
		_goBTN.setOnClickListener(new View.OnClickListener() {


			public void onClick(View v) {

				Intent i1=new Intent(ACTListCities.this,ACTListProdByFlavour.class);
				i1.putExtra("city_id", citydata.get(currentCitySpinnerPosition).get_storeCityID());
				startActivity(i1);


			}
		});


	}
	@SuppressWarnings("unchecked")
	public void cityPopulate() {
		db=openOrCreateDatabase("EasyCake", MODE_PRIVATE, null);
		cities=(Spinner)findViewById(R.id.cityforflavour);
		citydata=DALRegistration.getCities(db);
		cityname=new String[citydata.size()];

		for(int i=0;i<citydata.size();i++)
		{
			cityname[i]=citydata.get(i).get_storeCityName();
		}


		cities.setAdapter(new ArrayAdapter(getBaseContext(),
				android.R.layout.simple_dropdown_item_1line,cityname));

		cities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,int pos, long arg3) {
				currentCitySpinnerPosition=pos;


			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});



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
