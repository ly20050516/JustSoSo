package com.ly.picassostudy.listview;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Scroller;

/**
 * Created by LY on 6/9/2016.
 */
public class DetailImageView extends FrameLayout {

    public static final String TAG = DetailImageView.class.getCanonicalName();
    public ImageView mImageView;
    private boolean isShow;

    float mDownRawX;
    Scroller mScroller;
    public DetailImageView(Context context) {
        super(context);
        mImageView = new ImageView(context);
        isShow = true;
        addView(mImageView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setBackgroundColor(0xff0099cc);
        mScroller = new Scroller(context);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                mDownRawX = event.getRawX();
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                float dx = event.getRawX() - mDownRawX;
                mDownRawX = event.getRawX();
                if(dx > 0 && dx < 3)
                    break;
                if(dx < 0 && dx > -3)
                    break;
                smoothScrollBy(-dx,0);
                break;
            }
            case MotionEvent.ACTION_UP:{
                smoothScrollTo(0,0);
                break;
            }
        }
        return isShow;
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }

    void smoothScrollBy(float dx,float dy){
        mScroller.startScroll(mScroller.getFinalX(),mScroller.getFinalY(),(int)dx,(int)dy);
        invalidate();
    }
    void smoothScrollTo(float x,float y){
        float dx = x - mScroller.getFinalX();
        float dy = y - mScroller.getFinalY();
        smoothScrollBy(dx,dy);
    }
    public void show() {
        isShow = true;
        setVisibility(View.VISIBLE);
    }

    public void hide() {
        isShow = false;
        setVisibility(View.GONE);
    }

    public boolean isShowing() {
        return isShow;
    }
}
