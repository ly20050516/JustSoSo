package com.ly.justsoso.osc;


import com.ly.framework.mvp.BasePresenter;
import com.ly.framework.mvp.BaseView;

/**
 * Created by LY on 2016-07-10.
 */
public interface OSCContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
