package com.android.easycake.presentors;

import static com.android.easycake.presentors.SuperListActivity.updateHeaderCartDetails;

import java.util.ArrayList;
import java.util.List;

import com.android.easycake.R;
import com.android.easycake.dal.DALFavourites;
import com.android.easycake.dal.DALProductDetails;
import com.android.easycake.dal.DALSearchByFlavour;
import com.android.easycake.dal.DALStoreDetailsByProductId;
import com.android.easycake.models.MODELCartProduct;
import com.android.easycake.models.MODELFlavourDetails;
import com.android.easycake.models.MODELProduct;
import com.android.easycake.models.MODELShoppingCart;
import com.android.easycake.models.MODELStore;
import com.android.easycake.models.MODELStoreBranch;
import com.android.easycake.models.MODELStoreInfoforProduct;
import com.android.easycake.utilities.UTILSSessionVariables;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ACTListProdByFlavour extends SuperListActivity{

	List<MODELFlavourDetails> flavourDetails =new ArrayList<MODELFlavourDetails>();
	List<MODELProduct> productdetails =new ArrayList<MODELProduct>();
	String[] productname;
	String[] flavourname;
	String[] prodweight;
	String cityid;
	String[] prodingrd;
	String[] prodid;
	String[] productlogo;
	SQLiteDatabase db;
	String selectedCityId;
	MODELFlavourDetails modelflavourdetails=new MODELFlavourDetails();
	Button _headerCartDetailsBT;

	MODELProduct modelproduct=new MODELProduct();
	MODELStore modelstores=new MODELStore();
	MODELStoreBranch modelstorebranchs=new MODELStoreBranch();

	TextView welcomeUser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listflavours);

		_headerCartDetailsBT=(Button)findViewById(R.id.shopping);
		welcomeUser=(TextView)findViewById(R.id.welcomeuser);

		super.updateHeaderCartDetails(_headerCartDetailsBT,welcomeUser);

		db=openOrCreateDatabase("EasyCake", MODE_PRIVATE, null);
		selectedCityId=getIntent().getExtras().getString("city_id");

		flavoursPopulate();


		//		flavourDetails=DALSearchByFlavour.getStoreBranchesByCity(db, selectedCityId);
		//		
		//		
		//		flavourname=new String[flavourDetails.size()];
		//		for (int i = 0; i < flavourDetails.size(); i++) {
		//			flavourname[i]=flavourDetails.get(i).get_flavourName();
		//			
		//		}
		//		setListAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1,flavourname));
		//		
		//		
		//	
		//		
		//		
		//	
		//	}
		//	
		//	@Override
		//	protected void onListItemClick(ListView l, View v, int position, long id) {
		//		super.onListItemClick(l, v, position, id);
		//		flavourDetails.get(position).get_flavourId();
		//		Intent i2=new Intent(ACTStoresByFlavour.this,ACTProductDisplayByFlavour.class);
		//		i2.putExtra("Flavour_id", flavourDetails.get(position).get_flavourId());
		//		startActivity(i2);
		//	}
		//}




	}

	public void flavoursPopulate() {
		flavourDetails=DALSearchByFlavour.getStoreBranchesByCity(db,selectedCityId);
		Spinner _flavourSP = (Spinner) findViewById(R.id.flavourlistSP);
		flavourname=new String[flavourDetails.size()];

		for(int i=0;i<flavourDetails.size();i++)
		{
			flavourname[i]=flavourDetails.get(i).get_flavourName();
		}

		_flavourSP.setAdapter(new ArrayAdapter(getBaseContext(),android.R.layout.simple_dropdown_item_1line, flavourname));

		_flavourSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,int pos, long arg3) {

				productspopulate(flavourDetails.get(pos).get_flavourId());

				//


			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});



	}
	public void productspopulate(String flavourID)
	{

		productdetails=DALProductDetails.getProductsByFlavId(db, flavourID);	

		//		productlogo=new String[productdetails.size()];
		//		for (int i = 0; i < productdetails.size(); i++) {
		//			
		//			productlogo[i]=productdetails.get(i).get_image();
		//			
		//		}


		setListAdapter(new MyFlavourListAdapter(ACTListProdByFlavour.this));

	}




	@SuppressWarnings("unchecked")
	public class MyFlavourListAdapter  extends ArrayAdapter{
		Activity context;

		public MyFlavourListAdapter(Activity context) {
			super(context,R.layout.rowforproductdetail,productdetails);
			this.context=context;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflator=context.getLayoutInflater();
			View row=inflator.inflate(R.layout.rowforproductdetail, null);

			MODELStoreInfoforProduct modelinfostore=DALStoreDetailsByProductId.getCategoriesByproductId(db,productdetails.get(position).get_productId());

			ImageView prodimg=(ImageView)findViewById(R.id.producticon);
			TextView prodname=(TextView)row.findViewById(R.id.productname);	

			TextView prodweight=(TextView)row.findViewById(R.id.weightTV);
			TextView prodingrg=(TextView)row.findViewById(R.id.ingridientTV);
			TextView storename=(TextView)row.findViewById(R.id.storenameinprodlistTV);
			//TextView storeaddress=(TextView)row.findViewById(R.id.storeaddinprodlistTV);
			final Button addToCartBTN=(Button)row.findViewById(R.id.addToCartBT);
			final Button favouriteBTN=(Button)row.findViewById(R.id.addToFavBT);
			addToCartBTN.setId(position);
			favouriteBTN.setId(productdetails.size()+position);

			//prodimg.setBackgroundResource(getResources().getIdentifier(productlogo[position], "drawable", getPackageName()));
			prodname.setText(productdetails.get(position).get_productName());
			prodingrg.setText(productdetails.get(position).get_ingredient());
			prodweight.setText(productdetails.get(position).get_price()+" / "+productdetails.get(position).get_productWeight());
			//storename.setText(modelinfostore.getModelStore().get_storeName());
			String storenameforTV=modelinfostore.getModelStore().get_storeName();
			String storeAddress=modelinfostore.getModelStoreBranch().get_address();
			storename.setText(storenameforTV+" , "+storeAddress);


			addToCartBTN.setOnClickListener(new View.OnClickListener() 
			{			
				public void onClick(View v) {

					//Toast.makeText(getBaseContext(),""+click.getId(), Toast.LENGTH_SHORT).show();
					final MODELProduct modelProducts=productdetails.get(addToCartBTN.getId());
					Log.w("button value", ""+addToCartBTN.getId());
					Log.w("productdetails", ""+productdetails);
					final String prodActualPrice=modelProducts.get_price().toString();
					Log.w("prodActualPrice", ""+prodActualPrice);
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



					AlertDialog.Builder alertbox = new AlertDialog.Builder(ACTListProdByFlavour.this);

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
								updateHeaderCartDetails();
								Toast.makeText(getBaseContext(),"Product Added to Cart", Toast.LENGTH_SHORT).show();
							}else{
								Boolean found=false;
								List<MODELCartProduct> sessionCartList=UTILSSessionVariables.modelShoppingCart.getModelCartProductsList();
								for (int i = 0; i < sessionCartList.size(); i++) {

									if(sessionCartList.get(i).getModelProduct().get_productId().equals(modelCartProduct.getModelProduct().get_productId())){
										found=true;
										Integer currentquant=Integer.parseInt(sessionCartList.get(i).getQuantity());
										UTILSSessionVariables.modelShoppingCart.getModelCartProductsList().get(i).setQuantity(new Integer(currentquant+1).toString());
										ACTListProdByFlavour.super.updateHeaderCartDetails(_headerCartDetailsBT, welcomeUser);
										Toast.makeText(getBaseContext(),"Product Added to Cart", Toast.LENGTH_SHORT).show();
									}

								}
								if(!found){
									UTILSSessionVariables.modelShoppingCart.getModelCartProductsList().add(modelCartProduct);
									updateHeaderCartDetails();
									Toast.makeText(getBaseContext(),"Product Added to Cart", Toast.LENGTH_SHORT).show();
								}
							}

						}


						//-----------------------------------

					});

					alertbox.setNeutralButton("CANCEL>>",  new OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {


						}
					});




					alertbox.show();


				}
			});

			favouriteBTN.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					MODELProduct modelProduct=productdetails.get(favouriteBTN.getId()-productdetails.size());        
					if(DALFavourites.insertFavProd(db,UTILSSessionVariables.modelUserSessionVar.get_userId(), modelProduct)){
						Toast.makeText(getBaseContext(), modelProduct.get_productName()+" added to your favourites.", Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(getBaseContext(), "Insertion Failure", Toast.LENGTH_LONG).show();
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

		super.easyGo(v, this);

	}

	private void updateHeaderCartDetails(){
		super.updateHeaderCartDetails(_headerCartDetailsBT,welcomeUser);
	}

}
