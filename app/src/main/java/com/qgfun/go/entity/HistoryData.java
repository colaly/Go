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
public class HistoryData extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    Long id;

    @Column
    @Unique
    String key;


    public HistoryData(String key) {
        this.key = key;
    }

    public HistoryData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
