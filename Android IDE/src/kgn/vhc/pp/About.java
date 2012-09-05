package kgn.vhc.pp; 

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class About extends Activity implements OnClickListener{
	
	Button btn;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        btn=(Button)findViewById(R.id.aboutok);
        btn.setOnClickListener(this);
	}
	 public void onClick(View V)
	    {
	    	
	    	Intent intent=new Intent();
	    	intent.setClass(this,Options.class);
	    	startActivity(intent);
	    	
	    }

}
