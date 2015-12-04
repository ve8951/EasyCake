package com.android.easycake.dal;

import java.util.ArrayList;
import java.util.List;

import com.android.easycake.models.MODELCategoryFlavour;
import com.android.easycake.models.MODELProduct;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



public class DALProductDetails {
	
	
	public static List<MODELCategoryFlavour> getflavour(SQLiteDatabase db, String storeId)
	{
		
		List<MODELCategoryFlavour> list=new ArrayList<MODELCategoryFlavour>();
		
		Cursor cs2=db.query("Product_Table",new String[]{"distinct flavour_id_fk"}, "store_branch_id_fk='"+storeId+"'", null, null , null, null);
		
		if(cs2.moveToFirst())
		{
			do
			{
							
				
				list.add(getFlavourData(db,cs2.getString(0)));
				
			}while(cs2.moveToNext());
			return list;
	
		}
		else
		{
		return null;
		}
	}
	
	
	public static MODELCategoryFlavour getFlavourData(SQLiteDatabase db,String flavourId)
	{		
		
		Cursor cs2=db.query("Flavour_Category",null, "flavour_id='"+flavourId+"'", null, null , null, null);
		
		if(cs2.moveToFirst())
		{
				MODELCategoryFlavour modelCategoryFlovour=new MODELCategoryFlavour();
				modelCategoryFlovour.set_categoryID(cs2.getString(0));
				modelCategoryFlovour.set_categoryName(cs2.getString(1));
				
			
			
			return modelCategoryFlovour;
	
		}
		else
		{
		return null;
		}
		
	}
	
	
	public static List<MODELProduct> getProductsByFlavId(SQLiteDatabase db, String flavourId)
	{
		List<MODELProduct> list=new ArrayList<MODELProduct>();
	
		
		Cursor cs1= db.query("Product_Table", new String[]{"distinct product_id"}, "flavour_id_fk='"+flavourId+"'", null, null, null, null);
		if(cs1.moveToFirst())
		{

			do
			{
				
				
				
				list.add(getProductData(db, cs1.getString(0)));



			} while(cs1.moveToNext());
			return list;
		}
		else
		{

			return null;
		}		
	}
	
	public static MODELProduct getProductData(SQLiteDatabase db,String productId)
	{		
		
		Cursor cs1=db.query("Product_Table",null, "product_id='"+productId+"'", null, null , null, null);
		
		if(cs1.moveToFirst())
		{
				MODELProduct modelProduct=new MODELProduct();
				modelProduct.set_categoryId(cs1.getString(2));
				modelProduct.set_ingredient(cs1.getString(5));
				modelProduct.set_price(cs1.getString(6));
				modelProduct.set_productId(cs1.getString(0));
				modelProduct.set_productName(cs1.getString(3));
				modelProduct.set_productWeight(cs1.getString(4));
				modelProduct.set_storeId(cs1.getString(1));
				
			
			
			return modelProduct;
	
		}
		else
		{
		return null;
		}
		
	}
	
	public static List<MODELProduct> getproductsByStoreId(SQLiteDatabase db, String storeId)
	{
		List<MODELProduct> list=new ArrayList<MODELProduct>();
	
		
		Cursor cs1= db.query("Product_Table",null, "store_id_fk='"+storeId+"'", null, null, null, null);
		if(cs1.moveToFirst())
		{

			do
			{
				MODELProduct modelProduct=new  MODELProduct();
				modelProduct.set_categoryId(cs1.getString(2));
				modelProduct.set_ingredient(cs1.getString(5));
				modelProduct.set_price(cs1.getString(6));
				modelProduct.set_productId(cs1.getString(0));
				modelProduct.set_productName(cs1.getString(3));
				modelProduct.set_productWeight(cs1.getString(4));
				modelProduct.set_storeId(cs1.getString(1));
				
				list.add(modelProduct);



			} while(cs1.moveToNext());
			return list;
		}
		else
		{

			return null;
		}		
	}


	
}
