package com.example.draganddrop;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.cocos2d.nodes.CCLabel;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;
import android.util.Xml;

public class Question {
	
	CCLabel text_;
	public MyQuestion easyQuestion;
	public MyQuestion sortQuestion;
    public Question(MainActivity activity)
    {
    	easyQuestion=new MyQuestion(activity,"question1.xml");
    	sortQuestion=new MyQuestion(activity,"question2.xml");
    	
    }
	
	//inner class
	 class MyQuestion{

		public ArrayList<String> question=null;
		public ArrayList<Level> level=null;
		 public MyQuestion(MainActivity activity,String filePath)
		 {
			 question=new ArrayList<String>();
			 level=new ArrayList<Level>();
			 XmlPullParserFactory pullParserFactory;
				try {
					pullParserFactory = XmlPullParserFactory.newInstance();
					XmlPullParser parser = pullParserFactory.newPullParser();
					InputStream in_s = activity.getApplicationContext().getAssets().open(filePath);
			        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		            parser.setInput(in_s, null);
		            Log.v("question","load question.xml success!1");
		            parseXML(parser);
		            Log.v("question","load question.xml success!2");
				} catch (XmlPullParserException e) {
					Log.v("question","load question.xml error1:"+e);
					e.printStackTrace();
					
				}catch (IOException e) {
					// TODO Auto-generated catch block
					Log.v("question","load question.xml error2:"+e);
					e.printStackTrace();
				}
		 }
		 private void parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
			{
			//	ArrayList<product> products = null;
				
		        int eventType = parser.getEventType();
		        Log.v("question","into parseXML success!");
//		      //  Product currentProduct = null;
		        Level currenLevel=null;
		        String currentQuestion="";
		        while (eventType != XmlPullParser.END_DOCUMENT){
		            String name = null;        
		            switch (eventType){
		                case XmlPullParser.START_DOCUMENT:
//		            //    	products = new ArrayList();
		                    break;
		                case XmlPullParser.START_TAG:
		                	Log.v("question","into star_tag success!");
		                    name = parser.getName();
		                    Log.v("question","Start_tagename:"+name);
		                    try{
		                    if (name.equalsIgnoreCase("question")){
		                    	currentQuestion=parser.nextText();
		                    	question.add(currentQuestion);
		                    	//currentQuestion="";
		                    	Log.v("question",""+currentQuestion);
		                    }
		                    }catch(Exception e){
		                    	Log.v("question","name==question");
		                    }
		                    try{
		                    	
		                    if(name.equalsIgnoreCase("level"))
		                    {
		                    	currenLevel=new Level();
		                    }else if(currenLevel!=null){
		                    	if(name.equalsIgnoreCase("answerNumber"))
		                    	{
		                    		
		                    		currenLevel.answerNumber=Integer.parseInt(parser.nextText());
		                    		Log.v("question",":"+currenLevel.answerNumber);
		                    	}else if(name.equalsIgnoreCase("maxNumber"))
		                    	{
		                    		currenLevel.maxNumber=Integer.parseInt(parser.nextText());
		                    		Log.v("question",":"+currenLevel.maxNumber);
		                    	}
		                    	else if(name.equalsIgnoreCase("minNumber"))
		                    	{
		                    		currenLevel.minNumber=Integer.parseInt(parser.nextText());
		                    		Log.v("question",":"+currenLevel.minNumber);
		                    	}
		                   
		                    } }
		                    catch(Exception e)
		                    {
		                    	Log.v("question","name==level");
		                    }
		                    break;
		                  
		                case XmlPullParser.END_TAG:
		                    name = parser.getName();
//		                    if (name.equalsIgnoreCase("question")){
//		                    	question.add(currentQuestion);
//		                    	Log.v("question","END_TAG"+currentQuestion);
//		                    	currentQuestion="";
//		                    } 
		                    if (name.equalsIgnoreCase("level") && level !=null){
		                    	level.add(currenLevel);
		                    	Log.v("question","END_TAG"+level);
		                    	currenLevel=null;
		                    } 
		                    break;
		           }
		            eventType = parser.next();
		        }
		//
//		        printProducts(products);
			}
	   
	} 
     class Level
	    {    	
	        int answerNumber;
	        int maxNumber;
	        int minNumber;       
	    }
    
}

