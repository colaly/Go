package com.qgfun.go.enums;

import com.xuexiang.xui.widget.statelayout.MultipleStatusView;

public enum ViewStatusEnum {
    CONTENT("显示内容"),
    LOADING("加载中..."),
    ERROR("加载错误"),
    NET_ERROR("网络错误"),
    EMPTY("没有找到相关页面");
    private String description;

    ViewStatusEnum(String description) {
        this.description = description;
    }

    public static void setStatus(MultipleStatusView statusView, ViewStatusEnum status) {
        switch (status) {
            case CONTENT:
                statusView.showContent();
                break;
            case LOADING:
                statusView.showLoading();
                break;
            case ERROR:
                statusView.showError();
                break;
            case NET_ERROR:
                statusView.showNoNetwork();
                break;
            case EMPTY:
                statusView.showEmpty();
                break;
            default:
                break;
        }
    }

    public String getDescription() {
        return description;
    }
}
