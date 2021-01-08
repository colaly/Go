package com.qgfun.go.fragment.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.orhanobut.logger.Logger;
import com.qgfun.go.R;
import com.qgfun.go.base.BaseMainFragment;
import com.qgfun.go.fragment.douban.DouBanIndexFragment;

import me.yokeyword.fragmentation.SupportFragment;


/**
 * @author LLY
 * date: 2020/4/7 13:15
 * package name: com.qgfun.beauty.fragment
 * description：TODO
 */
public class CategoryRootFragment extends BaseMainFragment {
    
    public  static CategoryRootFragment newInstance() {
        return new CategoryRootFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_root, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (findChildFragment(CategoryIndexFragment.class) == null) {
            loadRootFragment(R.id.fl_container, CategoryIndexFragment.newInstance());
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
