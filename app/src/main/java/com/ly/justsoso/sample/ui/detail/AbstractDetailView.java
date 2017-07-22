package com.ly.justsoso.sample.ui.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

import com.ly.justsoso.R;

/**
 * Created by ly on 2017/7/22.
 */

public abstract class AbstractDetailView extends FrameLayout {
    public AbstractDetailView(@NonNull Context context) {
        super(context);

        setBackgroundColor(getResources().getColor(R.color.primary));
        inflat(context);
        init(context);

    }

    protected abstract void inflat(Context context);
    protected abstract void init(Context context);
}
