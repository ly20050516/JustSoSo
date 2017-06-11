package com.ly.justsoso.headline;

import com.ly.framework.mvp.BaseDataSource;
import com.ly.justsoso.headline.bean.NewsList;
import com.ly.justsoso.headline.common.RequestNewsList;
import com.ly.justsoso.headline.utils.ConstantUtils;

/**
 * Created by LY on 2017-06-10.
 */

public class HeadLinePresenter implements HeadLineContract.Presenter {

    HeadLineContract.View mHeadLineView;
    HeadLineRepository mHeadLineRepository;

    public HeadLinePresenter(HeadLineRepository mHeadLineRepository,HeadLineContract.View mHeadLineView) {
        this.mHeadLineRepository = mHeadLineRepository;
        this.mHeadLineView = mHeadLineView;
    }
    @Override
    public void start() {

    }

    @Override
    public void end() {

    }

    @Override
    public void requestList(RequestNewsList requestNewsList, BaseDataSource.DataLoadCallback<NewsList> callback) {
        mHeadLineRepository.getDatas(requestNewsList,callback);
    }
}
