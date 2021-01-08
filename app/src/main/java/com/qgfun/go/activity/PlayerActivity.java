package com.qgfun.go.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.VideoView;
import com.orhanobut.logger.Logger;
import com.qgfun.go.R;
import com.qgfun.go.base.BaseActivity;
import com.qgfun.go.util.ProgressManagerImpl;
import com.qgfun.go.util.SPTool;
import com.qgfun.go.view.VideoControllerUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.qgfun.go.util.PlayUrlChangeUtils.changeUrl;

/**
 * @author LLY
 */
public class PlayerActivity extends BaseActivity {
    @BindView(R.id.player)
    VideoView mPlayer;
    private String title;
    private VideoControllerUtils mVideoControllerUtils;

    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_player;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        mVideoControllerUtils = new VideoControllerUtils(this, mPlayer);
    }

    @Override
    public void initData() {
        String dataString = this.getIntent().getDataString();
        if (!TextUtils.isEmpty(dataString)) {
            mVideoControllerUtils.getLelinkPlayerInfo().setUrl(dataString);
            initPlayer(this, mPlayer, changeUrl(dataString));
        } else {
            Toast.makeText(this, "无效播放地址", Toast.LENGTH_SHORT).show();
        }

    }

    private void initPlayer(Context context, VideoView videoView, String url) {
        String type = SPTool.getString(context, "type");
        Logger.e("video url"+url);
        videoView.setUrl(url);
        StandardVideoController controller = mVideoControllerUtils.getController();
        //设置控制器
        videoView.setVideoController(controller);
        //保存播放进度
        videoView.setProgressManager(new ProgressManagerImpl(this));
        videoView.startFullScreen();
        videoView.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPlayer != null) {
            mPlayer.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPlayer != null) {
            mPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.release();
        }
    }
}
