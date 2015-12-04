package com.android.easycake.dal;

import com.android.easycake.models.MODELStore;
import com.android.easycake.models.MODELStoreBranch;
import com.android.easycake.models.MODELStoreInfoforProduct;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



public class DALStoreDetailsByProductId {



	public static MODELStoreInfoforProduct getCategoriesByproductId(SQLiteDatabase db, String productId){


//		MODELStoreInfoforProduct storeInfoforProduct=new MODELStoreInfoforProduct();
//		MODELStore modelStore=new MODELStore();
//		MODELStoreBranch modelStoreBranch=new MODELStoreBranch();
		Cursor cs2 = db.query("Product_Table", null, "product_id='"+ productId + "'", null, null, null, null);
		if (cs2.moveToFirst()) {
			
			return getStoreByBranchId(db, cs2.getString(1));			

		}
		else
		{
			return null;
		}

	}



	public static MODELStoreInfoforProduct getStoreByBranchId(SQLiteDatabase db, String branchId){


		MODELStoreBranch modelStoreBranch = new MODELStoreBranch();
		Cursor cs3 = db.query("Store_Branches", null, "branch_id='"+ branchId + "'", null, null, null, null);
		if (cs3.moveToFirst()) {
			
			modelStoreBranch.set_storeBranchID(cs3.getString(0));
			modelStoreBranch.set_parentStoreId(cs3.getString(1));
			modelStoreBranch.set_email(cs3.getString(2));
			modelStoreBranch.set_address(cs3.getString(3));
			modelStoreBranch.set_cityId(cs3.getString(4));
			modelStoreBranch.set_areaID(cs3.getString(5));
			modelStoreBranch.set_pincode(cs3.getString(6));
			modelStoreBranch.set_latitude(cs3.getString(7));
			modelStoreBranch.set_longitude(cs3.getString(8));
			modelStoreBranch.set_head(cs3.getString(9));			
			

			MODELStore modelStore=getStoreById(db,cs3.getString(1));
			
			MODELStoreInfoforProduct modelStoreInfoforProduct=new MODELStoreInfoforProduct();
			modelStoreInfoforProduct.setModelStore(modelStore);
			modelStoreInfoforProduct.setModelStoreBranch(modelStoreBranch);

			return modelStoreInfoforProduct;
		} else {

			return null;
		}

	}

	public static MODELStore getStoreById(SQLiteDatabase db, String storeId) {
		MODELStore modelStore = new MODELStore();

		Cursor cs = db.query("Store_Table", null, "store_id='" + storeId + "'",null, null, null, null);

		if (cs.moveToFirst()) {


			modelStore.set_pdf(cs.getString(0));
			modelStore.set_storeEmail(cs.getString(3));
			modelStore.set_storeID(cs.getString(1));
			modelStore.set_storeName(cs.getString(2));
			modelStore.set_logoStore(cs.getString(4));

			return modelStore;

		} else {
			return null;
		}



	}




}