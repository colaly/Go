package com.qgfun.go.util;

import com.alibaba.fastjson.JSON;
import com.qgfun.go.entity.VideoUrl;

import java.util.List;

/**
 * @author LLY
 * date: 2020/3/21 15:18
 * package name: com.qgfun.go.utils
 * description：TODO
 */
public class ListStringUtils {
    public static String list2Json(List<VideoUrl> list) {
        return JSON.toJSONString(list);
    }

    public static List<VideoUrl> json2List(String json) {
        return JSON.parseArray(json, VideoUrl.class);
    }
}
