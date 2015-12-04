package com.android.easycake.dal;

import java.util.ArrayList;
import java.util.List;

import com.android.easycake.models.MODELCity;
import com.android.easycake.models.MODELStore;
import com.android.easycake.models.MODELStoreBranch;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DALStoreSearch {

	public static List<MODELCity> getCities(SQLiteDatabase db) {
		List<MODELCity> list = new ArrayList<MODELCity>();

		Cursor cs1 = db.query("Cities_Table", null, null, null, null, null,
				null);

		if (cs1.moveToFirst()) {

			do {
				MODELCity modelCities = new MODELCity();
				modelCities.set_storeCityID(cs1.getString(0));
				modelCities.set_storeCityName(cs1.getString(1));

				list.add(modelCities);

			} while (cs1.moveToNext());
			return list;
		} else {

			return null;
		}

	}

	public static List<MODELStore> getStoresByCity(SQLiteDatabase db,
			String cityID) {
		List<MODELStore> list = new ArrayList<MODELStore>();

		// Cursor cs2 =
		// db.execSQL("Select unique parent_store_id_fk from Store_Branches where city_id_fk='cityID' order by store_id_fk asc");
		Cursor cs2 = db.query("Store_Branches",
												  new String[]{
												  "distinct parent_store_id_fk"}
												 , "city_id_fk='"
				+ cityID + "'", null, null, null, "parent_store_id_fk asc");

		if (cs2.moveToFirst()) {

			do {
				list.add(getStoreById(db, cs2.getString(0)));

			} while (cs2.moveToNext());
			return list;
		} else {

			return null;
		}

	}

	public static MODELStore getStoreById(SQLiteDatabase db, String storeId) {
		MODELStore modelStores = new MODELStore();

		Cursor cs = db.query("Store_Table", null, "store_id='" + storeId + "'",
				null, null, null, null);

		if (cs.moveToFirst()) {

			do {
				modelStores.set_pdf(cs.getString(0));
				modelStores.set_storeEmail(cs.getString(3));
				modelStores.set_storeID(cs.getString(1));
				modelStores.set_storeName(cs.getString(2));
				modelStores.set_logoStore(cs.getString(4));

			} while (cs.moveToNext());

		} else {
			return null;
		}

		return modelStores;
	}

	public List<MODELStoreBranch> readBranchesByStoreId(
			SQLiteDatabase db, String storeId) {
		List<MODELStoreBranch> list = new ArrayList<MODELStoreBranch>();
		Cursor cs3 = db.query("Store_Branches", null, "parent_store_id_fk='"
				+ storeId + "'", null, null, null, null);

		if (cs3.moveToFirst()) {

			do {
				MODELStoreBranch modelstoreBranches = new MODELStoreBranch();
				modelstoreBranches.set_address(cs3.getString(3));
				modelstoreBranches.set_areaID(cs3.getString(5));
				modelstoreBranches.set_cityId(cs3.getString(4));
				modelstoreBranches.set_email(cs3.getString(2));
				modelstoreBranches.set_head(cs3.getString(9));
				modelstoreBranches.set_latitude(cs3.getString(7));
				modelstoreBranches.set_longitude(cs3.getString(8));
				modelstoreBranches.set_parentStoreId(cs3.getString(1));
				modelstoreBranches.set_pincode(cs3.getString(6));
				modelstoreBranches.set_storeBranchID(cs3.getString(0));

				list.add(modelstoreBranches);

			} while (cs3.moveToNext());
			return list;
		} else {

			return null;
		}
	}

	public static List<MODELStore> getAllStores(SQLiteDatabase db) {
		List<MODELStore> list = new ArrayList<MODELStore>();

		Cursor cs4 = db
				.query("Store_Table", null, null, null, null, null, null);

		if (cs4.moveToFirst()) {

			do {
				MODELStore modelStores = new MODELStore();
				modelStores.set_storeID(cs4.getString(1));
				modelStores.set_storeName(cs4.getString(2));

				list.add(modelStores);

			} while (cs4.moveToNext());
			return list;
		} else {

			return null;
		}
	}

}
