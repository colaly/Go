package com.qgfun.go.event;

/**
 * @author LLY
 * date: 2020/4/21 10:21
 * package name: com.qgfun.go.event
 * description：TODO
 */
public class SearchViewEvent {
    boolean isShow;

    private SearchViewEvent(Builder builder) {
        isShow = builder.isShow;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public static final class Builder {
        private boolean isShow;

        public Builder() {
        }

        public Builder isShow(boolean val) {
            isShow = val;
            return this;
        }

        public SearchViewEvent build() {
            return new SearchViewEvent(this);
        }
    }
}
