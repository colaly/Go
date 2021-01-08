package com.qgfun.go.fragment.douban;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.orhanobut.logger.Logger;
import com.qgfun.go.R;
import com.qgfun.go.activity.DownloadManagerActivity;
import com.qgfun.go.activity.SearchActivity;
import com.qgfun.go.activity.WebActivity;
import com.qgfun.go.adapter.DouBanPagerFragmentAdapter;
import com.qgfun.go.base.BaseMainFragment;
import com.qgfun.go.entity.CategoriesData;
import com.qgfun.go.entity.Category;
import com.qgfun.go.event.SearchViewEvent;
import com.qgfun.go.fragment.CollectFragment;
import com.qgfun.go.fragment.FeedbackFragment;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * @author LLY
 * date: 2020/4/7 13:15
 * package name: com.qgfun.beauty.fragment
 * description：TODO
 */
public class DouBanIndexFragment extends BaseMainFragment {
    @BindView(R.id.llyt_top)
    LinearLayout mTopTab;


    @BindView(R.id.app_name)
    TextView appName;

    @BindView(R.id.app_slogn)
    TextView appSlogn;


    @BindView(R.id.tabSegment)
    TabSegment mTabSegment;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    public static DouBanIndexFragment newInstance() {
        return new DouBanIndexFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_douban_index, container, false);
        ButterKnife.bind(this, view);
        Logger.i("ViewPagerFragment");
        initView();
        return view;
    }

    @Subscribe
    public void onSearchViewEvent(SearchViewEvent event) {
        Logger.e("onSearchViewEvent:" + event.isShow());
        if (event.isShow()) {
            mTopTab.setVisibility(View.VISIBLE);
            appName.setVisibility(View.VISIBLE);
            appSlogn.setVisibility(View.VISIBLE);
        } else {
            mTopTab.setVisibility(View.GONE);
            appName.setVisibility(View.GONE);
            appSlogn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        Logger.i("----initView begin-----");

    }

    private void initView() {
        EventBusActivityScope.getDefault(_mActivity).register(this);
        List<Category> categories = CategoriesData.getDouBanCategories();
        mViewPager.setAdapter(new DouBanPagerFragmentAdapter(getChildFragmentManager(), categories));
        Logger.e("----initView end-----");
        for (Category categorie : categories) {
            mTabSegment.addTab(new TabSegment.Tab(categorie.getCategory()));
        }
        //int space = DensityUtils.dp2px(_mActivity, 16);
        mTabSegment.setHasIndicator(true);
        mTabSegment.setMode(TabSegment.MODE_FIXED);
        //mTabSegment.setItemSpaceInScrollMode(space);
        mTabSegment.setupWithViewPager(mViewPager, false);
        //mTabSegment.setPadding(space, 0, space, 0);
        mTabSegment.addOnTabSelectedListener(new TabSegment.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {

            }

            @Override
            public void onTabUnselected(int index) {

            }

            @Override
            public void onTabReselected(int index) {

            }

            @Override
            public void onDoubleTap(int index) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
    }


    @OnClick(R.id.rlyt_search)
    public void search() {
        startActivity(new Intent(_mActivity, SearchActivity.class));
    }

    @OnClick(R.id.llyt_4)
    public void collect() {
        ((SupportFragment) getParentFragment().getParentFragment()).start(CollectFragment.newInstance());
    }

    @OnClick({R.id.llyt_1})
    public void third() {
        Intent intent = new Intent(_mActivity, WebActivity.class);
        intent.putExtra("url", "http://api.qgfun.com/free");
        startActivity(intent);
    }

    @OnClick(R.id.llyt_3)
    public void download() {
        startActivity(new Intent(_mActivity, DownloadManagerActivity.class));
    }

    @OnClick(R.id.llyt_5)
    public void feedback() {
        ((SupportFragment) getParentFragment().getParentFragment()).start(FeedbackFragment.newInstance());
    }


}
