package kgn.vhc.pp;

import java.io.File;
import java.io.StringWriter;

import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.Properties;

import android.graphics.Color;
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

import org.w3c.dom.Node;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import org.w3c.dom.NodeList;
import android.view.View;
import android.app.Dialog;


public class XmlContents extends Activity implements OnClickListener {
	int x=1,t=1,modifyPostn;
	String sltdBtn[];
	String childElementsName,childText;
	String nameOfAttr;
	Node resNode;
	NodeList widgetsNodes;
	 Node[] n;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.xmlcontents);
        xmlContents();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		setContentView(R.layout.xmlcontents);
		x=1;
		LinearLayout ll= (LinearLayout)findViewById(R.id.xmlcontentsll);
		ll.removeAllViews();
		xmlContents();
	}
	
	public void xmlContents() {
		  x=1;
        
        try {
        	x=1;
        	File parseFile=new File(Options.root+"/workspace/"+Options.openProjnm+"/res/layout/main.xml");
        	Log.i("abs path is(Widgets.java)",parseFile.getAbsolutePath());
        	
        	DocumentBuilderFactory docBuildFactory=DocumentBuilderFactory.newInstance();
        	
        	DocumentBuilder docBuilder=docBuildFactory.newDocumentBuilder();
        	
        	Document doc=docBuilder.parse(parseFile);
        	
        	Element root=doc.getDocumentElement();
           boolean b=false;
           
           Log.i("the root is, and has children or not",root.getNodeName()+"   "+b);
                  
            widgetsNodes=root.getChildNodes();
           LinearLayout ll= (LinearLayout)findViewById(R.id.xmlcontentsll);
           
           for(int i=0;i<widgetsNodes.getLength();i++) {
           if((widgetsNodes.item(i).getNodeType()==Node.ELEMENT_NODE)) {
        	   b=true;
        	   break;
        	   
           }
           }
           Log.i("the value of b!!!","   "+b+widgetsNodes.getLength());
           
           
           Log.i("the value of b","   "+b);
           
           if(!b) {
        	   
        	   TextView tv=new TextView(this);
        	   tv.setText("No added widgets");
        	   tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        	   ll.addView(tv);
           }
           
           else
           {
        	
        	   displayWdgts(ll,root);
        	   
        	    }
        	   
	}
        catch(SAXException e) { 
            e.printStackTrace(); 
            Log.i("the exception is(XmlContents.java)",e.toString());
         } 
         catch(IOException e) { 
        	 Log.i("the exception is(XmlContents.java)",e.toString());
        	 e.printStackTrace();             
         } 
         catch(ParserConfigurationException e) { 
           e.printStackTrace();             
           Log.i("the exception is(XmlContents.java)",e.toString());
         } 
    	

 }
	
	public void displayWdgts(LinearLayout ll,Element root) {
		   Log.i("all is well","executing");
		   NodeList widgetNodes=root.getChildNodes();
    	   for(int i=0;i<widgetNodes.getLength();i++) {
    		   Log.i("value of i",""+i);
               if((widgetNodes.item(i).getNodeType()==Node.ELEMENT_NODE)) {
            	   Log.i("type is and name","element"+widgetNodes.item(i).getNodeName());
            	   Node n=widgetNodes.item(i);
            	   childElementsName=n.getNodeName();
            	   childText=((Element)n).getAttribute("android:text");
            	   Button tv=new Button(this);
            	   display(tv,ll);
            	   if(n.hasChildNodes()){
            		   Log.i("child???","yes!!it has");
            		   displayWdgts(ll,(Element)n);
            	      }
            	    }  
                 }
               }
	
	
	public void display(Button tv,LinearLayout ll) {
		tv.setTextColor(Color.WHITE);
 	   tv.setBackgroundColor(Color.BLACK);
 	   tv.setOnClickListener(this);
 	   tv.setText((x++)+"."+childElementsName+"."+childText);
 	   tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
 	   ll.addView(tv);
 	   
	}
	
	
	public void onClick(View v) {
		
		Button selctdWdgt=(Button)v;
		Log.i("the seltd button is(button)",(String)selctdWdgt.getText());
		String s=(String)selctdWdgt.getText();
		sltdBtn=s.split("\\.");
		Log.i("th1 n 2 is",sltdBtn[0]+"  "+sltdBtn[1]);
		
		final Dialog dl=new Dialog(this);
		
		dl.setContentView(R.layout.modifyordelete);
		dl.setTitle("What do you want to do?");
		
		Button mfyBtn=(Button)dl.findViewById(R.id.modifycontents);
		Button delBtn=(Button)dl.findViewById(R.id.deletecontents);
		
		mfyBtn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				modify(0,null);
				dl.dismiss();
			}
		});
		
		delBtn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				deleteWidgets(v);
				dl.dismiss();
				
				
			}
		});
		dl.show();
	}
		
	
       public void modify(int flag,String value){
    	   try {
    		   Node n;
    		   String attrNames[];
    		   String attrValues[];
    		   
    		   
    		   modifyPostn=Integer.parseInt(sltdBtn[0]);
    		File parseFile=new File(Options.root+"/workspace/"+Options.openProjnm+"/res/layout/main.xml");
        	Log.i("abs path is(Widgets.java)",parseFile.getAbsolutePath());
        	
        	DocumentBuilderFactory docBuildFactory=DocumentBuilderFactory.newInstance();
        	
        	DocumentBuilder docBuilder=docBuildFactory.newDocumentBuilder();
        	
        	Document doc=docBuilder.parse(parseFile);
        	
        	Element root=doc.getDocumentElement();   
    	    t=1;
        	resNode=findIt(root);
        	Log.i("the resulting node is",resNode.getNodeName());
        	
        	if(flag==1) {
        		
        		((Element)resNode).setAttribute(nameOfAttr,value);
        		
        		
        		
        		TransformerFactory transFactory=TransformerFactory.newInstance();
            	Transformer transformer=transFactory.newTransformer();
            	
            	
            	Properties outFormat = new Properties();
            	outFormat.setProperty(OutputKeys.INDENT, "yes");
            	transformer.setOutputProperties(outFormat);
                
            	StringWriter sw = new StringWriter(); 
                StreamResult result = new StreamResult(parseFile); 
                DOMSource source = new DOMSource(doc); 
                transformer.transform(source, result);
                LinearLayout ll= (LinearLayout)findViewById(R.id.xmlcontentsll);
                ll.removeAllViews();
        		xmlContents();
        		
        	}
        	else {
        	NamedNodeMap attr=((Element)resNode).getAttributes();
        	int len=attr.getLength();
        	
        	attrNames=new String[len];
        	
        	Log.i("the length of attributes in",""+attr.getLength());
        
        	
        	for(int i=0;i<attr.getLength();i++){
        		 n=attr.item(i);
        		 
        	if(n.getNodeType()==Node.ATTRIBUTE_NODE){
        			attrNames[i]=n.getNodeName();
        			Log.i("the attributes are",attrNames[i]);
        		
        		}
        		 
        	}
        	
        /*	for(int i=0;i<attrNames.length;i++){
        	attrValues[i]=((Element)resNode).getAttribute(attrNames[i]);
        				
        	}*/
        	
        	final Dialog d=new Dialog(this);
        	d.setContentView(R.layout.attributes);
        	LinearLayout ln=(LinearLayout)d.findViewById(R.id.attributelayout);
        	d.setTitle("Attributes");
        	for(int i=0;i<attrNames.length;i++){
        		Button btn=new Button(this);
        		btn.setText(attrNames[i]);
        		ln.addView(btn);
        		btn.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						nameOfAttr=((Button)v).getText().toString();
						String attVal=((Element)resNode).getAttribute(nameOfAttr.trim());
						displayVal(attVal);
						d.dismiss();
					}
				});
        	}
        	d.show();
        	}
    	   }
    	   catch(ParserConfigurationException e) {
    		   Log.i("he exception is",e.toString());
    		   e.printStackTrace();
    	   }
    	   catch(SAXException e) { 
               e.printStackTrace(); 
               Log.i("the exception is(XmlContents.java)",e.toString());
            } 
            catch(IOException e) { 
           	 Log.i("the exception is(XmlContents.java)",e.toString());
           	 e.printStackTrace();             
            }   catch (TransformerConfigurationException e) {
        		Log.i("TransformerConfigurationException",e.toString());
        	}	
        	catch (TransformerException e) {
        		Log.i("TransformerException",e.toString());
        	}	
    	    
       }
       
       public void displayVal(String Value){
    	   
    	   final Dialog showValues=new Dialog(this);
    	   showValues.setContentView(R.layout.attval);
    	   TextView attrNm=(TextView)showValues.findViewById(R.id.attrtext);
    	   attrNm.setText(nameOfAttr);
    	 final  EditText et=(EditText)showValues.findViewById(R.id.attrval);
           et.setText(Value);
           Button btn=(Button)showValues.findViewById(R.id.attrok);
           btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				String chngdVal=et.getText().toString();
				//changeElement(chngdVal);
				modify(1,chngdVal);
				showValues.dismiss();
			}
		});
           showValues.show();
    	   
       }
       
      
	
	   public Node findIt(Element root){
		   int i;
		    
			NodeList childNodes=root.getChildNodes();
			//Node child=root.getFirstChild();
			for( i=0;i<childNodes.getLength();i++)  {
			if(childNodes.item(i).getNodeType()==Node.ELEMENT_NODE) {
				String node=childNodes.item(i).getNodeName();
				Log.i("the node is and t in findIt() is",node+"  "+t);
				if((t==modifyPostn)) {
					++t;
					resNode=childNodes.item(i);
					Log.i("the res node is",resNode.getNodeName()); 
		            break;	
		      }
				else {
					++t;
					if(childNodes.item(i).hasChildNodes()) {
						findIt((Element)childNodes.item(i));
					}
				}
			  }
			}
		   return resNode;
	   }
		
		public void deleteWidgets(View v) {
		final Dialog d=new Dialog(this);
		d.setTitle("Confirm");
		d.setContentView(R.layout.confirm);
		TextView confrmTxt=(TextView)d.findViewById(R.id.confirmtext);
		confrmTxt.setText("Do you want delete "+sltdBtn[1]);
		d.show();
		
		Button canclBtn=(Button)d.findViewById(R.id.confirmcancel);
		
		
		
		canclBtn.setOnClickListener(new OnClickListener() {      //onclick listener for cancel button
			public void onClick(View v) {
			d.dismiss();	
			}
		});
		
		Button okBtn=(Button)d.findViewById(R.id.confirmok);
		okBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				delWdgtXml();
				d.dismiss();
			}
		});
		
	}
	
	
	public void delWdgtXml()  {
		try {
			int postnChild=Integer.parseInt(sltdBtn[0]);
			Log.i("the postn is"," "+postnChild+sltdBtn[1]);
			
        	File parseFile=new File(Options.root+"/workspace/"+Options.openProjnm+"/res/layout/main.xml");
        	Log.i("abs path is(Widgets.java)",parseFile.getAbsolutePath());
        	
        	DocumentBuilderFactory docBuildFactory=DocumentBuilderFactory.newInstance();
        	
        	DocumentBuilder docBuilder=docBuildFactory.newDocumentBuilder();
        	
        	Document doc=docBuilder.parse(parseFile);
        	
        	Element root=doc.getDocumentElement();
        	NodeList childNodes=root.getChildNodes();
        	t=1;
        	Node delNode=deleteIt(root,postnChild);
        	//Log.i("the recvd delNode is",delNode.getNodeName());
        //	root=(Element)delNode.getParentNode();
        	//root.removeChild(delNode);
        	
        	//	Log.i("not deleted","the node is null");
        	//Node child=root.getFirstChild();
		 	
        	
  /*      	for(int i=0;i<childNodes.getLength();i++)  {
        	if(childNodes.item(i).getNodeType()==Node.ELEMENT_NODE) {
        		String node=childNodes.item(i).getNodeName();
        		Log.i("the node is and t is",node+"  "+t);  
        		deleteIt(root,t,postnChild); 
        		if((t==postnChild)) {
        			
        			Log.i("res",sltdBtn[1]+"will be deleted");
        			Node deltdNode=root.removeChild(childNodes.item(i));   */
        			TransformerFactory transFactory=TransformerFactory.newInstance();
                	Transformer transformer=transFactory.newTransformer();
                	
                	
                	Properties outFormat = new Properties();
                	outFormat.setProperty(OutputKeys.INDENT, "yes");
                	transformer.setOutputProperties(outFormat);
                    
                	StringWriter sw = new StringWriter(); 
                    StreamResult result = new StreamResult(parseFile); 
                    DOMSource source = new DOMSource(doc); 
                    transformer.transform(source, result);
                    LinearLayout ll= (LinearLayout)findViewById(R.id.xmlcontentsll);
                    ll.removeAllViews();
            		xmlContents();
            
        	   
		 	
        	//child=child.getNextSibling();
        	//Log.i("the first chils is",child.getNodeName());
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


public Node deleteIt(Element root,int postnChild) {
    int i;
    Node delNode=null;
	NodeList childNodes=root.getChildNodes();
	//Node child=root.getFirstChild();
	
	
	for( i=0;i<childNodes.getLength();i++)  {
	if(childNodes.item(i).getNodeType()==Node.ELEMENT_NODE) {
		String node=childNodes.item(i).getNodeName();
		Log.i("the node is and t is",node+"  "+t);
		if((t==postnChild)) {
			++t;
			Log.i("Deleted child is",node);
			Log.i("res",sltdBtn[1]+"will be deleted");
			delNode=childNodes.item(i);
			Log.i("delNode is",delNode.getNodeName());
			root=(Element)delNode.getParentNode();
        	root.removeChild(delNode);
        	
			 //return(root.removeChild(childNodes.item(i)));
            break;	
      }
		else {
			++t;
			if(childNodes.item(i).hasChildNodes()) {
				root=(Element)childNodes.item(i);
				deleteIt(root,postnChild);
			}
		}
	  }
	}
	return(delNode);
   }
}