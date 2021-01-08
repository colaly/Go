package com.qgfun.go.entity;

/**
 * @author LLY
 */
public class TiktokBean {
    public String title;
    public String category;
    public String tag;
    public String coverImgUrl;
    public long createTime;
    public String videoPlayUrl;

    public TiktokBean(String title, String category, String tag, String coverImgUrl, long createTime, String videoPlayUrl) {
        this.title = title;
        this.category = category;
        this.tag = tag;
        this.coverImgUrl = coverImgUrl;
        this.createTime = createTime;
        this.videoPlayUrl = videoPlayUrl;
    }

    public TiktokBean() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getVideoPlayUrl() {
        return videoPlayUrl;
    }

    public void setVideoPlayUrl(String videoPlayUrl) {
        this.videoPlayUrl = videoPlayUrl;
    }
}
