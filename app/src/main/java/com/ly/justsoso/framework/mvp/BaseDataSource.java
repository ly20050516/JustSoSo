package com.ly.justsoso.framework.mvp;

/**
 * Created by LY on 7/3/2016.
 */
public interface BaseDataSource<C,T> {

    interface DataLoadCallback<T>{
        void onDataLoadComplete(T t);
        void onDataNotAvailable();
    }
    void getDatas(C c,DataLoadCallback<T> callback);
    void emptyDatas();
}
