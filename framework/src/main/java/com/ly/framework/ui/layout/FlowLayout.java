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
    private int interval;

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

        interval = dp2px(5);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        if (a != null) {
            interval = (int) a.getDimension(R.styleable.FlowLayout_interval, dp2px(5));
            a.recycle();
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        Log.d(TAG, "onLayout: changed = " + changed + ";l = " + l + ";t = " + t + ";b = " + b);

        int childCount = getChildCount();

        if(!changed) {
            return;
        }

        int left = 0;
        int top = 0;
        int width = getWidth();
        int height = getHeight();
        int maxHeight = 0;
        Log.d(TAG, "onLayout: width = " + width + ";height = " + height);
        for (int i = 0; i < childCount; i++) {

            View child = getChildAt(i);
            FlowLayout.LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();

            int childWidth = child.getWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int childHeight = child.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

            if(childHeight > maxHeight) {
                maxHeight = childHeight;
            }
            if(left + childWidth > width) {
                /**
                 * 超过一行的宽度了，换行
                 */
                top = top + maxHeight + interval;
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
             * Wrap_content，计算出共有多少行，行高 = 每一行的最大子高度 + interval。注意，在仅一行的情况下，不要加上  interval
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

                childWidth = layoutParams.width + layoutParams.leftMargin + layoutParams.rightMargin;
                childHeight = layoutParams.height + layoutParams.topMargin + layoutParams.bottomMargin;

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
                        height += interval;
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
                height += interval;
            }
        } else {
            /**
             * 具体值，或者 match_parent
             */
            height = heightSize;
        }

        Log.d(TAG, "onMeasure: width = " + width + ";height = " + height);
        setMeasuredDimension(width, height);

    }


    public static class LayoutParams extends MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

    }

    private int dp2px(int dp) {
        return DisplayUtil.dp2px(getContext(), dp);
    }
}
