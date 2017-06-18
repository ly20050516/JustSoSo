package com.ly.justsoso.sample;

import com.ly.framework.mvp.BasePresenter;
import com.ly.framework.mvp.BaseView;
import com.ly.justsoso.osc.OSCContract;

/**
 * Created by LY on 2017-06-18.
 */

public interface SampleContract {
    interface View extends BaseView<OSCContract.Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
