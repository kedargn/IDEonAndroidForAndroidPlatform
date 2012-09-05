package kgn.vhc.pp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.app.AlertDialog;
import java.io.File;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.InputStream;

public class CreateProj extends Activity{
	
	EditText projname_field,pckge_name,ed;
	String projname,pckgname; 
	File root;
	char[] tempPckg;
	String[] pckgFile;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createproj);
        ed=(EditText)findViewById(R.id.projname);
        ed.setText("Enter Here");
        
        ed=(EditText)findViewById(R.id.packagename);
        ed.setText("Enter Here");

   }
	
	public void createFileStructures(View V) 
	//onClickListener implemented through xml onclick attribute
	{
		String temp;
		projname_field=(EditText)findViewById(R.id.projname);
		projname=projname_field.getText().toString();
		
		pckge_name=(EditText)findViewById(R.id.packagename);
		pckgname=pckge_name.getText().toString();
		 root=Environment.getExternalStorageDirectory();
		
		new AlertDialog.Builder(this).setTitle("testing!!!!").setMessage("project name:"+projname+"\npackage name:"+pckgname+"external path is:"+root.getAbsolutePath()).setNeutralButton("Close",null).show();
		createDir(projname);
		createDir(projname+"/src");
		createDir(projname+"/gen");
		
		separatngPckg(pckgname);
		
		createDir(projname+"/assets");
		createDir(projname+"/bin");
		createDir(projname+"/bin"+"/res");
		createDir(projname+"/bin"+"/res"+"/drawable");
		createDir(projname+"/res");
		createDir(projname+"/res/drawable");
		createDir(projname+"/res/layout");
		
		
		temp=root+"/"+projname+"/src/";
		
		for(int i=0;i<pckgFile.length;i++) {
			temp=temp+pckgFile[i]+"/";
		 }
		
		genDir();
		createFile(temp,projname+".java");
		createFile(root+"/"+projname+"/res/layout/","main.xml");
		createFile(root+"/"+projname+"/","AndroidManifest.xml");
		
		copyngFromAssets("javafile.txt",temp+projname+".java");
		copyngFromAssets("Manifest.txt",root+"/"+projname+"/AndroidManifest.xml");
		copyngFromAssets("main.txt",root+"/"+projname+"/res/layout/main.xml");
		
	}
	
	
	public void createDir(String path)
	{
		File fs=new File(root+"/"+path);
		if(fs.mkdir())
		{
			Log.i("file created",fs.getAbsolutePath());
			
		}
		else
			Log.i("file NOT created",fs.getAbsolutePath());
			
	
		
		
	}
	
	public void separatngPckg(String pckg)
	{
		String tempPath=root+"/"+projname+"/src";
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
		String tempPath=root+"/"+projname+"/gen";
		for(int i=0;i<pckgFile.length;i++)
		{
			File pck1=new File(tempPath+"/"+pckgFile[i]);
			tempPath=tempPath+"/"+pckgFile[i];
			
			
			if(pck1.mkdir()) {
		    Log.i("file created is",pck1.getAbsolutePath());	
			}
			else
				
			//tempPath.concat(pckgFile[i]+"/");
			Log.i("file not created is ",tempPath);
			
		}
		
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
		InputStream is=getAssets().open(from);
		
		byte[] buffer=new byte[1000];
		
		is.read(buffer);
		
		is.close();
		
		File f1=new File(to);
		FileOutputStream fout=new FileOutputStream(f1);
		fout.write(buffer);
		fout.flush();
		fout.close();
		Log.i("PATH",f1.getAbsolutePath());
		
		
		
		}
		catch(Exception e)
		{
			Log.i("exception is",e.toString());
		}
	}
	
}
