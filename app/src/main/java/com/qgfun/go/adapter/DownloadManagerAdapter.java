package com.qgfun.go.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadEntity;
import com.orhanobut.logger.Logger;
import com.qgfun.go.BuildConfig;
import com.qgfun.go.R;
import com.qgfun.go.activity.PlayerActivity;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.textview.label.LabelTextView;

import java.io.File;
import java.util.List;


/**
 * @author LLY cyclic inheritance involving
 */
public class DownloadManagerAdapter extends ArrayAdapter<DownloadEntity> {
    private Context mContext;
    private int mLayoutResourceId;
    private List<DownloadEntity> mInfos;
    private String categroyid;
    private OnEmptyListener mOnEmptyListener;
    private OnOptListener mOnOptListener;

    public void setOnOptListener(OnOptListener onOptListener) {
        mOnOptListener = onOptListener;
    }

    public DownloadManagerAdapter(Context context, String categroyid, List<DownloadEntity> downloadEntities, int layoutResourceId, OnEmptyListener onEmptyListener) {
        super(context, layoutResourceId, downloadEntities);
        Aria.download(this).register();
        this.mContext = context;
        this.categroyid = categroyid;

        this.mLayoutResourceId = layoutResourceId;
        this.mInfos = downloadEntities;
        this.mOnEmptyListener = onEmptyListener;
        if (mInfos.size() == 0) {
            mOnEmptyListener.show(true);
        } else {
            mOnEmptyListener.show(false);
        }
        //executeFixedDelay();
    }


    public void update(List<DownloadEntity> data) {
        mInfos.addAll(data);
        this.notifyDataSetChanged();
        if (mInfos == null || mInfos.size() == 0) {
            mOnEmptyListener.show(true);
        } else {
            mOnEmptyListener.show(false);
        }
    }


    public void deleteAndAdd(List<DownloadEntity> data) {
        mInfos.clear();
        mInfos.addAll(data);
        this.notifyDataSetChanged();
        if (mInfos == null || mInfos.size() == 0) {
            mOnEmptyListener.show(true);
        } else {
            mOnEmptyListener.show(false);
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(mLayoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.title = convertView.findViewById(R.id.tv);
            holder.progress = convertView.findViewById(R.id.progress);
            holder.operat = convertView.findViewById(R.id.operat);
            convertView.setTag(holder);
        } else {
            holder = (DownloadManagerAdapter.ViewHolder) convertView.getTag();
        }
        DownloadEntity downloadEntity = mInfos.get(position);
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
        holder.operat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnOptListener != null) {
                    mOnOptListener.operate(downloadEntity);
                }
            }
        });
        if ("1".equalsIgnoreCase(categroyid)) {
            convertView.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, PlayerActivity.class);
                //Uri uri = Uri.parse("file://" + downloadEntity.getFilePath());
                Uri uri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID +
                        ".fileProvider", new File(downloadEntity.getFilePath()));
                Logger.d("path:" + uri.toString());
                intent.setData(uri);
                mContext.startActivity(intent);
            });

        }
        convertView.setOnLongClickListener(v -> {
            new MaterialDialog.Builder(mContext)
                    .content("是否删除《" + downloadEntity.getFileName() + "》")
                    .positiveText("是")
                    .onPositive((dialog, which) -> {
                        Aria.download(this).load(downloadEntity.getId()).cancel(true);
                        mInfos.remove(position);
                        notifyDataSetChanged();
                        if (mInfos.size() == 0) {
                            mOnEmptyListener.show(true);
                        } else {
                            mOnEmptyListener.show(false);
                        }

                    })
                    .negativeText("否")
                    .show();
            return true;
        });
        return convertView;
    }

    public static class ViewHolder {
        public LabelTextView title;
        public TextView progress;
        public Button operat;
    }

    public interface OnEmptyListener {
        void show(boolean is);
    }


    public interface OnOptListener {
        void operate(DownloadEntity downloadEntity);
    }

    public List<DownloadEntity> getInfos() {
        return mInfos;
    }

    public static String getType(int type) {
        String typeString = "";
        switch (type) {
            case 0:
                typeString = "失败";
                break;
            case 1:
                typeString = "完成";
                break;
            case 2:
                typeString = "停止";
                break;
            case 3:
                typeString = "等待";
                break;
            case 4:
                typeString = "下载中";
                break;
            case 5:
                typeString = "预处理";
                break;
            case 6:
                typeString = "预处理完成";
                break;
            case 7:
                typeString = "取消";
                break;
            default:
                typeString = "未知";
                break;
        }
        return typeString;
    }

    void unRegister() {
        Aria.download(this).unRegister();
    }
}
