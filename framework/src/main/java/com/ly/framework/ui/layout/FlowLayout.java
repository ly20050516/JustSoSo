package com.ly.framework.ui.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.ly.framework.R;
import com.ly.framework.utilities.DisplayUtil;

/**
 * Created by ly on 2017/7/16.
 */

public class FlowLayout extends ViewGroup {

    public static final String TAG = "FlowLayout";
    private int mVerticalInterval;
    private int mHorizontalInterval;
    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs, int style) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        if (a != null) {
            mVerticalInterval = (int) a.getDimension(R.styleable.FlowLayout_vertical_interval, dp2px(0));
            mHorizontalInterval = (int) a.getDimension(R.styleable.FlowLayout_horizontal_interval,dp2px(0));
            a.recycle();
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        Log.d(TAG, "onLayout: changed = " + changed + ";l = " + l + ";t = " + t + ";r = "+ r + ";b = " + b);

        int childCount = getChildCount();

        if(!changed) {
            return;
        }

        int left = 0;
        int top = 0;
        int width = getWidth();
        int height = getHeight();
        int maxHeight = 0;
        Log.d(TAG, "onLayout: width = " + width + ";height = " + height + ";child count = " + childCount);
        for (int i = 0; i < childCount; i++) {

            View child = getChildAt(i);
            FlowLayout.LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int childHeight = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

            Log.d(TAG, "onLayout: childWidth = " + childWidth + ";childHeight = " + childHeight);
            if(childHeight > maxHeight) {
                maxHeight = childHeight;
            }
            if(left + childWidth > width) {
                /**
                 * 超过一行的宽度了，换行
                 */
                top = top + maxHeight + mVerticalInterval;
                left = 0;
                maxHeight = childHeight;
                child.layout(left,top,left + childWidth,top + childHeight);
            }else {
                child.layout(left,top,left + childWidth,top + childHeight);
                left += childWidth;
            }
        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width;
        int height;

        /**
         * 宽度计算
         */

        /**
         * 宽度必须是个精确值，否则是没有意义的。
         */
        width = widthSize;

        if (widthMode == MeasureSpec.AT_MOST) {

            /**
             * wrap_content
             */

        } else if (widthMode == MeasureSpec.EXACTLY) {

            /**
             * 具体值或者 match_parent
             */

        } else {

            /**
             * 无限大，后面考虑支持滑动
             */
        }

        /**
         * 高度计算
         */
        if (heightMode == MeasureSpec.AT_MOST) {
            /**
             * Wrap_content，计算出共有多少行，行高 = 每一行的最大子高度 + mVerticalInterval。注意，在仅一行的情况下，不要加上  mVerticalInterval
             */
            height = 0;
            int childCount = getChildCount();

            int maxHeight = 0;
            int childWidthSum = 0;

            int childWidth;
            int childHeight;

            int rows = 0;
            for (int i = 0; i < childCount; i++) {

                View view = getChildAt(i);
                measureChild(view, widthMeasureSpec, heightMeasureSpec);
                FlowLayout.LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();

                childWidth = view.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
                childHeight = view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

                if (childHeight > maxHeight) {
                    maxHeight = childHeight;
                }

                if (childWidthSum + childWidth > width) {
                    /**
                     * 需要换行了，并将高度加上这一行中的最大高度
                     */
                    height += maxHeight;
                    /**
                     * 加上 上下 间距
                     */
                    if (rows > 0) {
                        height += mVerticalInterval;
                    }

                    /**
                     * 用当前 Child 复位
                     */

                    childWidthSum = childWidth;
                    maxHeight = childHeight;
                    rows++;

                } else {
                    childWidthSum += childWidth;
                }
            }

            height += maxHeight;
            if (rows > 0) {
                height += mVerticalInterval;
            }
        } else {
            /**
             * 具体值，或者 match_parent
             */
            height = heightSize;
            int childCount = getChildCount();
            for(int i = 0;i < childCount;i++) {
                measureChild(getChildAt(i),widthMeasureSpec,heightMeasureSpec);
            }
        }

        Log.d(TAG, "onMeasure: width = " + width + ";height = " + height);
        setMeasuredDimension(width, height);

    }


    public static class LayoutParams extends MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int w,int h) {
            super(w,h);
        }

    }

    private int dp2px(int dp) {
        return DisplayUtil.dp2px(getContext(), dp);
    }
}
