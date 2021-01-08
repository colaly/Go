package com.qgfun.go.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.qgfun.go.R;
import com.qgfun.go.activity.VideoPlayerActivity;
import com.qgfun.go.entity.DataHolder;
import com.qgfun.go.entity.VideoDetail;
import com.qgfun.go.util.Log;

import java.util.List;

/**
 * @author LLY
 * date: 2020/2/25 11:49
 * package name: com.qgfun.go.adapter
 */
public class SearchListAdapter extends ArrayAdapter<VideoDetail> {
    private Context mContext;
    private int mLayoutResourceId;
    private List<VideoDetail> mVideoDetails;

    public SearchListAdapter(Context context, int layoutResourceId, List<VideoDetail> videoDetails) {
        super(context, layoutResourceId, videoDetails);
        mContext = context;
        this.mLayoutResourceId = layoutResourceId;
        mVideoDetails = videoDetails;
    }

    public void update(List<VideoDetail> data) {
        mVideoDetails.addAll(data);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        SearchListAdapter.ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(mLayoutResourceId, parent, false);
            holder = new SearchListAdapter.ViewHolder();
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
            holder = (SearchListAdapter.ViewHolder) convertView.getTag();
        }
        VideoDetail item = mVideoDetails.get(position);
        Log.i("VideoDetail:%s", item.toString());
        holder.title.setText(item.getName());
        holder.director.setText(String.format("导演：%s", item.getDirector()));
        if (TextUtils.isEmpty(item.getNote())){
            holder.note.setVisibility(View.GONE);
        }else {
            holder.note.setVisibility(View.VISIBLE);
            holder.note.setText(item.getNote());
        }
        holder.actor.setText(String.format("演员：%s", item.getActor()));
        holder.form.setText(String.format("来源：%s", item.getUrlResources().getFrom()));
        holder.type.setText(String.format("类型：%s", item.getType()));
        holder.last.setText(String.format("更新：%s", item.getLast()));
        Log.i(item.getPic());
        GlideUrl glideUrl = new GlideUrl(item.getPic(), new LazyHeaders.Builder()
                .addHeader("Referer", item.getPic())
                .addHeader("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.1.1; zh-cn;  MI2 Build/JRO03L) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30 XiaoMi/MiuiBrowser/1.0")
                .build());
        Glide.with(mContext)
                .load(glideUrl)
                .error(R.mipmap.load)
                .thumbnail(Glide.with(mContext).load(R.mipmap.load))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img);
        convertView.setOnClickListener(v -> {
            DataHolder.getInstance().setData(item);
            Intent intent = new Intent(mContext, VideoPlayerActivity.class);
            mContext.startActivity(intent);
        });
        return convertView;
    }

    private class ViewHolder {
        TextView title;
        TextView director;
        TextView note;
        TextView actor;
        TextView type;
        TextView form;
        TextView last;
        ImageView img;
    }
}
