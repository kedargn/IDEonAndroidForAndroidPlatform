package kgn.vhc.pp;

import java.io.File;
import java.io.IOException;
import android.view.ViewGroup;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup;

import org.w3c.dom.Node;
import org.w3c.dom.Document;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.util.Log;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import android.widget.*;
import android.app.Activity;
import android.view.View;
import android.view.animation.Transformation;
public class Preview extends Activity {
	//TableLayout tl=null;
	String m;
	boolean tabLay=false;
	LinearLayout ll;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        showPreview();
	}

	@Override
    public void onResume() {
        super.onResume();
      //  tl=null;
        showPreview();
	}

	public void showPreview() {
	    try {
        	File parseFile=new File(Options.root+"/workspace/"+Options.openProjnm+"/res/layout/main.xml");
        	Log.i("abs path is(Widgets.java)",parseFile.getAbsolutePath());
        	
        	DocumentBuilderFactory docBuildFactory=DocumentBuilderFactory.newInstance();
        	
        	DocumentBuilder docBuilder=docBuildFactory.newDocumentBuilder();
        	
        	Document doc=docBuilder.parse(parseFile);
            Element root=doc.getDocumentElement();
            
            if(root.getNodeName().equals("LinearLayout")){
            	 ll=new LinearLayout(this);
                NamedNodeMap nnm=root.getAttributes();
                for(int i=0;i<nnm.getLength();i++) {
                	Node node=nnm.item(i);
                	Log.i("the atributes are,length",node.getNodeName()+"   "+i);
                	 ll.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));  
                	if(node.getNodeName().contains("orientation")) {
                		String value=root.getAttribute(node.getNodeName());
                		if(value.equals("vertical")) {
                			ll.setOrientation(LinearLayout.VERTICAL);
                		}
                		else {
                			ll.setOrientation(LinearLayout.HORIZONTAL);
                		}
                	}
                }
            	
            }
            	NodeList children=root.getChildNodes();
            	
            	for(int i=0;i<children.getLength();i++) {
            		
            			if((children.item(i).getNodeType()==Node.ELEMENT_NODE)) {
            			
            				Node view=children.item(i);
            				Log.i("the node type",view.getNodeName());
            				if(view.getNodeName().equals("Button")) {
            					ll.addView(createBtn(view));
            				}
            				else if(view.getNodeName().equals("TextView")) {
            					ll.addView(createTv(view));
            				}
            				else if(view.getNodeName().equals("EditText")) {
            					ll.addView(createEt(view));
            				}
            				else if(view.getNodeName().equals("CheckBox")) {
            					ll.addView(createCheckbox(view));
            				}
            				else if(view.getNodeName().equals("RadioGroup")) {
            					ll.addView(createRadioButton(view));
            				}
            				else if(view.getNodeName().equals("Spinner")) {
            					ll.addView(createSpinner(view));
            				}
            			else if(view.getNodeName().equals("TableLayout")) {
            				String stretch=((Element)view).getAttribute("android:stretchColumns");
            				
            				
            				TableLayout tl=new TableLayout(this);
            				 tabLay=true;
            				 tl.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
            				 tl.setStretchAllColumns(true);
            				/* if(!stretch.equals(null)) {
            					 tl.setStretchAllColumns(false);
            					 Log.i("the stretch value",stretch);
            					 if(stretch.equals("*")) {
             					tl.setStretchAllColumns(true);
             					Log.i("stretch tl","entered *");
            					 }
            					 else if(stretch.contains(",")){
            						 String[] spanCol=stretch.split(",");
            						 for(int q=0;q<spanCol.length;q++) {
            							 int p=Integer.parseInt(spanCol[q]);
            							 tl.setColumnStretchable(p, true);
            							 Log.i("stretch tl","entered split");
            						 }
            						}
            						 else {
            							 int p=Integer.parseInt(stretch);
            							 tl.setColumnStretchable(p, true);
            							 Log.i("stretch tl","entered single   "+p);
            						 }
            					 }*/
             				
            				 
            				// TableLayout.LayoutParams params=new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.FILL_PARENT);
            				 //ll.addView(tl,params);
            	             Log.i("table layout","table layout added");	
            	
            	
            				 NodeList childOfTable=view.getChildNodes();
            				 for(int z=0;z<childOfTable.getLength();z++) {
            					 if((childOfTable.item(z).getNodeType()==Node.ELEMENT_NODE)) {
            						 Log.i("the childrn of tablelayout",childOfTable.item(z).getNodeName());
            						 Node tableChild=childOfTable.item(z);
            						 if(tableChild.getNodeName().equals("TableRow")) {
            							// tableRow();
            							 
            							 
            							 TableRow tr=new TableRow(this);
            							 tr.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
                                       //  tl.addView(tr);
            							 Log.i("added",tableChild.getNodeName()+" added");
            						     NodeList rowChildNodes=tableChild.getChildNodes();
            						     
            						     for(int y=0;y<rowChildNodes.getLength();y++) {
                        					 if((rowChildNodes.item(y).getNodeType()==Node.ELEMENT_NODE)) {
                        					 Log.i("the childrn od tableRow",rowChildNodes.item(y).getNodeName());
                        						 Node rowChild=rowChildNodes.item(y);
            						     
            						     
            						   if(rowChild.getNodeName().equals("Button")) {
            							// Button  btn=createBtn(rowChild);
            							   Button btn=new Button(this);
            							   String text=((Element)rowChild).getAttribute("android:text");
            							   String span=((Element)rowChild).getAttribute("android:layout_span");
            							  /* if(!span.equals(null)) {
            							   TableRow.LayoutParams params = new TableRow.LayoutParams();  
            							    params.span = Integer.parseInt(span);
            							    btn.setText(text);
            							    tr.addView(btn,params);
            							   }
            							   else
            							   {*/
            							   btn.setText(text);  
                     					tr.addView(btn);
            							//   }
                     					//tl.addView(tr);
                     					//ll.addView(tl);
                     					Log.i("added",rowChild.getNodeName()+" added");
                     				}
                     				else if(rowChild.getNodeName().equals("TextView")) {
                                        //TextView txt=createTv(rowChild);
                     					TextView txt=new TextView(this);
                     					String text=((Element)rowChild).getAttribute("android:text");
                     					
                     					Log.i("the text of txtview!!!!!!!!!",text.toString());
                     					txt.setText(text);
                                        tr.addView(txt);
                                       
                     					Log.i("added",rowChild.getNodeName()+" added");
                     				}
                     				else if(rowChild.getNodeName().equals("EditText")) {
                     					//tr.addView(createEt(rowChild));
                     					EditText et=new EditText(this);
                     					tr.addView(et);
                     					
                     					Log.i("added",rowChild.getNodeName()+" added");
                     				}
                     				else if(rowChild.getNodeName().equals("CheckBox")) {
                     					//tr.addView(createCheckbox(rowChild));
                     					CheckBox cb=new CheckBox(this);
                     					String text=((Element)rowChild).getAttribute("android:text");
                     					cb.setText(text);
                     					tr.addView(cb);
                     					Log.i("added",rowChild.getNodeName()+" added");
                     					
                     				}
                     				else if(rowChild.getNodeName().equals("RadioGroup")) {
                    					 RadioGroup rg=createRadioButton(rowChild);
                    					 tr.addView(rg);
                    					 Log.i("added",rowChild.getNodeName()+" added");
                    					 
                    			   }
                     				else if(rowChild.getNodeName().equals("Spinner")) {
                   					 Spinner sp=new Spinner(this);
                   					 tr.addView(sp);
                   					 Log.i("added",rowChild.getNodeName()+" added");
                   					 
                   			   }
            				    }
                        		//tr=null;
            				   }
            						     tl.addView(tr,new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                      					Log.i("the row is and child nos",tr.toString()+"  "+tr.getChildCount()+" "+tr.getChildAt(0));
                      					Log.i("Table layout no. of child and name"," "+tl.getChildCount()+"   "+tl.getChildAt(0)+"  "+tl.getChildAt(1));
                      					 
                      					 //addContentView(tl,new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
                      					 Log.i("the linear child no. and name"," "+ll.getChildCount()+"   "+ll.getChildAt(0)+"  "+ll.getChildAt(1)+" "+ll.getChildAt(2)+" "+ll.getChildAt(3));
            						     
            				 }
            						
             					
            			
                           }
            					         
            		     }
            				 ll.addView(tl);
            			}
            	       }
                     }
            	/*if(Options.typeOfProj.equals("grocery")) {
            	tl.setStretchAllColumns(true);
            	}*/
            //	if(tabLay)
            		//ll.addView(tl);
            			
            	HorizontalScrollView hsv=new HorizontalScrollView(this);
            	ScrollView sv=new ScrollView(this);
            	//hsv.addView(ll);
            	sv.addView(ll);
            	setContentView(sv);
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

	public Button createBtn(Node view) {
		Button btn=new Button(this);
		String text=((Element)view).getAttribute("android:text");
		btn.setText(text);
		btn=(Button)previewButton(btn,view,ll);
		return btn;

	}
	
	public EditText createEt(Node view) {
		EditText tv=new EditText(this);
		String text=((Element)view).getAttribute("android:text");
		tv.setText(text);
		tv=(EditText)previewButton(tv,view,ll);
		return tv;
	}
	
	public CheckBox createCheckbox(Node view) {
		CheckBox tv=new CheckBox(this);
		String text=((Element)view).getAttribute("android:text");
		tv.setText(text);
		tv=(CheckBox)previewButton(tv,view,ll);
		return tv;
	}
	
	
	public TextView createTv(Node view){
		TextView tv=new TextView(this);
		String text=((Element)view).getAttribute("android:text");
		tv.setText(text);
		tv=(TextView)previewButton(tv,view,ll);
		return tv;
	}
	
	public Spinner createSpinner(Node view){
		Spinner tv=new Spinner(this);
		//String text=((Element)view).getAttribute("android:text");
		//tv.setText(text);
		tv=(Spinner)previewButton(tv,view,ll);
		return tv;
	}
	
	
	public RadioGroup createRadioButton(Node view){
		RadioGroup tv=new RadioGroup(this);
		NodeList radioNode=view.getChildNodes();
		int len=radioNode.getLength();
		Log.i("createRadioButton","INSIDE createRadioButton");
		for(int i=0;i<len;i++) {
			if(radioNode.item(i).getNodeType()==Node.ELEMENT_NODE){
				RadioButton rb=new RadioButton(this);
				String text=((Element)radioNode.item(i)).getAttribute("android:text");
				rb.setText(text);
				tv.addView(rb);
			}
		}
		Log.i("(INSIDE FN)the radiogropu no of child and names ",""+tv.getChildCount()+""+tv.getChildAt(0));
		tv.setOrientation(RadioGroup.VERTICAL);
		return tv;
	}
	public View previewButton(View wdgt,Node view,ViewGroup layout) {
		Log.i("inside previewBtn","inside of previewButton");
		
		String widthOfView=((Element)view).getAttribute("android:layout_width");
		String heightOfView=((Element)view).getAttribute("android:layout_width");
		String p=((Element)view).getAttribute("android:padding");
		// m=((Element)view).getAttribute("android:margin");
		 
		//wdgt.setPadding(Integer.parseInt(p.trim()),Integer.parseInt(p.trim()),Integer.parseInt(p.trim()),Integer.parseInt(p.trim()));
		//wdgt.s
      //  Log.
		//btn.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		
        
		View res=width(wdgt,widthOfView,heightOfView);
		
		return res;
	}
	
	public View width(View v,String widthOfView,String heightOfView){
		
		//String widthOfView=((Element)v).getAttribute("android:layout_width");
		//String heightOfView=((Element)v).getAttribute("android:layout_width");
		
		if((widthOfView.equals("fill_parent"))&&(heightOfView.equals("fill_parent"))){
		v.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		Log.i("layout params","1");
		
	}
		else if((widthOfView.equals("fill_parent"))&&(heightOfView.equals("wrap_content"))){
			v.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
			Log.i("layout params","2");	
		}	
		
		else if((widthOfView.equals("wrap_content"))&&(heightOfView.equals("fill_parent"))){
			v.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.FILL_PARENT));
			Log.i("layout params","3");
		}
		
		else if((widthOfView.equals("wrap_content"))&&(heightOfView.equals("wrap_content"))){
			v.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			Log.i("layout params","4");
		}
		return v;
   }


 public void tableRowChild(View v){
	 
	 TableRow.LayoutParams params = new TableRow.LayoutParams();
	 
	 
	 
 }
 
 
}