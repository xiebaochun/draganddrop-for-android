package com.example.draganddrop;

import org.cocos2d.opengl.CCGLSurfaceView;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

public class MySurfaceView extends CCGLSurfaceView{
     
	public MySurfaceView(Context context)
     {
    	 super(context);
     }
	@Override
	public boolean onTouchEvent(MotionEvent event) {		
		CommonItem.touchPoint.x = (int)event.getX();
    	CommonItem.touchPoint.y = CommonItem.GAME_HEIGHT*CommonItem.SIZE_RATE_Y-(int)event.getY();
			 switch (event.getAction()) {
			    case MotionEvent.ACTION_DOWN:
			    	CommonItem.touchState=CommonItem.TouchState.down;
			    	CommonItem.downPoint=CommonItem.touchPoint;
			        Log.v("touchState","down");
			    	break;
		        case MotionEvent.ACTION_MOVE:
		        	CommonItem.touchState=CommonItem.TouchState.move;
		        	CommonItem.movePoint=CommonItem.touchPoint;
		        	Log.v("touchState","move");
		            //requestRender();
		            break;
		        case MotionEvent.ACTION_UP:
		        	CommonItem.touchState=CommonItem.TouchState.up;
		        	CommonItem.upPoint=CommonItem.touchPoint;
		        	Log.v("touchState","up");
		        	break;
		        default:
		        	//CommonItem.touchState=CommonItem.TouchState.none;
		        	break;
		    }
//			
			
		
		return super.onTouchEvent(event);
	}
}
