package com.ly.justsoso.headline;

import com.ly.framework.mvp.BaseDataSource;
import com.ly.justsoso.headline.bean.NewsList;
import com.ly.justsoso.headline.common.RequestNewsList;

/**
 * Created by LY on 2017-06-10.
 */

public class HeadLineRepository implements BaseDataSource<RequestNewsList,NewsList> {

    BaseDataSource<RequestNewsList,NewsList> localListSource,remoteListSource;

    public HeadLineRepository(BaseDataSource<RequestNewsList,NewsList> localListSource,
                              BaseDataSource<RequestNewsList,NewsList> remoteListSource){
        this.localListSource = localListSource;
        this.remoteListSource = remoteListSource;
    }
    @Override
    public void getDatas(RequestNewsList requestNewsList, DataLoadCallback<NewsList> callback) {
        remoteListSource.getDatas(requestNewsList,callback);
    }

    @Override
    public void emptyDatas() {

    }
}
