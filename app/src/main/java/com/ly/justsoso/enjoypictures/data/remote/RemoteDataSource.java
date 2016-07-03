package com.ly.justsoso.enjoypictures.data.remote;



import com.alibaba.fastjson.JSON;
import com.ly.justsoso.enjoypictures.bean.Pictures;
import com.ly.justsoso.enjoypictures.common.Constants;
import com.ly.justsoso.enjoypictures.common.SearchOption;
import com.ly.justsoso.framework.mvp.BaseDataSource;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by LY on 7/3/2016.
 */
public class RemoteDataSource implements BaseDataSource<SearchOption,Pictures>{


    @Override
    public void getDatas(SearchOption searchOption, final DataLoadCallback<Pictures> callback) {

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody formBuild = null;
        try {
            formBuild = new FormBody.Builder().add(Constants.KEY_WORD, URLEncoder.encode(searchOption.keywords, "utf-8"))
                    .add(Constants.PAGE_NO,"" + searchOption.pageNo)
                    .add(Constants.PAGE_COUNT,"" + searchOption.requestCounts)
                    .build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(null == formBuild){
            callback.onDataNotAvailable();
            return;
        }
        Request request = new Request.Builder().url(Constants.PICTURE_SERVER).post(formBuild).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onDataNotAvailable();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonString = response.body().string();
                Pictures pictures = JSON.parseObject(jsonString,Pictures.class);
                callback.onDataLoadComplete(pictures);
            }
        });
    }

    @Override
    public void emptyDatas() {

    }
}
