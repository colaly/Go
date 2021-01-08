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
import com.qgfun.go.entity.VideoDetail;

import java.util.List;

/**
 * @author LLY
 */
public class GridVideoListAdapter extends ArrayAdapter<VideoDetail> {
    private Context mContext;
    private int layoutResourceId;
    private List<VideoDetail> mGridData;

    public GridVideoListAdapter(Context context, int resource, List<VideoDetail> data) {
        super(context, resource, data);
        this.mContext = context;
        this.layoutResourceId = resource;
        this.mGridData = data;
    }

    public void update(List<VideoDetail> data) {
        mGridData.addAll(data);
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.title = convertView.findViewById(R.id.grid_video_list_item__title);
            holder.note = convertView.findViewById(R.id.grid_video_list_item__note);
            holder.img = convertView.findViewById(R.id.grid_video_list_item_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        VideoDetail item = mGridData.get(position);
        holder.title.setText(item.getName());
        holder.note.setText(item.getNote());
        Glide.with(mContext)
                .load(item.getPic())
                .error(R.mipmap.load)
                .placeholder(R.mipmap.load)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img);
        convertView.setOnClickListener(v -> {
            DataHolder.getInstance().setData(item);
            Intent intent=new Intent(mContext, VideoPlayerActivity.class);
            mContext.startActivity(intent);
        });
        return convertView;
    }

    private class ViewHolder {
        TextView title;
        TextView note;
        ImageView img;
    }
}
