package com.qgfun.go.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.m3u8.M3U8VodOption;
import com.arialyy.aria.core.processor.IBandWidthUrlConverter;
import com.arialyy.aria.core.processor.IVodTsUrlConverter;
import com.arialyy.aria.core.task.DownloadTask;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.VideoView;
import com.orhanobut.logger.Logger;
import com.qgfun.go.R;
import com.qgfun.go.adapter.GridVideoUrlAdapter;
import com.qgfun.go.base.BaseActivity;
import com.qgfun.go.entity.DataHolder;
import com.qgfun.go.entity.PlayHistory;
import com.qgfun.go.entity.PlayHistory_Table;
import com.qgfun.go.entity.UrlResources;
import com.qgfun.go.entity.VideoDetail;
import com.qgfun.go.entity.VideoUrl;
import com.qgfun.go.util.FileTool;
import com.qgfun.go.util.ListStringUtils;
import com.qgfun.go.util.Log;
import com.qgfun.go.util.ProgressManagerImpl;
import com.qgfun.go.util.XToast;
import com.qgfun.go.view.CustomGridView;
import com.qgfun.go.view.VideoControllerUtils;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xuexiang.xui.widget.statelayout.MultipleStatusView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.qgfun.go.util.PlayUrlChangeUtils.changeUrl;

/**
 * @author LLY
 */
public class VideoPlayerActivity extends BaseActivity {

    private String title;
    private String videoName;

