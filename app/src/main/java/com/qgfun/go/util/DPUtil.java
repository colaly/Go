package com.qgfun.go.util;

import android.content.res.Resources;

public class DPUtil {
    private DPUtil() {
    }

    public static int dip2px(float var0) {
        float var1 = Resources.getSystem().getDisplayMetrics().density;
        return (int) Math.floor(var0 * var1 + 0.5F);
    }

    public static int px2dip(float var0) {
        float var1 = Resources.getSystem().getDisplayMetrics().density;
        return  Math.round(var0 / var1 + 0.5F);
    }
}
