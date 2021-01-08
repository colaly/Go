package com.qgfun.go.util;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.orhanobut.logger.Logger;

import static com.qgfun.go.App.isDebug;

/**
 * @author LLY
 */
public class Log {
    public static void i(@NonNull String message, @Nullable Object... args) {
        if (isDebug()) {
            Logger.i(">>>"+message+"<<<timestamp:"+System.currentTimeMillis()/1000,args );
        }else {
            Logger.i("趣享GO",System.currentTimeMillis()/1000+"");
        }
    }

    public static void d(@NonNull String message, @Nullable Object... args) {
        if (isDebug()) {
            Logger.d(">>>"+message+"<<<timestamp:"+System.currentTimeMillis()/1000,args );
        }else {
            Logger.d("趣享GO",System.currentTimeMillis()/1000+"");
        }
    }

    public static void e(@NonNull String message, @Nullable Object... args) {
        if (isDebug()) {
            Logger.e(">>>"+message+"<<<timestamp:"+System.currentTimeMillis()/1000,args );
        }else {
            Logger.e("趣享GO",System.currentTimeMillis()/1000+"");
        }
    }
}
