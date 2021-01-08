package com.qgfun.go.util;

import android.content.Context;
import android.text.TextUtils;

import com.dueeeke.videoplayer.player.ProgressManager;

import org.apache.common.codec.digest.DigestUtils;

/**
 * @author : Cola
 * @date : 2019/8/21 9:16
 * description : 视频进度处理
 */
public class ProgressManagerImpl extends ProgressManager {
    private Context mContext;
    private static String NAME = "videoProgress";

    public ProgressManagerImpl(Context context) {
        mContext = context;
        long time = SPTool.getLong(mContext, NAME, "time");
        if (time==0L){
            SPTool.putLong(mContext,NAME,"time",System.currentTimeMillis()/1000);
        }else if (System.currentTimeMillis()/1000-time>2*24*3600){
            clearAllSavedProgress();
        }
    }

    @Override
    public void saveProgress(String url, long progress) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (progress == 0) {
            clearSavedProgressByUrl(url);
            return;
        }
        SPTool.putLong(mContext, NAME, DigestUtils.md5Hex(url), progress);
    }

    @Override
    public long getSavedProgress(String url) {
        return TextUtils.isEmpty(url) ? 0 : SPTool.getLong(mContext, NAME, DigestUtils.md5Hex(url));
    }

    public void clearAllSavedProgress() {
        SPTool.clearPreference(mContext, NAME, null);
    }

    public void clearSavedProgressByUrl(String url) {
        SPTool.remove(mContext, NAME, DigestUtils.md5Hex(url));
    }
}
