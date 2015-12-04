package com.android.easycake.dal;

import java.util.ArrayList;
import java.util.List;

import com.android.easycake.models.MODELFlavourDetails;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



public class DALSearchByFlavour {

	public static List<MODELFlavourDetails> getStoreBranchesByCity(SQLiteDatabase db,String cityID) {
		List<List<MODELFlavourDetails>> list = new ArrayList<List<MODELFlavourDetails>>();
		Cursor cs3 = db.query("Store_Branches",new String[]{"branch_id"}, "city_id_fk='"+ cityID + "'", null, null, null, null);

		if (cs3.moveToFirst()) {

			do {
				list.add(getFlavourIDByBranchID(db, cs3.getString(0)));

			} while (cs3.moveToNext());
			
			List<MODELFlavourDetails> details=new ArrayList<MODELFlavourDetails>();
			
			for(int i=0;i<list.size();i++)
			{
				for(int j=0;j<list.get(i).size();j++)
				{
					details.add(list.get(i).get(j));
					Log.w("Flavour Name: ",list.get(i).get(j).get_flavourName());
				}
			}
			
			
			for (int i = 0; i < details.size(); i++) {
				for (int j = i+1; j < details.size(); j++) {
					
					if(details.get(i).get_flavourId().equals(details.get(j).get_flavourId())){
						details.remove(j);
					}
					
				}
				
			}
			 	
			return details;			
			
		} else {

			return null;
		}

	}

	public static List<MODELFlavourDetails> getFlavourIDByBranchID(SQLiteDatabase db,String branchID) {
		List<MODELFlavourDetails> list = new ArrayList<MODELFlavourDetails>();

		Cursor cs2 = db.query("Product_Table",new String[]{"distinct flavour_id_fk"}, "store_branch_id_fk='"+ branchID + "'", null, null, null, null);

		if (cs2.moveToFirst()) {

			do {



				list.add(getFlavourByFlavourID(db, cs2.getString(0)));

			} while (cs2.moveToNext());
			return list;
		} else {

			return null;
		}




	}
	public static MODELFlavourDetails getFlavourByFlavourID(SQLiteDatabase db,String flavourID){
		MODELFlavourDetails modelFlavourDetails = new MODELFlavourDetails();

		Cursor cs1 = db.query("Flavour_Category",null, "flavour_id='"+ flavourID + "'", null, null, null, null);


		if (cs1.moveToFirst()) {

			modelFlavourDetails.set_flavourId(cs1.getString(0));
			modelFlavourDetails.set_flavourName(cs1.getString(1));
			return modelFlavourDetails;

		} else {

			return null;
		}

	}

}
