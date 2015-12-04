package com.android.easycake.presentors;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.easycake.R;
import com.android.easycake.dal.DALFavourites;
import com.android.easycake.dal.DALStoreDetailsByProductId;
import com.android.easycake.models.MODELCartProduct;
import com.android.easycake.models.MODELFavourites;
import com.android.easycake.models.MODELProduct;
import com.android.easycake.models.MODELShoppingCart;
import com.android.easycake.models.MODELStoreInfoforProduct;
import com.android.easycake.utilities.UTILSSessionVariables;

public class ACTFavourites extends SuperListActivity {

	String[] _prodName,_prodInfo,_pricePerKg,_favCity,_favBranch;

	List<MODELProduct> _favProductsList=new ArrayList<MODELProduct>();
	List<MODELFavourites> _favourite=new ArrayList<MODELFavourites>();
	SQLiteDatabase db;
	Button _headerCartDetailsBT;
	TextView welcomeUser;


	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favouritelist);
		
		_headerCartDetailsBT=(Button)findViewById(R.id.shopping);
		welcomeUser=(TextView)findViewById(R.id.welcomeuser);
		
		super.updateHeaderCartDetails(_headerCartDetailsBT,welcomeUser);

		db=openOrCreateDatabase("EasyCake", MODE_PRIVATE, null);
		_favProductsList=DALFavourites.getFavouriteList(db, UTILSSessionVariables.modelUserSessionVar.get_userId());

		if(_favProductsList==null){

			Toast.makeText(getBaseContext(), "NO item in Favourite", Toast.LENGTH_SHORT).show();
		}
		else {

			setListAdapter(new MyListAdapter(this));
		}



	}



	public class MyListAdapter  extends ArrayAdapter{
		Activity context;

		public MyListAdapter(Activity context) {
			super(context,R.layout.rowlistfavouriteprod,_favProductsList);
			this.context=context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflator=context.getLayoutInflater();
			View row=inflator.inflate(R.layout.rowlistfavouriteprod, null);
			//get handles
			TextView prodName=(TextView)row.findViewById(R.id.TVFavproductName);
			TextView prodDetail=(TextView)row.findViewById(R.id.TVFavdetails);
			TextView pricePerProd=(TextView)row.findViewById(R.id.TVFavCostPerKilo);
			TextView prodStoreAddress=(TextView)row.findViewById(R.id.TVProductFAVBranch);
			final Button orderNow=(Button)row.findViewById(R.id.BTAddFav);			
			orderNow.setId(position);


			final Button deleteFavourite=(Button)row.findViewById(R.id.deleteFavBT);			
			deleteFavourite.setId(_favProductsList.size()+position);


			//set data to the Each list row

			prodName.setText(_favProductsList.get(position).get_productName());
			prodDetail.setText(_favProductsList.get(position).get_ingredient());
			pricePerProd.setText(_favProductsList.get(position).get_price()+" / "+_favProductsList.get(position).get_productWeight());
			MODELStoreInfoforProduct modelStoreInfoforProduct=DALStoreDetailsByProductId.getCategoriesByproductId(db, _favProductsList.get(position).get_productId());

			prodStoreAddress.setText(modelStoreInfoforProduct.getModelStoreBranch().get_address()+" , "+modelStoreInfoforProduct.getModelStore().get_storeName());

			orderNow.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {

					final MODELProduct modelProducts=_favProductsList.get(orderNow.getId());

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
					userGivenQuantityET.setText("1");

					Float calcPrice=Float.parseFloat(prodActualPrice)*Integer.parseInt(userGivenQuantityET.getText().toString());
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



					AlertDialog.Builder alertbox = new AlertDialog.Builder(ACTFavourites.this);

					alertbox.setView(layout);

					alertbox.setTitle("Please Select Quantity..!");

					alertbox.setPositiveButton("ADD>>", new OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// code to add the product into shopping cart

							//----------------------------------------------


							// code to add the product into shopping cart
							MODELCartProduct modelCartProduct=new MODELCartProduct();
							modelCartProduct.setModelProduct(modelProducts);
							modelCartProduct.setQuantity(userGivenQuantityET.getText().toString());

							if(UTILSSessionVariables.modelShoppingCart==null){
								UTILSSessionVariables.modelShoppingCart=new MODELShoppingCart();

							}

							if(UTILSSessionVariables.modelShoppingCart.getModelCartProductsList()==null){
								List<MODELCartProduct> modelCartProductsList=new ArrayList<MODELCartProduct>();
								modelCartProductsList.add(modelCartProduct);

								UTILSSessionVariables.modelShoppingCart.setModelCartProductsList(modelCartProductsList);
								Toast.makeText(getBaseContext(),"Product Added to Cart", Toast.LENGTH_SHORT).show();
							}else{
								UTILSSessionVariables.modelShoppingCart.getModelCartProductsList().add(modelCartProduct);
								Toast.makeText(getBaseContext(),"Product Added to Cart", Toast.LENGTH_SHORT).show();
							}



						}
					});

					alertbox.setNeutralButton("CANCEL>>",  new OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {


						}
					});




					alertbox.show();



				}
			});


			deleteFavourite.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					if(DALFavourites.deleteFavProd(db, UTILSSessionVariables.modelUserSessionVar.get_userId(), _favProductsList.get(deleteFavourite.getId()-_favProductsList.size()))){
						Toast.makeText(getBaseContext(), "Product Successfully Deleted", Toast.LENGTH_SHORT).show();
						_favProductsList=DALFavourites.getFavouriteList(db, UTILSSessionVariables.modelUserSessionVar.get_userId());

						if(_favProductsList==null){

							Toast.makeText(getBaseContext(), "NO item in Favourite", Toast.LENGTH_SHORT).show();
						}
						else {

							setListAdapter(new MyListAdapter(ACTFavourites.this));
						}

					}else{
						Toast.makeText(getBaseContext(), "Deletion Failure", Toast.LENGTH_SHORT).show();
					}


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
	
	@Override
	protected void onResume() {
		db=openOrCreateDatabase("EasyCake", MODE_PRIVATE, null);
		super.updateHeaderCartDetails(_headerCartDetailsBT,welcomeUser);
		super.onResume();
	}
	
	public void easyGo(View v){

		super.easyGo(v, ACTFavourites.this);

	}	

}
