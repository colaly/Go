package com.qgfun.go.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author LLY
 * date: 2020/4/18 10:05
 * package name: com.qgfun.go.entity
 * description：TODO
 */
public class DouBanVideoInfo {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * directors : ["青木荣","碇谷敦","下平佑一","又贺大介","栗山贵行","久保田雄大","青柳隆平"]
         * rate : 9.1
         * cover_x : 780
         * star : 45
         * title : 异度侵入
         * url : https://movie.douban.com/subject/34456027/
         * casts : ["津田健次郎","细谷佳正","竹内良太","市道真央","布里德卡特·塞拉·惠美"]
         * cover : https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2584476257.webp
         * id : 34456027
         * cover_y : 1101
         */

        private String rate;
        private int cover_x;
        private String star;
        private String title;
        private String url;
        private String cover;
        private String id;
        private int cover_y;
        private List<String> directors;
        private List<String> casts;

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public int getCover_x() {
            return cover_x;
        }

        public void setCover_x(int cover_x) {
            this.cover_x = cover_x;
        }

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getCover_y() {
            return cover_y;
        }

        public void setCover_y(int cover_y) {
            this.cover_y = cover_y;
        }

        public List<String> getDirectors() {
            return directors;
        }

        public void setDirectors(List<String> directors) {
            this.directors = directors;
        }

        public List<String> getCasts() {
            return casts;
        }

        public void setCasts(List<String> casts) {
            this.casts = casts;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.rate);
            dest.writeInt(this.cover_x);
            dest.writeString(this.star);
            dest.writeString(this.title);
            dest.writeString(this.url);
            dest.writeString(this.cover);
            dest.writeString(this.id);
            dest.writeInt(this.cover_y);
            dest.writeStringList(this.directors);
            dest.writeStringList(this.casts);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.rate = in.readString();
            this.cover_x = in.readInt();
            this.star = in.readString();
            this.title = in.readString();
            this.url = in.readString();
            this.cover = in.readString();
            this.id = in.readString();
            this.cover_y = in.readInt();
            this.directors = in.createStringArrayList();
            this.casts = in.createStringArrayList();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }
}
