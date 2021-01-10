package com.qgfun.go.fragment.update;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.orhanobut.logger.Logger;
import com.qgfun.go.R;
import com.qgfun.go.adapter.UpdatePagerFragmentAdapter;
import com.qgfun.go.base.BaseMainFragment;
import com.qgfun.go.entity.AppInfo;
import com.qgfun.go.entity.CategoriesData;
import com.qgfun.go.entity.Category;
import com.qgfun.go.util.Log;
import com.qgfun.go.util.ResourceUtils;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * @author LLY
 * date: 2020/4/7 13:15
 * package name: com.qgfun.beauty.fragment
 * description：TODO
 */
public class UpdateIndexFragment extends BaseMainFragment {
    @BindView(R.id.tabSegment)
    TabSegment mTabSegment;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    public  static UpdateIndexFragment newInstance() {
        return new UpdateIndexFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_index, container, false);
        ButterKnife.bind(this, view);
        Logger.i("ViewPagerFragment");
        initView();
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        Logger.i("----initView begin-----");

    }

    private void initView() {

        AppInfo appInfo = ResourceUtils.getAppInfo(_mActivity);
        List<AppInfo.Resources> resources = appInfo.getResources();
        int size = resources.size();
        int index=appInfo.getIndex();
        AppInfo.Resources resource = appInfo.getResources().get(index >= size ? 0 : index);
        List<AppInfo.Resources.Category> categories = resource.getCategory().stream().filter(AppInfo.Resources.Category::getShow).collect(Collectors.toList());
        mViewPager.setAdapter(new UpdatePagerFragmentAdapter(getChildFragmentManager(), categories));

        Logger.e("----initView end-----");
        for (AppInfo.Resources.Category categorie :categories) {
            mTabSegment.addTab(new TabSegment.Tab(categorie.getName()));
            Log.i("categorie %s",categorie);
        }
        int space = DensityUtils.dp2px(_mActivity, 16);
        mTabSegment.setHasIndicator(true);
        mTabSegment.setMode(TabSegment.MODE_SCROLLABLE);
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


}
