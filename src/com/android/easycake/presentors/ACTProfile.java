package com.android.easycake.presentors;


import com.android.easycake.R;
import com.android.easycake.dal.DALEditUser;
import com.android.easycake.dal.DALProfileUpdate;
import com.android.easycake.models.MODELUser;
import com.android.easycake.utilities.UTILSSessionVariables;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ACTProfile extends SuperActivity {
	TextView _firstnameTV,_surnameTV,_contactTV,_emailTV,_addressTV;

	EditText _firstnameED,_surnameED,_contactED,_emailED,_addressED;

	Button _updateBT,_submitBT,_changepassBT;
	Boolean userNameAvailCheck,updateState=false,submitState=false;
	DALEditUser dalEditUser=new DALEditUser();
	DALProfileUpdate dalProfileUpdate=new DALProfileUpdate();
	MODELUser modeluser;
	SQLiteDatabase db;
	Button _headerCartDetailsBT;
	TextView welcomeUser;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		
_headerCartDetailsBT=(Button)findViewById(R.id.shopping);
welcomeUser=(TextView)findViewById(R.id.welcomeuser);
		
		super.updateHeaderCartDetails(_headerCartDetailsBT,welcomeUser);
		getHandles();
		db=openOrCreateDatabase("EasyCake",MODE_PRIVATE, null);
		modeluser=UTILSSessionVariables.modelUserSessionVar;

		updateUIData();


		_updateBT.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				if(!updateState){
					updateState=true;

					_firstnameED.setVisibility(View.VISIBLE);
					_surnameED.setVisibility(View.VISIBLE);
					_emailED.setVisibility(View.VISIBLE);
					_contactED.setVisibility(View.VISIBLE);
					_addressED.setVisibility(View.VISIBLE);

					_submitBT.setVisibility(View.VISIBLE);
					_changepassBT.setVisibility(View.VISIBLE);
					_firstnameED.setText(modeluser.get_firstName());
					_surnameED.setText(modeluser.get_surName());
					_emailED.setText(modeluser.get_email());
					_contactED.setText(modeluser.get_contact());
					_addressED.setText(modeluser.get_address());


				}else{
					updateState=false;

					_firstnameED.setVisibility(View.GONE);
					_surnameED.setVisibility(View.GONE);
					_emailED.setVisibility(View.GONE);
					_contactED.setVisibility(View.GONE);
					_addressED.setVisibility(View.GONE);
					_submitBT.setVisibility(View.GONE);
					_changepassBT.setVisibility(View.GONE);


				}

			}
		});

		
		_submitBT.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub


				if(!submitState){
					submitState=true;

					MODELUser userData=new MODELUser();
					userData.set_userId(UTILSSessionVariables.modelUserSessionVar.get_userId());

					userData.set_firstName(_firstnameED.getText().toString());
					userData.set_surName(_surnameED.getText().toString());
					userData.set_email(_emailED.getText().toString());
					userData.set_contact(_contactED.getText().toString());
					userData.set_address(_addressED.getText().toString());
					//userData.set_password(_passwordED.getText().toString());

					if(dalProfileUpdate.update(db,userData)!=null){

						UTILSSessionVariables.modelUserSessionVar=dalProfileUpdate.update(db,userData);
						modeluser=UTILSSessionVariables.modelUserSessionVar;
						updateUIData();
						Toast.makeText(getBaseContext()	, "Updation Succesfull", Toast.LENGTH_SHORT).show();

						_firstnameED.setVisibility(View.GONE);
						_surnameED.setVisibility(View.GONE);
						_emailED.setVisibility(View.GONE);
						_contactED.setVisibility(View.GONE);
						_addressED.setVisibility(View.GONE);	
						_submitBT.setVisibility(View.GONE);
						

					}
				}
				
				else{
					Toast.makeText(getBaseContext()	, "Update failure", Toast.LENGTH_SHORT).show();

					_firstnameED.setVisibility(View.GONE);
					_surnameED.setVisibility(View.GONE);
					_emailED.setVisibility(View.GONE);
					_contactED.setVisibility(View.GONE);
					_addressED.setVisibility(View.GONE);

					_submitBT.setVisibility(View.GONE);
				}
			}

		});
		

		_changepassBT.setOnClickListener(new View.OnClickListener() {


			public void onClick(View v) {
				// TODO Auto-generated method stub
				LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);					
				View layout = inflater.inflate(R.layout.profiledialogbox,(ViewGroup) findViewById(R.id.relativeprofile));

				final EditText oldPassword=(EditText)layout.findViewById(R.id.edpassword);
				final EditText newPassword=(EditText)layout.findViewById(R.id.ednewpassword);
				final EditText cnfPassword=(EditText)layout.findViewById(R.id.edcnfpassword);


				AlertDialog.Builder alertbox = new AlertDialog.Builder(ACTProfile.this);

				alertbox.setView(layout);

				alertbox.setTitle("Passsword Update");
				alertbox.setPositiveButton("Update",new OnClickListener() {

					
					public void onClick(DialogInterface dialog, int which) {
						//check if given password is the right one
						if(oldPassword.getText().toString().equals(UTILSSessionVariables.modelUserSessionVar.get_password())){
							if(newPassword.getText().toString().equals(cnfPassword.getText().toString())){

								MODELUser modelUser=UTILSSessionVariables.modelUserSessionVar;
								modelUser.set_password(newPassword.getText().toString());
								DALProfileUpdate dalProfileUpdate=new DALProfileUpdate();
								UTILSSessionVariables.modelUserSessionVar=dalProfileUpdate.update(db, modelUser);
								ACTProfile.this.modeluser=UTILSSessionVariables.modelUserSessionVar;

								Toast.makeText(getBaseContext()	, "Updation Succesfull", Toast.LENGTH_SHORT).show();

							}
							else {
								Toast.makeText(getBaseContext(), "Passwords not match", Toast.LENGTH_SHORT).show();
							}
						}
						else {
							Toast.makeText(getBaseContext(), "Given password doesnt match with our records", Toast.LENGTH_SHORT).show();
						}
					}	
				});

				alertbox.setNeutralButton("Cancel",new OnClickListener() {

					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				});

				alertbox.show();

			}
		});

	}



	private void updateUIData()
	{
		_firstnameTV.setText(modeluser.get_firstName());
		_surnameTV.setText(modeluser.get_surName());
		_emailTV.setText(modeluser.get_email());
		_contactTV.setText(modeluser.get_contact());
		_addressTV.setText(modeluser.get_address());

	}




	public void getHandles()
	{
		_firstnameTV=(TextView)findViewById(R.id.tvfirstname);
		_surnameTV=(TextView)findViewById(R.id.tvsurname);
		_emailTV=(TextView)findViewById(R.id.tvemail);
		_contactTV=(TextView)findViewById(R.id.tvcontact);
		_addressTV=(TextView)findViewById(R.id.tvaddress);
		_firstnameED=(EditText)findViewById(R.id.edfirstname);
		_surnameED=(EditText)findViewById(R.id.edsurname);
		_emailED=(EditText)findViewById(R.id.edemail);
		_contactED=(EditText)findViewById(R.id.edcontact);
		_addressED=(EditText)findViewById(R.id.edaddress);
		_updateBT=(Button)findViewById(R.id.update);
		_submitBT=(Button)findViewById(R.id.submit);
		_changepassBT=(Button)findViewById(R.id.passwordchange);

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
}
