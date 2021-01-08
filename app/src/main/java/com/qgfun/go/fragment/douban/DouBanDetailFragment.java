package com.qgfun.go.fragment.douban;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.orhanobut.logger.Logger;
import com.qgfun.go.R;
import com.qgfun.go.adapter.SearchListAdapter;
import com.qgfun.go.base.BaseMainFragment;
import com.qgfun.go.entity.DouBanVideoInfo;
import com.qgfun.go.entity.VideoDetail;
import com.qgfun.go.util.Log;
import com.qgfun.go.util.ResourceUtils;
import com.xuexiang.xui.widget.statelayout.MultipleStatusView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * @author LLY
 * date: 2020/4/7 13:15
 * package name: com.qgfun.beauty.fragment
 */
public class DouBanDetailFragment extends BaseMainFragment {
    @BindView(R.id.title)
    TextView mTitle;
    
    @BindView(R.id.statusView)
    MultipleStatusView mStatusView;

    @BindView(R.id.videoList)
    ListView mVideoList;
    private SearchListAdapter mAdapter;
    private boolean hasData = false;
    private DouBanVideoInfo.DataBean mData;
    private static final String KEY = "DouBanDetailCategory";

    public static DouBanDetailFragment newInstance(DouBanVideoInfo.DataBean data) {
        DouBanDetailFragment fragment = new DouBanDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY, data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_douban_detail, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        mData = bundle.getParcelable(KEY);
        mStatusView.showLoading();
        initView();
        return view;
    }

    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        Logger.i("----initView begin-----");

    }

    private void initView() {
        mTitle.setText(mData.getTitle());
        mAdapter = new SearchListAdapter(_mActivity, R.layout.list_video_list_item, new ArrayList<>());
        mVideoList.setAdapter(mAdapter);
        search(mData.getTitle());

    }

    private void search(String key) {
        mAdapter.clear();
        mStatusView.showLoading();
        AtomicInteger count=new AtomicInteger(0);
        for (Map.Entry<String, UrlResources> entry : ResourceUtils.getAppInfo().entrySet()) {
            count.incrementAndGet();
            Observable.create((ObservableOnSubscribe<List<VideoDetail>>) emitter -> {
                emitter.onNext(ResourceUtils.search(key, entry.getValue()));
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
                            Log.i("Observer onNext");
                            if (videoDetails == null || videoDetails.size() == 0) {
                                if (!hasData && count.get()>=ResourceUtils.getAppInfo().size()) {
                                    mStatusView.showEmpty();
                                }
                            } else {
                                mAdapter.update(videoDetails);
                                mStatusView.showContent();
                                hasData = true;
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i("Observer onError");
                            Log.e("getData：%s", e.getMessage());
                            if (!hasData && count.get()>=ResourceUtils.getAppInfo().size()) {
                                mStatusView.showError();
                            } else {

                            }

                        }

                        @Override
                        public void onComplete() {
                            Log.i("Observer onComplete");
                            disposable.dispose();
                        }
                    });
        }

    }

    @OnClick(R.id.back)
    public  void back(){
        this.pop();
    }


    @Override
    public boolean onBackPressedSupport() {
        this.pop();
        return true;
    }
}
