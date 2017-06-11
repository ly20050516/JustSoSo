package com.ly.justsoso.headline;

import com.ly.framework.mvp.BaseDataSource;
import com.ly.framework.mvp.BasePresenter;
import com.ly.framework.mvp.BaseView;
import com.ly.justsoso.headline.bean.NewsList;
import com.ly.justsoso.headline.common.RequestNewsList;

/**
 * Created by LY on 2017-06-10.
 */

public interface HeadLineContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        void requestList(RequestNewsList requestNewsList,BaseDataSource.DataLoadCallback<NewsList> callback);
    }
}
