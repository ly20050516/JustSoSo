package com.ly.justsoso.enjoypictures;

import android.util.Log;

import com.ly.justsoso.enjoypictures.bean.Pictures;
import com.ly.justsoso.enjoypictures.common.SearchOption;
import com.ly.justsoso.framework.mvp.BaseDataSource;

/**
 * Created by LY on 7/3/2016.
 */
public class EnjoyPicturePresenter implements EnjoyPictureContract.Presenter {

    public static final String TAG = "EnjoyPicturePresenter";
    EnjoyPictureContract.View mEnjoyPictureView;
    EnjoyPictureRepository mEnjoyPictureRepository;
    SearchOption mSearchOption;
    public EnjoyPicturePresenter(EnjoyPictureRepository repository,EnjoyPictureContract.View view){
        mEnjoyPictureRepository = repository;
        mEnjoyPictureView = view;
    }
    @Override
    public void start() {
        mSearchOption = new SearchOption();
        loadData(mSearchOption);
    }

    @Override
    public void newSearch(String KeyWords) {
        mSearchOption = new SearchOption();
        mSearchOption.keywords = KeyWords;
        loadData(mSearchOption);
    }

    @Override
    public void continueSearch() {
        if(mSearchOption.responesCounts >= mSearchOption.requestCounts){
            Log.d(TAG, "continueSearch: mSearchOption.responesCounts " + mSearchOption.responesCounts);
            mSearchOption.pageNo = mSearchOption.pageNo + mSearchOption.requestCounts;
            mSearchOption.responesCounts = 0;
            loadData(mSearchOption);
        }
    }

    private void loadData(final SearchOption searchOption){
        mEnjoyPictureRepository.getDatas(searchOption, new BaseDataSource.DataLoadCallback<Pictures>() {
            @Override
            public void onDataLoadComplete(Pictures pictures) {
                mSearchOption.responesCounts = pictures.getRealCounts();
                Log.d(TAG, "onDataLoadComplete:  mSearchOption.responesCounts " +  mSearchOption.responesCounts);
                if(searchOption.pageNo == 0){
                    mEnjoyPictureView.setDatas(pictures);
                }else{
                    mEnjoyPictureView.addDatas(pictures);
                }
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(TAG, "onDataNotAvailable: ");
            }
        });
    }
}
