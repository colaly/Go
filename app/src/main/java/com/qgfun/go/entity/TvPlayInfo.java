package com.qgfun.go.entity;

import java.util.List;

/**
 * @author LLY
 * date: 2020/4/21 21:58
 * package name: com.qgfun.go.entity
 * description：TODO
 */
public class TvPlayInfo {

    /**
     * timestamp : 1587477556
     * code : 200
     * msg : ok
     * data : {"urls":[{"name":"源1","url":"http://107.148.240.185:13164/vplay.m3u8?token=P77bJfByb0D7JeKWsxclPnNi&tid=itv&id=11"},{"name":"源2","url":"http://107.148.240.185:13164/vplay.m3u8?token=P77bJfByb0D7JeKWsxclPnNi&tid=itv&id=11&p=1"}],"notice":["00:00 精彩节目","01:00 精彩节目","02:00 精彩节目","03:00 精彩节目","04:00 精彩节目","05:00 精彩节目","06:00 精彩节目","07:00 精彩节目","08:00 精彩节目","09:00 精彩节目","10:00 精彩节目","11:00 精彩节目","12:00 精彩节目","13:00 精彩节目","14:00 精彩节目","15:00 精彩节目","16:00 精彩节目","17:00 精彩节目","18:00 精彩节目","19:00 精彩节目","20:00 精彩节目","21:00 精彩节目","22:00 精彩节目","23:00 精彩节目"]}
     */

    private String timestamp;
    private int code;
    private String msg;
    private DataBean data;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<UrlsBean> urls;
        private List<String> notice;

        public List<UrlsBean> getUrls() {
            return urls;
        }

        public void setUrls(List<UrlsBean> urls) {
            this.urls = urls;
        }

        public List<String> getNotice() {
            return notice;
        }

        public void setNotice(List<String> notice) {
            this.notice = notice;
        }

        public static class UrlsBean {
            /**
             * name : 源1
             * url : http://107.148.240.185:13164/vplay.m3u8?token=P77bJfByb0D7JeKWsxclPnNi&tid=itv&id=11
             */

            private String name;
            private String url;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
