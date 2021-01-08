package com.qgfun.go.event;

import com.qgfun.go.enums.ViewStatusEnum;

public class WebViewStatusEvent {
    ViewStatusEnum status;

    public WebViewStatusEvent(ViewStatusEnum status) {
        this.status = status;
    }

    public ViewStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ViewStatusEnum status) {
        this.status = status;
    }
}
