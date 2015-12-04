package com.android.easycake.dal;
import com.android.easycake.models.MODELUser;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DALProfileUpdate {

	public  MODELUser update( SQLiteDatabase db,MODELUser modelUser)
	{
		//Cursor cs1=db.query("User_Table", null, "user_name='"+modelUser.get_userId()+"'", null, null, null, null);

		MODELUser updatedUserData=new MODELUser();
		ContentValues cvUpdate=new ContentValues();
		cvUpdate.put("first_name", modelUser.get_firstName());
		cvUpdate.put("sur_name", modelUser.get_surName());
		cvUpdate.put("user_email", modelUser.get_email());
		cvUpdate.put("user_contact_no", modelUser.get_contact());
		cvUpdate.put("user_address", modelUser.get_address());
		cvUpdate.put("user_name", modelUser.get_userName());
		cvUpdate.put("password", modelUser.get_password());


		try{
			db.update("User_Table", cvUpdate, "user_id='"+modelUser.get_userId()+"'", null);
			Cursor cs1=db.query("User_Table", null, "user_id='"+modelUser.get_userId()+"'", null, null, null, null);
			if(cs1.moveToFirst())
			{
				
				updatedUserData.set_userId(cs1.getString(0));
				updatedUserData.set_firstName(cs1.getString(1));
				updatedUserData.set_surName(cs1.getString(2));
				updatedUserData.set_email(cs1.getString(3));
				updatedUserData.set_contact(cs1.getString(4));
				updatedUserData.set_address(cs1.getString(5));
				updatedUserData.set_userName(cs1.getString(7));
				updatedUserData.set_password(cs1.getString(8));


				return updatedUserData;
			}
			else
			{
				return null;
			}	

		}
		catch (Exception e) {
			
			return null;
		}


		

	}
	/*public static Boolean userNameAvailCheck(SQLiteDatabase db,String userName)
	{
		Cursor cs3 = db.query("User_Table",null, "user_name='"+userName+"'", null, null, null, null);

		if(cs3.moveToFirst())
		{

			if(cs3.getCount()==0){
				return true;
			}else return false;

		}
		else
		{

			return null;
		}	
	}*/

}
