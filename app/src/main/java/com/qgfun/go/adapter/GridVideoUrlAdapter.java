package com.qgfun.go.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.qgfun.go.R;
import com.qgfun.go.entity.VideoUrl;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;
import java.util.List;

/**
 * @author LLY
 */
public class GridVideoUrlAdapter extends ArrayAdapter<VideoUrl> {
    private Context mContext;
    private int layoutResourceId;
    private List<VideoUrl> mGridData;
    private onViewClick onViewClick;
    private onViewLongClick onViewLongClick;

    public GridVideoUrlAdapter(Context context, int resource, List<VideoUrl> data) {
        super(context, resource, data);
        this.mContext = context;
        this.layoutResourceId = resource;
        this.mGridData = data;
    }

    public void update(List<VideoUrl> data) {
        mGridData.addAll(data);
        this.notifyDataSetChanged();
    }

    public void sort() {
        Collections.reverse(mGridData);
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
            holder.button = convertView.findViewById(R.id.grid_video_url_item_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        VideoUrl item = mGridData.get(position);
        holder.button.setText(item.getTitle());
        convertView.setOnClickListener(v -> {
            EventBus.getDefault().post(item);
            if (onViewClick != null) {
                onViewClick.onViewClick(position);
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onViewLongClick != null) {
                    onViewLongClick.onViewLongClick(item);
                    return true;
                }
                return false;
            }
        });
        return convertView;
    }

    private static class ViewHolder {
        RoundButton button;
    }

    //接口回调
    public interface onViewClick {
        void onViewClick(int position);
    }

    public interface onViewLongClick {
        void onViewLongClick(VideoUrl item);
    }

    public void setOnViewClick(onViewClick onViewClick) {
        this.onViewClick = onViewClick;
    }

    public void setOnViewLongClick(GridVideoUrlAdapter.onViewLongClick onViewLongClick) {
        this.onViewLongClick = onViewLongClick;
    }
}
