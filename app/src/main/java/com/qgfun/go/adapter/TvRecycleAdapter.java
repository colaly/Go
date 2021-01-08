package com.qgfun.go.adapter;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.qgfun.go.R;
import com.qgfun.go.entity.TvInfo;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

/**
 * @author LLY
 * date: 2020/4/18 10:53
 * package name: com.qgfun.go.adapter
 * description：TODO
 */
public class TvRecycleAdapter extends BaseRecyclerAdapter<TvInfo.DataBean> {

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.adapter_item_tv_index;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, TvInfo.DataBean item) {
        if (item != null) {

            TextView tv = holder.findViewById(R.id.tv);
            tv.setText(item.getName());
        }
    }
}
