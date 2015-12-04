package com.android.easycake.presentors;

import com.android.easycake.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class ACTSplashScreen extends SuperActivity{
	ImageView cake;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		
		cake=(ImageView)findViewById(R.id.cakeimage);	
				
          new Handler().postDelayed(new Runnable() {
	           
            public void run() {
            	 
                	Intent i=new Intent(ACTSplashScreen.this,ACTLogin.class);
        		    startActivity(i);                    
        		    finish();
            }
        },2000);
	}
}



