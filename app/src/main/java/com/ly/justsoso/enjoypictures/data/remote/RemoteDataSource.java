package com.ly.justsoso.enjoypictures.data.remote;


import com.ly.framework.mvp.BaseDataSource;
import com.ly.justsoso.enjoypictures.bean.Pictures;
import com.ly.justsoso.enjoypictures.common.SearchOption;
import com.ly.justsoso.enjoypictures.picture.PicturesRepository;

/**
 * Created by LY on 7/3/2016.
 */
public class RemoteDataSource implements BaseDataSource<SearchOption,Pictures> {

    @Override
    public void getDatas(SearchOption searchOption, final DataLoadCallback<Pictures> callback) {

        PicturesRepository picturesRepository = new PicturesRepository(callback);
        picturesRepository.getAllPictures(searchOption.keywords,String.valueOf(searchOption.pageNo),searchOption.requestCounts);

    }

    @Override
    public void emptyDatas() {

    }
}
