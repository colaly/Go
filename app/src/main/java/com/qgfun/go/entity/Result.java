package com.qgfun.go.entity;

/**
 * @author LLY
 * date: 2020/3/16 17:41
 * package name: com.qgfun.go.activity
 */
public class Result {
    String timestamp;
    Integer code;
    String msg;
    Object data;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
