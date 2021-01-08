package com.qgfun.go.web;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.just.agentweb.WebViewClient;
import com.qgfun.go.entity.ResourceInfo;
import com.qgfun.go.enums.ViewStatusEnum;
import com.qgfun.go.event.PlayEvent;
import com.qgfun.go.event.TitleEvent;
import com.qgfun.go.event.WebViewStatusEvent;
import com.qgfun.go.util.ClipboardTool;
import com.qgfun.go.util.Log;
import com.qgfun.go.util.NetTool;

import org.apache.common.codec.binary.Base64;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import ren.yale.android.cachewebviewlib.WebViewCacheInterceptorInst;

/**
 * @author LLY
 */
public class CustomWebViewClient extends WebViewClient {
    private final Context context;
    private boolean isAvailable = true;
    private boolean isFirst = true;
    private boolean isClear;
    private OnVideoViewListener onVideoViewListener;


    public void setOnVideoViewListener(OnVideoViewListener onVideoViewListener) {
        this.onVideoViewListener = onVideoViewListener;
    }

    public void setClear(boolean clear) {
        isClear = clear;
    }

    public CustomWebViewClient(Context context) {
        this.context = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();
        if (onVideoViewListener != null) {
            onVideoViewListener.reload();
        }
        if (url.toLowerCase().startsWith("ed2k://") || url.toLowerCase().startsWith("magnet:?xt=")) {
            ClipboardTool.copyText(context, url);
            Toast.makeText(context, "复制下载地址成功", Toast.LENGTH_SHORT).show();
        }

        if (url.contains("https://xui.ptlogin2.qq.com/cgi-bin/xlogin")) {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("mqqapi://forward/url?src_type=web&version=1&url_prefix=" + Base64.encodeBase64String(url.getBytes(StandardCharsets.UTF_8)))));
            return true;
        }
        return super.shouldOverrideUrlLoading(view, request);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        view.getSettings().setBlockNetworkImage(true);
        view.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        EventBus.getDefault().post(new PlayEvent.Builder().url("").isShow(false).build());
        if (!NetTool.isAvailable(context)) {
            isAvailable = false;
            Toast.makeText(context, "网络不可用", Toast.LENGTH_SHORT).show();
            showWebStatus(ViewStatusEnum.NET_ERROR);
            Log.i("onPageStarted", "》》》onPageStarted");
            view.stopLoading();
        } else {
            isAvailable = true;
            if (isFirst) {
                EventBus.getDefault().post(new WebViewStatusEvent(ViewStatusEnum.LOADING));
                //execute the task
                new Handler().postDelayed(this::hideLoading, 30 * 1000);
            } else {
                showWebStatus(ViewStatusEnum.CONTENT);
            }

        }
    }


    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (isAvailable) {
            hideLoading();
            showWebStatus(ViewStatusEnum.CONTENT);
        }
        EventBusActivityScope.getDefault((Activity) context).post(new TitleEvent(view.getTitle()));
        //获取登陆后的cookie,看是否写入
        CookieManager cookieManager = CookieManager.getInstance();
        //String cookieStr = cookieManager.getCookie(url);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush();
        } else {
            CookieSyncManager.getInstance().sync();
        }
    }

    @Override
    public void onPageCommitVisible(WebView view, String url) {
        super.onPageCommitVisible(view, url);
        Log.i("onPageCommitVisible url:%s", url);
        new Handler().postDelayed(this::hideLoading, 3 * 1000);
        view.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        view.getSettings().setBlockNetworkImage(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        int statusCode = error.getErrorCode();
        Log.i("onReceivedError", "-----" + request.getUrl().toString() + "-----" + statusCode);
        if (request.isForMainFrame()) {
            Log.i("onReceivedError", "-----webError visible-----");
            showWebStatus(ViewStatusEnum.ERROR);
            Log.i("onReceivedError", "》》》onReceivedError");
        }
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        //6.0以下执行
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return;
        }
        if (400 == errorCode || 404 == errorCode || 500 == errorCode) {
            showWebStatus(ViewStatusEnum.ERROR);
            Log.i("onReceivedError old", "》》》onReceivedError old");
        }
    }


    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
        super.onReceivedHttpError(view, request, errorResponse);
        if (request.isForMainFrame()) {
            showWebStatus(ViewStatusEnum.ERROR);
            Log.i("onReceivedHttpError", "》》》onReceivedHttpError");
        }


    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        super.doUpdateVisitedHistory(view, url, isReload);
        if (isClear) {
            view.clearHistory();
            Log.i("clearHistory", url);
            isClear = false;
        }
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();
        if ("get".equalsIgnoreCase(request.getMethod())) {
            //Logger.i("shouldInterceptRequest,url:%s",url);
            int count = count(url, "http");
            if (count == 1 && (url.contains(".m3u") || url.contains(".mp4"))) {
                EventBus.getDefault().post(new PlayEvent.Builder().url(url).isShow(true).build());
            }
            WebResourceResponse webResourceResponse = myIntercept(url);
            if (webResourceResponse != null) {
                return webResourceResponse;
            }
        }

        return WebViewCacheInterceptorInst.getInstance().interceptRequest(request);
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        //Logger.i("过时 shouldInterceptRequest,url:%s",url);
        if (url.contains("z_stat.php") || url.contains("googletagmanager") || url.contains("hm.baidu.com") || url.contains("51.la")) {
            try {
                Map<String, String> responseHeaders = new HashMap<>(1);
                responseHeaders.put("Access-Control-Allow-Origin", "*");
                InputStream data = context.getAssets().open("static/qgstatic.js");
                return new WebResourceResponse("application/javascript", "utf-8", 200, "OK", responseHeaders, data);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return WebViewCacheInterceptorInst.getInstance().interceptRequest(url);
    }

    private WebResourceResponse myIntercept(String url) {
        ResourceInfo resourceInfo = null;
        if (url.contains("favicon.ico")) {
            resourceInfo = new ResourceInfo("static/favicon.ico", "image/x-icon");
        } else if (url.contains("z_stat.php") || url.contains("googletagmanager.com") || (url.contains("iptv") && url.contains("common.js"))) {
            resourceInfo = new ResourceInfo("static/qgstatic.js", "application/javascript");
        } else if (url.contains("jquery") && url.contains(".js")) {
            String path = "jquery.min.js";
            if (url.contains("mobile")) {
                path = "jquery.mobile.min.js";
            } else if (url.contains("ui")) {
                path = "jquery.ui.min.js";
            } else if (url.contains("cookie")) {
                path = "jquery.cookie.min.js";
            } else if (url.contains("base64")) {
                path = "jquery.base64.min.js";
            } else if (url.contains("slim")) {
                path = "jquery.slim.min.js";
            } else if (url.contains("lazyload")) {
                path = "jquery.lazyload.js";
            }
            resourceInfo = new ResourceInfo("static/" + path, "application/javascript");
        }
        if (resourceInfo != null) {
            try {
                Map<String, String> responseHeaders = new HashMap<>(1);
                responseHeaders.put("Access-Control-Allow-Origin", "*");
                InputStream data = context.getAssets().open(resourceInfo.getPath());
                return new WebResourceResponse(resourceInfo.getType(), "utf-8", 200, "OK", responseHeaders, data);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }

    /**
     * @param srcStr
     * @param findStr
     * @return
     */
    public static int count(String srcStr, String findStr) {
        int count = 0;
        // 通过静态方法compile(String regex)方法来创建,将给定的正则表达式编译并赋予给Pattern类
        Pattern pattern = Pattern.compile(findStr);
        Matcher matcher = pattern.matcher(srcStr);
        while (matcher.find()) {
            // boolean find() 对字符串进行匹配,匹配到的字符串可以在任何位置
            count++;
        }
        return count;
    }


    private void showWebStatus(ViewStatusEnum statusEnum) {
            EventBusActivityScope.getDefault((Activity) context).post(new WebViewStatusEvent(statusEnum));
    }

    private void hideLoading() {
        if (isFirst) {
            showWebStatus(ViewStatusEnum.CONTENT);
            isFirst = false;
        }
    }

    public interface OnVideoViewListener {
        void reload();
    }
}
