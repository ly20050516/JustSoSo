package com.ly.justsoso.sample.ui.detail;

import android.content.Context;
import android.view.LayoutInflater;

import com.ly.justsoso.R;

/**
 * Created by ly on 2017/7/19.
 */

public class SampleFlowView extends AbstractDetailView {

    public static final String TAG = "SampleFlowView";
    public SampleFlowView(Context context) {
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
