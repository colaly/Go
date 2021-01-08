package com.qgfun.go.adapter;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.qgfun.go.R;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

/**
 * @author LLY
 * date: 2020/4/18 10:53
 * package name: com.qgfun.go.adapter
 * description：TODO
 */
public class CategoryRecycleAdapter extends BaseRecyclerAdapter<String> {
    Context mContext;

    public CategoryRecycleAdapter(Context context) {
        mContext = context;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.adapter_item_category;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, String item) {
        TextView rate = holder.findViewById(R.id.tv_category);
        rate.setText(item);
        if (position == getPosition()) {
            rate.setBackground(mContext.getDrawable(R.drawable.shape_bg_category));
            rate.setTextColor(mContext.getResources().getColor(R.color.white));
        }else{
            //否则的话就全白色初始化背景
            rate.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            rate.setTextColor(mContext.getResources().getColor(R.color.black));
        }

    }

    private  int mPosition;

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
        this.notifyDataSetChanged();
    }

}
