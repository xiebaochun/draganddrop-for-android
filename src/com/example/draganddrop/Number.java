package com.example.draganddrop;

import org.cocos2d.types.CGPoint;

public class Number {
     MySprite num1;
     MySprite num2;
	public Number(int number,int size,CGPoint position)
	{
	   num1=new MySprite("stageNumber.png",false,position,11,1,-3);
	   num2=new MySprite("stageNumber.png",false,CGPoint.make(position.x+(num1.textTureWidth/2)*(float)size/32, position.y),11,1,-3);
	 
	   num1.fixedSizeRate((float)size/32);
	   num2.fixedSizeRate((float)size/32);
	   setNumber(number);
	}
	public void setNumber(int num)
	{
       if(num<10)
       {
    	  num1.setVisible(true);
    	  num2.setVisible(false);
    	  num1.currentFrameX=num;
    	  num1.rectUpdate();
       }
       if(num>=10&&num<100)
       {
    	  num1.setVisible(true);
     	  num2.setVisible(true);
     	  num1.currentFrameX=(int)num/10;
     	  num2.currentFrameX=(int)num%10;
     	  num1.rectUpdate();
     	  num2.rectUpdate();
       }
       if(num>=100)
       {
    	  num1.setVisible(false);
      	  num2.setVisible(false);
       }
       num1.rectUpdate();
	}
}
