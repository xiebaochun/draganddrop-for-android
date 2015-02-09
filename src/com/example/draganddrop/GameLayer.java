package com.example.draganddrop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor4B;

import com.example.draganddrop.CommonItem.TouchState;

import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;

public class GameLayer extends CCColorLayer {
    
	public enum GameState{start,play,over};
	public GameState gamestate=GameState.start;
	public int playerPositionX=0;
	public float timeTick=1;
	public int timeSeconedTick=0;
	public int eachStageTime=60;
	public int stage=1;
	public int level=1;
	public int score=0;
	public int target=4;
	public int question_choose=-1;
	public float result_show_time=1;
	
	public final CGPoint QUESTION_POSITION=CGPoint.make(150,450);
	public final CGPoint TIME_BAR_POSITION=CGPoint.make(280,605);
	//public final CGPoint GAME_START_POSITION=CGPoint.make(430,216);
	public final CGPoint GAME_START_POSITION=CGPoint.make(0,0);
	public final CGPoint CONFIRM_BUTTON_POSITION=CGPoint.make(1090,15);
	public final CGPoint QUESTION_TEXT_POSITION=CGPoint.make(65,370);
	public final CGPoint RESULT_POSITION=CGPoint.make(550,300);
	public final CGPoint FIX_POSITION=CGPoint.make(300,250);
	public final CGPoint CONFIRM_EXIT_POSITION=CGPoint.make(0,0);
	public final CGPoint REPLAY_POSITION=CGPoint.make(430,216);
	public final CGPoint PASS_POSITION=CGPoint.make(430,216);
	
	
	public final int TIME_BAR_HEIGHT=40;
	public final int TIME_BAR_WIDTH=768;
    public final int EACH_CARDS_COUNT=5;
	public CGPoint touchPoint;
	public Boolean isGamePause=true;
	public Boolean goToPrepare=true;
	public Boolean isSortMode=false;
	
	MySprite backGround;
	MySprite backGround_cover;
	MySprite stateBar;
	MySprite TimeBar;
	MySprite question_image;
	MySprite timeBar;
	MySprite result_sp;
	MySprite fix_sp;
		
	Button confirm_bt;
	Button gameStart;
	Button replay;
	Button pass;	
	Button confirmExit_bt;
	
