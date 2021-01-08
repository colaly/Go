package com.qgfun.go.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.qgfun.go.R;

/**
 * @author LLY
 */
public class IntentUtils {
    public static void open(Context context,String url) {
        Intent intent = new Intent();
        //Url 就是你要打开的网址
        intent.setData(Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        //启动浏览器
        context.startActivity(intent);
    }

    public static void share(Context context) {
        Intent shareIntent = new Intent();
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "我正在使用"+context.getString(R.string.app_name)+"，超级好用快来下载吧，下载地址：https://url.cn/5OA2WE4");
        //切记需要使用Intent.createChooser，否则会出现别样的应用选择框，您可以试试
        shareIntent = Intent.createChooser(shareIntent, "《"+context.getString(R.string.app_name)+"》分享");
        context.startActivity(shareIntent);
    }
}
