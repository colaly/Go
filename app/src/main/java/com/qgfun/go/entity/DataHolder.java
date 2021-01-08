package com.qgfun.go.entity;

/**
 * @author LLY
 */
public class DataHolder {
    private VideoDetail data;

    public VideoDetail getData() {
        return data;
    }

    public void setData(VideoDetail data) {
        this.data = data;
    }

    private static final DataHolder holder = new DataHolder();

    public static DataHolder getInstance() {
        return holder;
    }
}
