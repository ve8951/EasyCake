package com.android.easycake.presentors;

import java.util.ArrayList;
import java.util.List;

import com.android.easycake.R;
import com.android.easycake.dal.DALFavourites;
import com.android.easycake.dal.DALProductDetails;
import com.android.easycake.models.MODELCartProduct;
import com.android.easycake.models.MODELCategoryFlavour;
import com.android.easycake.models.MODELProduct;
import com.android.easycake.models.MODELShoppingCart;
import com.android.easycake.utilities.UTILSSessionVariables;


import android.R.integer;
import android.R.layout;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class ACTListProdByBranch extends SuperListActivity {
	List<MODELCategoryFlavour> flavourDataList = new ArrayList<MODELCategoryFlavour>();
	List<MODELProduct> modelProductsDataList = new ArrayList<MODELProduct>();

	DALProductDetails dalProductDetail = new DALProductDetails();
	String[] flavourName;
	String[] productName;
	SQLiteDatabase db;
	String storeBranchIdsecond, storeBranchTEXTname,cityStoreBranchname,cityName;
	Spinner flavour;
	TextView branch,welcomeUser;
	Button _headerCartDetailsBT;


	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listflavourandproducts);

		_headerCartDetailsBT=(Button)findViewById(R.id.shopping);
		welcomeUser=(TextView)findViewById(R.id.welcomeuser);
		

		super.updateHeaderCartDetails(_headerCartDetailsBT,welcomeUser);

		branch = (TextView) findViewById(R.id.storeInflavour);
		db = openOrCreateDatabase("EasyCake", MODE_PRIVATE, null);

		storeBranchIdsecond = getIntent().getExtras().getString("storeBranchId");
		storeBranchTEXTname = getIntent().getExtras().getString("storeBranchName");
		cityName=getIntent().getExtras().getString("cityName");
		cityStoreBranchname=cityName+">>"+storeBranchTEXTname;

		branch.setText(cityStoreBranchname);




		flavourPopulate();
		flavour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				productPopulate(pos);

			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

	}







	public class MyListAdapter extends ArrayAdapter {
		Activity context;

		public MyListAdapter(Activity context) {
			super(context, R.layout.rowforproductdetail,flavourName);
			this.context = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflator = context.getLayoutInflater();
			View row = inflator.inflate(R.layout.rowforproductdetail, null);

			TextView prodName = (TextView) row.findViewById(R.id.productname);
			TextView prodInfo = (TextView) row.findViewById(R.id.ingridientTV);
			TextView prodPricePerQuant = (TextView) row.findViewById(R.id.weightTV);
			TextView prodStoreName=(TextView)row.findViewById(R.id.storenameinprodlistTV);


			final Button click=(Button)row.findViewById(R.id.addToCartBT);
			click.setId(position);

			final Button fav=(Button)row.findViewById(R.id.addToFavBT);
			fav.setId(modelProductsDataList.size()+position);


			prodName.setText(modelProductsDataList.get(position).get_productName());
			prodInfo.setText(modelProductsDataList.get(position).get_ingredient());
			prodPricePerQuant.setText("Rs."+modelProductsDataList.get(position).get_price()+" / "+modelProductsDataList.get(position).get_productWeight());
			prodStoreName.setVisibility(View.GONE);


			click.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					Toast.makeText(getBaseContext(),""+click.getId(), Toast.LENGTH_SHORT).show();
					final MODELProduct modelProducts=modelProductsDataList.get(click.getId());

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



					AlertDialog.Builder alertbox = new AlertDialog.Builder(ACTListProdByBranch.this);

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
										Integer userQuant=Integer.parseInt(userGivenQuantityET.getText().toString());
										UTILSSessionVariables.modelShoppingCart.getModelCartProductsList().get(i).setQuantity(new Integer(currentquant+userQuant).toString());
										ACTListProdByBranch.super.updateHeaderCartDetails(_headerCartDetailsBT, welcomeUser);
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
					});

					alertbox.setNeutralButton("CANCEL>>",  new OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {


						}
					});




					alertbox.show();


				}
			});

			fav.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {


					MODELProduct modelProduct=modelProductsDataList.get(fav.getId()-modelProductsDataList.size());	
					if(DALFavourites.insertFavProd(db,UTILSSessionVariables.modelUserSessionVar.get_userId(), modelProduct)){
						Toast.makeText(getBaseContext(), modelProduct.get_productName()+" added to your favourites.", Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(getBaseContext(), "Insertion Failure", Toast.LENGTH_LONG).show();
					}



				}
			});





			return (row);
		}

	}

	@Override
	protected void onResume() {
		db=openOrCreateDatabase("EasyCake", MODE_PRIVATE, null);
		super.updateHeaderCartDetails(_headerCartDetailsBT,welcomeUser);
		super.onResume();
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {





	}











	public void productPopulate(int pos) {
		modelProductsDataList = dalProductDetail.getProductsByFlavId(db,flavourDataList.get(pos).get_categoryID());
		productName = new String[modelProductsDataList.size()];
		for (int j = 0; j < modelProductsDataList.size(); j++) {
			productName[j] = modelProductsDataList.get(j).get_productName();
		}
		setListAdapter(new MyListAdapter(ACTListProdByBranch.this));


	}

	public void flavourPopulate()
	{

		flavourDataList = dalProductDetail.getflavour(db, storeBranchIdsecond);
		flavourName = new String[flavourDataList.size()];
		for (int i = 0; i < flavourDataList.size(); i++) {
			flavourName[i] = flavourDataList.get(i).get_categoryName();
		}
		flavour = (Spinner) findViewById(R.id.spinner1);
		flavour.setAdapter(new ArrayAdapter(getBaseContext(),android.R.layout.simple_dropdown_item_1line, flavourName));
	}

	@Override
	protected void onPause() {
		db.close();
		super.onPause();
	}
	public void easyGo(View v){

		super.easyGo(v, this);

	}



	private void updateHeaderCartDetails(){
		super.updateHeaderCartDetails(_headerCartDetailsBT,welcomeUser);
	}

}
