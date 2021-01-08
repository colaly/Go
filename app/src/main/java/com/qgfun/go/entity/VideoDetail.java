package com.qgfun.go.entity;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * @author : LLY
 * @date : 2020/2/9 23:46
 * description : TODO
 */

public class VideoDetail implements Serializable {
    private String id;
    private String name;
    private String type;
    private String pic;
    private String note;
    private String actor;
    private String director;
    private String des;

    private List<VideoUrl> videoUrls;
    private UrlResources urlResources;
    private String last;

    public VideoDetail() {
        super();
    }

    public VideoDetail(String id, String name, String type, String pic, String note,String actor, String director, String des, List<VideoUrl> videoUrls, UrlResources urlResources, String last) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.pic = pic;
        this.note = note;
        this.actor = actor;
        this.director = director;
        this.des = des;
        this.videoUrls = videoUrls;
        this.urlResources = urlResources;
        this.last = last;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public List<VideoUrl> getVideoUrls() {
        return videoUrls;
    }

    public void setVideoUrls(List<VideoUrl> videoUrls) {
        this.videoUrls = videoUrls;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public UrlResources getUrlResources() {
        return urlResources;
    }

    public void setUrlResources(UrlResources urlResources) {
        this.urlResources = urlResources;
    }

    @Override
    public String toString() {
        return JSONObject.toJSON(this).toString();
    }
}
