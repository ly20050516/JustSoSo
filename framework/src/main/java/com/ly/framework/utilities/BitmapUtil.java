package com.ly.framework.utilities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by ly on 2017/7/22.
 */

public class BitmapUtil {
    public static final String TAG = "BitmapUtil";
    public static Bitmap drawableToBitmap(Drawable drawable) {

        if(drawable == null) {
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(

                drawable.getIntrinsicWidth(),

                drawable.getIntrinsicHeight(),

                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888

                        : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;

    }

    public static Bitmap createBitmap(Bitmap in) {
        if(in == null) {
            return null;
        }
        Log.d(TAG, "createBitmap: width = " + in.getWidth() + ";height = " + in.getHeight());
        Bitmap out = Bitmap.createBitmap(in.getWidth(),in.getHeight(), Bitmap.Config.ARGB_8888);
        return out;
    }
}
