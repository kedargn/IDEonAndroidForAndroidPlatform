package kgn.vhc.pp;



import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory ;
import android.content.res.XmlResourceParser;
import javax.xml.transform.TransformerFactory; 
import javax.xml.transform.Transformer ;
import javax.xml.transform.dom.DOMSource ;
import javax.xml.transform.stream.StreamResult;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.Random;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.EditText;
import android.view.View.OnClickListener;
import java.io.File;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import java.io.StringWriter;
import javax.xml.transform.TransformerException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import javax.xml.transform.OutputKeys;
import android.media.MediaRecorder.OutputFormat;
import org.w3c.dom.Node;
public class Widgets extends Activity  {
	int temp=0,x=1,orderInt=1,t=1;
	static int id;
	int qtty,intMargin,intPadding;
	EditText xmlId;
 	EditText xmlWidth;
 	EditText xmlHeight;
 	EditText xmlPadding;
 	EditText xmlMargin;
 	EditText xmlText;
 	EditText xmlOnClick,xmlOrder,xmlQty;		
 			
 	String clkdBtnText;		
 			
 	 String id1,width,height,order,text,qty,padding,onClick;		
 			
 			
 			
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        setContentView(R.layout.widgets);
	}
     
        
	
	//onclick listener called through xml file
         public void wdgtProprts(View v)                 //TO SHOW THE DIALOG FOR WiDGET PRPERTIES
        {   ++id; 
        	
        	String element;
        
           Log.i("the view clicked is",v.toString());
           
            final Dialog wdgt=new Dialog(this);
            wdgt.setContentView(R.layout.wdgtproperties);
            wdgt.show();
        	
            xmlId=(EditText)wdgt.findViewById(R.id.idetprop);
        	 xmlWidth=(EditText)wdgt.findViewById(R.id.widthetprop);
        	 xmlHeight=(EditText)wdgt.findViewById(R.id.heightetprop);
        	 xmlPadding=(EditText)wdgt.findViewById(R.id.paddingetprop);
        	// xmlMargin=(EditText)wdgt.findViewById(R.id.marginetprop);
             xmlText=(EditText)wdgt.findViewById(R.id.textetprop);
        	 xmlOnClick=(EditText)wdgt.findViewById(R.id.onclicketprop);
        	 xmlOrder=(EditText)wdgt.findViewById(R.id.orderetprop);
             xmlText=(EditText)wdgt.findViewById(R.id.textetprop);
             xmlQty=(EditText)wdgt.findViewById(R.id.qtyetprop);
        	 xmlId.setText("@+id/widget"+id);
        	
        	
        	Button okBtn=(Button)findViewById(R.id.defvalok);
        	
        	
        	Button bn=(Button)v;
        	Log.i("the text of btn",(String)bn.getText());
        	
        	 clkdBtnText=(String)bn.getText();
        	
        	
        	
        	try  {
        	XmlResourceParser defltval = getResources().getXml(R.xml.defaultvalues);
        	defltval.next();
        	String text;
        	int eventType=defltval.getEventType();
        	
        	
        	
        	if(eventType==XmlPullParser.START_DOCUMENT)
        	Log.i("event type",""+eventType);
        	
        	while((eventType!=XmlPullParser.END_DOCUMENT)) {
        		
        		defltval.next();
        		eventType=defltval.getEventType();
        		
        		if((eventType==XmlPullParser.START_TAG)) {
        			
        			element=defltval.getName();
        			
        			Log.i("ele name is",element.toString());
        			if(element.equals("layoutWidth")) {
        				defltval.next();
        				
        				xmlWidth.setText(defltval.getText());
        			}
        			
        			else if(element.equals("layoutHeight"))  {
        				defltval.next();
        				xmlHeight.setText(defltval.getText());
        			}
        			
        			else if(element.equals("padding"))  {
        				defltval.next();
        				xmlPadding.setText(defltval.getText());
        			}
        			
        			//else if(element.equals("margin"))  {
        				//defltval.next();
        				//xmlMargin.setText(defltval.getText());
        			//}
        			
        			else if(element.equals("textOfView"))  {
        				defltval.next();
        				xmlText.setText(defltval.getText());
        			}
        			
        			else if(element.equals("onClick")&&(!clkdBtnText.equals("TextView")))  {
        				defltval.next();
        				xmlOnClick.setText(defltval.getText());
        				//xmlOnClick.setFocusable(true);
        			}
        			else  {
        				xmlOnClick.setText("");
        				//xmlOnClick.setFocusable(false);
        			}
        			
        				
        			defltval.next();
        		}
        		
        	}
        }	
        
        	catch(XmlPullParserException e)
        	{
        		Log.i("the excptn in parser",e.toString());
        	}
        	catch(IOException e)
        	{
        		Log.i("the excptn in parser",e.toString());
        	}
        	catch(Exception e)
        	{
        		Log.i("the excptn in parser",e.toString());
        	}
        	
        	
        	
        
        
        Button onOkBtn=(Button)wdgt.findViewById(R.id.defvalok);
         
         onOkBtn.setOnClickListener(new OnClickListener(){
        	 
        	 
        	 public void onClick(View v) {
        		 try{
        		
        		 id1=xmlId.getText().toString();
        		 width=xmlWidth.getText().toString();
        		 height=xmlHeight.getText().toString();
        		 order=xmlOrder.getText().toString();
                 text=xmlText.getText().toString();
                 qty=xmlQty.getText().toString();
                 onClick=xmlOnClick.getText().toString();
                 padding=xmlPadding.getText().toString();
               
        		 wdgt.dismiss();
        		 
        		
        		 
        		 
        		 Log.i("the values recvd from xml r",id+width+height+order);
        		 doParsing();
        	 }catch(Exception e)
        	 {
        		Log.e("the Ecxeption is Widgets.java",e.toString()); 
        	 }
           }
         });
        
}

        public void doParsing() {
        	int pos=0;
        	
        	try {
        	File parseFile=new File(Options.root+"/workspace/"+Options.openProjnm+"/res/layout/main.xml");
        	Log.i("abs path is(Widgets.java)",parseFile.getAbsolutePath());
        	
        	DocumentBuilderFactory docBuildFactory=DocumentBuilderFactory.newInstance();
        	
        	DocumentBuilder docBuilder=docBuildFactory.newDocumentBuilder();
        	
        	Document doc=docBuilder.parse(parseFile);
        	
        	Element root=doc.getDocumentElement();
        	 NodeList child=root.getChildNodes();
        	 Log.i("flag valu in Widgets.java",""+XmlFile.flag);
        	 if(XmlFile.flag==true) {
        		 Log.i("inside ","inside table row wdgts");
             for(int i=0;i<child.getLength();i++) {
             	if(child.item(i).getNodeType()==Node.ELEMENT_NODE){
             		if(child.item(i).getNodeName().equals("TableLayout")){
             			pos=i;
             			Log.i("the pos ",""+pos);
             		}
             		
                  	}
             	
             }
             
             root=(Element)child.item(pos);
             child=null;
             child=root.getChildNodes();
             
             for(int i=0;i<child.getLength();i++) {
            	 if(child.item(i).getNodeType()==Node.ELEMENT_NODE){
              		if(child.item(i).getNodeName().equals("TableRow")){
                       pos=i;            	 
            	 
             }
           }
         }  	 root=(Element)child.item(pos);
               
         
         if(clkdBtnText.equals("RadioButton")) {
        	 createRadioBtn(doc, root, qty, order, null);
        	 
         }
         else {
        
        	 int intQty=1;
     		if(!qty.equals("")) {
               	 intQty=Integer.parseInt(qty);
               	}
 	       for(int i=0;i<intQty;i++) {         
        	 root=(Element)child.item(pos);
         
             Element childElement=doc.createElement(clkdBtnText);
         	root.appendChild(childElement);
         	
         	childElement.setAttribute("android:id",id1 );
         	childElement.setAttribute("android:layout_width",width );
         	childElement.setAttribute("android:layout_height",height);
         	childElement.setAttribute("android:text",text);
         	
       //  	childElement.setAttribute("android:padding",padding);
         	
         	if((!onClick.equals(""))&&(!onClick.equals("null"))){
         		childElement.setAttribute("android:onClick",onClick);
         	}
         			
              }
            }
        }
          // Log.i("the type of node",child.item(pos).getNodeName());
        	 // root=(Element)child.item(pos);
           else {
        	   root=doc.getDocumentElement();
        	NodeList n=root.getChildNodes();
        	
        	if(order.equals("")) {
        		Element childElement;
        	Log.i("the root is",""+root.getNodeName());
        	
        	Log.i("the root ele is(Widgets.java)",root.toString());
        	if(clkdBtnText.equals("RadioButton")){
        		 root=createRadioBtn (doc,root,qty,order,null);
        	}
        	else{
        		int intQty=1;
        		if(!qty.equals("")) {
                  	 intQty=Integer.parseInt(qty);
                  	}
        		for(int i=0;i<intQty;i++){
        	childElement=doc.createElement(clkdBtnText);
        	
        	
        	root.appendChild(childElement);
        	
        	childElement.setAttribute("android:id","@+id/widget"+id++ );
        	childElement.setAttribute("android:layout_width",width );
        	childElement.setAttribute("android:layout_height",height);
        	childElement.setAttribute("android:text",text);
        	
         	//childElement.setAttribute("android:padding",padding);
         	
         	if((!onClick.equals(""))&&(!onClick.equals("null"))){
         		childElement.setAttribute("android:onClick",onClick);
         	}
        		}
        	  }
        	}
        	else
        	{
        		root=doc.getDocumentElement();
            	 n=root.getChildNodes();
            	 orderInt=Integer.parseInt(order);
            	 x=1;
            	 Log.i("the order entered is",""+orderInt);
            	 
            	findWidgets(root,doc);
            	 
            	// Log.i("the value of t amd Node name",""+t+"  "+node.getNodeName());
            	
        	
              }
        	}
        	 
           
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
        	catch (Exception e){
        		Log.i("the exception is",e.toString());
        		e.printStackTrace();
        	}
        }

        
        public void findWidgets(Element root,Document doc) {
        	int i;
        
 		   Log.i("all is well","executing in findWidgets()");
 		   NodeList widgetNodes=root.getChildNodes();
 		   
     	   for(i=0;i<widgetNodes.getLength();i++) {
     		   Log.i("value of i in findWidgets()",""+i);
     		   
     		   
                if((widgetNodes.item(i).getNodeType()==Node.ELEMENT_NODE)) {
             	   Log.i("type is and name","element"+widgetNodes.item(i).getNodeName());
             	  Node n=widgetNodes.item(i);
             	  
             	   if(x==orderInt) {
             		   t=i;
             		   Log.i("found the widget in findWidgets() and t value is",""+x+" "+t);
             		  
                  	 if(clkdBtnText.equals("RadioButton")) {
                     	 createRadioBtn(doc, root, qty, order, widgetNodes.item(t));
                     	 
                      }
              		else{
              			int intQty=1;
                  		if(!qty.equals("")) {
                            	 intQty=Integer.parseInt(qty);
                            	}
              	       for(i=0;i<intQty;i++) {
              		Element childElement=doc.createElement(clkdBtnText);
                  	
              		
                  	childElement.setAttribute("android:id","@+id/widget"+id++);
                  	childElement.setAttribute("android:layout_width",width );
                  	childElement.setAttribute("android:layout_height",height);
                  	childElement.setAttribute("android:text",text);
                  	
                 //  	childElement.setAttribute("android:padding",padding);
                   	
                  	Log.i("the position of node(t) and node inserted to b4 is",""+t+widgetNodes.item(t));
                  	
                   	if((!onClick.equals(""))&&(!onClick.equals("null"))){
                   		childElement.setAttribute("android:onClick",onClick);
                   	}
                  	
                  	Node res=root.insertBefore((Node)childElement,widgetNodes.item(t));
              	       }
              		}
             		   
             		 return; // return widgetNodes.item(i);
             	   }
             	   ++x;
             	   
             	   
             	   
             	   if(n.hasChildNodes()){
             		   Log.i("child???","yes!!it has");
             		   findWidgets((Element)n,doc);
             	    }
             	    } 
                
                  }
     	           
                }
        
        
        
        
        
        public Element createRadioBtn(Document doc,Element root,String qty,String order,Node node) {
        	RadioGroup rg=new RadioGroup(this);
        	Element radioGrp=doc.createElement("RadioGroup");
        	int intOrder,intQty=1;
        	
        	
        	
        	if(!qty.equals("")) {
           	 intQty=Integer.parseInt(qty);
           	}
           	if(!order.equals("")) {
           	intOrder=Integer.parseInt(order);
           	}
           	
           	
        	radioGrp.setAttribute("android:id","@+id/widget"+id++ );
        	radioGrp.setAttribute("android:layout_width",width );
        	radioGrp.setAttribute("android:layout_height",height);
        	radioGrp.setAttribute("android:text",text);
        	
           //	radioGrp.setAttribute("android:padding",padding);
           	
        	if(order.equals("")){
        	root.appendChild(radioGrp);
        	}
        	else {
        		root.insertBefore((Node)radioGrp,node);
        	}
        		
        		
        	
        	
        	
        	  for(int i=0;i<intQty;i++) {
        		  id++;
        		  Element childEle;
        		  //RadioButton rBtn=new RadioButton(this);
        		childEle=doc.createElement("RadioButton");
        	    
             //   childEle.setAttribute("android:padding",padding);
        		childEle.setAttribute("android:id","@+id/rb"+id);
               	childEle.setAttribute("android:layout_width",width );
               	childEle.setAttribute("android:layout_height",height);
               	childEle.setAttribute("android:text",text);
               	radioGrp.appendChild(childEle);
        	  }
        	  return root;
        }

}

