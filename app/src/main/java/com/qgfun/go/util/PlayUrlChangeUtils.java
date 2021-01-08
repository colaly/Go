package com.qgfun.go.util;

import com.cdnbye.sdk.P2pEngine;

/**
 * @author LLY
 * date: 2020/2/27 19:23
 * package name: com.qgfun.go.utils
 * description：TODO
 */
public class PlayUrlChangeUtils {
    public static String changeUrl(String url){
        if (url.lastIndexOf(".mp4")>0){
            return url;
        }
        url=P2pEngine.getInstance().parseStreamUrl(url);
        Log.i("changeUrl:%s",url);
        return url;
    }
}
