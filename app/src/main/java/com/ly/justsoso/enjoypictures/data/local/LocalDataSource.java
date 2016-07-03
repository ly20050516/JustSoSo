package com.ly.justsoso.enjoypictures.data.local;

import android.content.Context;

import com.ly.justsoso.enjoypictures.bean.Pictures;
import com.ly.justsoso.enjoypictures.common.SearchOption;
import com.ly.justsoso.framework.mvp.BaseDataSource;

/**
 * Created by LY on 7/3/2016.
 */
public class LocalDataSource implements BaseDataSource<SearchOption,Pictures> {

    public LocalDataSource(Context context){}

    @Override
    public void getDatas(SearchOption searchOption, DataLoadCallback<Pictures> callback) {

    }

    @Override
    public void emptyDatas() {

    }
}
