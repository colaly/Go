package com.qgfun.go.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.qgfun.go.R;

import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * @author LLY
 * date: 2020/4/8 11:27
 * package name: com.qgfun.beauty.base
 */
public class BaseBackFragment extends SwipeBackFragment {
    private static final String TAG = "Fragmentation";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setParallaxOffset(0.5f);
    }

    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.mipmap.icon_back_white);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressed());
    }
}
