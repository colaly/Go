package com.qgfun.go.entity;

import java.io.Serializable;

/**
 * @author : LLY
 * @date : 2020/2/10 9:30
 * description : TODO
 */

public class VideoUrl implements Serializable {
    String title;
    String url;

    public VideoUrl() {
        super();
    }

    public VideoUrl(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

