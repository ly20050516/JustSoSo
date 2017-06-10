package com.ly.justsoso.headline.data.local;

import com.ly.framework.mvp.BaseDataSource;
import com.ly.justsoso.headline.bean.NewsList;
import com.ly.justsoso.headline.common.RequestNewsList;

/**
 * Created by LY on 2017-06-11.
 */

public class LocalListSource implements BaseDataSource<RequestNewsList,NewsList> {
    @Override
    public void getDatas(RequestNewsList requestNewsList, DataLoadCallback<NewsList> callback) {

    }

    @Override
    public void emptyDatas() {

    }
}
