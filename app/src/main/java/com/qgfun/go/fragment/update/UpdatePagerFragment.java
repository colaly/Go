package com.qgfun.go.fragment.update;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.logger.Logger;
import com.qgfun.go.R;
import com.qgfun.go.activity.VideoPlayerActivity;
import com.qgfun.go.adapter.UpdateRecycleAdapter;
import com.qgfun.go.entity.Category;
import com.qgfun.go.entity.DataCache;
import com.qgfun.go.entity.DataCache_Table;
import com.qgfun.go.entity.DataHolder;
import com.qgfun.go.entity.UrlResources;
import com.qgfun.go.entity.VideoDetail;
import com.qgfun.go.util.NetTool;
import com.qgfun.go.util.ResourceUtils;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.xuexiang.xui.adapter.recyclerview.GridDividerItemDecoration;
import com.xuexiang.xui.utils.DensityUtils;
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
import me.yokeyword.fragmentation.SupportFragment;

import static com.qgfun.go.util.ResourceUtils.changeUrl;

/**
 * @author LLY
 * date: 2020/4/19 18:14
 * package name: com.qgfun.go.fragment.update
 */
public class UpdatePagerFragment extends SupportFragment {
    private Category mCategory;
    private static final String KEY = "UpdateCategory";

    @BindView(R.id.ll_stateful)
    MultipleStatusView mStatusView;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private UpdateRecycleAdapter mAdapter;

    private boolean isFirst = true;
    private int total = 1;
    private int current = 1;
    UrlResources zuida = ResourceUtils.getUrlSource().get("zuida");

    public static UpdatePagerFragment newInstance(@NonNull Category category) {
        UpdatePagerFragment fragment = new UpdatePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY, category);
        fragment.setArguments(bundle);
        Logger.i("category:%s", category.toString());
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        mCategory = bundle.getParcelable(KEY);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveStateToArguments();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.i("onDestroyView ");
        saveStateToArguments();
    }

    public void saveStateToArguments() {
        Logger.i("保存数据");
        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putParcelable(KEY, mCategory);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_page, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        mCategory = bundle.getParcelable(KEY);
        DataCache dataCache = SQLite.select()
                //查询第一个
                .from(DataCache.class)
                .where(DataCache_Table.url.eq(changeUrl(zuida.getUrl() + mCategory.getId())))
                .querySingle();
        if (dataCache != null) {
            try {
                total = Integer.parseInt(dataCache.getData());

            } catch (NumberFormatException e) {
                Logger.e(e.getMessage());
                total = 1;
            }

        } else {
            Logger.e("dataCache is null");

            total = 1;
        }
        Logger.i("total:%d", total);
        current = total;
        mStatusView.showLoading();
        initView();
        return view;
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerView.addItemDecoration(new GridDividerItemDecoration(_mActivity, 3, DensityUtils.dp2px(3)));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter = new UpdateRecycleAdapter());
        initListeners();
        mRefreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);


    }

    protected void initListeners() {
        mStatusView.setOnRetryClickListener(v -> mRefreshLayout.autoRefresh());
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(final @NonNull RefreshLayout refreshLayout) {
                if (current > 0) {
                    getData(current, false);
                } else {
                    //XToast.toast("数据全部加载完毕");
                    refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                }
            }

            @Override
            public void onRefresh(final @NonNull RefreshLayout refreshLayout) {
                getData(total, true);
            }

        });

        mAdapter.setOnItemClickListener((itemView, item, position) -> {
            Logger.e("setOnItemClickListener:%s", item.getName());
            DataHolder.getInstance().setData(item);
            startActivity(new Intent(_mActivity, VideoPlayerActivity.class));
        });
    }

    private void getData(int page, boolean isRefersh) {

        if (total == 1 && !isFirst) {
            DataCache dataCache = SQLite.select()
                    //查询第一个
                    .from(DataCache.class)
                    .where(DataCache_Table.url.eq(changeUrl(zuida.getUrl() + mCategory.getId())))
                    .querySingle();
            if (dataCache != null) {
                try {
                    total = Integer.parseInt(dataCache.getData());
                    Logger.i("total:%d", total);
                } catch (NumberFormatException e) {
                    total = 1;
                    Logger.i("total:%d", total);
                }

            } else {
                total = 1;
            }
            current = total;
            page = current;
            Logger.i("page:%d", page);
        }
        int finalPage = page;
        Observable.create((ObservableOnSubscribe<List<VideoDetail>>) emitter -> {
            emitter.onNext(ResourceUtils.newVideo(zuida, mCategory.getId(), finalPage + ""));
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
                    public void onNext(List<VideoDetail> indexList) {
                        Logger.i("Observer onNext");
                        mStatusView.showContent();
                        if (indexList != null && indexList.size() != 0) {
                            if (isRefersh) {
                                mAdapter.refresh(indexList);

                            } else {
                                mAdapter.loadMore(indexList);
                                current--;
                            }
                            isFirst = false;
                        } else {
                            if (isFirst) {
                                mStatusView.showEmpty();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("onError:%s", e.getMessage());
                        mRefreshLayout.closeHeaderOrFooter();
                        if (isFirst) {
                            if (!NetTool.isAvailable(_mActivity)) {
                                mStatusView.showNoNetwork();
                            } else {
                                mStatusView.showEmpty();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        Logger.e("Observer onComplete");
                        disposable.dispose();
                        if (isRefersh) {
                            mRefreshLayout.finishRefresh();
                        } else {
                            mRefreshLayout.finishLoadMore();
                        }
                    }
                });
    }
}
