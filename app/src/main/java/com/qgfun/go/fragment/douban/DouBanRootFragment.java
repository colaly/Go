package com.qgfun.go.fragment.douban;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.orhanobut.logger.Logger;
import com.qgfun.go.R;
import com.qgfun.go.adapter.DouBanPagerFragmentAdapter;
import com.qgfun.go.base.BaseMainFragment;
import com.qgfun.go.entity.CategoriesData;
import com.qgfun.go.entity.Category;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * @author LLY
 * date: 2020/4/7 13:15
 * package name: com.qgfun.beauty.fragment
 * description：TODO
 */
public class DouBanRootFragment extends BaseMainFragment {
    
    public  static DouBanRootFragment newInstance() {
        return new DouBanRootFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_douban_root, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (findChildFragment(DouBanIndexFragment.class) == null) {
            loadRootFragment(R.id.fl_container, DouBanIndexFragment.newInstance());
        }
    }
    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        Logger.i("----initView begin-----");

    }


}
