package com.example.draganddrop;

import org.cocos2d.nodes.CCLabel;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import android.R.string;
import android.util.Log;

public class MyLabel {
	public CCLabel label;
	public CCLabel label2;
	public CCLabel label3;
	public int fontSize=28;
	public int padding=5;
	public String fontName="";
	public CGPoint dimension=CGPoint.make(300, 50);
	public CCLabel.TextAlignment textAlignment= CCLabel.TextAlignment.LEFT;
	public MyLabel(String string,CGPoint position)
	{
		try{		
			label=CCLabel.makeLabel(string,CGSize.make(dimension.x*CommonItem.SIZE_RATE_X, dimension.y*CommonItem.SIZE_RATE_Y),textAlignment, fontName, fontSize*CommonItem.SIZE_RATE_X);
			
			
			label2=CCLabel.makeLabel(string,CGSize.make(dimension.x*CommonItem.SIZE_RATE_X, dimension.y*CommonItem.SIZE_RATE_Y),textAlignment, fontName, fontSize*CommonItem.SIZE_RATE_X);
			
			
			label3=CCLabel.makeLabel(string,CGSize.make(dimension.x*CommonItem.SIZE_RATE_X, dimension.y*CommonItem.SIZE_RATE_Y),textAlignment, fontName, fontSize*CommonItem.SIZE_RATE_X);
		    
		    label.setAnchorPoint(0, 0);
		    label.setPosition(position.x*CommonItem.SIZE_RATE_X,position.y*CommonItem.SIZE_RATE_Y);
		    label.setColor(new ccColor3B(0,0,0));
		    CommonItem.gameLayer.addChild(label,0);
		   
		    label2.setAnchorPoint(0, 0);
		    label2.setPosition(position.x*CommonItem.SIZE_RATE_X,(position.y-(fontSize+padding))*CommonItem.SIZE_RATE_Y);
		    label2.setColor(new ccColor3B(0,0,0));
		    CommonItem.gameLayer.addChild(label2,0);
		    
		    label3.setAnchorPoint(0, 0);
		    label3.setPosition(position.x*CommonItem.SIZE_RATE_X,(position.y-(fontSize+padding)*2)*CommonItem.SIZE_RATE_Y);
		    label3.setColor(new ccColor3B(0,0,0));
		    CommonItem.gameLayer.addChild(label3,0);
		    this.setString(string);
			
	      Log.v("log","Label load success!");
		}catch(Exception e){
			Log.v("log","Label load errror!");
		}
	   
	    
	    
	    	
	    
	}
	 public void setString(String string) 
	 {  
		 try{  
			    if(string.length()<11)
				{
					    label.setString(string);
					    label2.setString("");
					    label3.setString("");
				}
				else if(string.length()<21&&string.length()>=11)
				{
					label.setString(string.substring(0, 10));
				    label2.setString(string.substring(10, string.length()));
				    label3.setString("");
				}
				else if(string.length()>=21)
				{
					label.setString(string.substring(0, 10));
				    label2.setString(string.substring(10, 20));
				    label3.setString(string.substring(20, string.length()));
				}
	     Log.v("log","Label load success!");
		 }catch(Exception e)
		 {
			 Log.v("log","label SetString errror!"+e);
		 }
	 }
	 public void setVisible(Boolean isVisible)
	 {
		 this.label.setVisible(isVisible);
		 this.label2.setVisible(isVisible);
		 this.label3.setVisible(isVisible);
	 }
}
