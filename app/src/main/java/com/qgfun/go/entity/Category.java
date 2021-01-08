package com.qgfun.go.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author LLY
 * date: 2020/4/18 10:39
 * package name: com.qgfun.go.entity
 */
public class Category  implements Parcelable {
    private String category;
    private String remark;
    private String id;

    public Category(String category, String id) {
        this.category = category;
        this.id = id;
    }

    public Category(String category, String id,String remark) {
        this.category = category;
        this.id = id;
        this.remark = remark;
    }

    public String getCategory() {
        return category;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.category);
        dest.writeString(this.remark);
        dest.writeString(this.id);
    }

    protected Category(Parcel in) {
        this.category = in.readString();
        this.remark = in.readString();
        this.id = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
