package com.ly.justsoso.headline.ui;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.webkit.WebView;

import com.ly.justsoso.R;

/**
 * Created by LY on 2017-06-18.
 */

public class HeadLineChannelDetailView extends AbstractDetailView implements HeadLineContract.View{

    WebView mDetailWebView;

    public HeadLineChannelDetailView(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.head_line_channel_detail,this,true);
        mDetailWebView = (WebView) findViewById(R.id.head_line_detail_web_view);
    }
}
