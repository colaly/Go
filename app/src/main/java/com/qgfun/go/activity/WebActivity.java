package com.qgfun.go.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.orhanobut.logger.Logger;
import com.qgfun.go.R;
import com.qgfun.go.base.BaseActivity;
import com.qgfun.go.enums.ViewStatusEnum;
import com.qgfun.go.event.PlayEvent;
import com.qgfun.go.event.TitleEvent;
import com.qgfun.go.event.WebViewStatusEvent;
import com.qgfun.go.util.Log;
import com.qgfun.go.util.SPTool;
import com.qgfun.go.util.TextManipulationUtils;
import com.qgfun.go.view.CoolIndicatorLayout;
import com.qgfun.go.web.CustomSettings;
import com.qgfun.go.web.CustomWebChromeClient;
import com.qgfun.go.web.CustomWebViewClient;
import com.qgfun.go.web.JsInterface;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.tencent.bugly.beta.Beta;
import com.xuexiang.xui.widget.statelayout.MultipleStatusView;
import com.xuexiang.xui.widget.toast.XToast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * @author LLY
 */
public class WebActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.home)
    ImageView home;

    @BindView(R.id.player)
    ImageView player;

    @BindView(R.id.iv_refresh)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.ll_stateful)
    MultipleStatusView mStatusView;

    private AgentWeb mAgentWeb;
    private CustomWebViewClient customWebViewClient;
    private JsInterface mJsInterface;
    private String mPlayerUrl = "";
    private String mUrl;

    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_web;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        if (!EventBusActivityScope.getDefault(this).isRegistered(this)) {
            EventBusActivityScope.getDefault(this).register(this);
        }
        player.setVisibility(View.GONE);
        mUrl = this.getIntent().getStringExtra("url");
        if (mUrl == null) {
            mUrl = "http://iptv200.com/";
        }

        back.setOnClickListener(this);
        home.setOnClickListener(this);
        player.setOnClickListener(this);
        customWebViewClient = new CustomWebViewClient(this);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mStatusView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT))
                .setCustomIndicator(new CoolIndicatorLayout(this))
                .setAgentWebWebSettings(new CustomSettings(false))
                .setWebViewClient(customWebViewClient)
                .setWebChromeClient(new CustomWebChromeClient(this))
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setMainFrameErrorView(R.layout.web_error, -1)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)
                .interceptUnkownUrl()
                .createAgentWeb()
                .ready()
                .go(mUrl);
        mRefreshLayout.setRefreshHeader(new MaterialHeader(this));
        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            mAgentWeb.getUrlLoader().reload();
            if (mJsInterface != null) {
                mJsInterface.videoViewRelease();
            }
            mRefreshLayout.postDelayed(mRefreshLayout::finishRefresh, 2000);
        });
        mJsInterface = new JsInterface(this, mAgentWeb);
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", mJsInterface);
        mStatusView.setOnRetryClickListener(v -> mAgentWeb.getUrlLoader().reload());
    }

    private void setWebTitle(String t) {
        Log.i("title:%s", t);
        // android 6.0 以下通过title获取判断
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (t.contains("404") || t.contains("500") || t.contains("502") || t.contains("504") || t.toLowerCase().contains("error") || t.contains("找不到网页") || t.contains("网页无法打开")) {
                mStatusView.showError();
                Log.i("setWebTitle");
                return;
            }
        }

        if (!TextUtils.isEmpty(t)) {
            t = t.replaceAll(" |在线观看.*|看吧电影网.*|_.*|,.*", "");
        }
        if (TextUtils.isEmpty(t)) {
            t = getString(R.string.app_name);
        }
        SPTool.putString(this, "title", t);
        //title.setText(t);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Beta.checkUpgrade(false, true);
        if (mJsInterface != null) {
            mJsInterface.onResume();
        }
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }

        checkPermission(WRITE_EXTERNAL_STORAGE, READ_PHONE_STATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mJsInterface != null) {
            mJsInterface.onPause();
        }
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBusActivityScope.getDefault(this).isRegistered(this)) {
            EventBusActivityScope.getDefault(this).unregister(this);
        }
        if (mJsInterface != null) {
            mJsInterface.videoViewRelease();
        }
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mJsInterface != null) {
            boolean backPressed = mJsInterface.onBackPressed();
            if (backPressed) {
                return true;
            }
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            player.setVisibility(View.GONE);
            if (mAgentWeb != null && mAgentWeb.handleKeyEvent(keyCode, event)) {
                title.setText(getString(R.string.app_name));
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void title(TitleEvent titleEvent) {
        String t = titleEvent.getTitle();
        setWebTitle(t);
    }

    @Subscribe
    public void changeStatus(WebViewStatusEvent event) {
        ViewStatusEnum.setStatus(mStatusView,event.getStatus());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void playUrl(PlayEvent event) {
        player.setVisibility(event.isShow() ? View.VISIBLE : View.GONE);
        mPlayerUrl = event.getUrl();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                if (mAgentWeb != null) {
                    mAgentWeb.getWebCreator().getWebView().stopLoading();
                }
                finish();
                break;
            case R.id.home:
                if (mAgentWeb != null) {
                    mAgentWeb.getWebCreator().getWebView().stopLoading();
                    customWebViewClient.setClear(true);
                    if (mJsInterface != null) {
                        boolean backPressed = mJsInterface.onBackPressed();
                    }
                    mAgentWeb.getUrlLoader().loadUrl(mUrl);
                }
                break;
            case R.id.rl_web_error:
                if (mAgentWeb != null) {
                    mAgentWeb.getUrlLoader().reload();
                }
                break;
            case R.id.player:
                if (!TextUtils.isEmpty(mPlayerUrl) && !mPlayerUrl.startsWith("b")) {
                    Intent intent = new Intent(this, PlayerActivity.class);
                    intent.setData(Uri.parse(mPlayerUrl));
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "播放地址获取失败", Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Uri data = intent.getData();
        if (data != null) {
            String result = data.toString();
            Logger.e("login:"+ result+"-------"+mAgentWeb.getWebCreator().getWebView().getUrl());
            String openidStr = TextManipulationUtils.takeSpecifiedText2(result, "&openid=", "&").trim();
            String accessTokenStr = TextManipulationUtils.takeSpecifiedText2(data.toString(), "access_token=", "&").trim();
            XToast.success(this, "登录成功", Toast.LENGTH_SHORT).show();
            Logger.e("openidStr:"+openidStr+",accessTokenStr:"+accessTokenStr);
            String url = mAgentWeb.getWebCreator().getWebView().getUrl();
            if (TextUtils.isEmpty(url)){
                XToast.error(this,"系统功能调用出错,请使用网页访问");
            }
            String key="gogogo";
            if (url.contains("qq")){
                key="dd";
            }else if (url.contains("ali"))  {
                key="life";
            }else if (url.contains("yuedong"))  {
                key="yd";
            }
            mAgentWeb.getJsAccessEntrace().quickCallJs("callByAndroid",key,openidStr,accessTokenStr);
        }
    }
}


