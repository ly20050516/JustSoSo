package com.ly.framework.ui.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.ly.framework.R;

/**
 * Created by ly on 2017/6/26.
 */

public class XProgressBar extends ProgressBar {

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
        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.XProgressBar);
        if(typedArray != null) {
            mUnreachHeight = (int) typedArray.getDimension(R.styleable.XProgressBar_unreach_height,DEFAULT_UNREACH_HEIGHT);
            mUnreachColor = typedArray.getColor(R.styleable.XProgressBar_unreach_color,DEFAULT_COLOR);
            mTextSize = typedArray.getDimensionPixelSize(R.styleable.XProgressBar_text_size,DEFAULT_TEXT_SIZE);
            mTextColor = typedArray.getColor(R.styleable.XProgressBar_text_color,DEFAULT_COLOR);
            mReachHeight = (int) typedArray.getDimension(R.styleable.XProgressBar_reach_height,DEFAULT_REACH_HEIGHT);
            mReachColor = typedArray.getColor(R.styleable.XProgressBar_reach_color,DEFAULT_COLOR);
            typedArray.recycle();
        }
    }


    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
