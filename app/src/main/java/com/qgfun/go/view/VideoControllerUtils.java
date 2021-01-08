package com.qgfun.go.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videocontroller.component.CompleteView;
import com.dueeeke.videocontroller.component.ErrorView;
import com.dueeeke.videocontroller.component.GestureView;
import com.dueeeke.videocontroller.component.PrepareView;
import com.dueeeke.videoplayer.ijk.IjkPlayer;
import com.dueeeke.videoplayer.player.AbstractPlayer;
import com.dueeeke.videoplayer.player.AndroidMediaPlayerFactory;
import com.dueeeke.videoplayer.player.PlayerFactory;
import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.util.PlayerUtils;
import com.hpplay.sdk.source.api.LelinkPlayerInfo;
import com.hpplay.sdk.source.browse.api.LelinkServiceInfo;
import com.hpplay.sdk.source.browse.api.LelinkServiceManager;
import com.hpplay.sdk.source.player.LelinkPlayerImpl;
import com.qgfun.go.R;
import com.qgfun.go.activity.TvScreenActivity;
import com.qgfun.go.util.Log;

import java.util.List;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

/**
 * @author LLY
 * date: 2020/4/14 18:27
 * package name: com.qgfun.go.view
 * description：TODO
 */
public class VideoControllerUtils {
    private int speed = 1;
    private int screenScale = 0;
    private int playerType =1;
    @NonNull
    private Context context;
    @NonNull
    private VideoView videoView;

    //标题栏
    private CustomTitleView titleView;

    private LelinkPlayerInfo lelinkPlayerInfo;

    private boolean isLive = false;
    private StandardVideoController mController;

    public StandardVideoController getController() {
        return mController;
    }

    public VideoControllerUtils(@NonNull Context context, @NonNull VideoView videoView) {
        this.context = context;
        this.videoView = videoView;
        mController = new StandardVideoController(context) {
            @Override
            public boolean showNetWarning() {
                return false;
            }
        };
        initView();
    }

    public VideoControllerUtils(@NonNull Context context, @NonNull VideoView videoView,int playerType) {
        this.context = context;
        this.videoView = videoView;
        this.playerType = playerType;
        mController = new StandardVideoController(context) {
            @Override
            public boolean showNetWarning() {
                return false;
            }
        };
        initView();
    }

    public VideoControllerUtils(@NonNull Context context,@NonNull  VideoView videoView,int playerType, boolean isLive) {
        this.context = context;
        this.videoView = videoView;
        this.isLive = isLive;
        this.playerType = playerType;
        mController = new StandardVideoController(context) {
            @Override
            public boolean showNetWarning() {
                return false;
            }
        };
        initView();
    }

   private void initView(){
        initVideoView();
        addPrepareView();
        addTitleView();
        addVodControlView();
        isLive();
        addGestureView();
        addErrorView();
        addCompleteView();
    }


    private void initVideoView() {
        switch (playerType){
            case 0:
               // videoView.setPlayerFactory(ExoMediaPlayerFactory.create());
                setIjkPlayerFactory();
                break;
            case 1:
                setIjkPlayerFactory();
                break;
            case 2:
                //使用MediaPlayer解码
                videoView.setPlayerFactory(AndroidMediaPlayerFactory.create());
                break;
            default:
                setIjkPlayerFactory();
                break;
        }
        if (videoView != null) {
            videoView.setOnStateChangeListener(new VideoView.OnStateChangeListener() {
                @SuppressLint("SourceLockedOrientationActivity")
                @Override
                public void onPlayerStateChanged(int playerState) {
                    if (playerState == VideoView.PLAYER_FULL_SCREEN) {
                        //获取设置的配置信息
                        Configuration mConfiguration = context.getResources().getConfiguration();
                        //获取屏幕方向
                        int ori = mConfiguration.orientation;
                        //竖屏
                        if (ori == ORIENTATION_LANDSCAPE) {
                            Log.i("orientation", "横屏");
                            int[] videoSize = videoView.getVideoSize();
                            if (videoSize.length == 2) {
                                int width = videoSize[0];
                                int height = videoSize[1];
                                Log.i("videoSize", "width:" + width + ",height:" + height);
                                if (width * 2 > height && width < height) {
                                    Activity activity = PlayerUtils.scanForActivity(context);
                                    //强制为竖屏
                                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                                }
                            }

                        } else if (ori == ORIENTATION_PORTRAIT) {
                            //竖屏
                            Log.i("orientation", "竖屏");
                        }
                    }
                }

                @Override
                public void onPlayStateChanged(int playState) {

                }
            });
        }

    }

