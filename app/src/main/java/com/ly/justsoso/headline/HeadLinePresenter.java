package com.ly.justsoso.headline;

import com.ly.framework.mvp.BaseDataSource;
import com.ly.justsoso.headline.bean.NewsDetail;
import com.ly.justsoso.headline.bean.NewsItem;
import com.ly.justsoso.headline.bean.NewsList;
import com.ly.justsoso.headline.request.RequestDetail;
import com.ly.justsoso.headline.request.RequestNewsList;

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
    public void requestDetail(RequestDetail requestDetail, BaseDataSource.DataLoadCallback<NewsDetail> callback) {
        mHeadLineDetailRepository.getDatas(requestDetail,callback);
    }
}
