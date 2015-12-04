package com.android.easycake.presentors;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.easycake.R;
import com.android.easycake.dal.DALLogin;
import com.android.easycake.models.MODELUser;
import com.android.easycake.utilities.UTILSSessionVariables;

public class ACTLogin extends SuperActivity {

	EditText _usernameET,_passwordET;
	Button _loginBT,_regBT;
	private final static String dbPath="data/data/com.android.easycake/databases/";
	private final static String DB_FILE_NAME="EasyCake";
	SQLiteDatabase db;
	


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		
		gethandles();
		if(!UTILSSessionVariables.dbCopiedState){

			UTILSSessionVariables.dbCopiedState=true;
			copydatabase();
		}
		db=openOrCreateDatabase("EasyCake", MODE_PRIVATE, null);


		//login button
		_loginBT.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				MODELUser modeluser=new MODELUser();


				modeluser.set_userName(_usernameET.getText().toString());
				modeluser.set_password(_passwordET.getText().toString());


				if((DALLogin.getlogin(db, modeluser))!=null)//login successfull case
				{				

					Toast.makeText(getBaseContext(), "!!!...Successsssssss...!!!", Toast.LENGTH_SHORT).show();
					finish();
					UTILSSessionVariables.modelUserSessionVar=DALLogin.getlogin(db, modeluser);	
					Intent i1=new Intent(ACTLogin.this,ACTHomePage.class);				
					startActivity(i1);

				}
				else
				{
					//Intent i2=new Intent(ACTLogin.this,ACTCitys.class);
					Toast.makeText(getBaseContext(), "!!!...Login Failed...!!!", Toast.LENGTH_SHORT).show();
					//startActivity(i2);					
				}

			}
		});


		//register button
		_regBT.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent i2=new Intent(ACTLogin.this,ACTRegistration.class);
				startActivity(i2);

			}
		});
	}
	void copydatabase()
	{

		File file=new File(dbPath+DB_FILE_NAME);//creates the file in data/data
		try
		{
			if(file.exists())
			{
				InputStream assetDB=getApplicationContext().getAssets().open(DB_FILE_NAME);
				OutputStream dbOut=new FileOutputStream(dbPath+DB_FILE_NAME);
				byte[] buffer=new byte[1024];
				int length;
				while ((length=assetDB.read(buffer))>0){
					dbOut.write(buffer,0,length);
				}
				dbOut.flush();
				dbOut.close();
				assetDB.close();
				Toast.makeText(this, "database copied", Toast.LENGTH_LONG).show();

			}
		}
		catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
	}






	private void gethandles() {


		_usernameET=(EditText)findViewById(R.id.ETUsername);
		_passwordET=(EditText)findViewById(R.id.ETPassword);

		_loginBT=(Button)findViewById(R.id.BTNLogin);
		_regBT=(Button)findViewById(R.id.BTNRegister);


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