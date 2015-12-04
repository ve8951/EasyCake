package com.android.easycake.dal;

import com.android.easycake.models.MODELDeliveryStatus;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



public class DALOrderStatus {
	
	public static MODELDeliveryStatus getStatusById(SQLiteDatabase db,String statusId){
		MODELDeliveryStatus modelDeliveryStatus = new MODELDeliveryStatus();

		Cursor cs1 = db.query("Delivary_Status",null, "status_id='"+ statusId + "'", null, null, null, null);


		if (cs1.moveToFirst()) {

			modelDeliveryStatus.set_statusId(cs1.getString(0));
			modelDeliveryStatus.set_statusValue(cs1.getString(1));
			
			return modelDeliveryStatus;

		} else {

			return null;
		}

	}


}
