package com.android.easycake.dal;

import java.util.ArrayList;
import java.util.List;
import com.android.easycake.models.MODELArea;
import com.android.easycake.models.MODELCity;
import com.android.easycake.models.MODELUser;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DALRegistration  {

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

	public static List<MODELArea> getAreas(SQLiteDatabase db,String cityId)
	{

		List<MODELArea> list=new ArrayList<MODELArea>();

		Cursor cs2 = db.query("Area_Table", null, "city_id_fk='"+cityId+"'", null, null, null, null);

		if(cs2.moveToFirst())
		{

			do
			{
				MODELArea modelArea=new  MODELArea();
				modelArea.set_areaID(cs2.getString(0));
				modelArea.set_areaName(cs2.getString(1));

				list.add(modelArea);



			} while(cs2.moveToNext());
			return list;
		}
		else
		{

			return null;
		}

	}

	public static Boolean userNameAvailCheck(SQLiteDatabase db,String userName)
	{
		Cursor cs3 = db.query("User_Table",null, "user_name='"+userName+"'", null, null, null, null);



		if(cs3.getCount()==0){
			return true;
		}


		return false;
	}

	public static	boolean userRegistration( SQLiteDatabase db,MODELUser modelUser)
	{



		try{

			ContentValues cvInsert=new ContentValues();

			cvInsert.put("first_name", modelUser.get_firstName());
			cvInsert.put("sur_name", modelUser.get_surName());
			cvInsert.put("user_email", modelUser.get_email());

			cvInsert.put("user_contact_no", modelUser.get_surName());

			cvInsert.put("user_address", modelUser.get_address());
			cvInsert.put("user_name", modelUser.get_userName());
			cvInsert.put("password", modelUser.get_password());

			db.insert("User_Table", null, cvInsert);
			return true;
		}catch (Exception e) {
			return false;
		}

	}






}
