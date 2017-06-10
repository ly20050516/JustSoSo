package com.ly.justsoso.enjoypictures.data.local;

import android.content.Context;

import com.ly.framework.mvp.BaseDataSource;
import com.ly.justsoso.JustSoSoApplication;
import com.ly.justsoso.enjoypictures.bean.PictureData;
import com.ly.justsoso.enjoypictures.bean.Pictures;
import com.ly.justsoso.enjoypictures.common.SearchOption;
import com.ly.justsoso.greendao.PictureDataDao;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by LY on 7/3/2016.
 */
public class LocalDataSource implements BaseDataSource<SearchOption,Pictures> {

    public LocalDataSource(Context context){

    }

    @Override
    public void getDatas(SearchOption searchOption, final DataLoadCallback<Pictures> callback) {
        Observable.create(new Observable.OnSubscribe<Pictures>() {
            @Override
            public void call(Subscriber<? super Pictures> subscriber) {
                PictureDataDao dao = JustSoSoApplication.getInstance().getDaoSession().getPictureDataDao();
                List<PictureData> pictureDataList = dao.loadAll();
                Pictures pictures = new Pictures();
                pictures.setPictures((ArrayList<PictureData>) pictureDataList);
                pictures.setRealCounts(pictureDataList.size());
                subscriber.onNext(pictures);
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .subscribe(new Observer<Pictures>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Pictures pictures) {
                callback.onDataLoadComplete(pictures);
            }
        });
    }

    @Override
    public void emptyDatas() {
        PictureDataDao dao = JustSoSoApplication.getInstance().getDaoSession().getPictureDataDao();
        dao.deleteAll();
    }
}
