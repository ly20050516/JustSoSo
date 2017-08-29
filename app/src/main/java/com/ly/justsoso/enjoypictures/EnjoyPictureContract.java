package com.ly.justsoso.enjoypictures;

import com.ly.framework.mvp.BasePresenter;
import com.ly.framework.mvp.BaseView;
import com.ly.justsoso.enjoypictures.bean.Pictures;


/**
 * Created by LY on 7/3/2016.
 */
public interface EnjoyPictureContract {

    interface View extends BaseView<Presenter> {


        void setDatas(Pictures pictures);
        void addDatas(Pictures pictures);
    }

    interface Presenter extends BasePresenter {

        void newSearch(String KeyWords);
        void newSearch();
        void continueSearch();
    }
}
