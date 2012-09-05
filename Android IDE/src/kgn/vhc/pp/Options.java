package kgn.vhc.pp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Context;

import android.app.Dialog;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;
import java.io.FileOutputStream;
import android.util.Log;
import android.widget.TextView;
import android.view.ViewGroup;
import java.io.IOException;
import java.io.FileWriter;

public class Options extends Activity  {
	 String tempPath;
	Button btn1;
	static String pckgname;
	String projname,delProj,ifempty="Empty";
	static String typeOfProj;
	String[] pckgFile;
	static File root=Environment.getExternalStorageDirectory();   //EEE
	Context ctx=this;
	
	static String  openProjnm;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     
        setContentView(R.layout.options);
        TextView t=(TextView)findViewById(R.id.prjname0);
        refreshEntry();
        
        
   }
	
public void selectProj(View v) {
	final Dialog selctProj=new Dialog(this);
	selctProj.setContentView(R.layout.custortemp);
	selctProj.show();
	
	
	Button custom=(Button)selctProj.findViewById(R.id.custombtn);
	
	custom.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			selctProj.dismiss();
			typeOfProj="custom";
			dialogToProjCreatn();
			
		}
	});
	
	
	//Button grcy=(Button)selctProj.findViewById(R.id.custombtn);
	typeOfProj="grocery";
	
Button grcy=(Button)selctProj.findViewById(R.id.grcystbtn);
	
	grcy.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			selctProj.dismiss();
			typeOfProj="grocery";
			dialogToProjCreatn();
			
		}
	});
