package com.android.easycake.presentors;

import static com.android.easycake.presentors.SuperListActivity.updateHeaderCartDetails;

import java.util.ArrayList;
import java.util.List;
import com.android.easycake.R;
import com.android.easycake.dal.DALCart;
import com.android.easycake.dal.DALStoreDetailsByProductId;
import com.android.easycake.models.MODELCartProduct;
import com.android.easycake.models.MODELProduct;
import com.android.easycake.models.MODELStoreInfoforProduct;
import com.android.easycake.utilities.UTILSSessionVariables;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ACTShoppingCart extends SuperListActivity{
	List<MODELCartProduct> cartProducts=new ArrayList<MODELCartProduct>();

	Button _conformBT;
	SQLiteDatabase db;
	Button _headerCartDetailsBT;
	TextView welcomeUser;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cartlist);
		_headerCartDetailsBT=(Button)findViewById(R.id.shopping);
		welcomeUser=(TextView)findViewById(R.id.welcomeuser);

		super.updateHeaderCartDetails(_headerCartDetailsBT,welcomeUser);


		db=openOrCreateDatabase("EasyCake", MODE_PRIVATE, null);
		_conformBT=(Button)findViewById(R.id.update);
		getAndSetData();	

		_conformBT.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				if(UTILSSessionVariables.modelShoppingCart==null)
				{
					Toast.makeText(getBaseContext(), "No Products in Cart", Toast.LENGTH_SHORT).show();
				}else if (UTILSSessionVariables.modelShoppingCart.getModelCartProductsList().size()==0) {
					Toast.makeText(getBaseContext(), "No Products in Cart", Toast.LENGTH_SHORT).show();
				}else
				{
					if(DALCart.cartProductsList(db, UTILSSessionVariables.modelShoppingCart))
					{
						Toast.makeText(getBaseContext(), "Order Placed Sucessfully and your cart is emptied", Toast.LENGTH_SHORT).show();
						UTILSSessionVariables.modelShoppingCart=null;
						getAndSetData();
						ACTShoppingCart.super.updateHeaderCartDetails(_headerCartDetailsBT,welcomeUser);


					}
					else
					{
						Toast.makeText(getBaseContext(), "ERROR Placing Order", Toast.LENGTH_SHORT).show();
					}
				}


			}
		});

	}



	private void getAndSetData() {
		if(UTILSSessionVariables.modelShoppingCart==null){
			cartProducts.clear();
			setListAdapter(new MyFlavourListAdapter(this));	
			Toast.makeText(getBaseContext(), "Empty Shopping cart ", Toast.LENGTH_SHORT).show();

		}else if(UTILSSessionVariables.modelShoppingCart.getModelCartProductsList().size()==0){
			cartProducts.clear();
			setListAdapter(new MyFlavourListAdapter(this));	
			Toast.makeText(getBaseContext(), "Empty Shopping cart ", Toast.LENGTH_SHORT).show();
		}else{
			cartProducts=UTILSSessionVariables.modelShoppingCart.getModelCartProductsList();
			setListAdapter(new MyFlavourListAdapter(this));			
		}
	}



	public class MyFlavourListAdapter  extends ArrayAdapter{
		Activity context;

		public MyFlavourListAdapter(Activity context) {
			super(context,R.layout.rowlistcartprod,cartProducts);
			this.context=context;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflator=context.getLayoutInflater();
			View row=inflator.inflate(R.layout.rowlistcartprod, null);
			MODELStoreInfoforProduct modelinfostore=DALStoreDetailsByProductId.getCategoriesByproductId(db,cartProducts.get(position).getModelProduct().get_productId());
			TextView prodName=(TextView)row.findViewById(R.id.cartproductname);
			TextView ingridient=(TextView)row.findViewById(R.id.cartingridientTV);
			TextView pricePerQuant=(TextView)row.findViewById(R.id.cartweightTV);
			TextView storeName=(TextView)row.findViewById(R.id.cartstorenameinprodlistTV);
			
			TextView quantity=(TextView)row.findViewById(R.id.cartorderedQuantity);
			//TextView branchName=(TextView)row.findViewById(R.id.storeaddinprodlistTV);
			final Button editBTN=(Button)row.findViewById(R.id.cartaddToCartBT);
			editBTN.setId(position);
			final Button deleteProdBT=(Button)row.findViewById(R.id.cartaddToFavBT);
			deleteProdBT.setId(position+cartProducts.size());


			String storeAddress=modelinfostore.getModelStoreBranch().get_address();
			String storenameforTV=modelinfostore.getModelStore().get_storeName();
			storeName.setText(storenameforTV+" , "+storeAddress);
			quantity.setVisibility(View.VISIBLE);
			quantity.setText(UTILSSessionVariables.modelShoppingCart.getModelCartProductsList().get(position).getQuantity()	);

			prodName.setText(cartProducts.get(position).getModelProduct().get_productName());
			ingridient.setText(cartProducts.get(position).getModelProduct().get_ingredient());
			pricePerQuant.setText(cartProducts.get(position).getModelProduct().get_price()+" / "+cartProducts.get(position).getModelProduct().get_productWeight());

			editBTN.setOnClickListener(new View.OnClickListener() {


				public void onClick(View v) {


					//Toast.makeText(getBaseContext(),""+click.getId(), Toast.LENGTH_SHORT).show();
					final MODELProduct modelProducts=cartProducts.get(editBTN.getId()).getModelProduct();
					final String prodActualPrice=modelProducts.get_price().toString();

					String weight=modelProducts.get_productWeight().toString();

					LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);					
					View layout = inflater.inflate(R.layout.orderproddialog,(ViewGroup) findViewById(R.id.relativeLayoutfordialog));

					//  getting handles for dialog box components
					TextView productNameTV=(TextView)layout.findViewById(R.id.TVproductName);
					TextView prodInfoTV=(TextView)layout.findViewById(R.id.TVdetails);
					TextView pricePerQuantTV=(TextView)layout.findViewById(R.id.TVCostPerKilo);					
					final EditText userGivenQuantityET=(EditText)layout.findViewById(R.id.EDUserQuantity);
					final TextView totalPriceTV=(TextView)layout.findViewById(R.id.TVTotalPrice);
					Button quantDecrementBT=(Button)layout.findViewById(R.id.BTDecrease);
					Button quantIncrementBT=(Button)layout.findViewById(R.id.BTincrease);
					

					//setting product data into the dialog box components
					productNameTV.setText(modelProducts.get_productName());
					prodInfoTV.setText(modelProducts.get_ingredient());
					pricePerQuantTV.setText(prodActualPrice+" / "+weight);
					Log.w("userGivenQuantityET", ""+userGivenQuantityET);
					userGivenQuantityET.setText("1");

					Float calcPrice=Float.parseFloat(prodActualPrice)* Float.parseFloat(userGivenQuantityET.getText().toString());
					totalPriceTV.setText("Rs."+calcPrice+"/-");


					quantDecrementBT.setOnClickListener(new View.OnClickListener() {

						public void onClick(View v) {

							Integer currentQuant=Integer.parseInt(userGivenQuantityET.getText().toString());
							if(currentQuant!=1){
								userGivenQuantityET.setText(new Integer(currentQuant-1).toString());
							}

						}
					});

					quantIncrementBT.setOnClickListener(new View.OnClickListener() {

						public void onClick(View v) {

							Integer currentQuant=Integer.parseInt(userGivenQuantityET.getText().toString());
							if(currentQuant!=99){
								userGivenQuantityET.setText(new Integer(currentQuant+1).toString());
							}

						}
					});

					userGivenQuantityET.addTextChangedListener(new TextWatcher() {



						public void onTextChanged(CharSequence s, int start, int before, int count) {

							if(userGivenQuantityET.getText().toString().length()==0){								
								totalPriceTV.setText("Rs.0.00");
							}else{
								Integer calcPrice=Integer.parseInt(prodActualPrice)*Integer.parseInt(userGivenQuantityET.getText().toString());
								totalPriceTV.setText("Rs."+calcPrice+"/-");
							}


						}

						public void beforeTextChanged(CharSequence s, int start, int count,int after) {}

						public void afterTextChanged(Editable s) {}
					});



					AlertDialog.Builder alertbox = new AlertDialog.Builder(ACTShoppingCart.this);

					alertbox.setView(layout);

					alertbox.setTitle("Please Select Quantity..!");

					alertbox.setPositiveButton("UPDATE>>", new OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							
							UTILSSessionVariables.modelShoppingCart.getModelCartProductsList().get(editBTN.getId()).setQuantity(userGivenQuantityET.getText().toString());
							cartProducts=UTILSSessionVariables.modelShoppingCart.getModelCartProductsList();
							ACTShoppingCart.super.updateHeaderCartDetails(_headerCartDetailsBT, welcomeUser);
							setListAdapter(new MyFlavourListAdapter(ACTShoppingCart.this));
						}						

					});

					alertbox.setNeutralButton("CANCEL>>",  new OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {}});




					alertbox.show();


				}
			});

			deleteProdBT.setOnClickListener(new View.OnClickListener() {


				public void onClick(View v) {
					UTILSSessionVariables.modelShoppingCart.getModelCartProductsList().remove(deleteProdBT.getId()-cartProducts.size());
					cartProducts=UTILSSessionVariables.modelShoppingCart.getModelCartProductsList();
					setListAdapter(new MyFlavourListAdapter(ACTShoppingCart.this));
					updateHeaderCartDetails();


				}
			});

			return(row);
		}
	}

	@Override
	protected void onPause() {
		db.close();
		super.onPause();
	}

	public void easyGo(View v){

		super.easyGo(v, this);

	}

	@Override
	protected void onResume() {
		db=openOrCreateDatabase("EasyCake", MODE_PRIVATE, null);
		super.updateHeaderCartDetails(_headerCartDetailsBT,welcomeUser);
		super.onResume();
	}

	private void updateHeaderCartDetails(){
		super.updateHeaderCartDetails(_headerCartDetailsBT,welcomeUser);
	}

}

