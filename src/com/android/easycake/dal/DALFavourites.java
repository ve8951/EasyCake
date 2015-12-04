package com.android.easycake.dal;

import java.util.ArrayList;
import java.util.List;

import com.android.easycake.models.MODELProduct;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



public class DALFavourites {
	
	public static List<MODELProduct> getFavouriteList(SQLiteDatabase db,String userID) {
		
		List<MODELProduct> list=new ArrayList<MODELProduct>();
		
		 Cursor cs2 = db.query("Favourite_Table",null, "user_id_fk='"+ userID + "'", null, null, null, null);
		 if(cs2.moveToFirst())
		 {
			 	do {
					
					
			 		list.add(DALProductDetails.getProductData(db, cs2.getString(2)));
			 		
			 		
				} while (cs2.moveToNext());
		 
		 
		 
		 
		return list;
		 }else
		 {
			 return null;
		 }
	}
	
	
	
	
	public static Boolean insertFavProd( SQLiteDatabase db, String userId,MODELProduct modelProduct){
		
		
		try{
		ContentValues cvInsert=new ContentValues();
		cvInsert.put("user_id_fk", userId);
		cvInsert.put("product_id_fk", modelProduct.get_productId());
		db.insert("Favourite_Table", null, cvInsert);
		
		return true;
		}
		catch (Exception e) {
			return false;
			
		}
		
	}
	
	
public static Boolean deleteFavProd( SQLiteDatabase db, String userId,MODELProduct modelProduct){
		
		
		try{
		db.delete("Favourite_Table", "user_id_fk='"+userId+"' and product_id_fk='"+modelProduct.get_productId()+"'", null);
		
		return true;
		}
		catch (Exception e) {
			return false;
			
		}
		
	}
	


}
