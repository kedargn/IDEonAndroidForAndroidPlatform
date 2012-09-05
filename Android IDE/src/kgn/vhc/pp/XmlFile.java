package kgn.vhc.pp;


import android.app.Activity;
import org.w3c.dom.Node;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.text.Editable;
import android.util.Log;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import android.widget.Toast;
import android.widget.EditText;
import android.view.View;
import android.view.WindowManager;
import android.app.Dialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import java.io.FileOutputStream;
import java.util.Properties;
import android.os.CountDownTimer;
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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class XmlFile extends Activity {
	static boolean flag=false;
	 EditText xmlEt	;
	 File f;
	 StringBuilder xmlText;
	 String openXmlFile,currentLayout;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xmlfile);
         xmlEt=(EditText)findViewById(R.id.xmltextarea);
        //flag=false;
        
        Log.i("proj opened name is",Options.openProjnm);
     openXmlFile=Options.root+"/workspace/"+Options.openProjnm+"/res/layout/main.xml";
    Log.i("the open file path is",openXmlFile);
    flag=false;
     f=new File(openXmlFile);
     refreshXmlTab();
    
	}
	
	@Override
	public void onResume() {
		super.onResume();
		flag=false;
		refreshXmlTab();
	}
	
	
    public void refreshXmlTab() {
   try {
   
    String line;
     xmlText=new StringBuilder();
    
    currentLayout="main";
    BufferedReader br=new BufferedReader(new FileReader(f));
    
    while ((line = br.readLine()) != null) {  
        xmlText.append(line);  
        xmlText.append('\n'); 
        
        Log.i("the edittext is",line);
    }  
    xmlEt.setText(xmlText);
    }   
   catch(FileNotFoundException e) {
	   Log.i("exception in XmlFile.java is ",e.toString());
   }
       
   
   catch(IOException e) {
	   Log.i("exception in XmlFile.java is ",e.toString());
   }
    
  }
    
    public void onSave(View v) {
    	//Dialog saveConfirm=new Dialog(this);
    	
    	Log.i("yes","entered onSave");
    	AlertDialog.Builder builder=new AlertDialog.Builder(this);
    	    	
    	builder.setMessage("Do you want to save "+currentLayout+"?").setCancelable(true)
    	.setPositiveButton("yes",new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface d,int id) {
    			saveToXmlFile();
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
    
    public void saveToXmlFile() {
    	
    	try {
    	
    	String xmlContent;
    	EditText xmlCntnt=(EditText)findViewById(R.id.xmltextarea);
    	xmlContent=xmlCntnt.getText().toString();
    	
    	File saveFile=new File(Options.root+"/workspace/"+Options.openProjnm+"/res/layout/"+currentLayout+".xml");
    	
    	FileOutputStream f0=new FileOutputStream(saveFile);
    	
    	byte[] buff=xmlContent.getBytes();
    	f0.write(buff);
    	f0.flush();
    	f0.close();
    	
    	
        AlertDialog.Builder msg=new AlertDialog.Builder(this);
    	
    	msg.setMessage("Changes have been saved").setCancelable(false)
    	.setPositiveButton("ok",new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface d,int id) {
    		
    			d.dismiss();
    			
    		}
    	});
    	msg.show();
    	
    	}
    	catch(FileNotFoundException e) {
    		
    	}
    	
    	catch(IOException e)  {
    		
    	}
    	
    }
    
    
    public void onTableLayout(View v) {
    	int pos=0;
    	try {
        	File parseFile=new File(Options.root+"/workspace/"+Options.openProjnm+"/res/layout/main.xml");
        	Log.i("abs path is(Widgets.java)",parseFile.getAbsolutePath());
        	
        	DocumentBuilderFactory docBuildFactory=DocumentBuilderFactory.newInstance();
        	
        	DocumentBuilder docBuilder=docBuildFactory.newDocumentBuilder();
        	
        	Document doc=docBuilder.parse(parseFile);
        	
        	Element root=doc.getDocumentElement();
            NodeList child=root.getChildNodes();   
            
      /*      for(int i=0;i<child.getLength();i++) {
            	if(child.item(i).getNodeType()==Node.ELEMENT_NODE) {
            		pos=+1;
            	}
            }*/
            Log.i("the pos of root",""+pos);
       //  root=(Element)child.item(pos);
         Log.i("the root is",root.getNodeName());
         Element tabLayt=doc.createElement("TableLayout");
         tabLayt.setAttribute("android:layout_width", "fill_parent");
         tabLayt.setAttribute("android:layout_height", "wrap_content");
         root.appendChild(tabLayt);
         TransformerFactory transFactory=TransformerFactory.newInstance();
     	Transformer transformer=transFactory.newTransformer();
     	
     	
     	Properties outFormat = new Properties();
     	outFormat.setProperty(OutputKeys.INDENT, "yes");
     	transformer.setOutputProperties(outFormat);
         
     	StringWriter sw = new StringWriter(); 
         StreamResult result = new StreamResult(parseFile); 
         DOMSource source = new DOMSource(doc); 
         transformer.transform(source, result);
         
         
        
         String xmlString = sw.toString();
         
         Log.i("the parsed xml file is ",xmlString);
         
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
    }
    
    public void onPlusRow(View v) {
    	flag=true;
    	int pos=0;
    	try {
        	File parseFile=new File(Options.root+"/workspace/"+Options.openProjnm+"/res/layout/main.xml");
        	Log.i("abs path is(Widgets.java)",parseFile.getAbsolutePath());
        	
        	DocumentBuilderFactory docBuildFactory=DocumentBuilderFactory.newInstance();
        	
        	DocumentBuilder docBuilder=docBuildFactory.newDocumentBuilder();
        	
        	Document doc=docBuilder.parse(parseFile);
        	
        	Element root=doc.getDocumentElement();
            NodeList child=root.getChildNodes();   
 
            for(int i=0;i<child.getLength();i++) {
            	if(child.item(i).getNodeType()==Node.ELEMENT_NODE){
            		if(child.item(i).getNodeName().equals("TableLayout")){
            			pos=i;
            			
            		}
            	}
            }	if(pos>0)
            		{
            	root=(Element)child.item(pos);
                
                Element tableRow=doc.createElement("TableRow");
                root.appendChild(tableRow);
                
                
             TransformerFactory transFactory=TransformerFactory.newInstance();
         	Transformer transformer=transFactory.newTransformer();
         	
         	
         	Properties outFormat = new Properties();
         	outFormat.setProperty(OutputKeys.INDENT, "yes");
         	transformer.setOutputProperties(outFormat);
             
         	StringWriter sw = new StringWriter(); 
             StreamResult result = new StreamResult(parseFile); 
             DOMSource source = new DOMSource(doc); 
             transformer.transform(source, result);
            
            			final Toast toast;
                        toast=Toast.makeText(this,"select widgets that are to be added to table row ",Toast.LENGTH_SHORT); 
                        //toast.setView(v);
                        toast.show();
                        XmlFile.flag=true;
                        Log.i("the flag value in XmlFIle.java",""+flag);
                        new CountDownTimer(2000, 1000)
                        {

                            public void onTick(long millisUntilFinished) {toast.show();}
                            public void onFinish() {toast.show();}

                        }.start();
            			Intent intent=new Intent();
            			intent.setClass(this, Widgets.class);
            			startActivity(intent);
            			          			
            		}
            		//flag=false;
                            
            	
            
             
        
         //String xmlString = sw.toString();
         
        // Log.i("the parsed xml file is ",xmlString);
         //flag=false;
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

    }
    
    
    public void onLayoutPlus(View v){
    	
    	final Dialog addAct=new Dialog(this);
		addAct.setContentView(R.layout.addactivity);
		((TextView)addAct.findViewById(R.id.alter)).setText("Enter the Layout file name");
		Button btn1=(Button)addAct.findViewById(R.id.okactivity);
		addAct.show();
		btn1.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				
				EditText et=(EditText)addAct.findViewById(R.id.activtyname);
				String lName=et.getText().toString();
				if(isNotNull(lName,"abc"))
				addLayout(lName);
				addAct.dismiss();
			}
		});
			
    	
    
   }
    
    
    public void addLayout(String layoutName){
    	String layoutPath=Options.root+"/workspace/"+Options.openProjnm+"/res/layout/"+layoutName+".xml";
    	File layoutFile=new File(layoutPath);
    	try {
    	if(!layoutFile.exists()){
    		layoutFile.createNewFile();
    		
    		AlertDialog.Builder builder=new AlertDialog.Builder(this);
	    	
        	builder.setMessage(layoutName+".xml created ").setCancelable(true)
        	.setPositiveButton("OK",new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface d,int id) {
        			//saveToXmlFile();
        			d.dismiss();
        			
        		}
        	});
    	  }
    	}
    	catch(IOException e){
    		Log.e("the Exception in addLayout(String) in XmlFile.java",e.toString());
    	}
    	
      }
    
    
    public void onSwitchLayout(View v){
    	String pathOfLayouts=Options.root+"/workspace/"+Options.openProjnm+"/res/layout";
    	File layoutPath=new File(pathOfLayouts);
    	String[] layoutFiles=layoutPath.list();
    	Log.i("the length of layoutPath.list()",""+layoutFiles.length);
    	LinearLayout ll=new LinearLayout(this);
    	ll.setOrientation(LinearLayout.VERTICAL);
    	final Dialog switchLay=new Dialog(this);
    	switchLay.setTitle("Select layout file");
    	
    	for(int i=0;i<layoutFiles.length;i++) {
    		Button btn=new Button(this);
    		btn.setText(layoutFiles[i]);
    		btn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v){
					Button b=(Button)v;
					switchLayouts(b.getText().toString());
					switchLay.dismiss();
					
				}
			});
			ll.addView(btn);
		}
    	switchLay.setContentView(ll);
		
    	switchLay.show();
    	}
    
     public void switchLayouts(String layoutName){
    	 String pathToLayout=Options.root+"/workspace/"+Options.openProjnm+"/res/layout/"+layoutName;
    	 File lyt=new File(pathToLayout);
    	 try{
    			BufferedReader br=new BufferedReader(new FileReader(lyt));
    			String line;
    			StringBuilder layoutText=new StringBuilder();
    			 while ((line = br.readLine()) != null) {  
    				 layoutText.append(line);  
    				 layoutText.append('\n'); 
    	             
    	            // Log.i("the edittext is",line);
    	           } 
    			 Editable clear=xmlEt.getText();
    			 currentLayout=new String(layoutName);
    			 clear.clear();
    			 xmlEt.setText(clear);
    			 xmlEt.setText(layoutText);
    			}
    			 catch(FileNotFoundException e){
    				 Log.e("the exception is",e.toString());
    			 }
    			catch(IOException e){
    				 Log.e("the exception is",e.toString());
    			 }
    		
     }
     
    
     
     public void onStore(View v)
     {
    	 final Dialog d=new Dialog(this);
    	 d.setTitle("Enter the Key Value Pairs");
    	 
    	 d.setContentView(R.layout.set);
    	 
    	 Button b=(Button)d.findViewById(R.id.setok);
    	 b.setOnClickListener(new View.OnClickListener(){
    		 public void onClick(View v){
    			 EditText et=(EditText)d.findViewById(R.id.key);
    		     String keyValue=et.getText().toString();
    		     EditText et1=(EditText)d.findViewById(R.id.value);
    		     String Value=et1.getText().toString();
    		     Log.i("the value of key value is",keyValue.toString()+"  "+Value.toString());
    		     if(isNotNull(keyValue,Value)){
    		     enterXml(keyValue,Value);
    		     }
    		     d.dismiss();
    		 }
    	 });
    	 WindowManager.LayoutParams lp=new WindowManager.LayoutParams();
     	lp.width=WindowManager.LayoutParams.FILL_PARENT;
     	lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
     	d.getWindow().setAttributes(lp);
    	 d.show();
     }
     
     public boolean isNotNull(String sss,String ss){
    	 if((sss.equals(""))||(ss.equals(""))){
    		 alert("Fields are empty!!!");
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
     
     public String getPckName(){
    	
    	 String pckName=new String();
    	 String t=Options.root+"/workspace/"+Options.openProjnm+"/src";
    	 File pck=new File(t);
    	 String[] list=pck.list();
    	 int len=list.length;
    	 while(len!=0){
    		 if(!(pck.isFile())){
    		 t=t+"/"+list[0];
    		 pckName=pckName+list[0];
    		 pck=new File(t);
    		 Log.i("package name name for key value is",pckName);
    		 Log.i("the path for paackage",pck.getAbsolutePath());
    		 len=pck.list().length;
    		 list=pck.list();
    		 if(len>0){
    			 File t1=new File(t+"/"+list[0]);
    			 Log.i("the path for paackage plus 1",t1.getAbsolutePath());
    			 if(!t1.isFile()){
    			 pckName=pckName+".";
    			 
    		 }
    			 if(t1.isFile())
    				 break;
    		 }	
    			 else break;
    	  } 
    		 else break;
    	 }
    	 Log.i("package name name for key value is",pckName);
    	 return pckName;
     
     } 
     public void enterXml(String s1,String s2){
    	 
    	 try{
    		 getPckName();
    	 File xml1=new File(Options.root+"/xmlfiles/"+getPckName().toString().trim()+"/share.xml");
    	 DocumentBuilderFactory docBuildFactory=DocumentBuilderFactory.newInstance();
     	
     	DocumentBuilder docBuilder=docBuildFactory.newDocumentBuilder();
     	
     	Document doc=docBuilder.parse(xml1);
     	
     	Element root=doc.getDocumentElement();
     	Log.i("the root of share.xml is",root.getNodeName());
     	Element keyElement=doc.createElement(s1);
     	root.appendChild(keyElement);
     	
     	keyElement.appendChild(doc.createTextNode(s2));
     	
     	 TransformerFactory factory = TransformerFactory.newInstance();
         Transformer transformer = factory.newTransformer();
         Properties outFormat = new Properties();
         outFormat.setProperty(OutputKeys.INDENT, "yes");
      
         transformer.setOutputProperties(outFormat);
         StringWriter sw = new StringWriter(); 
         StreamResult result = new StreamResult(xml1); 
         DOMSource source = new DOMSource(doc); 
         transformer.transform(source, result);
     	
     	}catch(SAXException e) { 
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
    	 
     }
     
    }
     
    
