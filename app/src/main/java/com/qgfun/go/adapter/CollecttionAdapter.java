package com.qgfun.go.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qgfun.go.R;
import com.qgfun.go.activity.VideoPlayerActivity;
import com.qgfun.go.entity.DataHolder;
import com.qgfun.go.entity.PlayHistory;
import com.qgfun.go.entity.UrlResources;
import com.qgfun.go.entity.VideoDetail;
import com.qgfun.go.util.ListStringUtils;
import com.qgfun.go.util.Log;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import java.util.Collections;
import java.util.List;


/**
 * @author LLY cyclic inheritance involving
 */
public class CollecttionAdapter extends ArrayAdapter<PlayHistory> {
    private Context mContext;
    private int mLayoutResourceId;
    private List<PlayHistory> mInfos;
    private OnEmptyListener mOnEmptyListener;

    public CollecttionAdapter(Context context, int layoutResourceId, List<PlayHistory> videoDetails, OnEmptyListener onEmptyListener) {
        super(context, layoutResourceId, videoDetails);
        mContext = context;
        this.mLayoutResourceId = layoutResourceId;
        mInfos = videoDetails;
        this.mOnEmptyListener = onEmptyListener;
        if (mInfos.size() == 0) {
            mOnEmptyListener.show(true);
        } else {
            mOnEmptyListener.show(false);
        }
        Collections.reverse(mInfos);
    }

    public void update(List<PlayHistory> data) {
        mInfos.addAll(data);
        this.notifyDataSetChanged();
        if (mInfos.size() == 0) {
            mOnEmptyListener.show(true);
        } else {
            mOnEmptyListener.show(false);
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        CollecttionAdapter.ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(mLayoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.title = convertView.findViewById(R.id.list_video_list_item_title);
            holder.director = convertView.findViewById(R.id.list_video_list_item_director);
            holder.note = convertView.findViewById(R.id.list_video_list_item__note);
            holder.actor = convertView.findViewById(R.id.list_video_list_item_actor);
            holder.type = convertView.findViewById(R.id.list_video_list_item_type);
            holder.form = convertView.findViewById(R.id.list_video_list_item_form);
            holder.last = convertView.findViewById(R.id.list_video_list_item_last);
            holder.img = convertView.findViewById(R.id.list_video_list_item_img);
            convertView.setTag(holder);
        } else {
            holder = (CollecttionAdapter.ViewHolder) convertView.getTag();
        }
        PlayHistory collectionInfo = mInfos.get(position);
        VideoDetail item = new VideoDetail();
        PlayHistory playHistory = mInfos.get(position);
        item.setId(playHistory.getVid());
        item.setName(playHistory.getName());
        item.setType(playHistory.getType());
        item.setPic(playHistory.getPic());
        item.setNote(playHistory.getNote());
        item.setActor(playHistory.getActor());
        item.setDirector(playHistory.getDirector());
        item.setDes(playHistory.getDes());
        item.setVideoUrls(ListStringUtils.json2List(playHistory.getVideoUrls()));
        Log.i("playHistory.getUrlReSources():%s",playHistory.getUrlReSources());
        UrlResources urlResources = UrlResources.jsonToObject(playHistory.getUrlReSources());
        Log.i("UrlResources:%s",urlResources.toString());
        item.setUrlResources(urlResources);
        item.setLast(playHistory.getLast());

        Log.i("VideoDetail:%s", item.toString());
        holder.title.setText(item.getName());
        holder.director.setText(String.format("导演：%s", item.getDirector()));
        holder.note.setText(item.getNote());
        holder.actor.setText(String.format("演员：%s", item.getActor()));
        holder.form.setText(String.format("来源：%s", item.getUrlResources().getFrom()));
        holder.type.setText(String.format("类型：%s", item.getType()));
        holder.last.setText(String.format("更新：%s", item.getLast()));
        Glide.with(mContext)
                .load(item.getPic())
                .error(R.mipmap.load)
                .placeholder(R.mipmap.load)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img);
        convertView.setOnClickListener(v -> {
            DataHolder.getInstance().setData(item);
            Intent intent = new Intent(mContext, VideoPlayerActivity.class);
            mContext.startActivity(intent);
        });
        convertView.setOnLongClickListener(v -> {
            new MaterialDialog.Builder(mContext)
                    .content("是否删除《" + collectionInfo.getName() + "》")
                    .positiveText("是")
                    .onPositive((dialog, which) -> {
                        boolean delete = collectionInfo.delete();
                        if (delete) {
                            mInfos.remove(position);
                            notifyDataSetChanged();
                            if (mInfos.size() == 0) {
                                mOnEmptyListener.show(true);
                            } else {
                                mOnEmptyListener.show(false);
                            }
                        }
                    })
                    .negativeText("否")
                    .show();
            return true;
        });
        return convertView;
    }

    private static class ViewHolder {
        TextView title;
        TextView director;
        TextView note;
        TextView actor;
        TextView type;
        TextView form;
        TextView last;
        ImageView img;
    }

    public interface OnEmptyListener {
        void show(boolean is);
    }
}
