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

        Bitmap out = Bitmap.createBitmap(in.getWidth(),in.getHeight(), Bitmap.Config.ARGB_8888);
        colorMatrix(out,in,rotate,saturation,scale);
        return out;
    }

    public static void colorMatrix(Bitmap out,Bitmap in,float rotate,float saturation,float scale) {

        if(out == null || out.isRecycled() || in == null || in.isRecycled()) {
            return;
        }

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

    }
    public static Bitmap colorMatrix(Bitmap in,float[]colorArray) {

        Bitmap out = Bitmap.createBitmap(in.getWidth(),in.getHeight(), Bitmap.Config.ARGB_8888);
        colorMatrix(out,in,colorArray);
        return out;
    }

    public static void colorMatrix(Bitmap out,Bitmap in,float[]colorArray) {


        if(out == null || out.isRecycled() || in == null || in.isRecycled()) {
            return;
        }

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        Canvas canvas = new Canvas(out);

        paint.setColorFilter(new ColorMatrixColorFilter(colorArray));
        canvas.drawBitmap(in,0,0,paint);
    }
}
