package com.ly.justsoso.sample;

import com.ly.justsoso.sample.adapter.item.SampleItem;

/**
 * Created by ly on 2017/6/26.
 */

public class SamplePresenter implements SampleContract.Presenter {

    SampleContract.View mSampleView;

    public SamplePresenter(SampleContract.View view) {
        mSampleView = view;
    }
    @Override
    public void start() {

    }

    @Override
    public void end() {

    }

    @Override
    public void itemClick(int action,SampleItem sampleItem) {
        mSampleView.onItemClick(action,sampleItem);
    }
}
