package com.qgfun.go.fragment.douban;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.logger.Logger;
import com.qgfun.go.R;
import com.qgfun.go.adapter.DouBanRecycleAdapter;
import com.qgfun.go.entity.Category;
import com.qgfun.go.entity.DouBanVideoInfo;
import com.qgfun.go.event.SearchViewEvent;
import com.qgfun.go.util.NetTool;
import com.qgfun.go.util.ResourceUtils;
import com.qgfun.go.util.XToast;
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
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_FLING;

/**
 * @author LLY
 * date: 2020/4/7 19:57
 * package name: com.qgfun.beauty.fragment.picture
 * description：TODO
 */
public class DouBanPagerFragment extends SupportFragment {
    private Category mCategory;
    private static final String KEY = "DouBanCategory";

    @BindView(R.id.ll_stateful)
    MultipleStatusView mStatusView;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private DouBanRecycleAdapter mAdapter;
    private int mPage = 1;
    private boolean isFirst = true;


    public static DouBanPagerFragment newInstance(@NonNull Category category) {
        DouBanPagerFragment fragment = new DouBanPagerFragment();
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
        mStatusView.showLoading();
        initView();
        return view;
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerView.addItemDecoration(new GridDividerItemDecoration(_mActivity, 3, DensityUtils.dp2px(3)));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter = new DouBanRecycleAdapter());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isText = false;
            int state;

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                //返回false表示不能往下滑动，即代表到顶部了
                if (dy < 0 && state == SCROLL_STATE_FLING) {
                    /**
                     * 下滑监听  显示分类
                     */
                    EventBusActivityScope.getDefault(_mActivity).post(new SearchViewEvent.Builder().isShow(true).build());
                    isText = false;
                    Logger.i("下滑监听 显示全部");
                } else if (dy > 0 && !isText && state == SCROLL_STATE_FLING) {
                    EventBusActivityScope.getDefault(_mActivity).post(new SearchViewEvent.Builder().isShow(false).build());
                    /**
                     * 上滑监听 显示文字
                     */
                    Logger.i("上滑监听 显示文字");
                    isText = true;
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                state = newState;
            }
        });
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
                if (mPage <= 10000) {
                    getData(mPage, false);
                } else {
                    //XToast.toast("数据全部加载完毕");
                    refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                }
            }

            @Override
            public void onRefresh(final @NonNull RefreshLayout refreshLayout) {
                getData(0, true);
            }

        });

        mAdapter.setOnItemClickListener((itemView, item, position) -> {
            //((ImageIndexFragment)getParentFragment()).startBrotherFragment(ImageDetailFragment.newInstance(item));
            Logger.e("setOnItemClickListener:%s", item.getTitle());
            // 这里是父Fragment启动,要注意 栈层级
            //((SupportFragment) getParentFragment()).start(DouBanDetailFragment.newInstance(item));
            Fragment parentFragment = getParentFragment();
            if (parentFragment != null) {
                Fragment parentFragment1 = parentFragment.getParentFragment();
                if (parentFragment1 != null) {
                    Fragment parentFragment2 = parentFragment1.getParentFragment();
                    if (parentFragment2 != null) {
                        ((SupportFragment) parentFragment2).start(DouBanDetailFragment.newInstance(item));
                    } else {
                        ((SupportFragment) parentFragment1).start(DouBanDetailFragment.newInstance(item));
                    }
                } else {
                    ((SupportFragment) parentFragment).start(DouBanDetailFragment.newInstance(item));
                }
            } else {
                XToast.error(_mActivity, "应用出现层级显示异常，请反馈相关问题").show();
            }
            //((SupportFragment) .getParentFragment().getParentFragment()).start(DouBanDetailFragment.newInstance(item));

        });
    }

    private void getData(int page, boolean isRefersh) {
        Observable.create((ObservableOnSubscribe<List<DouBanVideoInfo.DataBean>>) emitter -> {
            emitter.onNext(ResourceUtils.getDouBanVideoIndex(mCategory.getId(), page));
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<DouBanVideoInfo.DataBean>>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(List<DouBanVideoInfo.DataBean> indexList) {
                        Logger.i("Observer onNext");
                        mStatusView.showContent();
                        if (indexList != null && indexList.size() != 0) {
                            if (isRefersh) {
                                mAdapter.refresh(indexList);

                            } else {
                                mAdapter.loadMore(indexList);
                                mPage += 20;
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
