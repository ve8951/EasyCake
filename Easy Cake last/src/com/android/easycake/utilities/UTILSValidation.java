package com.android.easycake.utilities;



import com.android.easycake.dal.DALRegistration;

import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class UTILSValidation {


	public static final int VALIDATE_USERNAME=0;
	public static final int VALIDATE_PASSWORD=1;
	public static final int VALIDATE_EMAIL=2;
	public static final int VALIDATE_CONTACT=3;
	public static final int VALIDATE_NAME=4;
	public static final int VALIDATE_NULL=5;

	private  static String usernameRegExp="^([a-zA-Z0-9_\\.])+$";
	private  static String passwordRegExp="^([\\S])+$";
	private  static String emailRegExp="^[\\_]*([a-z0-9]+(\\.|\\_*)?)+@([a-z][a-zA-Z0-9\\-]+(\\.|\\-*\\.))+[a-zA-Z]{2,6}$";
	private  static String contactRegExp="^[\\d]{10}$";
	private  static String nameRegExp="^([a-zA-Z\\w])+$";

	private  static String usernameErrorMsg="Username must only contain letters A-Z or numbers 0-9 ";
	private static String passwordErrorMsg="Password must only contain letters A-Z or numbers 0-9 ";
	private static String emailErrorMsg="Email must only contain letters A-Z or numbers 0-9 ";
	private static String contactErrorMsg="Contact number  must only contain numbers 0-9 and should exactly have 10 characters";
	private  static String nameErrorMsg="Name must only contain letters A-Z or \".\" operator";
	private  static String nullErrorMsg="This field is mandatory";
	private  static String validationResult="";

	public static Boolean validate(int validationtype,EditText sourceWidget,ImageView warningWidget){

		switch (validationtype) {

		case VALIDATE_USERNAME:
			if(!sourceWidget.getText().toString().matches(usernameRegExp)) {
				warningWidget.setVisibility(View.VISIBLE);
				validationResult.concat(usernameErrorMsg+"/n");

				return false;
			}
			break;


		case VALIDATE_PASSWORD:
			if(!sourceWidget.getText().toString().matches(passwordRegExp)) {
				warningWidget.setVisibility(View.VISIBLE);
				validationResult.concat(passwordErrorMsg+"/n");
				return false;
			}

			break;

		case VALIDATE_EMAIL:
			if(!sourceWidget.getText().toString().matches(emailRegExp)) {
				warningWidget.setVisibility(View.VISIBLE);
				validationResult.concat(emailErrorMsg+"/n");
				return false;
			}

			break;

		case VALIDATE_CONTACT:
			if(!sourceWidget.getText().toString().matches(contactRegExp)) {
				warningWidget.setVisibility(View.VISIBLE);
				validationResult.concat(contactErrorMsg+"/n");
				return false;
			}

			break;
		case VALIDATE_NAME:
			if(!sourceWidget.getText().toString().matches(nameRegExp)) {
				warningWidget.setVisibility(View.VISIBLE);
				validationResult.concat(nameErrorMsg+"/n");
				return false;
			}

			break;

		case VALIDATE_NULL:
			if(sourceWidget.getText().toString().length()==0) {
				warningWidget.setVisibility(View.VISIBLE);
				validationResult.concat(nullErrorMsg+"/n");;
				return false;
			}

			break;



		}
		return true;
	}

	public static Boolean matchPassword(EditText passwordET,EditText cnfPasswordET,ImageView warningWidget){
		if(!(passwordET.getText().toString().equals(cnfPasswordET.getText().toString()))){
			warningWidget.setVisibility(View.VISIBLE);
			validationResult.concat(usernameErrorMsg+"/n");
			return false;
		}else {
			return true;
		}


	}
	
	public static Boolean usernameAvailCheck(SQLiteDatabase db,EditText username,ImageView warningWidget){
		
		if(!DALRegistration.userNameAvailCheck(db, username.getText().toString())){
			warningWidget.setVisibility(View.VISIBLE);
			return false;
		}else{
			return true;
		}
		
	
	}

}
