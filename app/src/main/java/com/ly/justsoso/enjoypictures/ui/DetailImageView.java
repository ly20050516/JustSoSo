package com.ly.justsoso.enjoypictures.ui;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ly.justsoso.R;

/**
 * Created by LY on 6/9/2016.
 */
public class DetailImageView extends FrameLayout {

    public static final String TAG = DetailImageView.class.getCanonicalName();
    public ImageView mImageView;
    public DetailImageView(Context context) {
        super(context);
        mImageView = new ImageView(context);
        addView(mImageView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setBackgroundColor(getResources().getColor(R.color.primary));
    }
}
