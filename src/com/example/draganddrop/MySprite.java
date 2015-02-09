package com.example.draganddrop;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import android.util.Log;

public class MySprite{
	public CCSprite sprite;
	public int currentFrameX=0;
	public int currentFrameY=0;
	public int textTureWidth;
	public int textTureHeight;
	public Rectangle collisionRect;
	public float fixSizeRate=1;
	public Boolean isHoldCard=false;
	
	public MySprite(String filePath,Boolean isVisible,CGPoint position,int z)
	{
		
		try{
			this.sprite=CCSprite.sprite(filePath);
			Log.v("log","rightfilePath:"+filePath);
		}
		catch(Exception e){
			Log.v("log","errorfilePath:"+filePath);
		}
		this.sprite.setAnchorPoint(0,0);
		this.sprite.setPosition(position.x*CommonItem.SIZE_RATE_X,position.y*CommonItem.SIZE_RATE_Y);
		this.sprite.setScaleY(CommonItem.SIZE_RATE_Y*fixSizeRate);
		this.sprite.setScaleX(CommonItem.SIZE_RATE_X*fixSizeRate);
		sprite.setVisible(isVisible);
		this.textTureWidth=(int)sprite.getTexture().getWidth();
		this.textTureHeight=(int)sprite.getTexture().getHeight();
		this.collisionRect=new Rectangle(this.sprite.getPosition().x,this.sprite.getPosition().y,this.textTureWidth*CommonItem.SIZE_RATE_X*fixSizeRate,this.textTureHeight*CommonItem.SIZE_RATE_X*fixSizeRate);
		CommonItem.gameLayer.addChild(sprite,z);
		Log.v("log","rate:"+CommonItem.SIZE_RATE_Y+"visible:"+sprite.getVisible()+":filePath:"+filePath);
	}
	public MySprite(String filePath,Boolean isVisible,CGPoint position,int spriteFrameX,int spriteFrameY,int z)
	{
		
		try{
			this.sprite=CCSprite.sprite(filePath);
		}
		catch(Exception e){
			Log.v("log","filePath:"+filePath);
		}
		this.sprite.setAnchorPoint(0,0);
		this.sprite.setPosition(position.x*CommonItem.SIZE_RATE_X,position.y*CommonItem.SIZE_RATE_Y);
		this.sprite.setScaleY(CommonItem.SIZE_RATE_Y*fixSizeRate);
		this.sprite.setScaleX(CommonItem.SIZE_RATE_X*fixSizeRate);
		sprite.setVisible(isVisible);
		this.textTureWidth=(int)sprite.getTexture().getWidth()/spriteFrameX;
		this.textTureHeight=(int)sprite.getTexture().getHeight()/spriteFrameY;
		this.collisionRect=new Rectangle(this.sprite.getPosition().x,this.sprite.getPosition().y,this.textTureWidth*CommonItem.SIZE_RATE_X*fixSizeRate,this.textTureHeight*CommonItem.SIZE_RATE_X*fixSizeRate);
		sprite.setTextureRect(0, 0, this.textTureWidth, this.textTureHeight, false);
		CommonItem.gameLayer.addChild(sprite,z);
	}
	public void setPosition(float x, float y) {
		this.sprite.setPosition(x*CommonItem.SIZE_RATE_X, y*CommonItem.SIZE_RATE_Y);
    }
	public void setPosition(CGPoint position) {
		int x=(int)(position.x*CommonItem.SIZE_RATE_X);
		int y=(int)(position.y*CommonItem.SIZE_RATE_Y);
		this.sprite.setPosition(x, y);
		this.collisionRect=new Rectangle(x,y,this.textTureWidth*CommonItem.SIZE_RATE_X*fixSizeRate,this.textTureHeight*CommonItem.SIZE_RATE_X*fixSizeRate);
    }
	public void fixedSizeRate(float rate) {
		fixSizeRate=rate;
		this.sprite.setScaleY(CommonItem.SIZE_RATE_Y*fixSizeRate);
		this.sprite.setScaleX(CommonItem.SIZE_RATE_X*fixSizeRate);
		this.collisionRect=new Rectangle(this.sprite.getPosition().x,this.sprite.getPosition().y,this.textTureWidth*CommonItem.SIZE_RATE_X*fixSizeRate,this.textTureHeight*CommonItem.SIZE_RATE_X*fixSizeRate);
    }
	public void setVisible(Boolean isVisible) {
		this.sprite.setVisible(isVisible);
    }
	public Boolean getVisible() {
		return this.sprite.getVisible();
    }
	public void collisionRectUpdate()
	{
		this.collisionRect=new Rectangle(this.sprite.getPosition().x,this.sprite.getPosition().y,this.textTureWidth*CommonItem.SIZE_RATE_X*fixSizeRate,this.textTureHeight*CommonItem.SIZE_RATE_X*fixSizeRate);
	    //this.sprite.setTextureRect(this.currentFrameX* this.textTureWidth,this.currentFrameY*this.textTureHeight,this.textTureWidth,this.textTureHeight,false);
	    //this.sprite.setScaleY(CommonItem.SIZE_RATE_Y*fixSizeRate);
		//this.sprite.setScaleX(CommonItem.SIZE_RATE_X*fixSizeRate);
	}
	public void rectUpdate()
	{
		this.sprite.setTextureRect(this.currentFrameX* this.textTureWidth,this.currentFrameY*this.textTureHeight,this.textTureWidth,this.textTureHeight,false);
	}
	
}
