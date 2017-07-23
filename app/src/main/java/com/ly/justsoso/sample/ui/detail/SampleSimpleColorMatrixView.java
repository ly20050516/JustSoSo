package com.ly.justsoso.sample.ui.detail;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.ly.framework.utilities.BitmapUtil;
import com.ly.framework.utilities.ColorMatrixUtil;
import com.ly.justsoso.R;

/**
 * Created by ly on 2017/7/22.
 */

public class SampleSimpleColorMatrixView extends AbstractDetailView {

    ImageView mImageView;
    SeekBar mSeekBarRotate,mSeekBarSaturation,mSeekBarScale;
    Bitmap mBitmap;
    Bitmap mColorMatrixBitmap;
    float mRotate = 1.0f,mSaturation = 1.0f,mScale = 1.0f;

    public SampleSimpleColorMatrixView(Context context) {
        super(context);
    }

    @Override
    protected void inflat(Context context) {
        LayoutInflater.from(context).inflate(R.layout.sample_simple_color_matrix_view,this,true);

    }

    @Override
    protected void init(final Context context) {

        mImageView = (ImageView) findViewById(R.id.color_matrix_image_view);
        mSeekBarRotate = (SeekBar) findViewById(R.id.color_matrix_seek_bar_rotate);
        mSeekBarSaturation = (SeekBar) findViewById(R.id.color_matrix_seek_bar_saturation);
        mSeekBarScale = (SeekBar) findViewById(R.id.color_matrix_seek_bar_scale);
        mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.color_matrix);
        mImageView.setImageBitmap(mBitmap);

        initRotate();
        initSaturation();
        initScale();
    }

    private void initRotate() {
        mSeekBarRotate.setMax(255);
        mSeekBarRotate.setProgress(127);
        mSeekBarRotate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mRotate = (progress - 127) * 1.0f / 127 * 180;
                applySeekBarChange();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initSaturation() {
        mSeekBarSaturation.setMax(255);
        mSeekBarSaturation.setProgress(127);
        mSeekBarSaturation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                mSaturation  = progress * 1.0f / 127;
                applySeekBarChange();


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }



    private void initScale() {
        mSeekBarScale.setMax(255);
        mSeekBarScale.setProgress(127);
        mSeekBarScale.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mScale = progress * 1.0f / 127;
                applySeekBarChange();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void applySeekBarChange() {
        if(mColorMatrixBitmap == null) {
            mColorMatrixBitmap = BitmapUtil.createBitmap(mBitmap);
        }
        ColorMatrixUtil.colorMatrix(mColorMatrixBitmap,mBitmap,mRotate,mSaturation,mScale);
        if(mColorMatrixBitmap != null) {
            mImageView.setImageBitmap(mColorMatrixBitmap);
        }
    }

}
