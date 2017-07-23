package com.ly.justsoso.sample.ui.detail;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;

import com.ly.framework.utilities.BitmapUtil;
import com.ly.framework.utilities.ColorMatrixUtil;
import com.ly.justsoso.R;

/**
 * Created by ly on 2017/7/23.
 */

public class SampleColorMatrixView extends AbstractDetailView implements TextWatcher{

    private EditText[] mEditTextArray;
    private ImageView mImageView;
    private Bitmap mBitmap;
    private Bitmap mColorMatrixBitmap;

    private float[] mColorArray;
    private int[] mIds;

    public SampleColorMatrixView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void inflat(Context context) {
        LayoutInflater.from(context).inflate(R.layout.sample_color_matrix_view,this,true);
    }

    @Override
    protected void init(Context context) {

        mEditTextArray = new EditText[20];
        mIds = new int[]{
                R.id.color_matrix_edit_text_1,R.id.color_matrix_edit_text_2,
                R.id.color_matrix_edit_text_3,R.id.color_matrix_edit_text_4,
                R.id.color_matrix_edit_text_5,R.id.color_matrix_edit_text_6,
                R.id.color_matrix_edit_text_7,R.id.color_matrix_edit_text_8,
                R.id.color_matrix_edit_text_9,R.id.color_matrix_edit_text_10,
                R.id.color_matrix_edit_text_11,R.id.color_matrix_edit_text_12,
                R.id.color_matrix_edit_text_13,R.id.color_matrix_edit_text_14,
                R.id.color_matrix_edit_text_15,R.id.color_matrix_edit_text_16,
                R.id.color_matrix_edit_text_17,R.id.color_matrix_edit_text_18,
                R.id.color_matrix_edit_text_19,R.id.color_matrix_edit_text_20
        };
        mColorArray = new float[20];
        mImageView = (ImageView) findViewById(R.id.color_matrix_image_view);
        mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.color_matrix);
        mImageView.setImageBitmap(mBitmap);

        for (int i = 0;i < 20;i++) {
            mEditTextArray[i] = (EditText) findViewById(mIds[i]);
            mEditTextArray[i].addTextChangedListener(this);
        }

        for (int i = 0;i < 20;i++) {

            if(i % 6 == 0) {
                mColorArray[i] = 1.0f;
                mEditTextArray[i].setText("1");
            }else {
                mColorArray[i] = 0.0f;
                mEditTextArray[i].setText("0");
            }
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        changeColorMatrix();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void changeColorMatrix() {
        for (int i = 0; i < 20; i++) {
            mColorArray[i] = parseStringToFloat(mEditTextArray[i].getText().toString());
        }

        if(mColorMatrixBitmap == null) {
            mColorMatrixBitmap = BitmapUtil.createBitmap(mBitmap);
        }

        mColorMatrixBitmap = BitmapUtil.createBitmap(mBitmap);
        ColorMatrixUtil.colorMatrix(mColorMatrixBitmap,mBitmap,mColorArray);
        mImageView.setImageBitmap(mColorMatrixBitmap);
    }

    private float parseStringToFloat(String string) {
        float value = 0.0f;
        try{
            value = Float.parseFloat(string);
        }catch (Exception e) {

        }

        return value;
    }
}
