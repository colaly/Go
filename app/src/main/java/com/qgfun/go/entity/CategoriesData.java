package com.qgfun.go.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LLY
 * date: 2020/4/16 14:57
 * package name: com.qgfun.beauty.base
 */
public class CategoriesData {

    public static List<Category> getDouBanCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("电影", "电影"));
        categories.add(new Category("电视剧", "电视剧"));
        categories.add(new Category("综艺", "综艺"));
        categories.add(new Category("动漫", "动漫"));
        return categories;
    }

    public static List<Category> getUpdateCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("综艺", "3"));
        categories.add(new Category("国产剧", "12"));
        categories.add(new Category("动漫", "4"));
        categories.add(new Category("欧美剧", "15"));
        //categories.add(new Category("伦理", "17"));
        categories.add(new Category("全部", "0"));
        return categories;
    }
}
