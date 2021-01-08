package com.qgfun.go.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.orhanobut.logger.Logger;
import com.qgfun.go.R;
import com.qgfun.go.adapter.CollecttionAdapter;
import com.qgfun.go.base.BaseMainFragment;
import com.qgfun.go.entity.PlayHistory;
import com.qgfun.go.entity.UrlResources;
import com.qgfun.go.entity.VideoDetail;
import com.qgfun.go.util.DrawableUtils;
import com.qgfun.go.util.ListStringUtils;
import com.qgfun.go.util.Log;
import com.qgfun.go.util.ResourceUtils;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xuexiang.xui.widget.statelayout.MultipleStatusView;

import java.util.List;

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
 * date: 2020/4/23 12:36
 * package name: com.qgfun.go.fragment
 * description：TODO
 */
public class CollectFragment extends BaseMainFragment {
    private static final String KEY = "CollectFragment";
    @BindView(R.id.tv_collect)
    TextView mTitle;

    @BindView(R.id.statusView)
    MultipleStatusView mStatusView;

    @BindView(R.id.videoList)
    ListView mListView;
    public static CollectFragment newInstance() {
        CollectFragment fragment = new CollectFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collect_index, container, false);
        ButterKnife.bind(this, view);
        Logger.i("ViewPagerFragment");
        initView();
        return view;
    }

    private void initView() {
        new DrawableUtils(mTitle, new DrawableUtils.OnDrawableListener() {
            @Override
            public void onLeft(View v, Drawable left) {
                pop();
            }

            @Override
            public void onRight(View v, Drawable right) {

            }
        });

        List<PlayHistory> list = SQLite.select().from(PlayHistory.class).queryList();

        CollecttionAdapter adapter = new CollecttionAdapter(_mActivity, R.layout.list_video_list_item, list, is -> {
            if (is) {
                mStatusView.showEmpty();
            } else {
                mStatusView.showContent();
            }
        });
        mListView.setAdapter(adapter);
        for (PlayHistory info : list) {
            Observable.create((ObservableOnSubscribe<List<VideoDetail>>) emitter -> {
                emitter.onNext(ResourceUtils.getVideoDetail(info.getVid(), UrlResources.jsonToObject(info.getUrlReSources())));
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
                            if (videoDetails != null && videoDetails.size() > 0) {
                                info.setVideoUrls(ListStringUtils.list2Json(videoDetails.get(0).getVideoUrls()));
                                info.update();
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i("Observer onError");
                            Log.e("getData", e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            Log.i("Observer onComplete");
                            disposable.dispose();
                        }
                    });
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        Logger.i("----initView begin-----");

    }

    @Override
    public boolean onBackPressedSupport() {
        pop();
        return true;
    }
}
