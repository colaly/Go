package com.qgfun.go.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.orhanobut.logger.Logger;
import com.qgfun.go.R;
import com.qgfun.go.adapter.DownloadManagerPagerFragmentAdapter;
import com.qgfun.go.base.BaseActivity;
import com.qgfun.go.entity.Category;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.dialog.materialdialog.GravityEnum;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author LLY
 * date: 2020/5/9 22:22
 * package name: com.qgfun.go.activity
 * description：TODO
 */
public class DownloadManagerActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar mTitle;

    @BindView(R.id.tabSegment)
    TabSegment mTabSegment;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_download_manager;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        mTitle.setLeftClickListener(v -> finish());
        mTitle.addAction(new TitleBar.ImageAction(R.mipmap.icon_qustion) {
            @Override
            public void performAction(View view) {
                new MaterialDialog.Builder(DownloadManagerActivity.this)
                        .title(R.string.download_tips)
                        .titleGravity(GravityEnum.CENTER)
                        .content(Html.fromHtml(DownloadManagerActivity.this.getString(R.string.download_question_content)))
                        .positiveText("知道啦")
                        .show();
            }
        });
    }

    @Override
    public void initData() {
        List<Category> categories = new ArrayList<>(2);
        categories.add(new Category("下载列表", "0"));
        categories.add(new Category("完成列表", "1"));
        mViewPager.setAdapter(new DownloadManagerPagerFragmentAdapter(getSupportFragmentManager(), categories));

        Logger.e("----initView end-----");
        for (Category categorie : categories) {
            mTabSegment.addTab(new TabSegment.Tab(categorie.getCategory()));
        }
        int space = DensityUtils.dp2px(this, 16);
        mTabSegment.setHasIndicator(true);
        mTabSegment.setMode(TabSegment.MODE_FIXED);
        mTabSegment.setItemSpaceInScrollMode(space);
        mTabSegment.setupWithViewPager(mViewPager, false);
        mTabSegment.setPadding(space, 0, space, 0);
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
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        finish();
    }
}
