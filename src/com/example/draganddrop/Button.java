package com.example.draganddrop;

import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemImage;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;

import android.util.Log;


public class Button extends CCNode{
	public CCMenuItemImage sprite;
	 public int textTureWidth;
	 CCMenu menu;
	 	public int textTureHeight;
	 	public float fixSizeRate=1;
	 	public Rectangle collisionRect;
	 	public Boolean isClicked=false;
	    String name="none name";
     public Button(String normalImage,String selectedImage,CGPoint position,Boolean isVisible)
     {
    	 sprite=CCMenuItemImage.item(normalImage, selectedImage, this, "clickCallBack");
    	 sprite.setAnchorPoint(0,0);
    	 this.textTureWidth=(int)sprite.getContentSize().width;
 		 this.textTureHeight=(int)sprite.getContentSize().height;
    	 this.sprite.setScaleY(CommonItem.SIZE_RATE_Y*fixSizeRate);
 		 this.sprite.setScaleX(CommonItem.SIZE_RATE_X*fixSizeRate);
 		 
  		 sprite.setContentSize(sprite.getContentSize().width  , sprite.getContentSize().height);
  		 this.setAnchorPoint(0,0);
  		 menu= CCMenu.menu() ;
    	 menu.setContentSize(sprite.getContentSize()) ;
    	 menu.setPosition(0,0);
		 menu.addChild(sprite,1,1);
  		 
    	 this.addChild(menu,1,1);
    	 this.setVisible(isVisible);
    	 this.setPosition(position.x*CommonItem.SIZE_RATE_X, position.y*CommonItem.SIZE_RATE_Y);
 		
 		this.collisionRect=new Rectangle(this.getPosition().x,this.getPosition().y,this.textTureWidth*CommonItem.SIZE_RATE_X*fixSizeRate,this.textTureHeight*CommonItem.SIZE_RATE_X*fixSizeRate);
    	 CommonItem.gameLayer.addChild(this,0);
    	 this.name=normalImage;
     }
     public Button(String normalImage,Boolean isVisible,CGPoint position,int z)
     {
    	 try{
    	 sprite=CCMenuItemImage.item(normalImage, normalImage, this, "clickCallBack");
    	 sprite.setAnchorPoint(0,0);
    	 this.setAnchorPoint(0,0);
    	 this.textTureWidth=(int)sprite.getContentSize().width;
 		 this.textTureHeight=(int)sprite.getContentSize().height;
    	 this.sprite.setScaleY(CommonItem.SIZE_RATE_Y*fixSizeRate);
 		 this.sprite.setScaleX(CommonItem.SIZE_RATE_X*fixSizeRate);
 		 
  		 sprite.setContentSize(sprite.getContentSize().width  , sprite.getContentSize().height);
  		 menu= CCMenu.menu() ;
    	 menu.setContentSize(sprite.getContentSize()) ;
    	 menu.setPosition(0,0);
		 menu.addChild(sprite,1,1);
  		 
    	 this.addChild(menu,1,1);
    	 this.setPosition(position.x*CommonItem.SIZE_RATE_X, position.y*CommonItem.SIZE_RATE_Y);
 		 
 		 this.collisionRect=new Rectangle(this.getPosition().x,this.getPosition().y,this.textTureWidth*CommonItem.SIZE_RATE_X*fixSizeRate,this.textTureHeight*CommonItem.SIZE_RATE_X*fixSizeRate);
    	 CommonItem.gameLayer.addChild(this,z);
    	 this.mySetVisible(isVisible);
    	 this.name=normalImage;
    	 }
    	 catch(Exception e)
    	 {
    		 Log.v("log","Button Load:"+normalImage);
    	 }
     }
     public void fixedSizeRate(float rate) {
  		fixSizeRate=rate;
  		this.sprite.setScaleY(CommonItem.SIZE_RATE_Y*fixSizeRate);
  		this.sprite.setScaleX(CommonItem.SIZE_RATE_X*fixSizeRate);
  		this.collisionRect=new Rectangle(this.getPosition().x-this.textTureWidth*CommonItem.SIZE_RATE_X*fixSizeRate/2,this.getPosition().y-this.textTureHeight*CommonItem.SIZE_RATE_X*fixSizeRate/2,this.textTureWidth*CommonItem.SIZE_RATE_X*fixSizeRate,this.textTureHeight*CommonItem.SIZE_RATE_X*fixSizeRate);
      }
     public void clickCallBack(Object sender)
     {
    	 this.isClicked=true;
    	 Log.v("touchState","clicked!"+this.name);
     }
     public void mySetVisible(Boolean isVisible)
     {
    	 this.setVisible(isVisible);
    	 this.sprite.setVisible(isVisible);
//         if(isVisible==true)
//         {
//        	 this.getParent().reorderChild(this, 0);
//        	 this.reorderChild(sprite, 1);
//         }
//         else
//         {
//        	 this.getParent().reorderChild(this, -6);
//        	 this.reorderChild(sprite, 1);
//         }
     }
}
