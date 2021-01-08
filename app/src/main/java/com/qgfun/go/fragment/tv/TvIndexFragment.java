package com.qgfun.go.fragment.tv;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.orhanobut.logger.Logger;
import com.qgfun.go.R;
import com.qgfun.go.base.BaseMainFragment;
import com.qgfun.go.enums.ViewStatusEnum;
import com.qgfun.go.event.TitleEvent;
import com.qgfun.go.event.WebViewStatusEvent;
import com.qgfun.go.util.DataCleanManager;
import com.qgfun.go.util.Log;
import com.qgfun.go.util.SPTool;
import com.qgfun.go.view.CoolIndicatorLayout;
import com.qgfun.go.web.CustomSettings;
import com.qgfun.go.web.CustomWebChromeClient;
import com.qgfun.go.web.CustomWebViewClient;
import com.qgfun.go.web.JsInterface;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.xuexiang.xui.widget.statelayout.MultipleStatusView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;


/**
 * @author LLY
 * date: 2020/4/7 13:15
 * package name: com.qgfun.beauty.fragment
 */
public class TvIndexFragment extends BaseMainFragment {

    @BindView(R.id.title)
    TextView title;


    @BindView(R.id.iv_refresh)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.ll_stateful)
    MultipleStatusView mStatusView;

    private AgentWeb mAgentWeb;
    private CustomWebViewClient customWebViewClient;
    private boolean isHidden =true;
    private String mUrl = "https://player.ggiptv.com/iptv.php";
    private JsInterface mJsInterface;

    public static TvIndexFragment newInstance() {
        return new TvIndexFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_root, container, false);
        ButterKnife.bind(this, view);
        EventBusActivityScope.getDefault(_mActivity).register(this);
        Logger.i("ViewPagerFragment");
        clear();
        initView();

        return view;

    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 这里可以不用懒加载,因为Adapter的场景下,Adapter内的子Fragment只有在父Fragment是show状态时,才会被Attach,Create
    }

    private void initView() {
        mStatusView.setOnRetryClickListener(v -> {
            if (mJsInterface != null) {
                mJsInterface.videoViewRelease();
            }
            if (mAgentWeb != null) {
                mAgentWeb.getUrlLoader().reload();
            }
        });
        customWebViewClient = new CustomWebViewClient(_mActivity);
        customWebViewClient.setOnVideoViewListener(() -> {
            if (mJsInterface != null) {
                mJsInterface.videoViewRelease();
            }
        });
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mStatusView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT))
                .setCustomIndicator(new CoolIndicatorLayout(_mActivity))
                .setAgentWebWebSettings(new CustomSettings())
                .setWebViewClient(customWebViewClient)
                .setWebChromeClient(new CustomWebChromeClient(_mActivity))
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setMainFrameErrorView(R.layout.web_error, -1)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)
                .interceptUnkownUrl()
                .createAgentWeb()
                .ready()
                .go(mUrl);
        mRefreshLayout.setRefreshHeader(new MaterialHeader(_mActivity));
        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            clear();
            if (mJsInterface != null) {
                mJsInterface.videoViewRelease();
            }
            mAgentWeb.getUrlLoader().reload();
            mRefreshLayout.postDelayed(mRefreshLayout::finishRefresh, 2000);
        });
        mJsInterface = new JsInterface(_mActivity, mAgentWeb);
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", mJsInterface);
        mStatusView.setOnRetryClickListener(v -> mAgentWeb.getUrlLoader().reload());
    }

    private void setWebTitle(String t) {
        Log.i("title:%s", t);
        // android 6.0 以下通过title获取判断

        if (t.contains("404") || t.contains("500") || t.contains("502") || t.contains("504") || t.toLowerCase().contains("error") || t.contains("找不到网页") || t.contains("网页无法打开")|| t.contains("Not Found")) {
                mStatusView.showError();
                Log.i("setWebTitle setWebTitle");
                return;
        }

        if (!TextUtils.isEmpty(t)) {
            t = t.replaceAll(" |-|電視直播", "");
        }
        if (TextUtils.isEmpty(t)||t.contains("http")) {
            t = getString(R.string.app_name);
        }
        SPTool.putString(_mActivity, "title", t);
        title.setText(t);
    }

    @Subscribe
    public void changeTitle(TitleEvent event) {
        setWebTitle(event.getTitle());
    }

    @Subscribe
    public void changeStatus(WebViewStatusEvent event) {
        Log.e("changeStatus%s",event.getStatus().getDescription());
        ViewStatusEnum.setStatus(mStatusView,event.getStatus());
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mJsInterface != null) {
            mJsInterface.onPause();
        }
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mJsInterface != null&& !isHidden) {
            mJsInterface.onResume();
        }
        if (mAgentWeb != null&& !isHidden) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBusActivityScope.getDefault(_mActivity).isRegistered(this)) {
            EventBusActivityScope.getDefault(_mActivity).unregister(this);
        }
        if (mJsInterface != null) {
            mJsInterface.videoViewRelease();
        }
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onBackPressedSupport() {

        if (mJsInterface != null) {
            boolean backPressed = mJsInterface.onBackPressed();
            if (backPressed) {
                return true;
            }
        }
        if (mAgentWeb.back()) {
            if (mJsInterface != null) {
                mJsInterface.videoViewRelease();
            }
            return true;
        }

        return super.onBackPressedSupport();
    }

    @OnClick(R.id.back)
    public void back() {
        if (mAgentWeb.back()) {
            if (mJsInterface != null) {
                mJsInterface.videoViewRelease();
            }
        }
    }

    @OnClick(R.id.home)
    public void home() {
        if (mJsInterface != null) {
            mJsInterface.videoViewRelease();
        }
        if (mAgentWeb != null) {
            customWebViewClient.setClear(true);
            mAgentWeb.getUrlLoader().loadUrl(mUrl);
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isHidden =hidden;
        if (hidden) {
            if (mJsInterface != null) {
                mJsInterface.onPause();
            }
            if (mAgentWeb != null) {
                mAgentWeb.getWebLifeCycle().onPause();
            }
        } else {
            if (mJsInterface != null) {
                mJsInterface.onResume();
            }
            if (mAgentWeb != null) {
                mAgentWeb.getWebLifeCycle().onResume();
            }
        }
    }

    public  void clear(){
        DataCleanManager.clearAllCache(_mActivity);
    }
}
