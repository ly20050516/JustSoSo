package com.ly.justsoso.sample;

import com.ly.framework.mvp.BaseDataSource;

/**
 * Created by ly on 2017/6/26.
 */

public class SampleRepository implements BaseDataSource<Object,Object> {
    @Override
    public void getDatas(Object o, DataLoadCallback<Object> callback) {

    }

    @Override
    public void emptyDatas() {

    }
}
