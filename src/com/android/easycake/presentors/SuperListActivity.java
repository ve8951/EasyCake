package com.android.easycake.presentors;

import java.util.List;

import com.android.easycake.R;
import com.android.easycake.models.MODELCartProduct;
import com.android.easycake.utilities.UTILSSessionVariables;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SuperListActivity extends ListActivity{

	public void easyGo(View v, Context context) {

		switch (v.getId()) {
		case R.id.shopping:
			startActivity(new Intent(context,ACTShoppingCart.class));
			break;
			
			
		case R.id.home:
			startActivity(new Intent(context,ACTHomePage.class));
			break;
		case R.id.update:
			//startActivity(new Intent(SuperActivity.this,.class));
			break;
		case R.id.search:
			//startActivity(new Intent(SuperActivity.this,.class));
			break;
		case R.id.logout:
			
			UTILSSessionVariables.modelUserSessionVar=null;
			UTILSSessionVariables.modelShoppingCart=null;
			((Activity) context).finish();
			
			startActivity(new Intent(context,ACTLogin.class));
			break;


		}}	

	public static void updateHeaderCartDetails(Button destination,TextView welcome)
	{	
		if(UTILSSessionVariables.modelShoppingCart==null)
		{
			destination.setText("0 items\nRS.0.00");
		}
		else if (UTILSSessionVariables.modelShoppingCart.getModelCartProductsList().size()==0) {
			destination.setText("0 items\nRS.0.00");	
		}
		else{

			List<MODELCartProduct> modelShoppingCarts=UTILSSessionVariables.modelShoppingCart.getModelCartProductsList();

			Integer cartSize=modelShoppingCarts.size();
			Float totalPrice=0.0f;

			for(int i=0;i<modelShoppingCarts.size();i++)
			{
				totalPrice+=Float.parseFloat(modelShoppingCarts.get(i).getModelProduct().get_price().toString())*Float.parseFloat(modelShoppingCarts.get(i).getQuantity());
			}

			destination.setText(""+cartSize+" items\nRS."+totalPrice);
		}
		
		welcome.setText("Welcome "+UTILSSessionVariables.modelUserSessionVar.get_firstName().toUpperCase());
	}
	
	

}
