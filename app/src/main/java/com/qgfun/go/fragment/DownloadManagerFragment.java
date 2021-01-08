package com.qgfun.go.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.download.m3u8.M3U8VodOption;
import com.arialyy.aria.core.processor.IBandWidthUrlConverter;
import com.arialyy.aria.core.processor.IVodTsUrlConverter;
import com.arialyy.aria.core.task.DownloadTask;
import com.orhanobut.logger.Logger;
import com.qgfun.go.R;
import com.qgfun.go.adapter.DownloadManagerAdapter;
import com.qgfun.go.base.BaseBackFragment;
import com.qgfun.go.entity.Category;
import com.qgfun.go.util.XToast;
import com.xuexiang.xui.widget.statelayout.MultipleStatusView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.qgfun.go.adapter.DownloadManagerAdapter.getType;

/**
 * @author LLY
 * date: 2020/4/23 12:36
 * package name: com.qgfun.go.fragment
 * description：TODO
 */
public class DownloadManagerFragment extends BaseBackFragment {
    private static final String KEY = "DownloadManagerFragment";
    private Category mCategory;

    @BindView(R.id.statusView)
    MultipleStatusView mStatusView;

    @BindView(R.id.task)
    LinearLayout mTaskView;


    @BindView(R.id.videoList)
    ListView mListView;
    private DownloadManagerAdapter mDownloadManagerAdapter;

