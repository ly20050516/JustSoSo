package com.ly.justsoso.headline.bean;

/**
 * Created by LY on 2017-06-11.
 */

public class NewsItem {
    String imgurl;
    boolean has_content;
    String docUrl;
    long id;
    String time;
    String title;
    String channelname;

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public boolean isHas_content() {
        return has_content;
    }

    public void setHas_content(boolean has_content) {
        this.has_content = has_content;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannelname() {
        return channelname;
    }

    public void setChannelname(String channelname) {
        this.channelname = channelname;
    }
}
