package com.qgfun.go.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hpplay.sdk.source.browse.api.LelinkServiceInfo;
import com.qgfun.go.R;
import com.qgfun.go.util.Log;

import java.util.List;

/**
 * @author LLY
 */
public class DevicesAdapter extends BaseAdapter {
    public List<LelinkServiceInfo> getData() {
        return data;
    }

    public void setData(List<LelinkServiceInfo> data) {
        this.data = data;
    }
    public void updateDatas(List<LelinkServiceInfo> infos) {
        if (null != infos) {
            data.clear();
            data.addAll(infos);
            notifyDataSetChanged();
        }
    }
    public List<LelinkServiceInfo> data;
    private LayoutInflater inflater;

    public DevicesAdapter(List<LelinkServiceInfo> data, Context context) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data!=null?data.size():0;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //加载布局为一个视图
        View view = inflater.inflate(R.layout.device_list_dialog_item, parent,false);
        TextView name = view.findViewById(R.id.name);
        TextView ip = view.findViewById(R.id.ip);
        ImageView icon=view.findViewById(R.id.icon);
        ImageView connect=view.findViewById(R.id.connect);
        LelinkServiceInfo lelinkServiceInfo = data.get(position);
        String types = lelinkServiceInfo.getTypes();
        Log.i("LelinkServiceInfo",lelinkServiceInfo.toString());
        Log.i("LelinkServiceInfo",lelinkServiceInfo.getTypes());
        name.setText(lelinkServiceInfo.getName());
        ip.setText(lelinkServiceInfo.getIp());
        icon.setImageResource(R.mipmap.icon_dlna);
        if (lelinkServiceInfo.isConnect()){
            connect.setImageResource(R.mipmap.icon_connected);
        }
        return view;
    }
}
