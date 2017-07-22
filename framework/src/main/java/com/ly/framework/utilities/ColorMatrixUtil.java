package com.ly.framework.utilities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * Created by ly on 2017/7/22.
 */

public class ColorMatrixUtil {

    public static Bitmap colorMatrix(Bitmap in,float rotate,float saturation,float scale) {

        if(in == null || in.isRecycled()) {

            return null;
        }

        Bitmap out = Bitmap.createBitmap(in.getWidth(),in.getHeight(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        Canvas canvas = new Canvas(out);

        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);

        ColorMatrix huaMatrix = new ColorMatrix();
        huaMatrix.setRotate(0,rotate);
        huaMatrix.setRotate(1,rotate);
        huaMatrix.setRotate(2,rotate);

        ColorMatrix lightMatrix = new ColorMatrix();
        lightMatrix.setScale(scale,scale,scale,1);

        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.postConcat(huaMatrix);
        colorMatrix.postConcat(lightMatrix);
        colorMatrix.postConcat(saturationMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

        canvas.drawBitmap(in,0,0,paint);

        return out;
    }
}
