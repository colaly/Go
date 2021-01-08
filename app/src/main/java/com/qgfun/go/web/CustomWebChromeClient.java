package com.qgfun.go.web;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.webkit.WebView;

import com.just.agentweb.WebChromeClient;
import com.qgfun.go.R;
import com.qgfun.go.event.TitleEvent;
import com.qgfun.go.util.Log;


import org.greenrobot.eventbus.EventBus;

/**
 * @author LLY
 */
public class CustomWebChromeClient extends WebChromeClient {
    private Context context;

    public CustomWebChromeClient(Context context) {
        this.context = context;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
                /*if (newProgress % 5 == 0) {
                    mAgentWeb.getUrlLoader().loadUrl(JS);
                }*/
    }

    @Override
    public Bitmap getDefaultVideoPoster() {
        try {
            //这个地方是加载h5的视频列表 默认图   点击前的视频图
            Log.i("VideoPoster", "   加载视频默认图片");
            return BitmapFactory.decodeResource(context.getResources(),
                    R.mipmap.loading);

        } catch (Exception e) {
            Log.i("VideoPoster", "   加载视频默认图片失败");
            return super.getDefaultVideoPoster();
        }

    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        EventBus.getDefault().post(new TitleEvent(title));
    }
}
