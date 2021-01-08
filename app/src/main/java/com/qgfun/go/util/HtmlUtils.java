package com.qgfun.go.util;

import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.orhanobut.logger.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import okhttp3.ResponseBody;

/**
 * @author : Cola
 * @date : 2018/10/25 11:39
 * description : HtmlUtils
 */

public class HtmlUtils {

   public static Document getHtml(String url, boolean isWebAgent, String referrer) {
        int count = 0;
        while (count < 2) {
            count++;
            try {
                String html = getHtmlResource(url, isWebAgent, referrer);
                if (TextUtils.isEmpty(html)) {
                    throw new NullPointerException();
                }
                Document document = Jsoup.parse(html);
                if (document != null || count >= 3) {
                    return document;
                }
            } catch (NullPointerException e) {
                Logger.e("Jsoup 请求HTML发生IO异常,url:%s", url);
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String getHtmlResource(String url, boolean isWebAgent, String referer) {
        try {
            String userAgent;
            if (isWebAgent) {
                userAgent = "Mozilla/5.0 (Linux; Android 8.0.0; WAS-AL00 Build/HUAWEIWAS-AL00; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/66.0.3359.126 MQQBrowser/6.2 TBS/044502 Mobile Safari/537.36 MMWEBID/2443 MicroMessenger/7.0.1380(0x27000038) Process/tools NetType/WIFI Language/zh_CN";
            } else {
                userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.92 Safari/537.36";
            }
            if (TextUtils.isEmpty(referer)) {
                referer = "http://qq.com/";
            }
            return OkGo.<ResponseBody>get(url)
                    .cacheMode(CacheMode.DEFAULT)
                    .cacheTime(-1)
                    .retryCount(3)
                    .headers("user-agent", userAgent)
                    .headers("referer", referer)
                    .execute().body().string();

        } catch (Exception e) {
            Logger.e(e.getMessage());
            return null;
        }
    }
}
