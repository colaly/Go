package com.qgfun.go.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.util.L;
import com.qgfun.go.R;
import com.qgfun.go.adapter.Tiktok3Adapter;
import com.qgfun.go.base.BaseActivity;
import com.qgfun.go.cache.PreloadManager;
import com.qgfun.go.entity.TiktokBean;
import com.qgfun.go.util.ProxyVideoCacheManager;
import com.qgfun.go.util.Utils;
import com.qgfun.go.view.TikTokController;
import com.qgfun.go.view.VerticalViewPager;
import com.qgfun.go.view.render.TikTokRenderViewFactory;
import com.xuexiang.xui.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LLY
 * date: 2020/5/12 16:58
 * package name: com.qgfun.go.activity
 * description：TODO
 */
public class TikTokActivity extends BaseActivity {

    /**
     * 当前播放位置
     */
    private int mCurPos;
    VideoView mVideoView;
    private List<TiktokBean> mVideoList = new ArrayList<>();
    private Tiktok3Adapter mTiktok3Adapter;
    private ViewPager2 mViewPager;

    private PreloadManager mPreloadManager;

    private TikTokController mController;

    private static final String KEY_INDEX = "index";
    private RecyclerView mViewPagerImpl;
    
    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_tiktok;
    }

    @Override
    public void initView() {
        StatusBarUtils.translucent(this);
        initViewPager();
        initVideoView();
        mPreloadManager = PreloadManager.getInstance(this);
        addData();
        Intent extras = getIntent();
        int index = extras.getIntExtra(KEY_INDEX, 0);

        mViewPager.post(() -> {
            if (index == 0) {
                startPlay(0);
            } else {
                mViewPager.setCurrentItem(index, false);
            }
        });
    }

    @Override
    public void initData() {

    }

    private void initVideoView() {
        mVideoView = new VideoView(this);
        mVideoView.setLooping(true);
        //以下只能二选一，看你的需求
        mVideoView.setRenderViewFactory(TikTokRenderViewFactory.create());
//        mVideoView.setScreenScaleType(VideoView.SCREEN_SCALE_CENTER_CROP);

        mController = new TikTokController(this);
        mVideoView.setVideoController(mController);
    }

    private void initViewPager() {
        mViewPager = findViewById(R.id.vp2);
        mViewPager.setOffscreenPageLimit(4);
        mTiktok3Adapter = new Tiktok3Adapter(mVideoList);
        mViewPager.setAdapter(mTiktok3Adapter);
        mViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            private int mCurItem;

            /**
             * VerticalViewPager是否反向滑动
             */
            private boolean mIsReverseScroll;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (position == mCurItem) {
                    return;
                }
                mIsReverseScroll = position < mCurItem;
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == mCurPos) {
                    return;
                }
                mViewPager.post(() -> startPlay(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == VerticalViewPager.SCROLL_STATE_DRAGGING) {
                    mCurItem = mViewPager.getCurrentItem();
                }
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    mPreloadManager.resumePreload(mCurPos, mIsReverseScroll);
                } else {
                    mPreloadManager.pausePreload(mCurPos, mIsReverseScroll);
                }
            }
        });

        //ViewPage2内部是通过RecyclerView去实现的，它位于ViewPager2的第0个位置
        mViewPagerImpl = (RecyclerView) mViewPager.getChildAt(0);
    }

    private void startPlay(int position) {
        int count = mViewPagerImpl.getChildCount();
        for (int i = 0; i < count; i++) {
            View itemView = mViewPagerImpl.getChildAt(i);
            Tiktok3Adapter.ViewHolder viewHolder = (Tiktok3Adapter.ViewHolder) itemView.getTag();
            if (viewHolder.mPosition == position) {
                mVideoView.release();
                Utils.removeViewFormParent(mVideoView);
                TiktokBean tiktokBean = mVideoList.get(position);
                String playUrl = mPreloadManager.getPlayUrl(tiktokBean.videoPlayUrl);
                L.i("startPlay: " + "position: " + position + "  url: " + playUrl);
                mVideoView.setUrl(playUrl);
                mController.addControlComponent(viewHolder.mTikTokView, true);
                viewHolder.mPlayerContainer.addView(mVideoView, 0);
                mVideoView.start();
                mCurPos = position;
                break;
            }
        }
    }

    public void addData() {
        int size = mVideoList.size();
        List<TiktokBean> data=new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(new TiktokBean("","","","http://api.qgfun.com/v1/gallery/rand" +
                    "?t="+System.currentTimeMillis()/1000,System.currentTimeMillis()/1000,"http://api.qgfun.com/v1/weal/rand?t="+System.currentTimeMillis()/1000));
        }
        mVideoList.addAll(data);
        //使用此方法添加数据，使用notifyDataSetChanged会导致正在播放的视频中断
        mTiktok3Adapter.notifyItemRangeChanged(size, mVideoList.size());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPreloadManager.removeAllPreloadTask();
        //清除缓存，实际使用可以不需要清除，这里为了方便测试
        ProxyVideoCacheManager.clearAllCache(this);
    }
}
