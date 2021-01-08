package com.qgfun.go.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.qgfun.go.R;
import com.qgfun.go.adapter.SearchListAdapter;
import com.qgfun.go.base.BaseActivity;
import com.qgfun.go.entity.AppInfo;
import com.qgfun.go.entity.HistoryData;
import com.qgfun.go.entity.VideoDetail;
import com.qgfun.go.util.Log;
import com.qgfun.go.util.ResourceUtils;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;
import com.xuexiang.xui.widget.searchview.MaterialSearchView;
import com.xuexiang.xui.widget.statelayout.MultipleStatusView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author LLY
 */
public class SearchActivity extends BaseActivity {
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;

    @BindView(R.id.search_view)
    MaterialSearchView mSearchView;

    @BindView(R.id.tag)
    FlowTagLayout mTagLayout;

    @BindView(R.id.rl_tips)
    RelativeLayout mTipLayout;

    @BindView(R.id.tips)
    TextView mTips;

    @BindView(R.id.clear)
    ImageView mClear;

    @BindView(R.id.statusView)
    MultipleStatusView mStatusView;

    @BindView(R.id.videoList)
    ListView mListView;

    private String mSearchKey;

    private SearchListAdapter mAdapter;

    private boolean hasData = false;
    String[] hotVideo;
    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        mTitleBar.addAction(new TitleBar.ImageAction(R.mipmap.search) {
            @Override
            public void performAction(View view) {
                mSearchView.showSearch();
                mSearchView.setVisibility(View.VISIBLE);
            }
        });
        mTitleBar.setLeftClickListener(v -> finish());

        List<HistoryData> historyDataList = SQLite.select()
                //查询第一个
                .from(HistoryData.class)
                .queryList();
        if (historyDataList.size() > 0){
           mTips.setText("最近搜索");
            List<String> hislist=new ArrayList<>();
            for (HistoryData historyData : historyDataList) {
                     hislist.add(historyData.getKey());
            }
             hotVideo=hislist.toArray(new String[hislist.size()]);
        }else {
            mTips.setText("热门推荐");
            hotVideo= getResources().getStringArray(R.array.hot_video);
            mClear.setVisibility(View.GONE);
        }
        mClear.setOnClickListener(v -> {
            SQLite.delete(HistoryData.class).execute();
            mTips.setText("热门推荐");
            hotVideo= getResources().getStringArray(R.array.hot_video);
            mTagLayout.setItems(hotVideo);
            mClear.setVisibility(View.GONE);
        });
        mTagLayout.setItems(hotVideo);
        mSearchView.setVoiceSearch(false);
        mSearchView.setEllipsize(true);
        mSearchView.setSuggestions(hotVideo);
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query) && query.startsWith("http")) {
                    Intent intent = new Intent(SearchActivity.this, WebActivity.class);
                    intent.putExtra("url", query);
                    startActivity(intent);
                }else if("tiktok".equalsIgnoreCase(query)){
                    Intent intent = new Intent(SearchActivity.this, TikTokActivity.class);
                    startActivity(intent);
                } else {
                    mSearchKey = query;
                    //Intent intent = new Intent(SearchActivity.this, PlayerActivity.class);
                    //intent.setData(Uri.parse("http://video.qging.com/v1/weal/rand"));
                    //startActivity(intent);
                    hasData = false;
                    search();
                    try {
                        new HistoryData(mSearchKey).save();
                    }catch (SQLiteConstraintException e){
                        Logger.e(mSearchKey+"已存在");

                    }

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });
        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
        mSearchView.setSubmitOnClick(true);
        mSearchView.setVisibility(View.GONE);
        mTagLayout.setOnTagSelectListener((parent, position, selectedList) -> {
            //Toast.makeText(SearchActivity.this, "onItemSelect:" + hotVideo[position], Toast.LENGTH_SHORT).show();
            mSearchKey = hotVideo[position];
            hasData = false;
            search();
        });
        mStatusView.setOnRetryClickListener(v -> search());
    }

    @Override
    public void initData() {
        mAdapter = new SearchListAdapter(this, R.layout.list_video_list_item, new ArrayList<>());
        mListView.setAdapter(mAdapter);
    }

    private void search() {
        mAdapter.clear();
        mStatusView.showLoading();
        AtomicInteger count=new AtomicInteger(0);
        AppInfo appInfo = ResourceUtils.getAppInfo(SearchActivity.this);
        List<AppInfo.Resources> resources = appInfo.getResources();
        for (AppInfo.Resources item:resources) {
            count.incrementAndGet();
            Observable.create((ObservableOnSubscribe<List<VideoDetail>>) emitter -> {
                emitter.onNext(ResourceUtils.search(mSearchKey, item));
                emitter.onComplete();
            }).subscribeOn(Schedulers.io())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<VideoDetail>>() {
                        Disposable disposable;

                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable = d;
                        }

                        @Override
                        public void onNext(List<VideoDetail> videoDetails) {
                            Log.i("Observer", "onNext");
                            if (videoDetails != null &&videoDetails.size() > 0){
                                mAdapter.update(videoDetails);
                                mStatusView.showContent();
                                hasData = true;
                                mTagLayout.setVisibility(View.GONE);
                                mTipLayout.setVisibility(View.GONE);
                            }else {
                                if (!hasData && count.get()>=resources.size()) {
                                    mStatusView.showEmpty();
                                    mTagLayout.setVisibility(View.VISIBLE);
                                    mTipLayout.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Logger.e(e.getMessage());
                            if (!hasData && count.get()>=resources.size()) {
                                mStatusView.showError();
                                mTagLayout.setVisibility(View.VISIBLE);
                                mTipLayout.setVisibility(View.VISIBLE);
                            } else {
                                mTagLayout.setVisibility(View.GONE);
                                mTipLayout.setVisibility(View.GONE);
                            }

                        }

                        @Override
                        public void onComplete() {
                            Log.i("Observer", "onComplete");
                            disposable.dispose();
                        }
                    });
        }

    }

    @Override
    public void onBackPressedSupport() {
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
            return;
        }
        super.onBackPressedSupport();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        }
    }
}
