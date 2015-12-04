package com.android.easycake.presentors;

import java.util.ArrayList;
import java.util.List;

import com.android.easycake.R;
import com.android.easycake.dal.DALStoreSearch;
import com.android.easycake.models.MODELStoreBranch;
import com.android.easycake.models.MODELStoreBranches;

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

public class ACTListStoreBranches extends SuperListActivity{
	List<MODELStoreBranch> storeBranchesData=new ArrayList<MODELStoreBranch>();
	String[] storeBranch;
	SQLiteDatabase db;
	DALStoreSearch dalStoreSearch=new DALStoreSearch();
	String myValstoreId;
	String myValstoreName;
	String myCityName;
	String cityAndStore;
	Button _headerCartDetailsBT;
	TextView welcomeUser;
	//Bundle recdData=getIntent().getExtras();


	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		myValstoreId=getIntent().getExtras().getString("storePass");
		myValstoreName=getIntent().getExtras().getString("storeName");
		myCityName=getIntent().getExtras().getString("cityName");
		cityAndStore=myCityName+">>"+myValstoreName;
		setContentView(R.layout.listbranches);

		_headerCartDetailsBT=(Button)findViewById(R.id.shopping);
		welcomeUser=(TextView)findViewById(R.id.welcomeuser);

		super.updateHeaderCartDetails(_headerCartDetailsBT,welcomeUser);
		db=openOrCreateDatabase("EasyCake", MODE_PRIVATE, null);

		TextView storeName=(TextView)findViewById(R.id.storeNametextView);
		storeName.setText(cityAndStore);


		storeBranchesData=dalStoreSearch.readBranchesByStoreId(db,myValstoreId);

		storeBranch=new String[storeBranchesData.size()];

		for(int i=0;i<storeBranchesData.size();i++)
		{
			storeBranch[i]=storeBranchesData.get(i).get_address();
		}
		//setListAdapter(new ArrayAdapter(getBaseContext(),android.R.layout.simple_list_item_1,storeBranch));

		setListAdapter(new MyListAdapter(this));


	}





	public class MyListAdapter  extends ArrayAdapter{
		Activity context;

		public MyListAdapter(Activity context) {
			super(context,R.layout.rowlistbranches,storeBranch);
			this.context=context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflator=context.getLayoutInflater();
			View row=inflator.inflate(R.layout.rowlistbranches, null);


			TextView name=(TextView)row.findViewById(R.id.storeBranchNameTextview);
			//TextView name1=(TextView)row.findViewById(R.id.storeNameTextview);


			name.setText(storeBranch[position]);
			//name1.setText(myValstoreName);



			return(row);
		}

	}



	protected void onListItemClick(ListView l, View v, int position, long id) {

		//		  
		//		  MODELStoreBranches branches=new MODELStoreBranches();
		//		  branches=UtrilBranchDetails.branches;
		//		  branches.get_storeBranchID();
		//storeData.get(position).get_storeID()
		// Intent i=new Intent(ACTStorebranchesdisplay.this,ACTProducts.class);
		//		  Bundle b=new Bundle();
		//		  b.putSerializable("selectedBranch", storeBranchesData.get(position));
		//i.putExtra("storePass",storeBranchesData.get(position).get_storeBranchID());
		//i.putExtra("storeName", storeData.get(position).get_storeName());
		// startActivity(i);

		Intent i=new Intent(ACTListStoreBranches.this,ACTListProdByBranch.class);
		Toast.makeText(getBaseContext(), storeBranchesData.get(position).get_storeBranchID(), Toast.LENGTH_SHORT).show();

		i.putExtra("storeBranchId", storeBranchesData.get(position).get_storeBranchID());
		i.putExtra("storeBranchName", storeBranchesData.get(position).get_address());
		i.putExtra("cityName",cityAndStore);


		startActivity(i);
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


