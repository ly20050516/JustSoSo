package com.ly.justsoso.headline.request;

/**
 * Created by LY on 2017-06-11.
 */

public class RequestNewsList {
    String type;
    int page;
    int limit;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
