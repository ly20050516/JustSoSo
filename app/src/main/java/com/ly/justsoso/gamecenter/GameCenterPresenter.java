package com.ly.justsoso.gamecenter;

/**
 * Created by LY on 2016-07-10.
 */
public class GameCenterPresenter implements GameCenterContract.Presenter {

    GameCenterContract.View mGameCenterView;
    GameCenterRepository mGameCenterRepository;

    public GameCenterPresenter(GameCenterContract.View view,GameCenterRepository model){
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
