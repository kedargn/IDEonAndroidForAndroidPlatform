package kgn.vhc.pp;


import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;


public class Tab extends TabActivity{
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab);
        
        
        TabHost tabs=getTabHost();
        Intent intent=new Intent();
        intent.setClass(this,Widgets.class);
        TabHost.TabSpec spec=tabs.newTabSpec("tab1");
       
        spec.setIndicator("widgets");
        
        spec.setContent(intent);
        tabs.addTab(spec);
        
        
        Intent intent1=new Intent();
        intent1.setClass(this,XmlFile.class);
        TabHost.TabSpec spec1=tabs.newTabSpec("tab2");
        spec1.setIndicator("Xml File");
        spec1.setContent(intent1);
        tabs.addTab(spec1);
        
        intent.setClass(this,JavaFile.class);
        
       TabHost.TabSpec spec2=tabs.newTabSpec("tabs 3");
       spec2.setIndicator("Java File");
       spec2.setContent(intent);
       tabs.addTab(spec2);
       
       TabHost.TabSpec spec3=tabs.newTabSpec("tabs 4");
       spec3.setIndicator("Contents");
       Intent intent4=new Intent();
       intent4.setClass(this,XmlContents.class);
       spec3.setContent(intent4);
       tabs.addTab(spec3);
       
       TabHost.TabSpec spec4=tabs.newTabSpec("tabs 5");
       spec4.setIndicator("Preview");
       Intent intent5=new Intent();
       intent5.setClass(this,Preview.class);
       spec4.setContent(intent5);
       
       tabs.addTab(spec4);
         
         tabs.setCurrentTab(0);
	
	}

}
