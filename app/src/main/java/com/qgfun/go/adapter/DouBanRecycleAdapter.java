package com.qgfun.go.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qgfun.go.R;
import com.qgfun.go.entity.DouBanVideoInfo;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.utils.DrawableUtils;
import com.xuexiang.xui.widget.imageview.preview.loader.GlideMediaLoader;

/**
 * @author LLY
 * date: 2020/4/18 10:53
 * package name: com.qgfun.go.adapter
 * description：TODO
 */
public class DouBanRecycleAdapter extends BaseRecyclerAdapter<DouBanVideoInfo.DataBean> {

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.adapter_item_douban_index;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, DouBanVideoInfo.DataBean item) {
        if (item != null) {
            ImageView cover = holder.findViewById(R.id.cover);
            TextView rate = holder.findViewById(R.id.rate);
            TextView note = holder.findViewById(R.id.note);
            note.setVisibility(View.GONE);
            TextView title = holder.findViewById(R.id.title);
            rate.setText(item.getRate());
            title.setText(item.getTitle());
            Glide.with(cover.getContext())
                    .load(item.getCover())
                    .apply(GlideMediaLoader.getRequestOptions())
                    .placeholder(R.mipmap.place_holder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(cover);
            cover.setTag(R.id.cover, item.getCover());
        }
    }
}
