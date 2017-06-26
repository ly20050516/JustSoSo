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
}
