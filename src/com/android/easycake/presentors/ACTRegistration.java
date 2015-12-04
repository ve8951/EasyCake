package com.android.easycake.presentors;

import java.util.ArrayList;
import java.util.List;
import com.android.easycake.R;
import com.android.easycake.dal.DALRegistration;
import com.android.easycake.models.MODELArea;
import com.android.easycake.models.MODELCity;
import com.android.easycake.models.MODELUser;
import com.android.easycake.utilities.UTILSValidation;


import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ACTRegistration extends SuperActivity {

	EditText firstName,lastName,email,contact,userName,password,cnfpassword;
	ImageView firstNameErrIV,lastNameErrIV,emailErrIV,contactErrIV,usernameErrIV,passwordErrIV,cnfPasswordErrIV;
	Boolean usernamecheck;
	Button userNameAvailBT,submitBT;
	SQLiteDatabase db;
	String[] cityName;
	String selectedCityId;
	String[] areaName,selectedArea;
	List<MODELCity> cityData=new ArrayList<MODELCity>();
	List<MODELArea> areaData=new ArrayList<MODELArea>();
	DALRegistration dalRegistration;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		getHandles();

		db=openOrCreateDatabase("EasyCake", MODE_PRIVATE, null);


		//-----------------------Checking for username availability---------------------------------------//
		userNameAvailBT.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {				


				if(DALRegistration.userNameAvailCheck(db, userName.getText().toString()))	
					Toast.makeText(getBaseContext(), "UserName Available", Toast.LENGTH_SHORT).show();

				else {
					Toast.makeText(getBaseContext(), "UserName not Available", Toast.LENGTH_SHORT).show();

				}

			} 
		});

		submitBT.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				//				if(password.getText().toString().equals(cnfpassword.getText().toString()))
				//				{
				//					if(DALRegistration.userNameAvailCheck(db, userName.getText().toString()))
				//					{
				if (validate()) {


					MODELUser userData=new MODELUser();
					userData.set_firstName(firstName.getText().toString());
					userData.set_surName(lastName.getText().toString());
					userData.set_email(email.getText().toString());
					userData.set_contact(contact.getText().toString());
					userData.set_userName(userName.getText().toString());
					userData.set_password(password.getText().toString());

					DALRegistration.userRegistration(db, userData);
					Intent i=new Intent(ACTRegistration.this,ACTLogin.class);
					startActivity(i);
					Toast.makeText(getBaseContext()	, "Registration Successfull", Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(getBaseContext()	, "Please check for corrections", Toast.LENGTH_SHORT).show();

				}		
			}
		});

	}


	public Boolean validate(){
		firstNameErrIV.setVisibility(View.GONE);
		lastNameErrIV.setVisibility(View.GONE);
		emailErrIV.setVisibility(View.GONE);
		contactErrIV.setVisibility(View.GONE);
		usernameErrIV.setVisibility(View.GONE);
		passwordErrIV.setVisibility(View.GONE);
		cnfPasswordErrIV.setVisibility(View.GONE);


		//----------------------------------------null check validation------------------------------------
		Boolean validate1=UTILSValidation.validate(UTILSValidation.VALIDATE_NULL, firstName,firstNameErrIV);
		Boolean validate2=UTILSValidation.validate(UTILSValidation.VALIDATE_NULL, lastName,lastNameErrIV);
		validate1=validate1 && validate2;
		validate2=UTILSValidation.validate(UTILSValidation.VALIDATE_NULL, email,emailErrIV);
		validate1=validate1 && validate2;
		validate2=UTILSValidation.validate(UTILSValidation.VALIDATE_NULL, contact,contactErrIV);
		validate1=validate1 && validate2;
		validate2=UTILSValidation.validate(UTILSValidation.VALIDATE_NULL, userName,usernameErrIV);
		validate1=validate1 && validate2;
		validate2=UTILSValidation.validate(UTILSValidation.VALIDATE_NULL, password,passwordErrIV);
		validate1=validate1 && validate2;
		validate2=UTILSValidation.validate(UTILSValidation.VALIDATE_NULL, cnfpassword,cnfPasswordErrIV);
		validate1=validate1 && validate2;


		if(validate1){


			//----------------------------------------password match check validation------------------------------------
			validate2=UTILSValidation.matchPassword(password, cnfpassword, cnfPasswordErrIV);		
			validate1=validate1 && validate2;

			//----------------------------------------regular expression match check validation------------------------------------
			validate2=UTILSValidation.validate(UTILSValidation.VALIDATE_NAME, firstName,firstNameErrIV);
			validate1=validate1 && validate2;
			validate2=UTILSValidation.validate(UTILSValidation.VALIDATE_NAME, lastName,lastNameErrIV);
			validate1=validate1 && validate2;
			validate2=UTILSValidation.validate(UTILSValidation.VALIDATE_EMAIL, email,emailErrIV);
			validate1=validate1 && validate2;
			validate2=UTILSValidation.validate(UTILSValidation.VALIDATE_CONTACT, contact,contactErrIV);
			validate1=validate1 && validate2;
			validate2=UTILSValidation.validate(UTILSValidation.VALIDATE_USERNAME, userName,usernameErrIV);
			validate1=validate1 && validate2;
			validate2=UTILSValidation.validate(UTILSValidation.VALIDATE_PASSWORD, password,passwordErrIV);
			validate1=validate1 && validate2;

			if(validate1){

				//----------------------------------------user name availability check validation------------------------------------
				validate2=UTILSValidation.usernameAvailCheck(db, userName, usernameErrIV); 
				validate1=validate1 && validate2;
			}
		}

		return validate1;

	}


	public void getHandles()
	{
		firstName=(EditText)findViewById(R.id.EDREGname);
		lastName=(EditText)findViewById(R.id.EDREGlastname);
		email=(EditText)findViewById(R.id.EDREGemail);
		contact=(EditText)findViewById(R.id.EDREGcontact);
		userName=(EditText)findViewById(R.id.EDREGusername);
		password=(EditText)findViewById(R.id.EDREGpassword);
		cnfpassword=(EditText)findViewById(R.id.EDREGcnfpassword);
		userNameAvailBT=(Button)findViewById(R.id.regnamecheck);
		submitBT=(Button)findViewById(R.id.BTregsubmit);
		firstNameErrIV=(ImageView)findViewById(R.id.firstNameErrIV);
		lastNameErrIV=(ImageView)findViewById(R.id.lastNameErrIV);
		emailErrIV=(ImageView)findViewById(R.id.emailErrIV);
		contactErrIV=(ImageView)findViewById(R.id.contactErrIV);
		usernameErrIV=(ImageView)findViewById(R.id.usernameErrIV);
		passwordErrIV=(ImageView)findViewById(R.id.passwordErrIV);
		cnfPasswordErrIV=(ImageView)findViewById(R.id.cnfPasswordErrIV);
	}

	@Override
	protected void onPause() {
		db.close();
		super.onPause();
	}

	@Override
	protected void onResume() {
		db=openOrCreateDatabase("EasyCake", MODE_PRIVATE, null);
		super.onResume();
	}


}