    public static DownloadManagerFragment newInstance(Category category) {
        DownloadManagerFragment fragment = new DownloadManagerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY, category);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download_manager, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCategory = bundle.getParcelable(KEY);
        }
        Logger.i("ViewPagerFragment");
        Aria.download(this).register();
        initView();

        return view;
    }

    private void initView() {
        if ("1".equalsIgnoreCase(mCategory.getId())) {
            mTaskView.setVisibility(View.GONE);
        }
        mDownloadManagerAdapter = new DownloadManagerAdapter(_mActivity, mCategory.getId(), getData(mCategory.getId()), R.layout.download_manager_list, is -> {
            if (is) {
                mStatusView.showEmpty();
            } else {
                mStatusView.showContent();

            }
        });
        mListView.setAdapter(mDownloadManagerAdapter);
        mDownloadManagerAdapter.setOnOptListener(new DownloadManagerAdapter.OnOptListener() {
            @Override
            public void operate(DownloadEntity downloadEntity) {
                if (downloadEntity.getState() == 4) {
                    Aria.download(this).load(downloadEntity.getId()).stop();
                } else {
                    // 创建m3u8点播文件配置
                    M3U8VodOption option = new M3U8VodOption();
                    option.merge(true)
                            .setBandWidthUrlConverter(new BandWidthUrlConverter(downloadEntity.getUrl()))
                            //url转换器
                            .setVodTsUrlConvert(new VodTsUrlConverter());
                    Aria.download(this).load(downloadEntity.getId()).m3u8VodOption(option).resume();
                }

                mDownloadManagerAdapter.deleteAndAdd(getData(mCategory.getId()));
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        Logger.i("----initView begin-----");

    }

    private List<DownloadEntity> getData(String categoryId) {
        List<DownloadEntity> list;
        if ("0".equalsIgnoreCase(categoryId)) {
            list = Aria.download(this).getAllNotCompleteTask();
        } else {
            list = Aria.download(this).getAllCompleteTask();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        Logger.d("categoryId:" + categoryId + ",list:" + list.size());
        return list;
    }

    private void updataItem(DownloadEntity downloadEntity) {
        List<DownloadEntity> infos = mDownloadManagerAdapter.getInfos();
        int position = 0;
        boolean isFind = false;
        if (infos == null || infos.size() == 0) {
            Logger.e("infos is null");
            return;
        }
        for (int i = 0; i < infos.size(); i++) {
            if (infos.get(i).getFileName().equalsIgnoreCase(downloadEntity.getFileName())) {
                position = i;
                isFind = true;
                Logger.e("position is find");
                break;
            }
        }
        if (!isFind) {
            return;
        }
        int firstvisible = mListView.getFirstVisiblePosition();
        int lastvisibale = mListView.getLastVisiblePosition();
        if (position >= firstvisible && position <= lastvisibale) {
            View view = mListView.getChildAt(position - firstvisible);
            DownloadManagerAdapter.ViewHolder holder = (DownloadManagerAdapter.ViewHolder) view.getTag();
            holder.title = view.findViewById(R.id.tv);
            holder.progress = view.findViewById(R.id.progress);
            holder.operat = view.findViewById(R.id.operat);

            holder.title.setText(downloadEntity.getFileName().replace(".mp4", ""));
            holder.progress.setText(String.format("%d%%", downloadEntity.getPercent()));
            holder.title.setLabelText(getType(downloadEntity.getState()));
            if (downloadEntity.getState() == 2) {
                holder.operat.setText("继续");
            } else if (downloadEntity.getState() == 4) {
                holder.operat.setText("暂停");
            } else if (downloadEntity.getState() == 1) {
                holder.progress.setVisibility(View.GONE);
                holder.operat.setVisibility(View.GONE);
            }
        }
    }

    @Download.onTaskStart
    void taskStart(DownloadTask task) {
        Logger.d("taskStart:" + task.getTaskName() + ", " + task.getState());
    }

    @Download.onTaskResume
    void taskResume(DownloadTask task) {
        Logger.d("taskResume1:" + task.getTaskName() + ", " + task.getState());
        updataItem(task.getDownloadEntity());
    }

    @Download.onTaskStop
    void taskStop(DownloadTask task) {
        Logger.d("taskStop1:" + task.getEntity());
        updataItem(task.getDownloadEntity());
    }

    @Download.onTaskCancel
    void taskCancel(DownloadTask task) {
        Logger.d("taskCancel1:" + task.getEntity());
        updataItem(task.getDownloadEntity());
    }

    @Download.onTaskFail
    void taskFail(DownloadTask task) {
        Logger.d("taskFail1:" + task.getEntity());
        XToast.error(_mActivity, "下载任务出错").show();
        updataItem(task.getDownloadEntity());
        //Aria.download(this).load(task.getDownloadEntity().getId()).cancel(true);
    }

    @Download.onTaskComplete
    void taskComplete(DownloadTask task) {
        Logger.d("taskComplete:" + task.getEntity());
        mDownloadManagerAdapter.deleteAndAdd(getData(mCategory.getId()));
    }

    @Download.onTaskRunning()
    void taskRunning(DownloadTask task) {
        Logger.d("taskRunning:" + task.getPercent());
        updataItem(task.getDownloadEntity());
    }

    private static class BandWidthUrlConverter implements IBandWidthUrlConverter {
        private String url;

        BandWidthUrlConverter(String url) {
            this.url = url;
        }

        @Override
        public String convert(String m3u8Url, String bandWidthUrl) {
            if (bandWidthUrl.startsWith("/")) {
                Uri uri = Uri.parse(url);
                String parentUrl1 = uri.getScheme() + "://" + uri.getHost();
                if (uri.getPort() != -1) {
                    parentUrl1 += ":" + uri.getPort();
                }
                return parentUrl1 + bandWidthUrl;
            } else if (!bandWidthUrl.startsWith("http")) {
                int index = url.lastIndexOf("/");
                Logger.e("index:" + url.substring(0, index + 1) + ",bandWidthUrl:" + bandWidthUrl);
                return url.substring(0, index + 1) + bandWidthUrl;
            }

            return bandWidthUrl;
        }
    }

    private static class VodTsUrlConverter implements IVodTsUrlConverter {
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

    @OnClick(R.id.resumeAllTask)
    public void resumeAllTask() {
        Aria.download(this).resumeAllTask();
        mDownloadManagerAdapter.deleteAndAdd(getData("0"));
    }

    @OnClick(R.id.stopAllTask)
    public void stopAllTask() {
        Aria.download(this).stopAllTask();
        mDownloadManagerAdapter.deleteAndAdd(getData("0"));
    }

    @OnClick(R.id.removeAllTask)
    public void removeAllTask() {
        List<DownloadEntity> downloadEntities = getData("0");
        for (DownloadEntity entity : downloadEntities) {
            Aria.download(this).load(entity.getId()).cancel(true);
        }

        mDownloadManagerAdapter.deleteAndAdd(new ArrayList<>());
    }

}
