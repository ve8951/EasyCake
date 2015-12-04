package com.android.easycake.dal;

import java.util.ArrayList;
import java.util.List;

import com.android.easycake.models.MODELCategoryFlavour;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DALFlavours {
	
	public static List<MODELCategoryFlavour> flavourRetrieve(SQLiteDatabase db)
	{
		List<MODELCategoryFlavour> list=new ArrayList<MODELCategoryFlavour>();
		Cursor cs=db.query("Flavour_Category",null,null,null,null,null, null);
		if (cs.moveToFirst()) {
			do
			{
				MODELCategoryFlavour modelCategoryFlavour=new MODELCategoryFlavour();
				modelCategoryFlavour.set_categoryID(cs.getString(0));
				modelCategoryFlavour.set_categoryName(cs.getString(1));
				list.add(modelCategoryFlavour);
			
			}while(cs.moveToNext());
			return list;
		} else 
		return null;
		
	}
	

}
