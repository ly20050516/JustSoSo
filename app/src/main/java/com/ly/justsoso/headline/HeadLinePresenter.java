package com.ly.justsoso.headline;

import com.ly.framework.mvp.BaseDataSource;
import com.ly.justsoso.headline.bean.NewsItem;
import com.ly.justsoso.headline.bean.NewsList;
import com.ly.justsoso.headline.common.RequestNewsList;
import com.ly.justsoso.headline.data.local.LocalDetailSource;
import com.ly.justsoso.headline.data.local.LocalListSource;
import com.ly.justsoso.headline.data.remote.RemoteDetailSource;
import com.ly.justsoso.headline.data.remote.RemoteListSource;

/**
 * Created by LY on 2017-06-10.
 */

public class HeadLinePresenter implements HeadLineContract.Presenter {

    HeadLineContract.View mHeadLineView;
    HeadLineRepository mHeadLineRepository;
    HeadLineDetailRepository mHeadLineDetailRepository;

    public HeadLinePresenter(HeadLineContract.View mHeadLineView) {
        this.mHeadLineView = mHeadLineView;
        mHeadLineRepository = new HeadLineRepository();
        mHeadLineDetailRepository = new HeadLineDetailRepository();

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

    @Override
    public void itemClick(NewsItem newsItem) {
        mHeadLineView.onItemClick(newsItem);
    }

    @Override
    public void requestDetail(NewsItem newsItem) {

    }
}
