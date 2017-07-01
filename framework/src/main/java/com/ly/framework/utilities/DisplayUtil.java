package com.ly.framework.utilities;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by ly on 2017/6/26.
 */

public class DisplayUtil {

    public static int dp2px(Context context,int dp) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context,int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,context.getResources().getDisplayMetrics());
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
