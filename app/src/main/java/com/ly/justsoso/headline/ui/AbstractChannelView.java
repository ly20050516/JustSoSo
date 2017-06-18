package com.ly.justsoso.headline.ui;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ly.justsoso.headline.bean.ViewPageTabTitle;

/**
 * Created by LY on 2017-06-11.
 */

public abstract class AbstractChannelView extends AbstractView {

    ViewPageTabTitle mViewPageTabTitle;

    public AbstractChannelView(@NonNull Context context) {
        super(context);
    }

    public ViewPageTabTitle getViewPageTabTitle() {
        return mViewPageTabTitle;
    }

    public void setViewPageTabTitle(ViewPageTabTitle mViewPageTabTitle) {
        this.mViewPageTabTitle = mViewPageTabTitle;
    }

    public String getViewPageType() {
        return mViewPageTabTitle.getChannelId();
    }
}
