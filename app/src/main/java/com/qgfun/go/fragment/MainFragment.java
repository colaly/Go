package com.qgfun.go.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.orhanobut.logger.Logger;
import com.qgfun.go.R;
import com.qgfun.go.base.BaseMainFragment;
import com.qgfun.go.fragment.category.CategoryRootFragment;
import com.qgfun.go.fragment.douban.DouBanRootFragment;
import com.qgfun.go.fragment.mine.MineRootFragment;
import com.qgfun.go.fragment.tv.TvIndexFragment;
import com.qgfun.go.fragment.update.UpdateIndexFragment;
import com.xuexiang.xui.utils.ColorUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author LLY
 */
public class MainFragment extends BaseMainFragment {

    @BindView(R.id.tab_layout)
    BottomNavigationBar mNavigationBar;

    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;
    private static final int FOURTH = 3;
    private static final int FIFTH = 4;

    private SupportFragment[] mFragments = new SupportFragment[5];

    public static MainFragment newInstance() {
        Bundle bundle = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_root, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mNavigationBar.setBarBackgroundColor(android.R.color.white);

        mNavigationBar
                //设置选中的颜色
                .setActiveColor(ColorUtils.colorToString(com.qgfun.go.util.ColorUtils.getColorAccent(_mActivity)))
                //未选中颜色
                .setInActiveColor(ColorUtils.colorToString(com.qgfun.go.util.ColorUtils.getColorPrimary(_mActivity)));
        mNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.icon_hot, "热门"))
                .addItem(new BottomNavigationItem(R.mipmap.icon_category, "分类"))
                .addItem(new BottomNavigationItem(R.mipmap.icon_new, "最新"))
                .addItem(new BottomNavigationItem(R.mipmap.icon_tv, "电视"))
                .addItem(new BottomNavigationItem(R.mipmap.icon_mine, "我的"))
                .setFirstSelectedPosition(0)
                .initialise();
        mNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                Logger.e("TabSelected:%d", position);
                showHideFragment(mFragments[position]);

            }

            @Override
            public void onTabUnselected(int position) {
                Logger.e("TabUnselected:%d", position);
                //XToast.info(MainActivity.this,"TabUnselected:"+position+"").show(); ;
            }

            @Override
            public void onTabReselected(int position) {
                Logger.i("TabReselected:%d", position);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //这里需要如下判断，否则可能出现这个错误https://xuexuan.blog.csdn.net/article/details/103733622
        SupportFragment firstFragment = findChildFragment(DouBanRootFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = DouBanRootFragment.newInstance();
            mFragments[SECOND] = CategoryRootFragment.newInstance();
            mFragments[THIRD] = UpdateIndexFragment.newInstance();
            mFragments[FOURTH] = TvIndexFragment.newInstance();
            mFragments[FIFTH] = MineRootFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH],
                    mFragments[FIFTH]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findChildFragment(CategoryRootFragment.class);
            mFragments[THIRD] = findChildFragment(UpdateIndexFragment.class);
            mFragments[FOURTH] = findChildFragment(TvIndexFragment.class);
            mFragments[FIFTH] = findChildFragment(MineRootFragment.class);
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        Logger.i("----initView begin-----");

    }
}

