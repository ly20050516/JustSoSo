package com.ly.justsoso.sample.ui.detail;

import android.content.Context;
import android.graphics.Matrix;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ly.justsoso.R;

/**
 * Created by ly on 2017/7/19.
 */

public class MatrixInActionView extends AbstractDetailView implements View.OnClickListener{

    public static final String TAG = "MatrixInActionView";
    ImageView mImageView;
    Button[] mButtons;

    public MatrixInActionView(Context context) {
        super(context);
    }

    @Override
    protected void inflat(Context context) {
        LayoutInflater.from(context).inflate(R.layout.sample_matrix_in_action_view,this,true);
    }

    @Override
    protected void init(Context context) {

        int[] button_ids = new int[]{
                R.id.matrix_in_action_rotate,
                R.id.matrix_in_action_scale,
                R.id.matrix_in_action_skew_x,
                R.id.matrix_in_action_skew_y,
                R.id.matrix_in_action_symmetry_x,
                R.id.matrix_in_action_symmetry_y,
                R.id.matrix_in_action_translate_pre,
                R.id.matrix_in_action_translate_post,
        };

        mImageView = (ImageView) findViewById(R.id.matrix_in_action_image_view);
        mButtons = new Button[button_ids.length];
        for (int i = 0;i < button_ids.length;i++) {
            mButtons[i] = (Button) findViewById(button_ids[i]);
            mButtons[i].setOnClickListener(this);
        }
        mImageView.setImageResource(R.drawable.matrix_in_action);
        mImageView.setScaleType(ImageView.ScaleType.MATRIX);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.matrix_in_action_rotate) {
            onRotate();
        }else if(id == R.id.matrix_in_action_scale) {
            onScale();
        }else if(id == R.id.matrix_in_action_skew_x) {
            onSkewX();
        }else if(id == R.id.matrix_in_action_skew_y) {
            onSkewY();
        }else if(id == R.id.matrix_in_action_symmetry_x) {
            onSymmetryX();
        }else if(id == R.id.matrix_in_action_symmetry_y) {
            onSymmetryY();
        }else if(id == R.id.matrix_in_action_translate_pre) {
            onTranslatePre();
        }else if(id == R.id.matrix_in_action_translate_post) {
            onTranslatePost();
        }
    }

    private void onRotate() {
        Matrix matrix = new Matrix();
        matrix.reset();
        mImageView.setImageMatrix(matrix);
        logMatrix("onRotate before",matrix);
        matrix = new Matrix();

        matrix.setRotate(45,mImageView.getMeasuredWidth() / 2,getMeasuredHeight() / 2);
        logMatrix("onRotate after",matrix);
        mImageView.setImageMatrix(matrix);
        matrix = mImageView.getImageMatrix();
        logMatrix("onRotate after 2",matrix);

    }

    private void onScale() {
        Matrix matrix = new Matrix();
        matrix.reset();
        mImageView.setImageMatrix(matrix);
        logMatrix("onScale before",matrix);
        matrix = new Matrix();

        matrix.setScale(2,2);
        logMatrix("onScale after",matrix);
        mImageView.setImageMatrix(matrix);
        matrix = mImageView.getImageMatrix();
        logMatrix("onScale after 2",matrix);
    }

    private void onSkewX() {
        Matrix matrix = new Matrix();
        matrix.reset();
        mImageView.setImageMatrix(matrix);
        logMatrix("onSkewX before",matrix);
        matrix = new Matrix();

        matrix.setSkew(-0.3f,0);
        logMatrix("onSkewX after",matrix);
        mImageView.setImageMatrix(matrix);
        matrix = mImageView.getImageMatrix();
        logMatrix("onSkewX after 2",matrix);
    }

    private void onSkewY() {
        Matrix matrix = new Matrix();

        matrix.reset();
        mImageView.setImageMatrix(matrix);
        logMatrix("onSkewY before",matrix);
        matrix = new Matrix();

        matrix.setSkew(0,-0.3f);
        logMatrix("onSkewY after",matrix);
        mImageView.setImageMatrix(matrix);
        matrix = mImageView.getImageMatrix();
        logMatrix("onSkewY after 2",matrix);
    }

    private void onSymmetryX() {
        Matrix matrix = new Matrix();

        float matrix_values[] = {1f, 0f, 0f, 0f, -1f, 0f, 0f, 0f, 1f};
        matrix.reset();
        mImageView.setImageMatrix(matrix);
        logMatrix("onSymmetryX before",matrix);
        matrix = new Matrix();

        matrix.setValues(matrix_values);
        logMatrix("onSymmetryX after",matrix);
        mImageView.setImageMatrix(matrix);
        matrix = mImageView.getImageMatrix();
        logMatrix("onSymmetryX after 2",matrix);
    }

    private void onSymmetryY() {
        Matrix matrix = new Matrix();

        float matrix_values[] = {-1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f};
        matrix.reset();
        mImageView.setImageMatrix(matrix);
        logMatrix("onSymmetryY before",matrix);
        matrix = new Matrix();

        matrix.setValues(matrix_values);
        logMatrix("onSymmetryY after",matrix);
        mImageView.setImageMatrix(matrix);
        matrix = mImageView.getImageMatrix();
        logMatrix("onSymmetryY after 2",matrix);
    }

    private void onTranslatePre() {
        Matrix matrix = new Matrix();

        matrix.reset();
        mImageView.setImageMatrix(matrix);
        logMatrix("onSymmetryY before",matrix);
        matrix = new Matrix();

        matrix.preTranslate(10,0);
        logMatrix("onSymmetryY after",matrix);
        mImageView.setImageMatrix(matrix);
        matrix = mImageView.getImageMatrix();
        logMatrix("onSymmetryY after 2",matrix);
    }

    private void onTranslatePost() {
        Matrix matrix = new Matrix();
        matrix.reset();
        mImageView.setImageMatrix(matrix);
        logMatrix("onSymmetryY before",matrix);
        matrix = new Matrix();

        matrix.postTranslate(10,0);
        logMatrix("onSymmetryY after",matrix);
        mImageView.setImageMatrix(matrix);
        matrix = mImageView.getImageMatrix();
        logMatrix("onSymmetryY after 2",matrix);
    }

    private void logMatrix(String duration,Matrix matrix) {
        Log.d(TAG, "logMatrix: duration = " + duration);
        float[] matrix_value = new float[9];
        matrix.getValues(matrix_value);
        StringBuilder stringButilder = new StringBuilder();
        for(int i = 0;i < 3;i++) {
            for(int j = 0;j < 3;j++) {
                stringButilder.append("[" + matrix_value[3 * i + j] + "]\t");
            }
            stringButilder.append("\n");
        }

        Log.d(TAG, stringButilder.toString());
    }
}
