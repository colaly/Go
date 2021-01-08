package com.qgfun.go.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.cdnbye.sdk.P2pEngine;
import com.hpplay.sdk.source.browse.api.ILelinkServiceManager;
import com.hpplay.sdk.source.browse.api.LelinkServiceManager;
import com.hpplay.sdk.source.browse.api.LelinkSetting;
import com.qgfun.go.R;
import com.qgfun.go.util.FileTool;
import com.qgfun.go.util.Log;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import java.io.File;

import ren.yale.android.cachewebviewlib.CacheType;
import ren.yale.android.cachewebviewlib.WebViewCacheInterceptor;
import ren.yale.android.cachewebviewlib.WebViewCacheInterceptorInst;

import static com.qgfun.go.App.isDebug;


/**
 * @author LLY
 */
public class InitializeService extends IntentService {

    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.qgfun.go.service.action.INIT";

    public InitializeService() {
        super("InitializeService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
                performInit();
            }
        }
    }

    private void performInit() {
        initLeLink("14059", "116cb7aa3407ef03e8f84d33774d8cd0");
        buglyInit() ;
        cdnbyeInit();
        initWebCache();
    }
    /*private void  initDownload(){
        M3U8DownloaderConfig
                .build(getApplicationContext())
                .setSaveDir(FileTool.getSDCardPath() + "Go" + File.separator)
                .setConnTimeout(10000)
                .setReadTimeout(10000)
                .setThreadCount(3)
                .setDebugMode(isDebug())
        ;
    }*/
    private void buglyInit(){
        //true表示app启动自动初始化升级模块; false不会自动初始化; 开发者如果担心sdk初始化影响app启动速度，可以设置为false，在后面某个时刻手动调用Beta.init(getApplicationContext(),false);
        Beta.autoInit = true;
        Bugly.init(getApplicationContext(), "084a291580", isDebug());
        //true表示初始化时自动检查升级; false表示不会自动检查升级,需要手动调用Beta.checkUpgrade()方法;
        Beta.autoCheckUpgrade = true;
        //设置Wifi下自动下载
        Beta.autoDownloadOnWifi = true;

        //设置通知栏大图标  largeIconId为项目中的图片资源;
        Beta.largeIconId = R.mipmap.logo;


        //设置状态栏小图标    smallIconId为项目中的图片资源id
        Beta.smallIconId = R.mipmap.logo;

        //设置更新弹窗默认展示的banner
        Beta.defaultBannerId = R.mipmap.upgrade_banner;
    }


    private void initWebCache(){
        WebViewCacheInterceptor.Builder builder =  new WebViewCacheInterceptor.Builder(this);
        builder.setTrustAllHostname();//HostnameVerifier不验证，HostnameVerifier.verify()返回true，默认正常验证
        builder.setDebug(isDebug());
                   //设置缓存路径，默认getCacheDir，名称CacheWebViewCache
        builder
                //设置缓存大小，默认100M
                .setCacheSize(1024*1024*100)
                //设置http请求链接超时，默认20秒
                .setConnectTimeoutSecond(20)
                //设置http请求链接读取超时，默认20秒
                .setReadTimeoutSecond(20)
                //设置缓存为正常模式，默认模式为强制缓存静态资源
                .setCacheType(CacheType.NORMAL);
        WebViewCacheInterceptorInst.getInstance().init(builder);
        //WebViewCacheInterceptorInst.getInstance().enableForce(false);
        //WebViewCacheInterceptorInst.getInstance().clearCache();
    }

    private void cdnbyeInit(){
        String token="DXGs37lWR";
        Log.i("token",token);
        P2pEngine.init(this,token , null);
    }



    private void initLeLink(String appId, String appKey) {
        LelinkSetting lelinkSetting = new LelinkSetting.LelinkSettingBuilder(appId,
                appKey).build();
        ILelinkServiceManager lelinkServiceManager = LelinkServiceManager.getInstance(this);
        lelinkServiceManager.setLelinkSetting(lelinkSetting);
    }

}
