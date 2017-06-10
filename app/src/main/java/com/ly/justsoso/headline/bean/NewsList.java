package com.ly.justsoso.headline.bean;

import java.util.List;

/**
 * Created by LY on 2017-06-11.
 */

public class NewsList {
    int size;
    List<NewsItem> list;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<NewsItem> getList() {
        return list;
    }

    public void setList(List<NewsItem> list) {
        this.list = list;
    }
}