	public static MySprite[] answers=new MySprite[5];
    Number score_num;
    Number stage_num;
    Number target_num;
    //CCLabel test_label;
    Random gl_rnd;
    Card.Style currenAnswer=Card.Style.red;
    public static CGPoint[] CardsPosition=new CGPoint[10];
    CGPoint[] AnswersPosition=new CGPoint[5];
    MyLabel[] easyQuestionLabels=new MyLabel[3];
    MyLabel[] sortQuestionLabels=new MyLabel[3];
	public static CCScene scene()
	{
	    CCScene scene = CCScene.node();
	    CCColorLayer layer = new GameLayer(ccColor4B.ccc4(255, 255, 255, 0));

	 
	    scene.addChild(layer);
	 
	    return scene;
	}
	protected GameLayer(ccColor4B color)
	{
		
	    super(color);
	    this.setIsTouchEnabled(true);
	    CGSize winSize = CCDirector.sharedDirector().displaySize();
	    CommonItem.SIZE_RATE_Y=(float)(winSize.height/CommonItem.GAME_HEIGHT);
	    CommonItem.SIZE_RATE_X=(float)(winSize.width/CommonItem.GAME_WIDTH);
	    CommonItem.gameLayer=this;
	    gl_rnd=new Random();
	    Log.v("log","winSize.height:"+winSize.height);
	    Log.v("log","SIZE_RATE_Y"+ CommonItem.SIZE_RATE_Y);
	   
	    question_image= new MySprite("question.png",true,QUESTION_POSITION,-4);    
	    question_image.fixedSizeRate((float)1280/2500);
	    backGround=new MySprite("bg_b.png",true,CGPoint.zero(),-5);
	    stateBar=new MySprite("StateBar.png",true,CGPoint.make(15, 555),-4);
	    stateBar.collisionRect=new Rectangle(stateBar.collisionRect.x+stateBar.collisionRect.width*(float)6/7,stateBar.collisionRect.y,stateBar.collisionRect.width*(float)1/7,stateBar.collisionRect.height*(float)1/2);
	    fix_sp=new MySprite("correct.png",false,FIX_POSITION,0);
	    
	    score_num=new Number(0,16,CGPoint.make(240, 652));
	    score_num.setNumber(score);
	    stage_num=new Number(0,16,CGPoint.make(600, 652));
	    stage_num.setNumber(stage);
	    target_num=new Number(0,16,CGPoint.make(910, 652));
	    target_num.setNumber(target);
	    timeBar=new MySprite("TimeBar.png",true,TIME_BAR_POSITION,-3);
	    result_sp=new MySprite("answerShow.png",false,RESULT_POSITION,1,2,0);
	    //reorderChild(timeBar.sprite,4);
	    
	    confirm_bt=new Button("confirm.png","confirm.png",CONFIRM_BUTTON_POSITION,true);
	    confirm_bt.fixedSizeRate(CommonItem.fixedSizeRate); 
	    replay=new Button("replay.png",false,REPLAY_POSITION,0);
	    pass=new Button("Continue.png",false,PASS_POSITION,0);
	    confirmExit_bt=new Button("bg_white.png",false,CONFIRM_EXIT_POSITION,0);
	    gameStart=new Button("layout-04.png",true,GAME_START_POSITION,0);
	   // number=new MySprite("stageNumber.png",true,CGPoint.zero(),11,1,-3);
	    for(int i=0;i<3;i++)
	    {
	    	MyLabel question_text=new MyLabel(CommonItem.question.easyQuestion.question.get(i),QUESTION_TEXT_POSITION);
	    	question_text.setVisible(false);
	    	easyQuestionLabels[i]=question_text;
	    }
	    for(int i=0;i<3;i++)
	    {
	    	MyLabel question_text=new MyLabel(CommonItem.question.sortQuestion.question.get(i),QUESTION_TEXT_POSITION);
	    	question_text.setVisible(false);
	    	sortQuestionLabels[i]=question_text;
	    }
	  //preapare cards position
	    for(int i=0;i<2*5;i++)
	    {
	       CardsPosition[i]=CGPoint.make(530+(i%5)*150, 300-150*(i/5));
	    }
	  //prepare answers position
	    for(int i=0;i<5;i++)
	    {
	    	AnswersPosition[i]=CGPoint.make(480+i*150, 400);
	    }
	    Log.v("log","my");
	    try{
	        loadCards();
	       }
	    catch(Exception e)
	    {
	    	Log.v("log","loadcards error");
	    }
	    
		
	    
	    this.scheduleUpdate();
	}
	
	
	private void loadCards() {
		
		 for(int i=0;i<EACH_CARDS_COUNT;i++)
	   	 {
		    Card cardSprite=new Card("red-"+(i+1)+".png",false,CGPoint.zero());
		    cardSprite.style=Card.Style.red;
		    cardSprite.id=i;
		    cardSprite.fixedSizeRate(CommonItem.fixedSizeRate);
		    
	   		try{
	   			CommonItem.redCards[i]=cardSprite;
	   			CommonItem.allCards.add(cardSprite);
	   		}catch(Exception e){
	   			Log.v("log","error:"+e);
	   		}
	   		
	   		
	   	 }
	   	 for(int i=0;i<EACH_CARDS_COUNT;i++)
	   	 {
	   		Card cardSprite=new Card("blue-"+(i+1)+".png",false,CGPoint.zero());
	   		CommonItem.blueCards[i]=cardSprite;
	   	    cardSprite.style=Card.Style.blue;
		    cardSprite.id=i;
	   		CommonItem.allCards.add(cardSprite);
	   		cardSprite.fixedSizeRate(CommonItem.fixedSizeRate);
	   		
	   	 }
	   	 for(int i=0;i<EACH_CARDS_COUNT;i++)
	   	 {
	   		Card cardSprite=new Card("green-"+(i+1)+".png",false,CGPoint.zero());
	   		CommonItem.greenCards[i]=cardSprite;
	   	    cardSprite.style=Card.Style.green;
		    cardSprite.id=i;
	   		CommonItem.allCards.add(cardSprite);
	   		cardSprite.fixedSizeRate(CommonItem.fixedSizeRate);
	   		
	   	 }
	   	 for(int i=0;i<5;i++)
	   	 {
	   		 
	   		 MySprite answer=new MySprite("answer_frame.png",false,AnswersPosition[i],-4);
	   		 answers[i]=answer;
	   		 answer.fixedSizeRate(CommonItem.fixedSizeRate);
	   	 }
		
	}
	public void update(float dt) {	
		
		CommonItem.currenTouchState=CommonItem.touchState;
		touchPoint=CommonItem.touchPoint;
		spriteRectAndeventUpdate(dt);
		touchUpdate();
       //test_label.setString("currenTouch: "+CommonItem.currenTouchState+"  preCurrentTouch:"+CommonItem.preTouchState+"  touchState: "+CommonItem.touchState+"  xy: "+touchPoint);
       
		
//		if(tap())
//		{
//			stage++;
//			stage_num.setNumber(stage);
//		}
		switch(gamestate)
		{
		   case start:
			    gameStart.mySetVisible(true);
			    replay.mySetVisible(false);
			    pass.mySetVisible(false);
				   gamestate=GameState.play;
			    break;
		   case play:
			   if(isGamePause==false)
			   {
				   timeUpdate(dt);
				   if(goToPrepare==true)
				   {
					   goToPrepare=false;
					   question_choose=(int)(Math.random()*3);
					   prepareQuestion(question_choose);
				       prepareCards();
				   }
				   cardsPositionUpdate();
			   }
			   
			   break;
		   case over:
			   break;
		   
		}
		CommonItem.preTouchState=CommonItem.currenTouchState;
//		playerPositionX+=CommonItem.dt;
//		player.setPosition(playerPositionX, 0);
//		if(playerPositionX>=1500)CommonItem.dt=-1;
//		if(playerPositionX<0)CommonItem.dt=1;
		
		//CommonItem.preTouchState=CommonItem.currenTouchState;
		//CommonItem.touchState=CommonItem.TouchState.none;
	}
	private void touchUpdate() {
		if(isGamePause==false)
		{
			if(CommonItem.currenTouchState==CommonItem.TouchState.down)
			{
				for(Card card:CommonItem.allCards)
				{
				  if(card.collisionRect.contains(touchPoint)&&card.getVisible()==true)
				  {
					  card.isTouched=true;
					  CommonItem.cardOriginPoint=card.getPosition();
				  }
				}
			}
			if(CommonItem.currenTouchState==CommonItem.TouchState.up&&CommonItem.preTouchState==CommonItem.TouchState.down)
			{
				for(Card card:CommonItem.allCards)
				{
					if(card.getVisible()==true)
					{
						card.isUped=false;
						card.fixedSizeRate(CommonItem.fixedSizeRate);
					  if(card.collisionRect.contains(touchPoint)&&card.getVisible()==true)
					  {
						  
						  card.isUped=true;
						  card.fixedSizeRate(0.6f);
					  }
					}
			 
				}
			
				
			}
		}
		if(CommonItem.currenTouchState==CommonItem.TouchState.up&&CommonItem.preTouchState==CommonItem.TouchState.move)
		{
			for(Card thisCard:CommonItem.allCards)
			{
				if(thisCard.isTouched==true)
				{
						thisCard.isTouched=false;
						thisCard.fixedSizeRate(CommonItem.fixedSizeRate);
						thisCard.touchCount=0;
					for(Card card:CommonItem.allCards)
					{
						
						if(card!=thisCard&&card.getVisible()==true&&card.collisionRect.contains(CommonItem.touchPoint))
						{
							thisCard.setPosition(card.getPosition());
							card.setPosition(CommonItem.cardOriginPoint);
							return;
						}
					}
					for(MySprite answer:answers)
					{
						if(answer.getVisible()==true&&answer.collisionRect.contains(CommonItem.touchPoint))
						{
							//answer.isHoldCard=true;
							
							thisCard.setPosition(CGPoint.make(answer.sprite.getPosition().x+answer.collisionRect.width/2, answer.sprite.getPosition().y+answer.collisionRect.height/2));
							return;
						}
					}
					for(CGPoint point:CardsPosition)	
					{
						CGPoint p=CGPoint.make(point.x*CommonItem.SIZE_RATE_X,point.y*CommonItem.SIZE_RATE_Y);
						if(thisCard.collisionRect.contains(p))
						{
							thisCard.setPosition(p);
							return;
						}
					}
					thisCard.setPosition(CommonItem.cardOriginPoint);
				}
			}
		}
		
		
		
	}
	private void cardsPositionUpdate() {
		
		for(Card card:CommonItem.allCards)
		{
			if(card.getVisible()==true)
			{
				card.collisionRectUpdate();
				

				if(card.isTouched==true)
				{
					
					if(card.collisionRect.contains(CommonItem.touchPoint)&&CommonItem.currenTouchState==CommonItem.TouchState.move)
					{
					///allCards[i].setPosition(CommonItem.touchPoint.x-50,CommonItem.touchPoint.y-50);
						CommonItem.gameLayer.reorderChild(card, -2);
						card.setPosition((CommonItem.touchPoint.x),(CommonItem.touchPoint.y));
					}
				}
		   }
		
		}
	}
	private  void prepareCards() {
		Log.v("log","go into  prepareds method success!");
		resetCardsAndAnswers();
		
		 int level=stage-1;	 
		 int maxNumber=1;
		 int minNumber=4;
		 int answerNumber=8;
			
		if(isSortMode==false)
		{
			  maxNumber=CommonItem.question.easyQuestion.level.get(level).maxNumber;
			  minNumber= CommonItem.question.easyQuestion.level.get(level).minNumber;
			  answerNumber=CommonItem.question.easyQuestion.level.get(level).answerNumber;
	    }
		else
	    {
			 maxNumber=CommonItem.question.sortQuestion.level.get(level).maxNumber;
			  minNumber= CommonItem.question.sortQuestion.level.get(level).minNumber;
			  answerNumber=CommonItem.question.sortQuestion.level.get(level).answerNumber;
	    }
		
			if(currenAnswer==Card.Style.red)
			{
			   gotoPrepareCard(answerNumber,maxNumber,minNumber,CommonItem.redCards,CommonItem.blueCards,CommonItem.greenCards);
			}
		   if(currenAnswer==Card.Style.blue)
			{
				 gotoPrepareCard(answerNumber,maxNumber,minNumber,CommonItem.blueCards,CommonItem.redCards,CommonItem.greenCards);
	    	}
		   if(currenAnswer==Card.Style.green)
		    {
				 gotoPrepareCard(answerNumber,maxNumber,minNumber,CommonItem.greenCards,CommonItem.redCards,CommonItem.blueCards);
		    }
	  		
			
	}
	private void gotoPrepareCard(int answerNumber, int maxNumber,int minNumber, Card[]  mainCards,Card[]  unMainCards1,Card[]  unMainCards2) {
		ArrayList<CGPoint> positionContainer=new ArrayList<CGPoint>();
		 ArrayList<Integer> temps2=new ArrayList<Integer>();
		 ArrayList<Integer> temps3=new ArrayList<Integer>();
		 ArrayList<Integer> temps4=new ArrayList<Integer>();
		 
		int temp=gl_rnd.nextInt(maxNumber-minNumber)+minNumber;
		int temp2=0;
		int temp3=0;
		Log.v("log","toalCard:"+temp+" answerCard:"+answerNumber+"unMainCard:"+(temp-answerNumber));
		for(int i=0;i<answerNumber;i++)
		{
			answers[i].setVisible(true);
		}
		for(int i=0;i<answerNumber;i++)
		{
			
			while(true)
			{
		        temp2=gl_rnd.nextInt(EACH_CARDS_COUNT);
			   if(!temps2.contains(temp2))
			   {
				   temps2.add(temp2);
				   break;
			   }
			}
			CGPoint position;
			while(true)
			{
				
			    position=CardsPosition[gl_rnd.nextInt(10)];
				if(!positionContainer.contains(position))
				{
					positionContainer.add(position);
					break;
				}
			}
			
			mainCards[temp2].setPosition_ds(position);
			mainCards[temp2].setVisible(true);
			Log.v("log", mainCards[temp2].style+" prepared"+temp2+position.toString());
		}
		
		for(int i=0;i<temp-answerNumber;i++)
		{
			
			
			CGPoint position;
			while(true)
			{
				 position=CardsPosition[gl_rnd.nextInt(10)];
					if(!positionContainer.contains(position))
					{
						positionContainer.add(position);
						break;
					}
			}
			if(i%2==0)
			{
				while(true)
				{
			        temp3=gl_rnd.nextInt(EACH_CARDS_COUNT);
				   if(!temps3.contains(temp3))
				   {
					   temps3.add(temp3);
					   break;
				   }
				}
				unMainCards1[temp3].setPosition_ds(position);
				unMainCards1[temp3].setVisible(true);
				Log.v("log",unMainCards1[temp3].style+" prepared");
			}else
			{ 
				while(true)
				{
			        temp3=gl_rnd.nextInt(EACH_CARDS_COUNT);
				   if(!temps4.contains(temp3))
				   {
					   temps4.add(temp3);
					   break;
				   }
				}
				unMainCards2[temp3].setPosition_ds(position);
				unMainCards2[temp3].setVisible(true);
				Log.v("log",unMainCards2[temp3].style+" prepared");
			}
			
		}
		
		
	}
	/////////////////////////
	private void prepareQuestion(int temp) {
		for(MyLabel label:easyQuestionLabels)
		{
			if(label.label.getVisible()==true)
			{
			   label.setVisible(false);
			}
		}
		for(MyLabel label:sortQuestionLabels)
		{
			if(label.label.getVisible()==true)
			{
			   label.setVisible(false);
			}
		}
		switch(temp)
		{
		case 0:
			currenAnswer=Card.Style.red;
			break;
		case 1:
			currenAnswer=Card.Style.blue;
			break;
		case 2:
			currenAnswer=Card.Style.green;
			break;
		}
		
		int random2=(int)(Math.random()*2);
		
		//easyQuestionLabels[1].setVisible(true);
		
		if(random2==0)
		{
			isSortMode=false;
	    	easyQuestionLabels[temp].setVisible(true);
		}
		else
		{
			isSortMode=true;
			sortQuestionLabels[temp].setVisible(true);
		}
		
	}
	public void spriteRectAndeventUpdate(float delta) {
		if(tap()&&stateBar.collisionRect.contains(touchPoint))
		{
			confirmExit_bt.mySetVisible(true);
		}
		if(confirmExit_bt.getVisible()==true)
		{
           
		  if(confirmExit_bt.isClicked==true)
		  {
			  confirmExit_bt.isClicked=false;
			  
			  if(touchPoint.x<CCDirector.sharedDirector().displaySize().width/2)
			  {
				  System.exit(0);
			  }
			  else{
				  confirmExit_bt.mySetVisible(false);
			  }
		  }
		}
		if(gameStart.getVisible()==true)
		{

		  if(gameStart.isClicked==true)
		  {
			  gameStart.isClicked=false;
			  gameStart.mySetVisible(false);
			  isGamePause=false;
			  Log.v("log","gameStartEct_Z:"+gameStart.getZOrder()+replay.getZOrder()+pass.getZOrder());
		  }
		}
		if(pass.getVisible()==true)
		{
			
		  if(pass.isClicked==true)
		  {
			  pass.isClicked=false;
			  pass.mySetVisible(false);
			  isGamePause=false;
			  goToPrepare=true;
			  stage++;
			  stage_num.setNumber(stage);
			  score=0;
			  score_num.setNumber(score);
			  target++;
			  target_num.setNumber(target);
			  //goToNextRound();
		  }
		}
		if(replay.getVisible()==true)
		{
			
		  if(replay.isClicked==true)
		  {
			  replay.isClicked=false;
			  replay.mySetVisible(false);
			  isGamePause=false;
			  goToPrepare=true;
			  score=0;
			  score_num.setNumber(score);
			 // goToNextRound();
		  }
		}
		if(confirm_bt.getVisible()==true&&isGamePause==false)
		{
			 if(confirm_bt.isClicked==true)
			 {
				 confirm_bt.isClicked=false;
				 //confirm_bt.setVisible(false);
				 //if all answers place were holding card and those cards,which were holden were the same style to the current answer style
				 //goToNextRound(); 
				 if(isAnswerCorrect())//if answer id right then show the correct image 
				 {
					 result_sp.currentFrameY=0;
				 }
				 else                 //if answer is in incorrect
				 {
					 result_sp.currentFrameY=1;
					 
				 }
				 result_sp.rectUpdate();
				 result_sp.setVisible(true);
			 }
		}
	
		if(result_sp.sprite.getVisible()==true)
		{
			result_show_time-=delta;
			if(result_show_time<0)
			{
				result_show_time=1;
				result_sp.setVisible(false);
				if(result_sp.currentFrameY==0)
				{
					goToPrepare=true;
					score++;
					score_num.setNumber(score);
				}
				else
				{
					fix_sp.setVisible(true);
				}
			}
		}
		if(fix_sp.sprite.getVisible()==true)
		{
			result_show_time-=delta;
			if(result_show_time<0)
			{
				result_show_time=1;
				fix_sp.setVisible(false);
			}
		}
	}
	//juge answer rect is all hold the card,and push the card,which placed in answer's rect,into a array
	private boolean isAnswerCorrect() {
		ArrayList<Card> cards_=new ArrayList<Card>();
		for(MySprite answer:answers)
		{
			if(answer.getVisible()==true)
			{
				for(Card card:CommonItem.allCards)
				{
					if(card.getVisible()==true)
					{
						if(answer.collisionRect.contains(card.getPosition()))
						{
							answer.isHoldCard=true;
							card.isInAnswerPlace=true;
							cards_.add(card);
						}
					}
				}
			}
		}
		for(MySprite answer:answers)
		{
			if(answer.getVisible()==true)
			{
				if(answer.isHoldCard==false)
				{
					return false;
				}
			}
		}
		
			for(Card card:cards_)
			{
				if(card.style!=currenAnswer)
				{
					return false;
				}
			}
		if(isSortMode==true)
		{
			if(!isCardInOrder())
			{
				return false;
			}
		}
		
		return true;
	}
	//ready next stage things 
	private boolean isCardInOrder() {
		Card tempCard=null;
		Card[] cards=null;
		switch(question_choose)
		{
		case 0:
			cards=CommonItem.redCards;
			break;
		case 1:
			cards=CommonItem.blueCards;
			break;
		case 2:
			cards=CommonItem.greenCards;
			break;
		}
		for(int i=0;i<5;i++)
		{
			if(cards[i].isInAnswerPlace==true&&cards[i].getVisible()==true)
			{
				if(tempCard==null)
				{
					tempCard=cards[i];
				}
				else
				{
					if(tempCard.getPosition().x<cards[i].getPosition().x)
					{
						tempCard=cards[i];				
					}
					else
					{
						return false;
					}
				}
			}
		}
		 
		return true;
	}
	private void resetCardsAndAnswers() {
		
		for(MySprite answer:answers)
		{
			answer.setVisible(false);
			answer.isHoldCard=false;
		}
		for(Card card:CommonItem.allCards)
		{			
				card.isInAnswerPlace=false;
				card.touchCount=0;
    			card.isTouched=false;
    			card.fixedSizeRate(CommonItem.fixedSizeRate);
				card.setVisible(false);
				card.setPosition(0,0);
		}
		
		
	}
	//if is tap in screen
	private boolean tap() {
		if(CommonItem.currenTouchState==CommonItem.TouchState.up&&CommonItem.preTouchState==CommonItem.TouchState.down)//&&CommonItem.currenTouchState==CommonItem.TouchState.up)
		{
			Log.v("touchState","tap");
			Log.v("touchState","pisition:"+touchPoint.toString());
			return true;
		}
		return false;
	}
	public void timeUpdate(float dt) {
		timeTick-=dt;
		
		if(timeTick<=0)
		{
			
		   timeTick=1;
		   timeSeconedTick++;
			Log.v("log",""+timeSeconedTick);//log
		   if(timeSeconedTick>=eachStageTime)
		   {
			   timeSeconedTick=0;
			   isGamePause=true;
			   if(score>=target)
			   {
				   pass.mySetVisible(true);
			   }
			   else
			   {
				   replay.mySetVisible(true);
			   }
		   }
		   timeBar.sprite.setTextureRect(0, 0, ((float)(eachStageTime-timeSeconedTick)/eachStageTime)*TIME_BAR_WIDTH,40 , false);
		}
	}
	
