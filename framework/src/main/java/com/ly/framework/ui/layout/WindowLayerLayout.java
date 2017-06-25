package com.ly.framework.ui.layout;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
        Log.d(TAG, "onLayout: left = " + left + ";top = " + top + ";right = " + right + ";bottom = " + bottom);
        int count = getChildCount();
        for(int i = 0;i < count;i++) {
            View child = getChildAt(i);
            child.layout(left,top,right,bottom);
        }
    }


    @Override
    public void addView(View child) {
        super.addView(child);
        doAddViewAnimation(child);
    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
        doAddViewAnimation(child);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        doAddViewAnimation(child);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        doAddViewAnimation(child);

    }

    @Override
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
        doAddViewAnimation(child);
    }

    private void doAddViewAnimation(final View view) {
        if(getChildCount() <= 1) {
            return;
        }
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(getRight(),getLeft());
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int left = (int) animation.getAnimatedValue();
                view.layout(left,view.getTop(),left + view.getWidth(),view.getBottom());
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                valueAnimator.removeAllUpdateListeners();
                valueAnimator.removeAllListeners();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (widthMode) {
            case MeasureSpec.UNSPECIFIED:
                Log.d(TAG, "onMeasure: UNSPECIFIED");
                break;
            case MeasureSpec.AT_MOST:
                Log.d(TAG, "onMeasure: AT_MOST");
                break;
            case MeasureSpec.EXACTLY:
                Log.d(TAG, "onMeasure: EXACTLY");
                break;
        }
        Log.d(TAG, "onMeasure: width = " + widthSize + ";height = " + heightSize);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            Log.d(TAG, "onMeasure: child width = " + child.getWidth() + ";height = " + child.getHeight());
        }
    }

    float mLastDownX;
    float mLastDownY;
    boolean mIsAnimation;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if(getChildCount() <= 1) {
                    return false;
                }
                Log.d(TAG, "onTouchEvent: down mLastDownX = " + mLastDownX);
                return true;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                int distance = (int) (x - mLastDownX);
                View view = getChildAt(getChildCount() - 1);
                if(view == null) {
                    return false;
                }
                if (Math.abs(distance) >= MIN_DISTANCE && !mIsAnimation) {
                    Log.d(TAG, "onTouchEvent: move distance = " + distance);
                    int left = view.getLeft() + distance < 0 ? 0 : view.getLeft() + distance;
                    view.layout(left,view.getTop(), left + view.getWidth(),view.getBottom());
                    mLastDownX = x;
                    return true;
                }
                return false;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                view = getChildAt(getChildCount() - 1);
                if(view == null) {
                    return false;
                }
                float translationX = view.getLeft();
                Log.d(TAG, "onTouchEvent: up translationX = " + translationX);
                Log.d(TAG, "onTouchEvent: getLeft = " + getLeft() + ";getRight = " + getRight() + ";getWidth = " + getWidth());

                if(translationX * 4 < getWidth()) {
                    final ValueAnimator valueAnimator = ValueAnimator.ofFloat(translationX,0);
                    valueAnimator.setDuration(500);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float value = (float) animation.getAnimatedValue();
                            Log.d(TAG, "onAnimationUpdate: less value " + value);
                            View view = getChildAt(getChildCount() - 1);
                            view.layout((int)value,view.getTop(),(int)value + view.getWidth(),view.getBottom());
                        }
                    });
                    valueAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            valueAnimator.removeAllUpdateListeners();
                            valueAnimator.removeAllListeners();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            valueAnimator.removeAllUpdateListeners();
                            valueAnimator.removeAllListeners();
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    valueAnimator.start();
                }else {
                    final ValueAnimator valueAnimator = ValueAnimator.ofFloat(translationX,getRight());
                    valueAnimator.setDuration(500);
                    ValueAnimator.AnimatorUpdateListener updateAnimator = new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float value = (float) animation.getAnimatedValue();
                            Log.d(TAG, "onAnimationUpdate: more value " + value);
                            View view = getChildAt(getChildCount() - 1);
                            view.layout((int)value,view.getTop(),(int)value + view.getWidth(),view.getBottom());
                        }
                    };
                    valueAnimator.addUpdateListener(updateAnimator);
                    valueAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            View view = getChildAt(getChildCount() - 1);
                            if(view != null) {
                                removeView(view);
                            }
                            valueAnimator.removeAllUpdateListeners();
                            valueAnimator.removeAllListeners();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            valueAnimator.removeAllUpdateListeners();
                            valueAnimator.removeAllListeners();
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    valueAnimator.start();
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
    boolean mIsBeingDragged = false;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if(getChildCount() <= 1) {
            return false;
        }
        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastDownX = ev.getX();
                mLastDownY = ev.getY();
                mIsBeingDragged = false;
                Log.d(TAG, "onInterceptTouchEvent: down mIsBeingDragged = " + mIsBeingDragged);
                break;
            case MotionEvent.ACTION_MOVE:
                float x = ev.getX();
                float y = ev.getY();
                int distanceX = (int) (x - mLastDownX);
                int distanceY = (int) (y - mLastDownY);
                View view = getChildAt(getChildCount() - 1);
                if(view == null) {
                    return false;
                }
                if(Math.abs(distanceY) > Math.abs(distanceX)) {
                    mIsBeingDragged = false;
                }else if (Math.abs(distanceX) >= MIN_DISTANCE && !mIsAnimation) {
                    mIsBeingDragged = true;
                }
                Log.d(TAG, "onInterceptTouchEvent: move mIsBeingDragged = " + mIsBeingDragged);
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onInterceptTouchEvent: up mIsBeingDragged = " + mIsBeingDragged);
                break;
            default:
                break;
        }
        return mIsBeingDragged;
    }

    public boolean ismEnableTouch() {
        return mEnableTouch;
    }

    public void setmEnableTouch(boolean mEnableTouch) {
        this.mEnableTouch = mEnableTouch;
    }
}
