package com.ly.justsoso.headline;

import com.ly.framework.mvp.BaseDataSource;
import com.ly.justsoso.headline.bean.NewsDetail;
import com.ly.justsoso.headline.request.RequestDetail;
import com.ly.justsoso.headline.data.local.LocalDetailSource;
import com.ly.justsoso.headline.data.remote.RemoteDetailSource;

/**
 * Created by LY on 2017-06-10.
 */

public class HeadLineDetailRepository implements BaseDataSource<RequestDetail,NewsDetail> {

    BaseDataSource<RequestDetail,NewsDetail> localSource,remoteSource;

    public HeadLineDetailRepository() {
        localSource = new LocalDetailSource();
        remoteSource = new RemoteDetailSource();
    }

    @Override
    public void getDatas(RequestDetail requestDetail, DataLoadCallback<NewsDetail> callback) {
        remoteSource.getDatas(requestDetail, callback);
    }

    @Override
    public void emptyDatas() {

    }
}
