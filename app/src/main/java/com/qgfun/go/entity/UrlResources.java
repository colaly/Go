package com.qgfun.go.entity;

import com.alibaba.fastjson.JSONObject;
import com.qgfun.go.util.Log;

public class UrlResources {
    String flag;
    String url;
    String from;

    public UrlResources() {
    }

    public UrlResources(String flag, String url, String from) {
        this.flag = flag;
        this.url = url;
        this.from = from;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFlag() {
        return flag;
    }

    public String getUrl() {
        return url;
    }

    public String getFrom() {
        return from;
    }

    public static String toJsonString(UrlResources urlReSources){
        return JSONObject.toJSON(urlReSources).toString();
    }

    public static UrlResources jsonToObject(String json){
        Log.i("jsonToObject:%s",json);
        UrlResources urlResources = JSONObject.parseObject(json, UrlResources.class);
        Log.i(urlResources.toString());
        return urlResources;
    }

    @Override
    public String toString() {
        return JSONObject.toJSON(this).toString();
    }
}
