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
    RequestNewsList requestNewsList;
    public HeadLinePresenter(HeadLineRepository mHeadLineRepository,HeadLineContract.View mHeadLineView) {
        this.mHeadLineRepository = mHeadLineRepository;
        this.mHeadLineView = mHeadLineView;
    }
    @Override
    public void start() {
        requestNewsList = new RequestNewsList();
        requestNewsList.setType(ConstantUtils.TYPE_VALUE_EDU);
        requestNewsList.setPage(1);
        requestNewsList.setLimit(10);
        mHeadLineRepository.getDatas(requestNewsList, new BaseDataSource.DataLoadCallback<NewsList>() {
            @Override
            public void onDataLoadComplete(NewsList newsList) {

            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void end() {

    }
}