Button bankBtn=(Button)selctProj.findViewById(R.id.banktbtn);
	
	bankBtn.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			selctProj.dismiss();
			typeOfProj="bank";
			dialogToProjCreatn();
			
		}
	});
	}
	
	

	
	public void dialogToProjCreatn()
	{
		//Context cont=getApplicationContext();
		
		final Dialog dialog=new Dialog(this);
		//Log.i("maxcntr",projname+maxcounter);
		
		new File(root+"/workspace").mkdir();
		File f1=new File(root+"/workspace");
		String[] ts=f1.list();
		 if(ts.length>=5){
				
				popIt("Number of projects exceeded..");
				
				}
		 else{
		
		//View myView = LayoutInflater.from(getApplicationContext()).inflate( R.layout.createproj, null);
		dialog.setContentView(R.layout.createproj);
		Log.i("dialog",dialog.toString());
		
		Button btn=(Button)dialog.findViewById(R.id.finish);
		dialog.show();
		
		
		btn.setOnClickListener(new OnClickListener(){

	  public void onClick(View V)
	//onClickListener implemented through xml onclick attribute
	  {
    	 
    	 try{
    		 
    		// else
    		 //{
    	 Log.i("Viwe",V.toString());
    	 Log.i("dialog",dialog.toString());
		String temp;
		
		//Dialog dlg=new Dialog(Input.this);
		EditText projname_field=(EditText)dialog.findViewById(R.id.projname);
		projname=projname_field.getText().toString();
		ifempty=projname;
		// jus to save a copy its copying object to object..copy usin string functions.. wait ill sho
		
		EditText pckge_name=(EditText)dialog.findViewById(R.id.packagename);
		pckgname=pckge_name.getText().toString();
		
		
		if(projname.equals("Empty")){
			//Toast toast=Toast.makeText(Options.this, "Empty proect cannot be created", 2000);
			
			popIt("projname cant be named as Empty");
			dialog.dismiss();


		}
		
		else if((projname.equals("")) || (pckgname.equals(""))){
			popIt("Oops! Fields Empty..........");
			dialog.dismiss();
			Log.i("empty","the proj name or pck name is empty");
			
		}
		
		
		else
		{
			
		
		Log.i("pppp",projname+pckgname);
		 root=Environment.getExternalStorageDirectory();
		//create the directory
		 File f=new File("mnt/sdcard/workspace");
			f.mkdir();
		//new AlertDialog.Builder(this).setTitle("testing!!!!").setMessage("project name:").setNeutralButton("Close",null).show();
		createDir(projname);
		createDir(projname+"/src");
		createDir(projname+"/gen");
		
		separatngPckg(pckgname);
		
		createDir(projname+"/assets");
		createDir(projname+"/bin");
		//createDir(projname+"/bin/"+Options.openProjnm);
		createDir(projname+"/bin"+"/res");
		createDir(projname+"/bin"+"/res"+"/drawable");
		createDir(projname+"/res");
		createDir(projname+"/res/drawable");
		createDir(projname+"/res/layout");
		createDir("/mnt/sdcard/logData");
		createDir(projname+"/libs");
		
		
		File xmlFiles=new File(root+"/xmlfiles");
		xmlFiles.mkdir();
		xmlFiles=new File(root+"/xmlfiles/"+pckgname);
		xmlFiles.mkdir();
		
		File log=new File("/mnt/sdcard/logData");
		if(log.mkdir())
			Log.i("the LOG FILE CREATED",log.getAbsolutePath());
		else
			Log.i("LOG FILE NO CREATED","");
		
		
		createDir(projname+"/bin/"+projname);
		temp=root+"/workspace"+"/"+projname+"/src/";
		
		for(int i=0;i<pckgFile.length;i++) {
			temp=temp+pckgFile[i]+"/";
		 }
		
		genDir();
		createFile(temp,projname+".java");
		createFile(root+"/workspace/"+projname+"/res/layout/","main.xml");
		createFile(root+"/workspace/"+projname+"/","AndroidManifest.xml");
	
		createFile(root+"/workspace/"+projname+"/res/drawable/","ic_launcher.png");
		//createDir(root+"/logData");
		createFile("/mnt/sdcard/logData/","native_stdout.txt");
		createFile("/mnt/sdcard/logData/","native_stderr.txt");
		createFile(root+"/workspace/"+projname+"/","temp.txt");
		createFile(root+"/xmlfiles/"+pckgname+"/","share.xml");
		copyngFromAssets("share.xml",root+"/xmlfiles/"+pckgname+"/share.xml");
		
		copyngFromAssets("Manifest.xml",root+"/workspace/"+projname+"/AndroidManifest.xml");
		
		if(typeOfProj.equals("custom")) {
		copyngFromAssets("main.xml",root+"/workspace/"+projname+"/res/layout/main.xml");
		copyngFromAssets("javafile.txt",temp+projname+".java");
		}
		else if(typeOfProj.equals("grocery")) {
			copyngFromAssets("grocerymain.xml",root+"/workspace/"+projname+"/res/layout/main.xml");
			createFile(temp,"DataBaseHelper.java");
			copyngFromAssets("groceryjava.txt",temp+projname+".java");
			copyngFromAssets("grocerydb.txt",temp+"DataBaseHelper.java");
		}
		else{
			copyngFromAssets("bankmain.xml",root+"/workspace/"+projname+"/res/layout/main.xml");
			createFile(root+"/workspace/"+projname+"/res/layout/","next.xml");
			createFile(root+"/workspace/"+projname+"/res/layout/","screen2.xml");
			copyngFromAssets("screen2.xml",root+"/workspace/"+projname+"/res/layout/screen2.xml");
			createFile(root+"/workspace/"+projname+"/res/layout/","screen3.xml");
			createFile(root+"/workspace/"+projname+"/","AndroidManifest.xml");
			copyngFromAssets("bankmanifest.xml",root+"/workspace/"+projname+"/AndroidManifest.xml");
			copyngFromAssets("screen3.xml",root+"/workspace/"+projname+"/res/layout/screen3.xml");
			copyngFromAssets("next.xml",root+"/workspace/"+projname+"/res/layout/next.xml");
			createFile(temp,"DataBaseHelper.java");
			createFile(temp,"Next.java");
			copyngFromAssets("nextjava.txt",temp+"Next.java");
			copyngFromAssets("bankjava.txt",temp+projname+".java");
			copyngFromAssets("bankdb.txt",temp+"DataBaseHelper.java");
			
			copyngFromAssets("bank.png",root+"/workspace/"+projname+"/res/drawable/bank.png");
			copyngFromAssets("home.png",root+"/workspace/"+projname+"/res/drawable/home.png");
			copyngFromAssets("places.png",root+"/workspace/"+projname+"/res/drawable/places.png");
			copyngFromAssets("services.png",root+"/workspace/"+projname+"/res/drawable/services.png");
			copyngFromAssets("transactions.png",root+"/workspace/"+projname+"/res/drawable/transactions.png");
		}
		copyngFromAssets("ic_launcher.png",root+"/workspace/"+projname+"/res/drawable/ic_launcher.png");
		refreshEntry();
		
		
		dialog.dismiss();    //to dismiss dialog 
    	 }
    	}
    	 
    	 catch(Exception e)
    	 {
    	 Log.i("proj",V.toString());
    	 //Log.i("projname",projname+projname_field);
    	 Log.i("pppp",projname+"     "+pckgname);
    	 }
    	 
	  }
     });
		 }		
	  
	}	
 
	
	
	//pop your msgs into this
public void popIt(String msg){
		final AlertDialog alertDialog = new AlertDialog.Builder(Options.this).create();
		alertDialog.setTitle("Warning");
		alertDialog.setMessage(msg);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int which) {
		      
			   alertDialog.dismiss();
		   }
		});
		alertDialog.setIcon(R.drawable.alert);
		alertDialog.show();	
}		
	