    private void setIjkPlayerFactory() {
        //使用IjkPlayer解码
        //videoView.setPlayerFactory(IjkPlayerFactory.create());
        videoView.setPlayerFactory(new PlayerFactory() {
            @Override
            public AbstractPlayer createPlayer(Context context) {

                return new IjkPlayer(context) {
                    @Override
                    public void setOptions() {
                        //ijkplayer实时
                        //设置播放前的最大探测时间
//
                        //跳帧处理,放CPU处理较慢时，进行跳帧处理，保证播放流程，画面和声音同步
                        mMediaPlayer.setOption(4, "framedrop", 1L);
                        //倍速声音
                        //mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "soundtouch", 1);
                        //精准seek
                        mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "enable-accurate-seek", 1);
                        //http重定向到rtmp/Https，ijkplayer无法播放视频
                        mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "dns_cache_clear", 1);
                        ////设置seekTo能够快速seek到指定位置并播放
                        mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "fflags", "fastseek");
                    }
                };
            }
        });
    }

    public CustomTitleView getTitleView() {
        return titleView;
    }

    public LelinkPlayerInfo getLelinkPlayerInfo() {
        return lelinkPlayerInfo;
    }

    private void addPrepareView() {
        //准备播放界面
        PrepareView prepareView = new PrepareView(context);
        prepareView.setClickStart();
        //封面图
        ImageView thumb = prepareView.findViewById(R.id.thumb);
        thumb.setScaleType(ImageView.ScaleType.FIT_CENTER);
        thumb.setImageResource(R.mipmap.loading);
        mController.addControlComponent(prepareView);
    }

    private void addTitleView() {
        titleView = new CustomTitleView(context);
        lelinkPlayerInfo = new LelinkPlayerInfo();
        titleView.getmTv().setOnClickListener(v -> {

            // 设置媒体类型：LelinkPlayerInfo.TYPE_VIDEO：视频
            //               LelinkPlayerInfo.TYPE_AUDIO：音乐
            //               LelinkPlayerInfo.TYPE_IMAGE：图片

            lelinkPlayerInfo.setType(LelinkPlayerInfo.TYPE_VIDEO);
            // 设置本地文件path，支持本地推送
            // lelinkPlayerInfo.setLocalPath(localurl);
            // 设置网络url，支持网络推送，与本地推送二选一
            LelinkPlayerImpl leLinkPlayer = new LelinkPlayerImpl(context);
            leLinkPlayer.setPlayerListener(null);
            List<LelinkServiceInfo> connectLelinkServiceInfos = leLinkPlayer.getConnectLelinkServiceInfos();
            if (null != connectLelinkServiceInfos && !connectLelinkServiceInfos.isEmpty()) {
                leLinkPlayer.setDataSource(lelinkPlayerInfo);
                leLinkPlayer.start();
                LelinkServiceManager.getInstance(context).onPushButtonClick();
                Log.i("LeLink", "连接成功");
            } else {
                Log.i("LeLink", "未连接设备 ");

                Intent intent = new Intent();
                intent.setClass(context, TvScreenActivity.class);
                context.startActivity(intent);


            }
        });
        titleView.getmScreen().setOnClickListener(v -> {
            switch (screenScale) {
                case VideoView.SCREEN_SCALE_DEFAULT:
                    ((TextView) v).setText("默认");
                    videoView.setScreenScaleType(VideoView.SCREEN_SCALE_DEFAULT);
                    screenScale = 1;
                    break;
                case VideoView.SCREEN_SCALE_16_9:
                    videoView.setScreenScaleType(VideoView.SCREEN_SCALE_16_9);
                    ((TextView) v).setText("16:9");
                    screenScale = 2;
                    break;
                case VideoView.SCREEN_SCALE_4_3:
                    videoView.setScreenScaleType(VideoView.SCREEN_SCALE_4_3);
                    ((TextView) v).setText("4:3");
                    screenScale = 0;
                    break;
                default:
                    break;

            }
        });

        mController.addControlComponent(titleView);
    }

    private void addVodControlView() {
        //点播控制条
        CustomVodControlView vodControlView = new CustomVodControlView(context, isLive);
        vodControlView.getmSpeed().setOnClickListener(v -> {
            switch (speed) {
                case 1:
                    ((TextView) v).setText("1.5倍");
                    videoView.setSpeed(1.5f);
                    speed = 2;
                    break;
                case 2:
                    ((TextView) v).setText("2.0倍");
                    videoView.setSpeed(2.0f);
                    speed = 3;
                    break;
                case 3:
                    ((TextView) v).setText("0.75倍");
                    videoView.setSpeed(0.75f);
                    speed = 4;
                    break;
                case 4:
                    ((TextView) v).setText("1.0倍");
                    videoView.setSpeed(1.0f);
                    speed = 1;
                    break;
                default:
                    break;
            }
        });
        //是否显示底部进度条。默认显示
        vodControlView.showBottomProgress(true);
        mController.addControlComponent(vodControlView);
    }

    private void addGestureView() {
        //滑动控制视图
        GestureView gestureControlView = new GestureView(context);
        mController.addControlComponent(gestureControlView);
    }

    private void isLive() {
        //zhibo
        mController.setCanChangePosition(!isLive);
    }

    private void addErrorView() {
        mController.addControlComponent(new ErrorView(context) {
            @Override
            public void onPlayStateChanged(int playState) {
                if (playState == VideoView.STATE_ERROR) {
                    bringToFront();
                    setVisibility(VISIBLE);
                } else {
                    setVisibility(GONE);
                }
            }
        });//错误界面
    }

    private void addCompleteView() {
        //自动完成播放界面
        mController.addControlComponent(new CompleteView(context));
    }
}
