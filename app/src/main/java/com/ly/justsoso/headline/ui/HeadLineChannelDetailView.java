package com.ly.justsoso.headline.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.webkit.WebView;

import com.ly.framework.mvp.BaseDataSource;
import com.ly.justsoso.R;
import com.ly.justsoso.headline.HeadLineContract;
import com.ly.justsoso.headline.bean.NewsDetail;
import com.ly.justsoso.headline.bean.NewsItem;
import com.ly.justsoso.headline.request.RequestDetail;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by LY on 2017-06-18.
 */

public class HeadLineChannelDetailView extends AbstractDetailView{

    WebView mDetailWebView;
    NewsItem mNewItem;
    HeadLineContract.Presenter mHeadLineDetailPresenter;
    public HeadLineChannelDetailView(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.head_line_channel_detail,this,true);
        mDetailWebView = (WebView) findViewById(R.id.head_line_detail_web_view);
    }

    public void setHeadLineDetailPresenter(HeadLineContract.Presenter headLineDetailPresenter) {
        this.mHeadLineDetailPresenter = headLineDetailPresenter;
    }

    public void updateView(NewsItem newsItem) {
        if(mNewItem != null && mNewItem.equals(newsItem)) {
            return;
        }
        mNewItem = newsItem;
        RequestDetail requestDetail = new RequestDetail();
        requestDetail.setSimpleId(mNewItem.getId());
        mHeadLineDetailPresenter.requestDetail(requestDetail, new BaseDataSource.DataLoadCallback<NewsDetail>() {
            @Override
            public void onDataLoadComplete(NewsDetail newsDetail) {
                Observable.just(newsDetail)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<NewsDetail>() {
                            @Override
                            public void call(NewsDetail newsDetail) {
                                mDetailWebView.loadDataWithBaseURL(null, newsDetail.getContent(), "text/html", "utf-8", null);
                            }
                        });

            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}
