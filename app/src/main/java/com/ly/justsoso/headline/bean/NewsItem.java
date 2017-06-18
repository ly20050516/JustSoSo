package com.ly.justsoso.headline.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by LY on 2017-06-11.
 */

@Entity
public class NewsItem {
    @Id
    Long _id;
    String imgurl;
    boolean has_content;
    String docUrl;
    long id;
    String time;
    String title;
    String channelname;

    @Generated(hash = 179679663)
    public NewsItem(Long _id, String imgurl, boolean has_content, String docUrl,
                    long id, String time, String title, String channelname) {
        this._id = _id;
        this.imgurl = imgurl;
        this.has_content = has_content;
        this.docUrl = docUrl;
        this.id = id;
        this.time = time;
        this.title = title;
        this.channelname = channelname;
    }

    @Generated(hash = 1697690472)
    public NewsItem() {
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof NewsItem)) {
            return false;
        }

        NewsItem other = (NewsItem) o;
        return other.getHas_content() == has_content
                && other.getChannelname().equals(channelname)
                && other.getDocUrl().equals(docUrl)
                && other.getId() == id
                && other.getImgurl().equals(imgurl)
                && other.getTime().equals(time)
                && other.getTitle().equals(title);
    }

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

    public boolean getHas_content() {
        return this.has_content;
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
