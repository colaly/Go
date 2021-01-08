package com.qgfun.go.web;

import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.just.agentweb.AbsAgentWebSettings;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.just.agentweb.AgentWebUtils;
import com.just.agentweb.IAgentWebSettings;
import com.orhanobut.logger.Logger;

import static android.webkit.WebView.setWebContentsDebuggingEnabled;


/**
 * @author LLY
 */
public class CustomSettings extends AbsAgentWebSettings {
    public CustomSettings() {
        super();
    }

    @Override
    protected void bindAgentWebSupport(AgentWeb agentWeb) {

    }


    @Override
    public IAgentWebSettings toSetting(WebView webView) {
        super.toSetting(webView);
        WebSettings mWebSettings=getWebSettings();
        //隐藏滚动条
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        mWebSettings.setSupportZoom(true);
        mWebSettings.setBuiltInZoomControls(false);
        setWebContentsDebuggingEnabled(true);
        if (AgentWebUtils.checkNetwork(webView.getContext())) {
            //根据cache-control获取数据。
            mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
            //mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        } else {
            //没网，则从本地获取，即离线加载
            mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        //适配5.0不允许http和https混合使用情况
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        //自适应屏幕
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebSettings.setTextZoom(100);
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setAppCacheEnabled(true);
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setSupportMultipleWindows(false);
        //允许加载本地文件html  file协议, 这可能会造成不安全 , 建议重写关闭
        mWebSettings.setAllowFileAccess(true);
        //通过 file url 加载的 Javascript 读取其他的本地文件 .建议关闭
        mWebSettings.setAllowFileAccessFromFileURLs(true);
        //允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源
        mWebSettings.setAllowUniversalAccessFromFileURLs(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setNeedInitialFocus(true);
        //设置编码格式
        mWebSettings.setDefaultTextEncodingName("utf-8");
        mWebSettings.setDefaultFontSize(16);
        //设置 WebView 支持的最小字体大小，默认为 8
        mWebSettings.setMinimumFontSize(12);
        mWebSettings.setGeolocationEnabled(true);
        mWebSettings.setMediaPlaybackRequiresUserGesture(true);
        //
        String dir = AgentWebConfig.getCachePath(webView.getContext());

        Logger.i("dir:" + dir + "   appcache:" + AgentWebConfig.getCachePath(webView.getContext()));
        mWebSettings.setAppCachePath(dir);

        //缓存文件最大值
        mWebSettings.setUserAgentString("Mozilla/5.0 (Linux; Android 7.1.2; OXF-AN10 Build/N2G48H; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/66.0.3359.158 Safari/537.36 MicroMessengeriptv/1.2.3 VideoPlayer god/3.0.0 MPC 2.4.23 mitv baiduboxapp Html5Plus/1.0 (Immersed/24.166666),Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.106 Mobile Safari/537.36");
        return this;
    }
}