public void createDir(String path)
{
	File fs=new File(root+"/workspace"+"/"+path);
	if(fs.mkdir())
	{
		Log.i("file created",fs.getAbsolutePath());
		
	}
	else
		Log.i("file NOT created",fs.getAbsolutePath());
		

	
	
}






public  void separatngPckg(String pckg)
{
	tempPath=root+"/workspace"+"/"+projname+"/src";
	try {
		
	 pckgFile=pckg.split("\\.");
	Log.i("pack1",pckgFile[0]+pckgFile.length);
	
	
	for(int i=0;i<pckgFile.length;i++)
	{
		File pck=new File(tempPath+"/"+pckgFile[i]);
		tempPath=tempPath+"/"+pckgFile[i];
		if(pck.mkdir()) {
	    Log.i("file created is",pck.getAbsolutePath());	
		}
		else
		//tempPath.concat(pckgFile[i]+"/");
		Log.i("temp path is ",tempPath);
		
	}
	}
	catch(Exception e)
	{
		Log.i("e is",e.toString());
	}
}

public void genDir()

{
	File pck1;
	String tempPath=root+"/workspace"+"/"+projname+"/gen";
	for(int i=0;i<pckgFile.length;i++)
	{
		pck1=new File(tempPath+"/"+pckgFile[i]);
		tempPath=tempPath+"/"+pckgFile[i];
		
		
		if(pck1.mkdir()) {
	    Log.i("file created is",pck1.getAbsolutePath());	
		}
		else
			
		//tempPath.concat(pckgFile[i]+"/");
		Log.i("file not created is ",tempPath);
		
	}
	/*try {
	pck1=new File(tempPath,"/R.java");
	FileOutputStream fos1 = new FileOutputStream(pck1);
	fos1.close();
	}
	catch(Exception e)
	{
		
	}*/
	
}

public void createFile(String path,String fileName)
{
	try {
	String partialPath=path;
	Log.i("path is",partialPath);
	
	File file1=new File(partialPath,fileName);
	FileOutputStream fos1 = new FileOutputStream(file1);
	
	fos1.close();
	 if(file1.isFile())
	 {
		 Log.i("file created",file1.getAbsolutePath());
	 }
	 else
		 Log.i(fileName,"file is not created");
 }

catch(Exception e)
{
	Log.i("exception is ",e.toString());
}
}	


public void copyngFromAssets(String from,String to)
{
	try {
		File temp;
	InputStream is=getAssets().open(from);
	
	int by=0;
	byte[] buffRead=new byte[40000];
	byte[] buffer=new byte[40000];
	byte[] buff;
	if((from.equals("main.xml"))||(from.equals("share.xml"))||(from.equals("grocerymain.xml"))||(from.equals("ic_launcher.png"))||(from.equals("bank.png"))||(from.equals("home.png"))||(from.equals("places.png"))||(from.equals("transactions.png"))||(from.equals("services.png"))||(from.equals("bankmain.xml"))||(from.equals("next.xml"))||(from.equals("screen2.xml"))||(from.equals("screen3.xml"))){
		by=is.read(buffRead);
	}
	else {
	 by=is.read(buffer);}
	int sy=0;
	Log.i("the no of bytes read from "+from+"is bytes",""+by);
	
	is.close();
if(from.equals("javafile.txt")||(from.equals("groceryjava.txt"))||(from.equals("grocerydb.txt"))||(from.equals("bankdb.txt"))||(from.equals("bankjava.txt"))||(from.equals("nextjava.txt"))) {
		
		Log.i("entered","entered in copyng from assets");
		int z=by;
		buff=new byte[by];
		for(int i=0;i<z;i++)
		{
			buff[i]=buffer[i];
		}
		
	String stringOfFile=new String(buff);
	Log.i("tha java file contents",stringOfFile);
	String res=stringOfFile.replaceFirst("org\\.example\\.hello",pckgname);
	res=res.replaceFirst("Helloworld",projname );
	Log.i("the LENGTH of RES is",""+res.length());
	//Byte b=new Byte(res);
	
	temp=new File(root+"/workspace/"+projname+"/temp.txt");
	FileOutputStream tout=new FileOutputStream(temp);
	buffer=null;
	int strLen=res.length();
	buff=res.getBytes();
	tout.write(buff,0,strLen);
	tout.flush();
	tout.close();
	
	Log.i("the lENGTH OF RES STRING",""+res.length());
	InputStream tIn=new FileInputStream(temp);
	 sy=tIn.read(buffRead);
	
	
	Log.i("the available is",""+tIn.available());
	Log.i("the value of TIN in java is",""+sy);
	
	tIn.close();
	//buffer=null;
	
	//buffer=res.getBytes();
	//Log.i("tha java file contents",stringOfFile);
	}
	else if(from.equals("Manifest.xml")||from.equals("bankmanifest.xml")) {
		Log.i("entered","entered in copyng from assets");
		int z=by;
		buff=new byte[by];
		for(int i=0;i<z;i++)
		{
			buff[i]=buffer[i];
		}
		String stringOfFile=new String(buff);
		Log.i("tha manifest file contents",stringOfFile);
		String res=stringOfFile.replaceAll("org\\.example\\.hello",pckgname);
	res=res.replaceAll(".Helloworld", "."+projname);
		res=res.replaceAll("@string/app_name", projname);
		Log.i("the length of manifest file",""+res.length());
		
		temp=new File(root+"/workspace/"+projname+"/temp.txt");
		FileOutputStream tout=new FileOutputStream(temp);
		buffer=null;
		int strLen=res.length();
		buff=res.getBytes();
		tout.write(buff,0,strLen);
		tout.flush();
		tout.close();
		
		Log.i("the lENGTH OF RES STRING",""+res.length());
		InputStream tIn=new FileInputStream(temp);
		 sy=tIn.read(buffRead);
		
		
		Log.i("the available is",""+tIn.available());
		Log.i("the value of TIN in java is",""+sy);
		
		tIn.close();
	}
	else{
		sy=by;
		
	}
	
	
	File f1=new File(to);
	FileOutputStream fout=new FileOutputStream(f1);
	fout.write(buffRead,0,sy);
	
	fout.flush();
    fout.close();
   // BufferedWriter br=new BufferedWriter(new FileWriter(f1));
   // br.append("\0");
    //br.close();
	
    Log.i("the length of "+from+" file",""+f1.length());
	Log.i("PATH",f1.getAbsolutePath());
	
	
	
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}


}


