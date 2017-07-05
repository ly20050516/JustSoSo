package com.ly.framework.ui.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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

    public static final int DEFAULT_COLOR = Color.rgb(0xFF,0xa9,0x88);
    public static final int DEFAULT_UNREACH_HEIGHT = 3;
    public static final int DEFAULT_TEXT_SIZE = 14;
    public static final int DEFAULT_REACH_HEIGHT = 1;
    public static final int DEFAULT_TEXT_LEFT_MARGIN = 2;
    public static final int DEFAULT_TEXT_RIGHT_MARGIN = 2;


    private int mUnreachHeight;
    private int mUnreachColor;

    private int mTextSize;
    private int mTextColor;

    private int mReachHeight;
    private int mReachColor;

    private int mTextLeftMargin;
    private int mTextRightMargin;

    private Paint mPaint;
    private StringBuilder mTextPercent = new StringBuilder();
    private Rect mTextRect = new Rect();

    public XProgressBar(Context context) {
        this(context,null);
    }

    public XProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        initController(context,attrs);
    }

    private void initController(Context context, AttributeSet attrs) {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mUnreachHeight = dp2px(DEFAULT_UNREACH_HEIGHT);
        mUnreachColor = DEFAULT_COLOR;
        mTextSize = sp2px(DEFAULT_TEXT_SIZE);
        mTextColor = DEFAULT_COLOR;
        mReachHeight = dp2px(DEFAULT_REACH_HEIGHT);
        mReachColor = DEFAULT_COLOR;
        mTextLeftMargin = dp2px(DEFAULT_TEXT_LEFT_MARGIN);
        mTextRightMargin = dp2px(DEFAULT_TEXT_RIGHT_MARGIN);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.XProgressBar);
        if(typedArray != null) {
            mUnreachHeight = (int) typedArray.getDimension(R.styleable.XProgressBar_unreach_height, dp2px(DEFAULT_UNREACH_HEIGHT));
            mUnreachColor = typedArray.getColor(R.styleable.XProgressBar_unreach_color,DEFAULT_COLOR);
            mTextSize = typedArray.getDimensionPixelSize(R.styleable.XProgressBar_text_size,sp2px(DEFAULT_TEXT_SIZE));
            mTextColor = typedArray.getColor(R.styleable.XProgressBar_text_color,DEFAULT_COLOR);
            mReachHeight = (int) typedArray.getDimension(R.styleable.XProgressBar_reach_height,dp2px(DEFAULT_REACH_HEIGHT));
            mReachColor = typedArray.getColor(R.styleable.XProgressBar_reach_color,DEFAULT_COLOR);
            mTextLeftMargin = (int) typedArray.getDimension(R.styleable.XProgressBar_text_left_margin,dp2px(DEFAULT_TEXT_LEFT_MARGIN));
            mTextRightMargin = (int) typedArray.getDimension(R.styleable.XProgressBar_text_right_margin,dp2px(DEFAULT_TEXT_RIGHT_MARGIN));
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

        int maxTmpHeight = Math.max(mUnreachHeight,mReachHeight);
        Paint paint = new Paint();
        paint.setTextSize(mTextSize);
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int textHeight = fontMetricsInt.bottom - fontMetricsInt.top;
        maxTmpHeight = Math.max(maxTmpHeight,textHeight);
        height = getPaddingTop() + getPaddingBottom() + maxTmpHeight;

        if(heightMode == MeasureSpec.AT_MOST) {
            // keep height
        }else {
            height = Math.max(heightSize,height);
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

        canvas.save();
        canvas.translate(getPaddingLeft(),getPaddingTop());
        Log.d(TAG, "onDraw: padding left = " + getPaddingLeft() + ";padding right = " + getPaddingRight());
        int maxProgress = getMax();
        int progress = getProgress();
        Log.d(TAG, "onDraw: progress = " + progress + ";maxProgress = " + maxProgress);
        mTextPercent.delete(0,mTextPercent.length());
        mTextPercent.append(progress).append("%");
        mPaint.setTextSize(mTextSize);
        int textWidth = (int) mPaint.measureText(mTextPercent.toString());
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        int textHeight = fontMetricsInt.descent - fontMetricsInt.ascent;
        Log.d(TAG, "onDraw: textWidth = " + textWidth + ";textHeight = " + textHeight);
        int remainWidth = getWidth() - getPaddingLeft() - getPaddingRight() - mTextLeftMargin - mTextRightMargin - textWidth;
        int reachWidth = (int) (remainWidth * (progress * 1.0f / maxProgress));

        mPaint.setStrokeWidth(mReachHeight);
        mPaint.setColor(mReachColor);
        int y = (textHeight - mReachHeight) / 2;
        canvas.drawLine(0,y,reachWidth,y,mPaint);

        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        canvas.translate(reachWidth + mTextLeftMargin,0);
        mPaint.getTextBounds(mTextPercent.toString(),0,mTextPercent.toString().length(),mTextRect);
        canvas.drawText(mTextPercent.toString(),-mTextRect.left,-mTextRect.top,mPaint);

        mPaint.setStrokeWidth(mUnreachHeight);
        mPaint.setColor(mUnreachColor);
        canvas.translate(textWidth + mTextRightMargin,0);
        y = (textHeight - mUnreachHeight) / 2;
        canvas.drawLine(0,y,remainWidth - reachWidth,y,mPaint);

        Log.d(TAG, "onDraw: reachWidth = " + reachWidth + ";unreachWidth = " + (remainWidth - reachWidth));
        canvas.restore();
    }

    private int dp2px(int dp) {
        return DisplayUtil.dp2px(getContext(),dp);
    }

    private int sp2px(int sp) {
        return DisplayUtil.sp2px(getContext(),sp);
    }
}
