package com.qgfun.go.entity;

import com.qgfun.go.db.AppDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * @author LLY
 * date: 2020/4/9 13:46
 * package name: com.qgfun.beauty.entity
 * description：TODO
 */
@Table(database = AppDataBase.class)
public class DataCache extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    Long id;
    @Column
    @Unique
    String url;
    @Column
    String data;
    @Column
    long last;

    public DataCache() {
    }

    private DataCache(Builder builder) {
        setUrl(builder.url);
        setData(builder.data);
        setLast(builder.last);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getLast() {
        return last;
    }

    public void setLast(long last) {
        this.last = last;
    }

    public static final class Builder {
        private String url;
        private String data;
        private long last;

        public Builder() {
        }

        public Builder url(String val) {
            url = val;
            return this;
        }

        public Builder data(String val) {
            data = val;
            return this;
        }

        public Builder last(long val) {
            last = val;
            return this;
        }

        public DataCache build() {
            return new DataCache(this);
        }
    }
}
