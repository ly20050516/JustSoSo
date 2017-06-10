package com.ly.justsoso.headline;

import com.ly.framework.mvp.BaseDataSource;
import com.ly.justsoso.headline.bean.NewsDetail;
import com.ly.justsoso.headline.common.RequestDetail;

/**
 * Created by LY on 2017-06-11.
 */

public class HeadLineDetailRepository implements BaseDataSource<RequestDetail,NewsDetail> {
    @Override
    public void getDatas(RequestDetail requestDetailList, DataLoadCallback<NewsDetail> callback) {

    }

    @Override
    public void emptyDatas() {

    }
}
