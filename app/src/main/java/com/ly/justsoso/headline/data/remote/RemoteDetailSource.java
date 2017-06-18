package com.ly.justsoso.headline.data.remote;

import com.alibaba.fastjson.JSON;
import com.ly.framework.mvp.BaseDataSource;
import com.ly.justsoso.headline.bean.NewsDetail;
import com.ly.justsoso.headline.request.RequestDetail;

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

public class RemoteDetailSource implements BaseDataSource<RequestDetail,NewsDetail> {

    public static final String BASE_DETAIL_URL = "http://wangyi.butterfly.mopaasapp.com/detail/api";
    public static final String KEY_SIMPLE_ID = "simpleId";

    @Override
    public void getDatas(RequestDetail requestDetail, final DataLoadCallback<NewsDetail> callback) {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .addPathSegment("detail")
                .addPathSegment("api")
                .host("wangyi.butterfly.mopaasapp.com")
                .addQueryParameter(KEY_SIMPLE_ID,String.valueOf(requestDetail.getSimpleId()))
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
                NewsDetail newsDetail = JSON.parseObject(json,NewsDetail.class);
                callback.onDataLoadComplete(newsDetail);
            }
        });
    }

    @Override
    public void emptyDatas() {

    }
}
