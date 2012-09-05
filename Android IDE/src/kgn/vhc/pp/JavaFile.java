package kgn.vhc.pp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.format.Time;
import android.util.Log;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;

public class JavaFile extends Activity {
	
	StringBuilder javaText;
	String openJavaFile,genFile,currentActivity;
	String[] listOfFiles,packageNames;
	EditText javaEt;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.javafile);
         javaEt=(EditText)findViewById(R.id.javatextarea);
        
        openJavaFile=Options.root+"/workspace/"+Options.openProjnm+"/src";
        Log.i("the mthd for pckg",openJavaFile);
        File javaFile=new File(openJavaFile);
        listOfFiles=javaFile.list();
        int len=listOfFiles.length;
        while((len!=0)) {
        	int i;
        	boolean flag=false;
        	for( i=0;i<len;i++)
        	{
        		if(listOfFiles[i].equals(Options.openProjnm+".java")) {
        			openJavaFile=openJavaFile+"/"+listOfFiles[i];
        			flag=true;
        		}
        	}
        	if(!flag)
        		openJavaFile=openJavaFile+"/"+listOfFiles[0];	
       Log.i("the list for project",openJavaFile);
       javaFile=new File(openJavaFile);
       
       currentActivity=Options.openProjnm;
       if(javaFile.isFile())
    	   break;
       listOfFiles=javaFile.list();
       len=listOfFiles.length;
       Log.i("the mthd for pckg",openJavaFile+"  "+len); 
	}
        Log.i("the mthd for pckg",openJavaFile); 
  
	
        File f=new File(openJavaFile);
        
        try {
        
         String line;
          javaText=new StringBuilder();
         
         
         BufferedReader br=new BufferedReader(new FileReader(f));
         
         while ((line = br.readLine()) != null) {  
             javaText.append(line);  
             javaText.append('\n'); 
             
             Log.i("the edittext is",line);
           }  
         javaEt.setText(javaText);
         
         File manifest=new File("/mnt/sdcard/workspace/"+Options.openProjnm+"/AndroidManifest.xml");
javaText=new StringBuilder();
         
         
         BufferedReader br1=new BufferedReader(new FileReader(manifest));
         
         while ((line = br1.readLine()) != null) {  
             javaText.append(line);  
             javaText.append('\n'); 
             
             Log.i("the manifest is",line);
           }  
         
         File m=new File("/mnt/sdcard/workspace/xmlfiles/"+Options.pckgname+"/share.xml");
         javaText=new StringBuilder();
                  
                  
                   br1=new BufferedReader(new FileReader(m));
                  
                  while ((line = br1.readLine()) != null) {  
                      javaText.append(line);  
                      javaText.append('\n'); 
                      
                      Log.i("the share.xml  is",line);
                    }  
         

         File manifest1=new File(openJavaFile);
javaText=new StringBuilder();
         
         
         BufferedReader br2=new BufferedReader(new FileReader(manifest1));
         
         while ((line = br2.readLine()) != null) {  
             javaText.append(line);  
             javaText.append('\n'); 
             
             Log.i("the java is",line);
           }  
         
         File manifest2=new File("/mnt/sdcard/workspace/"+Options.openProjnm+"/res/layout/main.xml");
 BufferedReader br3=new BufferedReader(new FileReader(manifest2));
         
         while ((line = br3.readLine()) != null) {  
             javaText.append(line);  
             javaText.append('\n'); 
             
             Log.i("the main is",line);
           }  
         
         genFile=openJavaFile.replaceFirst("src","gen");
         genFile=genFile.replaceFirst("/"+Options.openProjnm+".java","");
         
         Log.i("the gen fie is",genFile);
         
         }  
        
        catch(FileNotFoundException e) {
     	   Log.i("exception in JavaFile.java is ",e.toString());
        }
            
        
        catch(IOException e) {
     	   Log.i("exception in JavaFile.java is ",e.toString());
        }
         
     	
	}
	
	  public boolean isNotNull(String sss,String ss){
	    	 if((sss.equals(""))||(ss.equals(""))){
	    		 alert("Field is empty!!!");
	    		 return false;
	    	 }
	    	 else
	    		 return true;
	     }
	  
	  
	public void alert(String error){
 		AlertDialog.Builder msg=new AlertDialog.Builder(this);
	    	
	    	msg.setMessage(error).setCancelable(false)
	    	.setPositiveButton("ok",new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface d,int id) {
	    		
	    			d.dismiss();
	    			
	    		}
	    	});
	    	msg.show();
 	}
	
	
	public void onActivityPlus(View v) {
		final Dialog addAct=new Dialog(this);
		addAct.setContentView(R.layout.addactivity);
		
		Button btn1=(Button)addAct.findViewById(R.id.okactivity);
		addAct.show();
		btn1.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				
				EditText et=(EditText)addAct.findViewById(R.id.activtyname);
				String actName=et.getText().toString();
				if(isNotNull(actName,"abc"))
				addActivity(actName);
				addAct.dismiss();
			}
		});
		
	}
	
	public void addActivity(String activityName) {
		
		int pos=0;
		String activityString=new String(openJavaFile);
		activityString=activityString.replace(Options.openProjnm+".java",activityName+".java");
		Log.i("the name of new activity",activityString);
		
		File create=new File(activityString);
		try {
		if(!create.exists()){
			create.createNewFile();
			File manifestFile=new File("/mnt/sdcard/workspace/"+Options.openProjnm+"/AndroidManifest.xml");
			
           DocumentBuilderFactory docBuildFactory=DocumentBuilderFactory.newInstance();
        	
        	DocumentBuilder docBuilder=docBuildFactory.newDocumentBuilder();
        	
        	Document doc=docBuilder.parse(manifestFile);
        	Element root=doc.getDocumentElement();
        	NodeList manifestChild=root.getChildNodes();
        	
        	for(int i=0;i<manifestChild.getLength();i++) {
        		if((manifestChild.item(i).getNodeType()==Node.ELEMENT_NODE)&&(manifestChild.item(i).getNodeName().equals("application"))){
        			pos=i;
        			break;
        		}
        	}
        	Element applicationNode=(Element)manifestChild.item(pos); 
        	Element newActivity=doc.createElement("activity");
        	applicationNode.appendChild(newActivity);
        	newActivity.setAttribute("android:name","."+activityName);
        	
        	TransformerFactory transFactory=TransformerFactory.newInstance();
        	Transformer transformer=transFactory.newTransformer();
        	
        	
        	Properties outFormat = new Properties();
        	outFormat.setProperty(OutputKeys.INDENT, "yes");
        	transformer.setOutputProperties(outFormat);
            
        	StringWriter sw = new StringWriter(); 
            StreamResult result = new StreamResult(manifestFile); 
            DOMSource source = new DOMSource(doc); 
            transformer.transform(source, result);
            
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
	    	
        	builder.setMessage(activityName+".java created ").setCancelable(true)
        	.setPositiveButton("OK",new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface d,int id) {
        			//saveToXmlFile();
        			d.dismiss();
        			
        		}
        	});
		}
		}
		catch(SAXException e) { 
            e.printStackTrace();             
         } 
         catch(IOException e) { 
            e.printStackTrace();             
         } 
         catch(ParserConfigurationException e) { 
           e.printStackTrace();             
         } 
    	catch (TransformerConfigurationException e) {
    		Log.i("TransformerConfigurationException",e.toString());
    	}	
    	catch (TransformerException e) {
    		Log.i("TransformerException",e.toString());
    	}	
		catch(Exception e) {
			Log.i("the exception in javafile.java",e.toString());
			e.printStackTrace();
			
			}
		
	}
	
	
	
	
	public void onActivitySwitch(View v) {
		final String pathToLayout=openJavaFile.replace("/"+Options.openProjnm+".java","");
		
		File pathTolayoutFiles=new File(pathToLayout);
		String[] layoutFiles=pathTolayoutFiles.list();
		
		final Dialog lay=new Dialog(this);
		lay.setTitle("Select actvivty file");
		LinearLayout ll=new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		
		for(int i=0;i<layoutFiles.length;i++) {
			Button btn=new Button(this);
			btn.setText(layoutFiles[i]);
			btn.setOnClickListener(new OnClickListener() {
				public void onClick(View v){
					Button b=(Button)v;
					switchActivities(b.getText().toString(),pathToLayout);
					lay.dismiss();
					
				}
			});
			ll.addView(btn);
		}
	    
	    
	    lay.setContentView(ll);
		
		lay.show();
	}
	
	
	public void switchActivities(String activityName,String path){
		path=path+"/"+activityName;
		File openActivity=new File(path);
		try{
		BufferedReader br=new BufferedReader(new FileReader(openActivity));
		String line;
		StringBuilder activityText=new StringBuilder();
		 while ((line = br.readLine()) != null) {  
			 activityText.append(line);  
			 activityText.append('\n'); 
             
            // Log.i("the edittext is",line);
           } 
		 Editable clear=javaEt.getText();
		 currentActivity=new String(activityName);
		 clear.clear();
		 javaEt.setText(clear);
		 javaEt.setText(activityText);
		}
		 catch(FileNotFoundException e){
			 Log.e("the exception is",e.toString());
		 }
		catch(IOException e){
			 Log.e("the exception is",e.toString());
		 }
	
	
	}
	
	public void onSave(View v){
		
		 
		currentActivity=currentActivity.replace(".java","");
		
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
    	
    	builder.setMessage("Do you want to save changes to  "+currentActivity+"?").setCancelable(true)
    	.setPositiveButton("yes",new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface d,int id) {
             savingContents();
    			d.dismiss();
    			
    		}
    	})
    	.setNegativeButton("No",new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface d,int id) {
    			
    			d.dismiss();
    			
    		}
    	});
    	
    	//AlertDialog alert=builder.create();	
	    builder.show();
	}
	   public void savingContents()   
	   {
	    try {
	    String savePath=openJavaFile.replace("/"+Options.openProjnm+".java","");
		savePath=savePath+"/"+currentActivity+".java";
		Log.i("the path of current activity is",savePath);
		String javaContents=javaEt.getText().toString();
		
       File saveJavaFile=new File(savePath);
    	
    	FileOutputStream f0=new FileOutputStream(saveJavaFile);
    	
    	byte[] buff=javaContents.getBytes();
    	f0.write(buff);
    	f0.flush();
    	f0.close();
		}
		catch(FileNotFoundException e){
			Log.e("the exception in onsave() in javaFile.java",e.toString());
			
		}
		catch(IOException e){
			 Log.e("the exception is  onsave() in javaFile.java",e.toString());
		 }
		
	}
	
	
	
	
	
	
	
	
	
	public void onRun(View v) {
		try {
			File errLog=new File("/mnt/sdcard/logData/native_stderr.txt");
			PrintStream pr=new PrintStream(new FileOutputStream(errLog));
			System.setErr(pr);
			
			final Dialog res=new Dialog(this);
			res.setTitle("Results");
			res.setContentView(R.layout.results);
			TextView showResults=(TextView)res.findViewById(R.id.resultsdialog);
			Log.i("the log of the results text view",showResults.toString());
		//res.show();
			
		int re;
		String cmd=new String(" p -m -J /mnt/sdcard/workspace/"+Options.openProjnm+"/gen -M /mnt/sdcard/workspace/"+Options.openProjnm+"/AndroidManifest.xml -S /mnt/sdcard/workspace/"+Options.openProjnm+"/res  -I /mnt/sdcard/android.jar -f -F /mnt/sdcard/workspace/"+Options.openProjnm+"/bin/a.apk");
		IDE ide=new IDE();
		re=ide.fnAapt(cmd);
		
		if(re == 0)
		{
		 int com=99;
		 
		 
		 showResults.append("Aapt tool Called \n");
		 
		 
		 
		 
		 
		 String arg=new String("-verbose -deprecation -extdirs \"\" -1.5 -bootclasspath /mnt/sdcard/android.jar:/mnt/sdcard/libs/bsh.jar:/mnt/sdcard/libs/dx_ta.jar:/mnt/sdcard/libs/ecj.jar:/mnt/sdcard/libs/sdklib_ta.jar:/mnt/sdcard/libs/zipsigner-lib_all.jar:/mnt/sdcard/libs/androidprefs.jar -classpath /mnt/sdcard/workspace/"+Options.openProjnm+"/src:/mnt/sdcard/workspace/"+Options.openProjnm+"/gen -d /mnt/sdcard/workspace/"+Options.openProjnm+"/bin/  "+openJavaFile+"");
		 IDE ide1= new IDE();
		 com=ide1.fnCompile(arg);
		 
		 if((com==0)||(com==1)) {
			 
			 showResults.append("Aapt tool successfull \n");
			 showResults.append("Compiler called \n");
			 
			 
			 
			 
			 int dexfy=99;
			 String dex=new String("--dex --output=/mnt/sdcard/workspace/"+Options.openProjnm+"/bin/classes.dex  /mnt/sdcard/workspace/"+Options.openProjnm+"/bin  /mnt/sdcard/workspace/"+Options.openProjnm+"/libs");  
			 IDE ide2=new IDE();
			 dexfy=ide2.fnDx(dex);
		 
		 
		 if(dexfy==0) {
			 
			 
			 
			 showResults.append("Compilation successfull \n");
			 showResults.append("dex tool called \n");
			 
			 
			  int apkunsngd=99;
			 String apk_unsgnd=new String("/mnt/sdcard/workspace/"+Options.openProjnm+"/bin/"+Options.openProjnm+"/"+Options.openProjnm+".apk.unsigned -u -z  /mnt/sdcard/workspace/"+Options.openProjnm+"/bin/a.apk"+"  -f /mnt/sdcard/workspace/"+Options.openProjnm+"/bin/classes.dex  -rf  /mnt/sdcard/workspace/"+Options.openProjnm+"/src  -rj  /mnt/sdcard/workspace/"+Options.openProjnm+"/libs");
			 IDE ide3=new IDE();
			 apkunsngd=ide3.fnApkBuilder(apk_unsgnd);
			 
			 if(apkunsngd==0) {
				 int apkSignd=99;
				 showResults.append("dex tool successfull \n");
				 showResults.append("apk tool called  \n");
				 
				 String apk_Sgnd=new String("-M testkey -I /mnt/sdcard/workspace/"+Options.openProjnm+"/bin/"+Options.openProjnm+"/"+Options.openProjnm+".apk.unsigned -O /mnt/sdcard/workspace/"+Options.openProjnm+"/bin/"+Options.openProjnm+".apk");
				 IDE ide4=new IDE();
				 apkSignd=ide4.fnSignApk(apk_Sgnd);
				 if(apkSignd==0) {
					 showResults.append("apk tool successfull  \n");
					 showResults.append("YOUR APP CREATED SUCCESSFULLY ");
					 res.show();
					 
				 }
				 else {
					 showResults.append("apk tool failed: probable cause might be due to not well formed xml files  \n");
					 res.show();
					 
				 }
					 
			 }
			 else{
				 showResults.append("The unsigning of the apk failed \n");
				 res.show();
			 }
		 }
		 else {
			 showResults.append("The dex tool has failed  \n");
			 res.show();
			 
		 }
		 }
		 else {
			 showResults.append("There are some errors in your project \n");
			 displayError(showResults);
			 res.show();
			 
		 }
		}
		else{
			showResults.append("Aapt tool failed \n");
			 
			 res.show();
		}
	   }
		
		
		catch(FileNotFoundException e){
		   Log.e("ecxeption in oNrun in JavaFile",e.toString());
	   }
	}
	
	
	
	public void displayError(TextView tv){
		String errors=null;
		try {
		File errorLog=new File("/mnt/sdcard/logData/native_stderr.txt");
		BufferedReader br=new BufferedReader(new FileReader(errorLog));
		String line;
		
		StringBuilder errorText=new StringBuilder();
		
		while(((line = br.readLine()) != null)){
			errorText.append(line);
			errorText.append('\n');
			
		}
		tv.setText(errorText);
		
		}
		catch(FileNotFoundException e){
			Log.e("ecxeption in displayError() in JavaFile",e.toString());
		}
		catch(IOException e){
			Log.e("ecxeption in displayError() in JavaFile",e.toString());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//===================================================================
	  public static void fnLog (String level, String msg)
	//===================================================================
	  {
//	    if (G.oSet.iLogLevel==0) return; // user wants no logging
	    Time t = new Time();
	    t.setToNow();
	    msg = t.format("%Y-%m-%d %H:%M:%S") + " "+msg;
	/*    if ((level=="v")&&(G.oSet.iLogLevel>=5)) Log.v(G.stProgramName, msg);
	    if ((level=="d")&&(G.oSet.iLogLevel>=4)) Log.d(G.stProgramName, msg);
	    if ((level=="i")&&(G.oSet.iLogLevel>=3)) Log.i(G.stProgramName, msg);
	    if ((level=="w")&&(G.oSet.iLogLevel>=2)) Log.w(G.stProgramName, msg);
	    if ((level=="e")&&(G.oSet.iLogLevel>=1)) Log.e(G.stProgramName, msg); */
	    Log.e("Log:", msg);
	  } //fnLog
//===================================================================
 public static boolean fnMakeLogDir (boolean showToast)
//===================================================================
{
	 File dir;
	    boolean ok = false;
	    try
	    {
	      dir = new File("/mnt/sdcard/logData");
	      if (!dir.exists()) dir.mkdir();
	      if (dir.isDirectory()) ok = true;
	    }
	    catch (Exception e)
	    {
	      ok = false;
	    }
//	    fnCheckWorkDir(showToast);
	    return ok;
	} // fnMakeLogDir
//===================================================================
 public static boolean fnCheckWorkDir (boolean showToast)
//===================================================================
 {
   boolean ok = true;
   if (! new File("Location to Working Directory").exists()) 
   {
     ok = false;
     JavaFile.fnLog("w", "Mandatory working directory /mnt/sdcard/logData/ does not exist.");
//     if (showToast) G.fnToast(R.string.msg_workdir_missing,15000);
   }
   return ok;
 }//fnCheckWorkDir

}