package com.ly.justsoso.sample.ui.detail;

import android.content.Context;
import android.view.LayoutInflater;

import com.ly.justsoso.R;

/**
 * Created by ly on 2017/7/19.
 */

public class SampleFileIOView extends AbstractDetailView {

    public static final String TAG = "SampleFileIOView";
    public SampleFileIOView(Context context) {
        super(context);
    }

    @Override
    protected void inflat(Context context) {
        LayoutInflater.from(context).inflate(R.layout.sample_flow_layout_view,this,true);

    }

    @Override
    protected void init(Context context) {

    }

}
