package com.qgfun.go.web;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.dueeeke.videoplayer.player.VideoView;
import com.just.agentweb.AgentWeb;
import com.orhanobut.logger.Logger;
import com.qgfun.go.event.PlayEvent;
import com.qgfun.go.util.DPUtil;
import com.qgfun.go.util.Log;
import com.qgfun.go.view.VideoControllerUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LLY
 */
public class JsInterface {

    private Context context;
    private Handler deliver = new Handler(Looper.getMainLooper());
    private AgentWeb agentWeb;
    private VideoView videoView = null;
    private FrameLayout frameLayout;
    String url = "";

    public JsInterface(Context context, AgentWeb agentWeb) {
        this.context = context;
        this.agentWeb = agentWeb;

    }

    @JavascriptInterface
    public void login() {
        String ali="aHR0cHM6Ly94dWkucHRsb2dpbjIucXEuY29tL2NnaS1iaW4veGxvZ2luP2FwcGlkPTcxNjAyNzYwOSZwdF8zcmRfYWlkPTExMDE3NzQ2MjAmZGFpZD0zODEmcHRfc2tleV92YWxpZD0xJnN0eWxlPTM1JnNfdXJsPWh0dHAlM0ElMkYlMkZjb25uZWN0LnFxLmNvbSZyZWZlcl9jZ2k9bV9hdXRob3JpemUmdWNoZWNrPTEmZmFsbF90b193dj0xJnN0YXR1c19vcz05LjMuMiZyZWRpcmVjdF91cmk9YXV0aCUzQSUyRiUyRnd3dy5xcS5jb20mY2xpZW50X2lkPTExMDQ5MDQyODYmcmVzcG9uc2VfdHlwZT10b2tlbiZzY29wZT1hbGwmc2RrcD1pJnNka3Y9Mi45JnN0YXRlPXRlc3Qmc3RhdHVzX21hY2hpbmU9aVBob25lOCUyQzEmc3dpdGNoPTE=";
        context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("mqqapi://forward/url?src_type=web&version=1&url_prefix="+ali)));
    }

    @JavascriptInterface
    public String getData(String key) {
        SharedPreferences sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    @JavascriptInterface
    public void saveData(String key, String data) {
        Log.i("save", "key:" + key + ",data:" + data);
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sharedPreferences.edit();
        e.putString(key, data).apply();
    }

    @JavascriptInterface
    public void showPlayer(String uri) {
        if (!TextUtils.isEmpty(uri) && !uri.startsWith("b")) {
            EventBus.getDefault().post(new PlayEvent.Builder().url(uri).isShow(true).build());
        }
    }

    @JavascriptInterface
    public void addVideoPlayerView(float width, float height, float top, float left, String uri, String referer) {
        Logger.i("width:%f,height:%f,top:%f,left:%f,url:%s", width, height, top, left, uri);
        deliver.post(() -> {
            if (videoView == null) {

                Logger.i("addVideoPlayerView start");
                videoView = new VideoView(context);

                videoView.setLayoutParams(new ViewGroup.LayoutParams(DPUtil.dip2px( width), DPUtil.dip2px( height)));
                if (!TextUtils.isEmpty(uri)) {
                    url = uri;
                }
                Map<String, String> headers = new HashMap<>(1);
                if (TextUtils.isEmpty(referer)) {
                    headers.put("Referer", "http://m.iptv805.com/");
                } else {
                    headers.put("Referer", referer);
                }
                int ran= (int) (Math.random()*100+1);
                headers.put("User-Agent","Mozilla/5.0 (Linux; U; Android "+ran+"."+ran+"."+ran+"; zh-cn; MI"+ran+" Build/JR"+ran+"L) AppleWebKit/"+ran+".30 (KHTML, like Gecko) Version/"+ran+".0 Mobile Safari/"+ran+".30 XiaoMi/MiuiBrowser/"+ran+".0");
                videoView.setUrl(url, headers);
                VideoControllerUtils videoControllerUtils = new VideoControllerUtils(context, videoView, 1, true);
                videoView.setVideoController(videoControllerUtils.getController());

                AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(DPUtil.dip2px( width), DPUtil.dip2px(height),DPUtil.dip2px( left),DPUtil.dip2px(top));
         /*       FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(DPUtil.dip2px( width), DPUtil.dip2px(height) );
                layoutParams.gravity = Gravity.LEFT|Gravity.TOP;
                layoutParams.leftMargin=DPUtil.dip2px( left);
                layoutParams.topMargin=DPUtil.dip2px(top);
*/
                if (frameLayout == null) {
                    frameLayout = new FrameLayout(context);
                } else {
                    frameLayout.removeAllViews();
                }
                frameLayout.addView(videoView);
                agentWeb.getWebCreator().getWebView().removeAllViews();
                agentWeb.getWebCreator().getWebView().addView(frameLayout, params);
                videoView.start(); //开始播放，不调用则不自动播放
                Logger.i("addVideoPlayerView end");
            } else {
                Logger.i("VideoPlayerView 存在");

                if (videoView != null) {
                    Log.i("videoView play url:%s", url);
                    videoView.setUrl(url);
                    videoView.replay(true);
                }

            }
        });
    }


    @JavascriptInterface
    public void play(final String uri) {
        url = uri;
        Log.i("play", url);
        deliver.post(() -> {
            if (videoView != null) {
                Log.i("videoView play url", url);
                videoView.setUrl(url);
                videoView.replay(true);
            }
        });
    }

    public void videoViewRelease() {
        if (agentWeb != null && agentWeb.getWebCreator().getWebView().getChildCount() > 0) {
            agentWeb.getWebCreator().getWebView().removeViewAt(0);
        }
        if (videoView != null) {
            videoView.release();
            videoView = null;
        }
    }


    public void onResume() {

        if (videoView != null) {
            videoView.resume();
        }
    }

    public void onPause() {

        if (videoView != null) {
            videoView.pause();
        }
    }

    public boolean onBackPressed() {
        if (videoView != null) {
            return videoView.onBackPressed();
        }

        return false;

    }

    /**
     * 获取ActionBar 高度
     *
     * @param context
     * @return
     */
    public static int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data,
                    context.getResources().getDisplayMetrics());
        }
        return 56;
    }
}
