package com.qgfun.go.adapter;

import androidx.annotation.NonNull;

import com.qgfun.go.R;
import com.qgfun.go.entity.TvPlayInfo;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;

/**
 * @author LLY
 * date: 2020/4/18 10:53
 * package name: com.qgfun.go.adapter
 * description：TODO
 */
public class TvUrlRecycleAdapter extends BaseRecyclerAdapter<TvPlayInfo.DataBean.UrlsBean> {

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.adapter_item_tv_url;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, TvPlayInfo.DataBean.UrlsBean item) {
        if (item != null) {
            RoundButton tv = holder.findViewById(R.id.video_url_item_title);
            tv.setText(item.getName());
        }
    }
}
