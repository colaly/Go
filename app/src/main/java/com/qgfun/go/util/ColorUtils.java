package com.qgfun.go.util;

import android.content.Context;
import android.util.TypedValue;

import com.qgfun.go.R;


/**
 * @author LLY
 * date: 2020/4/7 23:57
 * package name: com.qgfun.beauty.util
 * description：TODO
 */
public class ColorUtils {
    /**
     * 获取主题颜色
     *
     * @return
     */
    public static int getColorPrimary(Context context) {
        if (context != null) {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
            return typedValue.data;
        }else {
            return R.color.colorPrimary;
        }
    }

    public static int getColorAccent(Context context) {
        if (context != null) {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
            return typedValue.data;
        }else {
            return R.color.colorAccent;
        }
    }
}
