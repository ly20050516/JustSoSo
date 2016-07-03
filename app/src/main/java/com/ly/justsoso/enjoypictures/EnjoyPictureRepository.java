package com.ly.justsoso.enjoypictures;

import com.ly.justsoso.enjoypictures.bean.Pictures;
import com.ly.justsoso.enjoypictures.common.SearchOption;
import com.ly.justsoso.framework.mvp.BaseDataSource;

/**
 * Created by LY on 7/3/2016.
 */
public class EnjoyPictureRepository implements BaseDataSource<SearchOption,Pictures>{

    private BaseDataSource<SearchOption,Pictures> mLocalDataSource;
    private BaseDataSource<SearchOption,Pictures> mRemoteDataSource;

    private SearchOption mLastSearchOption;

    public EnjoyPictureRepository(BaseDataSource local,BaseDataSource remote){
        mLocalDataSource = local;
        mRemoteDataSource = remote;
    }

    @Override
    public void getDatas(SearchOption searchOption, DataLoadCallback<Pictures> callback) {
        if(mLastSearchOption == null){
            mLastSearchOption = searchOption;
        }else if(!mLastSearchOption.equals(searchOption)){
            mLocalDataSource.emptyDatas();
            mRemoteDataSource.emptyDatas();
        }
        mLastSearchOption = searchOption;
        mRemoteDataSource.getDatas(searchOption,callback);
    }

    @Override
    public void emptyDatas() {

    }
}
