package com.ly.justsoso.headline.ui;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.webkit.WebView;

import com.ly.framework.mvp.BaseDataSource;
import com.ly.justsoso.R;
import com.ly.justsoso.headline.HeadLineContract;
import com.ly.justsoso.headline.bean.NewsDetail;
import com.ly.justsoso.headline.bean.NewsItem;
import com.ly.justsoso.headline.common.RequestDetail;

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
        if(mNewItem != null & mNewItem.equals(newsItem)) {
            return;
        }
        mNewItem = newsItem;
        RequestDetail requestDetail = new RequestDetail();
        requestDetail.setSimpleId(mNewItem.getId());
        mHeadLineDetailPresenter.requestDetail(requestDetail, new BaseDataSource.DataLoadCallback<NewsDetail>() {
            @Override
            public void onDataLoadComplete(NewsDetail newsDetail) {
                mDetailWebView.loadData(newsDetail.getContent(),"text/html","utf-8");
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}
