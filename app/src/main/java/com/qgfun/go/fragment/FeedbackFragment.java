package com.qgfun.go.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.orhanobut.logger.Logger;
import com.qgfun.go.R;
import com.qgfun.go.base.BaseMainFragment;
import com.qgfun.go.enums.ViewStatusEnum;
import com.qgfun.go.event.WebViewStatusEvent;
import com.qgfun.go.view.CoolIndicatorLayout;
import com.qgfun.go.web.CustomSettings;
import com.qgfun.go.web.CustomWebChromeClient;
import com.qgfun.go.web.CustomWebViewClient;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.xuexiang.xui.widget.statelayout.MultipleStatusView;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;


/**
 * @author LLY
 * date: 2020/4/7 13:15
 * package name: com.qgfun.beauty.fragment
 * description：TODO
 */
public class FeedbackFragment extends BaseMainFragment {

    @BindView(R.id.iv_refresh)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.ll_stateful)
    MultipleStatusView mStatusView;

    private AgentWeb mAgentWeb;
    private CustomWebViewClient customWebViewClient;

    private String mUrl = "https://support.qq.com/embed/qq/146963/new-post";


    public static FeedbackFragment newInstance() {
        return new FeedbackFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        ButterKnife.bind(this, view);
        Logger.i("ViewPagerFragment");

        EventBusActivityScope.getDefault(_mActivity).register(this);

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

            if (mAgentWeb != null) {
                mAgentWeb.getUrlLoader().reload();
            }
        });
        customWebViewClient = new CustomWebViewClient(_mActivity);
        customWebViewClient.setOnVideoViewListener(new CustomWebViewClient.OnVideoViewListener() {
            @Override
            public void reload() {

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
            //WebViewCacheInterceptorInst.getInstance().enableForce(false);

            mAgentWeb.getUrlLoader().reload();
            mRefreshLayout.postDelayed(mRefreshLayout::finishRefresh, 2000);
        });
        mStatusView.setOnRetryClickListener(v -> mAgentWeb.getUrlLoader().reload());
    }


    @Subscribe
    public void changeStatus(WebViewStatusEvent event) {
        ViewStatusEnum.setStatus(mStatusView,event.getStatus());
    }


    @Override
    public void onPause() {
        super.onPause();

        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBusActivityScope.getDefault(_mActivity).isRegistered(this)) {
            EventBusActivityScope.getDefault(_mActivity).unregister(this);
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


        if (mAgentWeb.back()) {
            return true;
        }
        pop();
        return true;
    }

    @OnClick(R.id.back)
    public void back() {
        if (!mAgentWeb.back()) {
             pop();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {

            if (mAgentWeb != null) {
                mAgentWeb.getWebLifeCycle().onPause();
            }
        } else {
            if (mAgentWeb != null) {
                mAgentWeb.getWebLifeCycle().onResume();
            }
        }
    }
}
