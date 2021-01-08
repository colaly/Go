package com.qgfun.go.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qgfun.go.R;
import com.qgfun.go.entity.VideoDetail;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.imageview.preview.loader.GlideMediaLoader;

/**
 * @author LLY
 * date: 2020/4/19 20:27
 * package name: com.qgfun.go.adapter
 * description：TODO
 */
public class UpdateRecycleAdapter  extends BaseRecyclerAdapter<VideoDetail> {

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.adapter_item_douban_index;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, VideoDetail item) {
        if (item != null) {
            ImageView cover = holder.findViewById(R.id.cover);
            TextView rate = holder.findViewById(R.id.rate);
            TextView note = holder.findViewById(R.id.note);
            rate.setVisibility(View.GONE);
            TextView title = holder.findViewById(R.id.title);
            note.setText(item.getNote());
            title.setText(item.getName());
            Glide.with(cover.getContext())
                    .load(item.getPic())
                    .apply(GlideMediaLoader.getRequestOptions())
                    .placeholder(R.mipmap.place_holder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(cover);
            cover.setTag(R.id.cover, item.getPic());
        }
    }
}
