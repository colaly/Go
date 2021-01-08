package com.qgfun.go.base;

import android.os.Bundle;

/**
 * @author LLY
 */
public interface ICallback {
    /**
     * 设置layout
     * @param savedInstanceState
     * @return
     */
    int getLayoutId(Bundle savedInstanceState);

    /**
     *  初始化view
     */
    void initView();

    /**
     *  初始化data
     */
    void initData();
}