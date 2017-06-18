package com.ly.justsoso.headline;

import com.ly.framework.mvp.BaseDataSource;
import com.ly.justsoso.headline.bean.NewsList;
import com.ly.justsoso.headline.request.RequestNewsList;
import com.ly.justsoso.headline.data.local.LocalListSource;
import com.ly.justsoso.headline.data.remote.RemoteListSource;

/**
 * Created by LY on 2017-06-10.
 */

public class HeadLineRepository implements BaseDataSource<RequestNewsList,NewsList> {

    BaseDataSource<RequestNewsList,NewsList> localListSource,remoteListSource;

    public HeadLineRepository(){
        this.localListSource = new LocalListSource();
        this.remoteListSource = new RemoteListSource();
    }
    
    @Override
    public void getDatas(RequestNewsList requestNewsList, DataLoadCallback<NewsList> callback) {
        remoteListSource.getDatas(requestNewsList,callback);
    }

    @Override
    public void emptyDatas() {

    }
}
