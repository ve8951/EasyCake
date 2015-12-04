package com.android.easycake.dal;

import com.android.easycake.models.MODELUser;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DALLogin {
	
	
	public static MODELUser getlogin(SQLiteDatabase db,MODELUser modelUser)
	{
	MODELUser userData=new MODELUser();
		
		
		Cursor cs1=db.query("User_Table", null, "user_name='"+modelUser.get_userName()+"' and password='"+modelUser.get_password()+"'", null, null, null, null);
		if (cs1.getCount()==0){
			return null;
		}else{
			
			cs1.moveToFirst();
			userData.set_userId(cs1.getString(0));
			userData.set_firstName(cs1.getString(1));
			userData.set_surName(cs1.getString(2));
			userData.set_email(cs1.getString(3));
			userData.set_contact(cs1.getString(4));
			userData.set_address(cs1.getString(5));
			userData.set_userName(cs1.getString(7));
			userData.set_password(cs1.getString(8));
			
		return userData;
		}
	}
	

}