		//@Override
        public boolean ccTouchesBegan(MotionEvent event) {
            CGPoint convertedLocation = CCDirector.sharedDirector()
            	.convertToGL(CGPoint.make(event.getX(), event.getY()));
           // CommonItem.touchState=CommonItem.TouchState.down;
           // CCNode s = getChildByTag(kTagSprite);
            //s.stopAllActions();
            //s.runAction(CCMoveTo.action(1.0f, convertedLocation));
           
            //CGPoint pnt = s.getPosition();

           // float at = CGPoint.ccpCalcRotate(pnt, convertedLocation);

            //s.runAction(CCRotateTo.action(1, at));
            
            //progressTimer.setPercentage(10.0f + progressTimer.getPercentage());

            return CCTouchDispatcher.kEventHandled;
        }
        public boolean ccTouchesMoved(MotionEvent event) {
        	//CommonItem.touchState=CommonItem.TouchState.move;
            return CCTouchDispatcher.kEventIgnored;  // TODO Auto-generated method stub
        }

        public boolean ccTouchesEnded(MotionEvent event) {
        	//CommonItem.touchState=CommonItem.TouchState.up;
            return CCTouchDispatcher.kEventIgnored;  // TODO Auto-generated method stub
        }
//        public boolean thisPoistionHasNoCard(CGPoint position)
//        {
////        	for(Card card: CommonItem.redCards)
////        	{
////        		if(card.getVisible()==true&&card.getPosition()==position)return false;
////        	}
////        	for(Card card: CommonItem.blueCards)
////        	{
////        		if(card.getVisible()==true&&card.getPosition()==position)return false;
////        	}
//        	for(Card card: CommonItem.allCards)
//        	{
//        		if(card.getVisible()==true)
//        		{
//        			if(card.getPosition().x==position.x&&card.getPosition().y==position.y)
//        				{
//        				return false;
//        				
//        				}
//        		}
//        	}
//        	return true;
//        }
	
}
