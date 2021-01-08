package com.qgfun.go.event;


/**
 * @author LLY
 */
public class PlayEvent {
    private boolean isShow;
    private String url;

    private PlayEvent() {
    }

    public boolean isShow() {
        return isShow;
    }

    public String getUrl() {
        return url;
    }

    private PlayEvent(Builder builder) {
        isShow = builder.isShow;
        url = builder.url;
    }


    public static final class Builder {
        private boolean isShow;
        private String url;

        public Builder() {
        }

        public Builder isShow(boolean val) {
            isShow = val;
            return this;
        }

        public Builder url(String val) {
            url = val;
            return this;
        }

        public PlayEvent build() {
            return new PlayEvent(this);
        }
    }
}
