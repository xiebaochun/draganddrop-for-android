package com.example.draganddrop;

import java.util.ArrayList;

import org.cocos2d.types.CGPoint;

public class CommonItem {
	public static float SIZE_RATE_X;
	public static float SIZE_RATE_Y;
	public static final float GAME_WIDTH=1280;
	public static final float GAME_HEIGHT=768;
	public static float dt=1;
	public static CGPoint touchPoint=new CGPoint();
	public static CGPoint cardOriginPoint=new CGPoint();
    public static CGPoint downPoint=new CGPoint();
    public static CGPoint movePoint=new CGPoint();
    public static CGPoint upPoint=new CGPoint();
		                          
	public static enum TouchState{down,move,up,none};
	public static TouchState touchState=TouchState.none;
	public static TouchState currenTouchState=TouchState.none;
	public static TouchState preTouchState=TouchState.none;
	
	public static GameLayer gameLayer;
	public static  float fixedSizeRate=(float)1280/2500;
	
	public static Card[]  redCards=new Card[5];
	public static Card[]  blueCards=new Card[5];
	public static Card[]  greenCards=new Card[5];
	
	
	public static ArrayList<Card>  allCards=new ArrayList<Card>();
	
	public static Question question;    
	
}
