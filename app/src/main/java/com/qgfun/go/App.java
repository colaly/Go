package com.qgfun.go;

import  android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.qgfun.go.service.InitializeService;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.xuexiang.xui.XUI;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import me.yokeyword.fragmentation.Fragmentation;
import okhttp3.OkHttpClient;

/**
 * @author LLY
 */
public class App extends Application {
    private static Boolean isDebug = null;

    @Override
    public void onCreate() {
        super.onCreate();

        initIsDebug(this);
        initXUI();
        initOkGo();
        Logger.addLogAdapter(new AndroidLogAdapter(){
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return isDebug();
            }
        });
        FlowManager.init(this);
        InitializeService.start(this);
        Fragmentation.builder()
                // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(false)
                .install();

    }

    private void initOkGo() {


        HttpHeaders headers = new HttpHeaders();
        headers.put("User-Agent", "Mozilla/5.0 (Linux; Android 8.0.0; WAS-AL00 Build/HUAWEIWAS-AL00; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/70.0.3538.110 Mobile Safari/537.36 MicroMessengeriptv VideoPlayer god/3.0.0 Html5Plus/1.0 (Immersed/24.0)");

        OkGo.getInstance()
                //必须调用初始化
                .init(this)
                //建议设置OkHttpClient，不设置将使用默认的
                .setOkHttpClient(okHttpClient(this))
                //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                //全局统一缓存时间，默认永不过期，可以不传
                //.setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                .setCacheTime(60000)
                //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .setRetryCount(3);
    }

    public static OkHttpClient okHttpClient(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
        if (isDebug()) {
            loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.NONE);
        }
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //使用sp保持cookie，如果cookie不过期，则一直有效
        //builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
        //使用数据库保持cookie，如果cookie不过期，则一直有效
        if (context != null) {
            builder.cookieJar(new CookieJarImpl(new DBCookieStore(context)));
        }
        //使用内存保持cookie，app退出后，cookie消失
        //builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));
        //信任所有证书,不安全有风险
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        builder.hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier);
        return builder.build();
    }

    private void initXUI() {
        //初始化UI框架
        XUI.init(this);
        //开启UI框架调试日志
        XUI.debug(isDebug());
    }

    private static void initIsDebug(Context ctx) {
        if (isDebug == null) {
            isDebug = ctx.getApplicationInfo() != null &&
                    (ctx.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        }
    }

    public static boolean isDebug() {
        return isDebug != null && isDebug;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
