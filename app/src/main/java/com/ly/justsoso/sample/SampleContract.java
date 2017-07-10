package com.ly.justsoso.sample;

import com.ly.framework.mvp.BasePresenter;
import com.ly.framework.mvp.BaseView;
import com.ly.justsoso.osc.OSCContract;
import com.ly.justsoso.sample.adapter.item.SampleItem;

/**
 * Created by LY on 2017-06-18.
 */

public interface SampleContract {
    interface View extends BaseView<SampleContract.Presenter> {

        void onItemClick(int action,SampleItem sampleItem);

    }

    interface Presenter extends BasePresenter {
        void itemClick(int action,SampleItem sampleItem);
    }
}
