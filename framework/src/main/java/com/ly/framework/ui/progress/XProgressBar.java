package com.ly.framework.ui.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ProgressBar;

import com.ly.framework.R;
import com.ly.framework.utilities.DisplayUtil;

/**
 * Created by ly on 2017/6/26.
 */

public class XProgressBar extends ProgressBar {

    public static final String TAG = "XProgressBar";

    public static final int DEFAULT_COLOR = Color.rgb(0x33,0xa9,0x88);
    public static final int DEFAULT_UNREACH_HEIGHT = 3;
    public static final int DEFAULT_TEXT_SIZE = 14;
    public static final int DEFAULT_REACH_HEIGHT = 1;

    private int mUnreachHeight = DEFAULT_UNREACH_HEIGHT;
    private int mUnreachColor = DEFAULT_COLOR;

    private int mTextSize = DEFAULT_TEXT_SIZE;
    private int mTextColor = DEFAULT_COLOR;

    private int mReachHeight = DEFAULT_REACH_HEIGHT;
    private int mReachColor = DEFAULT_COLOR;

    public XProgressBar(Context context) {
        this(context,null);
    }

    public XProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        initController(context,attrs);
    }

    private void initController(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.XProgressBar);
        if(typedArray != null) {
            mUnreachHeight = (int) typedArray.getDimension(R.styleable.XProgressBar_unreach_height, dp2px(DEFAULT_UNREACH_HEIGHT));
            mUnreachColor = typedArray.getColor(R.styleable.XProgressBar_unreach_color,DEFAULT_COLOR);
            mTextSize = typedArray.getDimensionPixelSize(R.styleable.XProgressBar_text_size,sp2px(DEFAULT_TEXT_SIZE));
            mTextColor = typedArray.getColor(R.styleable.XProgressBar_text_color,DEFAULT_COLOR);
            mReachHeight = (int) typedArray.getDimension(R.styleable.XProgressBar_reach_height,dp2px(DEFAULT_REACH_HEIGHT));
            mReachColor = typedArray.getColor(R.styleable.XProgressBar_reach_color,DEFAULT_COLOR);
            typedArray.recycle();
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width;
        int height;

        /**
         * 1.MeasureSpec.EXACTLY:具体值,或者是 match_parent 的情况
         * 2.MeasureSpec.AT_MOST:wrap_content，此处没有意义，纠正为 match_parent
         * 3.MeasureSpec.UNSPECIFIED:无限大，如何设置呢？
         */
        width = widthSize;
        Log.d(TAG, "onMeasure: widthSize = " + widthSize + ";width = " + width);
        if(widthMode == MeasureSpec.EXACTLY) {
            /**
             * 具体值,或者是 match_parent 的情况
             */
            Log.d(TAG, "onMeasure: MeasureSpec.EXACTLY");
        }else if(widthMode == MeasureSpec.AT_MOST){
            /**
             * wrap_content，此处没有意义
             */
            Log.d(TAG, "onMeasure: MeasureSpec.AT_MOST");

        }else {
            /**
             * 无限大，此处也没有意义
             */
            Log.d(TAG, "onMeasure: MeasureSpec.UNSPECIFIED");

        }

        if(heightMode == MeasureSpec.AT_MOST) {
            int maxTmpHeight = Math.max(mUnreachHeight,mReachHeight);
            height = getPaddingTop() + getPaddingBottom() + maxTmpHeight;
        }else {
            height = Math.max(heightSize,getPaddingLeft() + getPaddingRight() + Math.max(mUnreachHeight,mReachHeight));
        }

        Log.d(TAG, "onMeasure: heightSize = " + heightSize + ";height = " + height);

        setMeasuredDimension(width,height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout: change = " + changed + ";left = " + left + ";top = " + top + ";right = " + right + ";bottom = " + bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
    }

    private int dp2px(int dp) {
        return DisplayUtil.dp2px(getContext(),dp);
    }

    private int sp2px(int sp) {
        return DisplayUtil.sp2px(getContext(),sp);
    }
}
