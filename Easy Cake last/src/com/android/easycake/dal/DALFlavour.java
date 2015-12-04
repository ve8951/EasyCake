package com.android.easycake.dal;

import java.util.ArrayList;
import java.util.List;

import com.android.easycake.models.MODELCity;
import com.android.easycake.models.MODELFlavourDetails;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



public class DALFlavour {
	
	
	public static List<MODELCity> getCities(SQLiteDatabase db)
	{
		List<MODELCity> list=new ArrayList<MODELCity>();


		Cursor cs1 = db.query("Cities_Table", null, null, null, null, null, null);

		if(cs1.moveToFirst())
		{

			do
			{
				MODELCity modelCities=new  MODELCity();
				modelCities.set_storeCityID(cs1.getString(0));
				modelCities.set_storeCityName(cs1.getString(1));

				list.add(modelCities);



			} while(cs1.moveToNext());
			return list;
		}
		else
		{

			return null;
		}

	}
	
	
	public static List<MODELFlavourDetails> getflavournames(SQLiteDatabase db)
	{
		
		List<MODELFlavourDetails> list=new ArrayList<MODELFlavourDetails>();
		
		Cursor cs2=db.query("Flavour_Category",null, null, null, null , null, null);
		
		if(cs2.moveToFirst())
		{
			do
			{
				MODELFlavourDetails modelFlovour=new MODELFlavourDetails();
				modelFlovour.set_flavourId(cs2.getString(0));
				modelFlovour.set_flavourName(cs2.getString(1));
				
				list.add(modelFlovour);
				
			}while(cs2.moveToNext());
			return list;
	
		}
		else
		{
		return null;
		}
	}

}
