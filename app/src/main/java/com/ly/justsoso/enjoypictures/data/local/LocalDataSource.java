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


/**
 * Created by LY on 7/3/2016.
 */
public class LocalDataSource implements BaseDataSource<SearchOption,Pictures> {

    public LocalDataSource(Context context){

    }

    @Override
    public void getDatas(SearchOption searchOption, final DataLoadCallback<Pictures> callback) {
        new Thread(){
            @Override
            public void run() {
                PictureDataDao dao = JustSoSoApplication.getInstance().getDaoSession().getPictureDataDao();
                List<PictureData> pictureDataList = dao.loadAll();
                Pictures pictures = new Pictures();
                pictures.setPictures((ArrayList<PictureData>) pictureDataList);
                pictures.setRealCounts(pictureDataList.size());
                callback.onDataLoadComplete(pictures);
            }
        }.start();
    }

    @Override
    public void emptyDatas() {
        PictureDataDao dao = JustSoSoApplication.getInstance().getDaoSession().getPictureDataDao();
        dao.deleteAll();
    }
}
