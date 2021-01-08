package com.qgfun.go.fragment.category;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.logger.Logger;
import com.qgfun.go.R;
import com.qgfun.go.adapter.CategoryRecycleAdapter;
import com.qgfun.go.adapter.DouBanRecycleAdapter;
import com.qgfun.go.base.BaseMainFragment;
import com.qgfun.go.entity.DouBanVideoInfo;
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
import me.yokeyword.fragmentation.SupportFragment;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_FLING;


/**
 * @author LLY
 * date: 2020/4/7 13:15
 * package name: com.qgfun.beauty.fragment
 * description：TODO
 */
public class CategoryIndexFragment extends BaseMainFragment {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.ll_stateful)
    MultipleStatusView mStatusView;

    @BindView(R.id.rv_category)
    RecyclerView mCategory;

    @BindView(R.id.rv_type)
    RecyclerView mType;

    @BindView(R.id.rv_area)
    RecyclerView mArea;

    @BindView(R.id.rv_year)
    RecyclerView mYear;

    @BindView(R.id.tv_category)
    TextView categoryView;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerDataView;

    @BindView(R.id.llyt_rv_category)
    LinearLayout recyclerViewParentView;

    String category="全部·全部·全部·全部", categoryTag="", typeTag="", areaTag="", yearTag="";
    int mPage;
    private DouBanRecycleAdapter mAdapter;

    boolean isFirst = true;

    public static CategoryIndexFragment newInstance() {
        Bundle args = new Bundle();
        CategoryIndexFragment fragment = new CategoryIndexFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_index, container, false);
        ButterKnife.bind(this, view);
        initCategory();
        init();
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 这里可以不用懒加载,因为Adapter的场景下,Adapter内的子Fragment只有在父Fragment是show状态时,才会被Attach,Create
    }

    private void init() {
        mRecyclerDataView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerDataView.addItemDecoration(new GridDividerItemDecoration(_mActivity, 3, DensityUtils.dp2px(3)));

        mRecyclerDataView.setHasFixedSize(true);
        mRecyclerDataView.setAdapter(mAdapter = new DouBanRecycleAdapter());
        initData();
        mRefreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果

        mRecyclerDataView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isText=false;
            int state;
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                //返回false表示不能往下滑动，即代表到顶部了
                if (dy < 0&&state==SCROLL_STATE_FLING){
                        /**
                         * 下滑监听  显示分类
                         */
                        categoryView.setVisibility(View.GONE);
                        recyclerViewParentView.setVisibility(View.VISIBLE);
                        isText=false;
                } else if (dy>0&&!isText&&state==SCROLL_STATE_FLING){

                        /**
                         * 上滑监听 显示文字
                         */
                        categoryView.setVisibility(View.VISIBLE);
                        categoryView.setText(category);
                        recyclerViewParentView.setVisibility(View.GONE);
                        isText=true;
                    }
                }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                 state=newState;
            }
        });

    }

    protected void initData() {
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

            //((SupportFragment) getParentFragment()).start(CategoryDetailFragment.newInstance(item));
            //start(CategoryDetailFragment.newInstance(item));
            Fragment parentFragment = getParentFragment();
            if (parentFragment != null) {
                Fragment parentFragment1 = parentFragment.getParentFragment();
                if (parentFragment1 != null) {
                        ((SupportFragment) parentFragment1).start(CategoryDetailFragment.newInstance(item));
                } else {
                    ((SupportFragment) parentFragment).start(CategoryDetailFragment.newInstance(item));
                }
            } else {
                XToast.error(_mActivity, "应用出现层级显示异常，请反馈相关问题").show();
            }

        });
    }

    private void getData(int page, boolean isRefersh) {
        String c = "全部".equalsIgnoreCase(categoryTag) ? "" : categoryTag;
        String t = "全部".equalsIgnoreCase(typeTag) ? "" : typeTag;
        String a = "全部".equalsIgnoreCase(areaTag) ? "" : areaTag;
        String y = "";
        if ("2020".equalsIgnoreCase(yearTag)) {
            y = "2020,2020";
        } else if ("2019".equalsIgnoreCase(yearTag)) {
            y = "2019,2019";
        } else if ("2010年代".equalsIgnoreCase(yearTag)) {
            y = "2010,2019";
        }else if ("2000年代".equalsIgnoreCase(yearTag)) {
            y = "2000,2009";
        }else if ("90年代".equalsIgnoreCase(yearTag)) {
            y = "1990,1999";
        }else if ("80年代".equalsIgnoreCase(yearTag)) {
            y = "1980,1989";
        }else if ("70年代".equalsIgnoreCase(yearTag)) {
            y = "1970,1979";
        }else if ("60年代".equalsIgnoreCase(yearTag)) {
            y = "1960,1969";
        }else if ("更早".equalsIgnoreCase(yearTag)) {
            y = "1,1959";
        }else {
            y="";
        }
        String finalY = y;
        Observable.create((ObservableOnSubscribe<List<DouBanVideoInfo.DataBean>>) emitter -> {
            emitter.onNext(ResourceUtils.getDouBanVideoCategoryIndex(c, t, a, finalY, page));
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

    private void initCategory() {
        categoryView.setVisibility(View.GONE);
        recyclerViewParentView.setVisibility(View.VISIBLE);
        mCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mType.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mArea.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mYear.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        //mCategory.addItemDecoration(new GridDividerItemDecoration(_mActivity, 2, DensityUtils.dp2px(3)));
        //mCategory.setHasFixedSize(true);
        CategoryRecycleAdapter categoryAdapter = new CategoryRecycleAdapter(_mActivity);
        CategoryRecycleAdapter typeAdapter = new CategoryRecycleAdapter(_mActivity);
        CategoryRecycleAdapter areaAdapter = new CategoryRecycleAdapter(_mActivity);
        CategoryRecycleAdapter yearAdapter = new CategoryRecycleAdapter(_mActivity);

        mCategory.setAdapter(categoryAdapter);
        mType.setAdapter(typeAdapter);
        mArea.setAdapter(areaAdapter);
        mYear.setAdapter(yearAdapter);
        categoryAdapter.loadMore(getResources().getStringArray(R.array.category_category));
        typeAdapter.loadMore(getResources().getStringArray(R.array.category_type));
        areaAdapter.loadMore(getResources().getStringArray(R.array.category_area));
        yearAdapter.loadMore(getResources().getStringArray(R.array.category_years));
        categoryAdapter.setOnItemClickListener((itemView, item, position) -> {
            categoryTag = item;
            categoryAdapter.setPosition(position);
            category();
        });
        typeAdapter.setOnItemClickListener((itemView, item, position) -> {
            typeTag = item;
            typeAdapter.setPosition(position);

            category();
        });
        areaAdapter.setOnItemClickListener((itemView, item, position) -> {
            areaTag = item;
            areaAdapter.setPosition(position);
            category();
        });
        yearAdapter.setOnItemClickListener((itemView, item, position) -> {
            yearTag = item;
            yearAdapter.setPosition(position);
            category();
        });
    }

    public void category() {

        if (TextUtils.isEmpty(categoryTag)) {
            categoryTag = "全部";
        }
        if (TextUtils.isEmpty(typeTag)) {
            typeTag = "全部";
        }
        if (TextUtils.isEmpty(areaTag)) {
            areaTag = "全部";
        }
        if (TextUtils.isEmpty(yearTag)) {
            yearTag = "全部";
        }
        category = categoryTag + "·" + typeTag + "·" + areaTag + "·" + yearTag;
        mPage=0;
        isFirst=true;
        mAdapter.clear();
        mRefreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果
    }


}
