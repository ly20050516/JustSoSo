package com.ly.justsoso.enjoypictures.common;

/**
 * Created by LY on 7/3/2016.
 */
public class SearchOption {
    public String keywords = "Android";
    public int pageNo = 0;
    public int requestCounts = 40;
    public int responesCounts;

    @Override
    public boolean equals(Object o) {
        SearchOption c = (SearchOption) o;
        return keywords.equals(c.keywords);
    }
}
