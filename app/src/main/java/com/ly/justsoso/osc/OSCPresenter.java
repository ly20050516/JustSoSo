package com.ly.justsoso.osc;

/**
 * Created by LY on 2016-07-10.
 */
public class OSCPresenter implements OSCContract.Presenter {

    OSCContract.View mGameCenterView;
    OSCRepository mGameCenterRepository;

    public OSCPresenter(OSCContract.View view, OSCRepository model){
        mGameCenterView = view;
        mGameCenterRepository = model;
    }
    @Override
    public void start() {

    }

    @Override
    public void end() {

    }
}
