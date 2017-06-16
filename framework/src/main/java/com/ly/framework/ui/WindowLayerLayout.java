package com.ly.framework.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.ly.framework.R;

/**
 * Created by LY on 2017-06-16.
 */

public class WindowLayerLayout extends FrameLayout {

    public static final String TAG = "WindowLayerLayout";



    private boolean mEnableTouch = true;
    private static final int MIN_DISTANCE = 5;

    public WindowLayerLayout(@NonNull Context context) {
        super(context);
        initController(context, null);
    }

    public WindowLayerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initController(context, attrs);
    }

    private void initController(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WindowLayerLayout);
        if (typedArray != null) {
            mEnableTouch = typedArray.getBoolean(R.styleable.WindowLayerLayout_enable_touch, true);
            typedArray.recycle();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

//        int childCount = getChildCount();
//        for(int i = 0;i < childCount;i ++) {
//            View view = getChildAt(i);
//            view.layout(left,top,right,bottom);
//        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
    }

    float mLastX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                int distance = (int) (x - mLastX);
                View view = getChildAt(getChildCount() - 1);
                if(view == null) {
                    break;
                }
                if (Math.abs(distance) >= MIN_DISTANCE ) {
                    int left = view.getLeft() + distance < 0 ? 0 : view.getLeft() + distance;
                    view.layout(left,view.getTop(), left + view.getWidth(),view.getBottom());
                    mLastX = x;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                view = getChildAt(getChildCount() - 1);
                if(view == null) {
                    break;
                }
                float translationX = view.getLeft();
                Log.d(TAG, "onTouchEvent: translationX = " + translationX);
                Log.d(TAG, "onTouchEvent: getLeft = " + getLeft() + ";getRight = " + getRight() + ";getWidth = " + getWidth());

                if(translationX * 2 < getWidth()) {
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,"translationX",0,-translationX);
                    objectAnimator.setDuration(500);
                    objectAnimator.start();
                }else {
                    final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,"translationX",0,getRight() - translationX);
                    objectAnimator.setDuration(500);
                    objectAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            View view = getChildAt(getChildCount() - 1);
                            if(view != null) {
                                removeView(view);
                            }
                            objectAnimator.removeListener(this);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    objectAnimator.start();
                }
                break;
            default:
                break;
        }
        return mEnableTouch;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        super.onInterceptTouchEvent(ev);
        return mEnableTouch;
    }

    public boolean ismEnableTouch() {
        return mEnableTouch;
    }

    public void setmEnableTouch(boolean mEnableTouch) {
        this.mEnableTouch = mEnableTouch;
    }
}
