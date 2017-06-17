package com.ly.justsoso.headline.ui;

import android.content.Context;

/**
 * Created by LY on 2017-06-11.
 */

public class HeadLineChannelViewFactory {

    public static final int CHANNEL_VIEW_NORMAL = 0;
    public static final int CHANNEL_VIEW_DETAIL = 1;

    public static AbstractView getChannelView(Context context, int channel_view_type) {
        AbstractView channelView;
        switch (channel_view_type) {
            case CHANNEL_VIEW_NORMAL:
                channelView =  new HeadLineChannelView(context);
                break;
            case CHANNEL_VIEW_DETAIL:
                channelView = new HeadLineChannelDetailView(context);
                break;
            default:
                channelView = null;
                break;
        }
        return channelView;
    }
}
