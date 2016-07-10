package com.ly.justsoso.enjoypictures;

import com.ly.justsoso.enjoypictures.bean.Pictures;
import com.ly.justsoso.framework.mvp.BasePresenter;
import com.ly.justsoso.framework.mvp.BaseView;

/**
 * Created by LY on 7/3/2016.
 */
public interface EnjoyPictureContract {

    interface View extends BaseView<Presenter>{

        void setDatas(Pictures pictures);
        void addDatas(Pictures pictures);
    }

    interface Presenter extends BasePresenter{

        void newSearch(String KeyWords);
        void newSearch();
        void continueSearch();
    }
}
