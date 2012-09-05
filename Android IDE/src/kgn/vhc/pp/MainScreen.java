package kgn.vhc.pp;

import android.app.Activity;
import android.widget.ImageButton;
import android.os.Bundle;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainScreen extends Activity   {
    /** Called when the activity is first created. */
	ImageButton btn,btn1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btn=(ImageButton)findViewById(R.id.next);
        //btn.setOnClickListener(this);
        btn1=(ImageButton)findViewById(R.id.about);
      //  btn1.setOnClickListener(this);
        
    }
    public void toNext(View V)
    {
    	
    	Intent intent=new Intent();
    	intent.setClass(this,Options.class);
    	startActivity(intent);
    	
    }
    
    public void toAbout(View V)
    {
    	
    	Intent intent=new Intent();
    	intent.setClass(this,About.class);
    	startActivity(intent);
    	
    }
}