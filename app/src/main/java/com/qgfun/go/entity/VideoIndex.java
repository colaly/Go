package com.qgfun.go.entity;



import java.io.Serializable;

/**
 * @author : LLY
 * @date : 2020/2/9 22:17
 * description : TODO
 */

public class VideoIndex implements Serializable {
    String id;
    String name;
    String type;
    String from;
    String last;

    public VideoIndex(String id, String name, String type, String from, String last) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.from = from;
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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }
}
