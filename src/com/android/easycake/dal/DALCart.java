package com.android.easycake.dal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.android.easycake.models.MODELProduct;
import com.android.easycake.models.MODELShoppingCart;
import com.android.easycake.models.MODELShoppingCartProduct;
import com.android.easycake.models.MODELUserCart;
import com.android.easycake.utilities.UTILSSessionVariables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



public class DALCart {




	public static Boolean cartProductsList(SQLiteDatabase db,MODELShoppingCart modelShoppingCart) {
		try{

			ContentValues cvInsert=new ContentValues();
			cvInsert.put("user_id_fk",UTILSSessionVariables.modelUserSessionVar.get_userId());
			cvInsert.put("creation_date", getCurrentDate());
			cvInsert.put("creation_time", getCurrenttime());
			cvInsert.put("status_id_fk", "2");

			//------calculating the total price of products-------------
			Float totalCartprice=0.0f;
			for (int i = 0; i < modelShoppingCart.getModelCartProductsList().size(); i++) {

				totalCartprice+=Float.parseFloat(modelShoppingCart.getModelCartProductsList().get(i).getModelProduct().get_price());

			}
			//---------------------------------------

			cvInsert.put("cart_price",""+totalCartprice) ;
			db.insert("Shopping_Cart", null, cvInsert);
			Cursor cs=db.query("Shopping_Cart", null,null,null,null,null,null);
			if(cs.moveToLast()){
				addProdtoCart(db, cs.getString(0), modelShoppingCart);
				return true;
			}else{
				return false;
			}



		}catch (Exception e) {
			return false;
		}


	}



	public static Boolean addProdtoCart( SQLiteDatabase db,String cartId, MODELShoppingCart modelCartProducts){


		try{

			for (int i = 0; i < modelCartProducts.getModelCartProductsList().size(); i++) {

				ContentValues cvInsert=new ContentValues();
				cvInsert.put("cart_id_fk", cartId);
				cvInsert.put("product_id_fk", modelCartProducts.getModelCartProductsList().get(i).getModelProduct().get_productId());
				cvInsert.put("quantity", modelCartProducts.getModelCartProductsList().get(i).getQuantity());
				cvInsert.put("quantity",""+ Float.parseFloat(modelCartProducts.getModelCartProductsList().get(i).getQuantity())*Float.parseFloat(modelCartProducts.getModelCartProductsList().get(i).getModelProduct().get_price()));


				db.insert("Shopping_Cart_Product", null, cvInsert);

			}

			return true;

		}
		catch (Exception e) {
			return false;
		}

	}

	public static List<MODELUserCart> retriveuserCarts(SQLiteDatabase db,String userId){

		List<MODELUserCart> shoppingCartsList=new ArrayList<MODELUserCart>();
		Cursor cs=db.query("Shopping_Cart",null,"user_id_fk='"+userId+"'",null,null,null,null);

		if (cs.moveToFirst()) {

			do {
				MODELUserCart modelUserCart=new MODELUserCart();
				modelUserCart.set_shoppingCartId(cs.getString(0));
				modelUserCart.set_userId(cs.getString(1));
				modelUserCart.set_cartprice(cs.getString(5));
				modelUserCart.set_dateOfCreation(cs.getString(2));
				modelUserCart.set_timeOfCreation(cs.getString(3));
				modelUserCart.set_status(cs.getString(4));

				shoppingCartsList.add(modelUserCart);
			} while (cs.moveToNext());

		}
		return shoppingCartsList;
	}




	public static Boolean deleteProductDetails(SQLiteDatabase db,String userId, MODELProduct modelProducts)
	{
		try{
			db.delete("Shopping_Cart", "user_id_fk='"+userId+"'and product_id_fk='"+modelProducts.get_productId()+"'", null);
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	public static List<MODELShoppingCartProduct> retriveOrderHistory(SQLiteDatabase db, String cartId)
	{
		try{


			List<MODELShoppingCartProduct> list=new ArrayList<MODELShoppingCartProduct>();

			Cursor cs=db.query("Shopping_Cart_Product", null, "cart_id_fk='"+cartId+"'", null, null, null, null);
			if (cs.moveToFirst()) {
				do {
					MODELShoppingCartProduct modelShoppingCartProduct=new MODELShoppingCartProduct(getProductByProdId(db, cs.getString(2)), cs.getString(3));
					list.add(modelShoppingCartProduct);
				} while (cs.moveToNext());

				return list;

			} else {
				return null;

			}

		}catch (Exception e) {
			return null;
		}



	}


	public static MODELProduct getProductByProdId(SQLiteDatabase db, String productId)
	{
		MODELProduct modelProduct=new MODELProduct();
		Cursor cs1= db.query("Product_Table",null, "product_id='"+productId+"'", null, null, null, null);
		if(cs1.moveToFirst())
		{

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




	public static String getCurrentDate()
	{
		try{
			Calendar c = Calendar.getInstance();

			String currentDate = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH)+ "-" + c.get(Calendar.DAY_OF_MONTH);
			return currentDate;
		}
		catch (Exception e) {
			return null;
		}


	}

	public static String getCurrenttime()
	{
		try{

			Date dt = new Date();
			int hours = dt.getHours();
			int minutes = dt.getMinutes();
			int seconds = dt.getSeconds();
			String curTime = hours + ":"+minutes + ":"+ seconds;
			return curTime;

		}catch (Exception e) {
			return null;
		}

	}



}