public void refreshEntry()
{
	TextView tv0,tv1,tv2,tv3,tv4;
	try{
	
	String[] fileEntries;
	int i;
	
	File t=new File(root+"/workspace");
	fileEntries=t.list();
	 /*if(fileEntries[0]==null)
		 throw new NullPointerException("fe is culprit");*/
	
	i=fileEntries.length;
	
	
	
			
	
	tv0=(TextView)findViewById(R.id.prjname0);
	tv0.setText("Empty");
	tv1=(TextView)findViewById(R.id.prjname1);
	tv1.setText("Empty");
	tv2=(TextView)findViewById(R.id.prjname2);
	tv2.setText("Empty");
	tv3=(TextView)findViewById(R.id.prjname3);
	tv3.setText("Empty");
	tv4=(TextView)findViewById(R.id.prjname4);
	tv4.setText("Empty");
	i=fileEntries.length;
	
	if(i>5){
		
		i=5;
		
		
	}
	
	
	switch(i)
	{
	case 5: tv4.setText(fileEntries[4]);
		
	case 4:	tv3.setText(fileEntries[3]);
	
	case 3: tv2.setText(fileEntries[2]);
	
	case 2: tv1.setText(fileEntries[1]);
	
	case 1: tv0.setText(fileEntries[0]);
	
	default:    ;
	}
	}
	catch(Exception e)
	{
		
	 
	 
	Log.i("EXception",e.toString());
	 
    }
	
	
  }



public void deletFileEntry(View v)
  {
            //String delProj;
            File f;
           
	        ViewGroup p=(ViewGroup)v.getParent();
	        
	        TextView prjNameTv=(TextView)p.getChildAt(0);
	        
	        delProj=(String)prjNameTv.getText();
	        
	       
	        
	        
	        if(delProj=="Empty"){
	        	
		        popIt("Oops! no project yet.");
		        }
	        
	        Log.i("the name of file to del is:",delProj);
	        
	        
	        f=new File(root+"/workspace/"+delProj);
	        Log.i("the name of file to del is:",delProj);
	        boolean b=delFile(f);
	        prjNameTv.setText("Empty");
	        refreshEntry();
  }

public boolean delFile(File f)
{
	
	 String[] fileChild;
	 boolean success;
	
 if(f.isDirectory())
 {
	fileChild=f.list();
	
	 for(int i=0;i<fileChild.length;i++)
	 {
		 success=delFile(new File(f,fileChild[i]));
	    Log.i("child file is",fileChild[i]);
	 if (!success) {
         return false;
	 }
   }
  }
    // maxcounter--;
	 return f.delete();
	
	
 }

public void openProj(View V)
{
	
	File f;
    
    ViewGroup p=(ViewGroup)V.getParent();
    
    TextView prjNameTv=(TextView)p.getChildAt(0);
    
    openProjnm=(String)prjNameTv.getText();
	
		if(openProjnm.equals("Empty")){
			//Toast toast=Toast.makeText(Options.this, "Empty proect cannot be created", 2000);
			
			popIt("Sorry! Open failed.......");
			//dialog.dismiss();


		}
		else {
			Intent tabScreen=new Intent();
			tabScreen.setClass(this,Tab.class);
			startActivity(tabScreen);
			
		}
	
	
	
}

  
}