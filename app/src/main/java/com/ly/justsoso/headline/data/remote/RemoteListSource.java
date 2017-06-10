package com.ly.justsoso.headline.data.remote;

import com.alibaba.fastjson.JSON;
import com.ly.framework.mvp.BaseDataSource;
import com.ly.justsoso.headline.bean.NewsList;
import com.ly.justsoso.headline.common.RequestNewsList;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by LY on 2017-06-11.
 */

public class RemoteListSource implements BaseDataSource<RequestNewsList,NewsList> {

    public static final String BASE_LIST_URL = "http://wangyi.butterfly.mopaasapp.com/news/api";
    public static final String KEY_TYPE = "type";
    public static final String KEY_PAGE = "page";
    public static final String KEY_LIMIT = "limit";

    @Override
    public void getDatas(RequestNewsList requestNewsList, final DataLoadCallback<NewsList> callback) {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("wangyi.butterfly.mopaasapp.com")
                .addPathSegment("news")
                .addPathSegment("api")
                .addQueryParameter(KEY_TYPE,requestNewsList.getType())
                .addQueryParameter(KEY_PAGE,String.valueOf(requestNewsList.getPage()))
                .addQueryParameter(KEY_LIMIT,String.valueOf(requestNewsList.getLimit()))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url(httpUrl)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                NewsList newsList = JSON.parseObject(json,NewsList.class);
                callback.onDataLoadComplete(newsList);
            }
        });
    }

    @Override
    public void emptyDatas() {

    }
}
