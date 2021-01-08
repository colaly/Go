package com.qgfun.go.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qgfun.go.R;
import com.qgfun.go.base.BaseActivity;
import com.qgfun.go.util.DrawableUtils;

/**
 * @author LLY
 */
public class TvScreenHelperActivity extends BaseActivity {

    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_tv_screen_helper;
    }

    @Override
    public void initView() {
        TextView common = findViewById(R.id.common_problem);
        new DrawableUtils(common, new DrawableUtils.OnDrawableListener() {
            @Override
            public void onLeft(View v, Drawable left) {
                finish();
            }

            @Override
            public void onRight(View v, Drawable right) {

            }
        });
    }

    @Override
    public void initData() {

    }
}
