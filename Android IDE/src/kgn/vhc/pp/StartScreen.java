package kgn.vhc.pp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;

public class StartScreen extends Activity{
	
	
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.start);
	        
	       

	        int secondsDelayed = 1;
            new Handler().postDelayed(new Runnable() {
                    public void run() {
                    	Intent intent=new Intent();
                    	intent.setClass(StartScreen. this, MainScreen.class);
                            startActivity(intent);
                            finish();
                    }
            }, secondsDelayed * 3000);
		

	        	    }
}
