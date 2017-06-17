package com.ly.justsoso.headline.ui;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by LY on 2017-06-18.
 */

public class AbstractDetailView extends AbstractView {
    public AbstractDetailView(@NonNull Context context) {
        super(context);
    }

    public AbstractDetailView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AbstractDetailView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
