package com.ly.justsoso.headline.bean;

/**
 * Created by LY on 2017-06-11.
 */

public class NewsDetail {

    long simple_id;
    String from;
    String old_title;
    long id;
    String time;
    String title;
    String content;

    public long getSimple_id() {
        return simple_id;
    }

    public void setSimple_id(long simple_id) {
        this.simple_id = simple_id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getOld_title() {
        return old_title;
    }

    public void setOld_title(String old_title) {
        this.old_title = old_title;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