    @BindView(R.id.ll_statusView)
    MultipleStatusView mStatusView;

    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.collect)
    ImageView mCollectView;
    boolean isCollect = false;

    @BindView(R.id.player)
    VideoView mPlayer;

    @BindView(R.id.grid)
    CustomGridView mGridView;

    @BindView(R.id.des)
    TextView mDes;

    @BindView(R.id.scrollView)
    ScrollView mScrollView;

    @BindView(R.id.sort)
    ImageView mSort;
    boolean isAz = false;

    private GridVideoUrlAdapter adapter;

    private int mCurrentVideoPosition = 0;
    private List<VideoUrl> videoUrls;
    private ProgressManagerImpl mProgressManager;
    private PlayHistory mPlayHistory;
    private VideoControllerUtils mControllerUtils;

    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_video;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        Aria.download(this).register();
        mControllerUtils = new VideoControllerUtils(this, mPlayer);
    }

    @Override
    public void initData() {
        mStatusView.showLoading();
        VideoDetail data = DataHolder.getInstance().getData();
        if (data != null) {
            // 区别与 queryList()
            mPlayHistory = SQLite.select()
                    //查询第一个
                    .from(PlayHistory.class)
                    .where(PlayHistory_Table.vid.eq(data.getId()))
                    .querySingle();
            if (mPlayHistory != null) {
                mCollectView.setImageResource(R.mipmap.collect);
                isCollect = true;
            }
            videoUrls = data.getVideoUrls();
            Collections.reverse(videoUrls);
            videoName = data.getName();
            mDes.setText(Html.fromHtml(data.getDes()));
            Log.i("data:%s", data.getName());
            if (videoUrls.size() > 0) {
                title = videoName + " " + videoUrls.get(mCurrentVideoPosition).getTitle();
                mTitle.setText(title);
                initPlayer(this, mPlayer, videoUrls.get(mCurrentVideoPosition).getUrl());
                //监听播放结束
                mPlayer.addOnStateChangeListener(new VideoView.SimpleOnStateChangeListener() {

                    @Override
                    public void onPlayStateChanged(int playState) {
                        if (playState == VideoView.STATE_PLAYBACK_COMPLETED && isContinuousPlay(data.getType())) {
                            if (isAz) {
                                mCurrentVideoPosition += 1;
                                if (mCurrentVideoPosition >= videoUrls.size()) {
                                    return;
                                }
                            } else {
                                mCurrentVideoPosition -= 1;
                                if (mCurrentVideoPosition < 0 || videoUrls.size() == 0) {
                                    return;
                                }
                            }
                            //mPlayer.release();
                            //重新设置数据
                            String t = videoName + " " + videoUrls.get(mCurrentVideoPosition).getTitle();
                            mTitle.setText(t);
                            mPlayer.setUrl(videoUrls.get(mCurrentVideoPosition).getUrl());
                            mControllerUtils.getTitleView().setTitle(t);
                            //开始播放
                            mPlayer.replay(true);
                        }
                    }
                });
                mStatusView.showContent();
            } else {
                mStatusView.showEmpty();
            }
            adapter = new GridVideoUrlAdapter(this, R.layout.grid_video_url_item, videoUrls);
            mGridView.setAdapter(adapter);
            adapter.setOnViewClick(position -> {
                mCurrentVideoPosition = position;
                Log.i("position", ">>>" + position + "<<<");
            });
            adapter.setOnViewLongClick(new GridVideoUrlAdapter.onViewLongClick() {

                @Override
                public void onViewLongClick(VideoUrl item) {
                    // 创建m3u8点播文件配置
                    M3U8VodOption option = new M3U8VodOption();
                    option.merge(true)
                            .setBandWidthUrlConverter(new BandWidthUrlConverter(item.getUrl()))
                            //url转换器
                            .setVodTsUrlConvert(new VodTsUrlConverter());

                    long taskId = Aria.download(this)
                            .load(item.getUrl()) // 设置点播文件下载地址
                            .setFilePath(FileTool.getSDCardPath() + "Go" + File.separator + videoName + "_" + item.getTitle() + ".mp4") // 设置点播文件保存路径
                            .m3u8VodOption(option)   // 调整下载模式为m3u8点播
                            .ignoreFilePathOccupy()
                            .create();
                    XToast.success(VideoPlayerActivity.this, "《" + videoName + "_" + item.getTitle() + "》已加入下载队列,请在个人中心查看详情").show();
                }
            });
        } else {
            mStatusView.showError();
        }

    }

    @Download.onTaskStart
    void taskStart(DownloadTask task) {
        Logger.d("taskStart" + task.getTaskName() + ", " + task.getState());

    }

    @Download.onTaskResume
    void taskResume(DownloadTask task) {
        Logger.d("taskResume" + task.getTaskName() + ", " + task.getState());

    }

    @Download.onTaskStop
    void taskStop(DownloadTask task) {
        Logger.d("taskStop" + task.getEntity());
    }

    @Download.onTaskCancel
    void taskCancel(DownloadTask task) {
        Logger.d("taskCancel" + task.getEntity());
    }

    @Download.onTaskFail
    void taskFail(DownloadTask task) {
        Logger.d("taskFail" + task.getEntity());
        XToast.error(VideoPlayerActivity.this, "下载任务出错").show();
        Aria.download(this).load(task.getDownloadEntity().getId()).cancel(true);
    }

    @Download.onTaskComplete
    void taskComplete(DownloadTask task) {
        Logger.d("taskComplete" + task.getEntity());
    }

    @Download.onTaskRunning()
    void taskRunning(DownloadTask task) {
        Logger.d("taskRunning" + task.getEntity());
    }

    public static class BandWidthUrlConverter implements IBandWidthUrlConverter {
        private String url;

        BandWidthUrlConverter(String url) {
            this.url = url;
        }

        @Override
        public String convert(String m3u8Url, String bandWidthUrl) {
            if (bandWidthUrl.startsWith("/")) {
                Uri uri = Uri.parse(url);
                String parentUrl = uri.getScheme() + "://" + uri.getHost();
                if (uri.getPort() != -1) {
                    parentUrl += ":" + uri.getPort();
                }
                return parentUrl + bandWidthUrl;
            } else if (!bandWidthUrl.startsWith("http")) {
                int index = url.lastIndexOf("/");
                Logger.e("index:" + url.substring(0, index + 1) + ",bandWidthUrl:" + bandWidthUrl);
                return url.substring(0, index + 1) + bandWidthUrl;
            }

            return bandWidthUrl;
        }
    }

    public static class VodTsUrlConverter implements IVodTsUrlConverter {
        @Override
        public List<String> convert(String m3u8Url, List<String> tsUrls) {
            // 转换ts文件的url地址
            Uri uri = Uri.parse(m3u8Url);
            String parentUrl1 = uri.getScheme() + "://" + uri.getHost();
            if (uri.getPort() != -1) {
                parentUrl1 += ":" + uri.getPort();
            }
            int index = m3u8Url.lastIndexOf("/");
            String parentUrl2 = m3u8Url.substring(0, index + 1);
            List<String> newUrls = new ArrayList<>();
            for (String url : tsUrls) {
                String nUrl = url;
                if (url.startsWith("/")) {
                    nUrl = parentUrl1 + url;
                } else if (!url.startsWith("http")) {
                    nUrl = parentUrl2 + url;
                }
                newUrls.add(nUrl);
                Logger.d("VodTsUrlConverter convert:" + (nUrl + url));

            }

            return newUrls; // 返回有效的ts文件url集合
        }
    }

    /**
     * 是否自动下一集
     *
     * @param type
     * @return
     */
    private boolean isContinuousPlay(String type) {
        return type.contains("剧") || type.contains("综艺") || type.contains("动漫");
    }

    private void initPlayer(Context context, VideoView videoView, String url) {
        //使用ExoPlayer解码
        //videoView.setPlayerFactory(ExoMediaPlayerFactory.create());
        //设置视频地址
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Referer", url);
        mControllerUtils.getLelinkPlayerInfo().setUrl(url);
        videoView.setUrl(changeUrl(url), headers);

        StandardVideoController controller = mControllerUtils.getController();
        mControllerUtils.getTitleView().setTitle(title);
        //设置控制器
        videoView.setVideoController(controller);
        mProgressManager = new ProgressManagerImpl(this);
        videoView.setProgressManager(mProgressManager);
        videoView.seekTo(mProgressManager.getSavedProgress(url));
        videoView.start();
    }

    @OnClick(R.id.sort)
    public void sort() {
        if (isAz) {
            mSort.setImageResource(R.mipmap.za);
            isAz = false;
        } else {
            mSort.setImageResource(R.mipmap.az);
            isAz = true;
        }
        adapter.sort();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changePlayerUrl(VideoUrl item) {
        if (mPlayer != null) {
            title = videoName + " " + item.getTitle();
            mControllerUtils.getTitleView().setTitle(title);
            mTitle.setText(title);
            mControllerUtils.getLelinkPlayerInfo().setUrl(item.getUrl());
            String url = changeUrl(item.getUrl());
            mPlayer.release();
            mPlayer.setUrl(url);
            mPlayer.seekTo(mProgressManager.getSavedProgress(url));
            mPlayer.start();
        }
    }

    @OnClick(R.id.back)
    public void back() {
        finish();
    }

    @OnClick(R.id.collect)
    public void collect() {
        VideoDetail data = DataHolder.getInstance().getData();
        if (!isCollect) {
            isCollect = true;
            mCollectView.setImageResource(R.mipmap.collect);
            mPlayHistory = new PlayHistory();
            mPlayHistory.setVid(data.getId());
            mPlayHistory.setName(data.getName());
            mPlayHistory.setType(data.getType());
            mPlayHistory.setPic(data.getPic());
            mPlayHistory.setNote(data.getNote());
            mPlayHistory.setActor(data.getActor());
            mPlayHistory.setDirector(data.getDirector());
            mPlayHistory.setDes(data.getDes());
            mPlayHistory.setVideoUrls(ListStringUtils.list2Json(data.getVideoUrls()));
            mPlayHistory.setUrlReSources(UrlResources.toJsonString(data.getUrlResources()));
            mPlayHistory.setLast(data.getLast());
            boolean save = mPlayHistory.save();
            XToast.success(this, "收藏" + (save ? "成功" : "失败")).show();
        } else {
            boolean save = mPlayHistory.delete();
            isCollect = false;
            mCollectView.setImageResource(R.mipmap.uncollect);
            XToast.success(this, "取消收藏" + (save ? "成功" : "失败")).show();
        }
    }

    @Override
    public void onBackPressedSupport() {
        if (!mPlayer.onBackPressed()) {
            super.onBackPressed();
        }
        super.onBackPressedSupport();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPlayer != null) {
            mPlayer.resume();
        }
        mScrollView.smoothScrollTo(0, 0);
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
        EventBus.getDefault().unregister(this);
    }


}
