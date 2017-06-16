package com.ly.framework.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
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

//        int childCount = getChildCount();
//        for(int i = 0;i < childCount;i ++) {
//            View view = getChildAt(i);
//            view.layout(left,top,right,bottom);
//        }
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

        doAddViewAnimation(child);
        super.addView(child, params);
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
        view.layout(getRight(),getTop(),getRight() + view.getWidth(),getBottom());
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
    }

    float mLastX;
    boolean mIsAnimation;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = event.getX();
                if(getChildCount() <= 1) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                int distance = (int) (x - mLastX);
                View view = getChildAt(getChildCount() - 1);
                if(view == null) {
                    break;
                }
                if (Math.abs(distance) >= MIN_DISTANCE && !mIsAnimation) {
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
        return mEnableTouch;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        super.onInterceptTouchEvent(ev);
        if(getChildCount() <= 1) {
            return false;
        }
        return mEnableTouch;
    }

    public boolean ismEnableTouch() {
        return mEnableTouch;
    }

    public void setmEnableTouch(boolean mEnableTouch) {
        this.mEnableTouch = mEnableTouch;
    }
}
