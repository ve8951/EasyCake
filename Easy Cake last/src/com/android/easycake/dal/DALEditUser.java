package com.android.easycake.dal;

import java.util.ArrayList;
import java.util.List;

import com.android.easycake.models.MODELUser;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DALEditUser {
	
	public static List<MODELUser> getCities(SQLiteDatabase db,MODELUser modeluser)
	{
		List<MODELUser> list=new ArrayList<MODELUser>();


		Cursor cs1 = db.query("User_Table",null, "user_id='"+modeluser.get_userId()+"'", null, null, null, null);

		if(cs1.moveToFirst())
		{

			do
			{
				MODELUser userdetails=new  MODELUser();
				userdetails.set_firstName(cs1.getString(1));
				userdetails.set_surName(cs1.getString(2));
				userdetails.set_address(cs1.getString(5));
				userdetails.set_userName(cs1.getString(7));
				userdetails.set_email(cs1.getString(3));
				userdetails.set_contact(cs1.getString(4));

				list.add(userdetails);



			} while(cs1.moveToNext());
			return list;
		}
		else
		{

			return null;
		}

	}
	

}
