package com.qgfun.go.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.hpplay.sdk.source.api.IConnectListener;
import com.hpplay.sdk.source.browse.api.IBrowseListener;
import com.hpplay.sdk.source.browse.api.ILelinkServiceManager;
import com.hpplay.sdk.source.browse.api.LelinkServiceInfo;
import com.hpplay.sdk.source.browse.api.LelinkServiceManager;
import com.hpplay.sdk.source.player.LelinkPlayerImpl;
import com.qgfun.go.R;
import com.qgfun.go.adapter.DevicesAdapter;
import com.qgfun.go.base.BaseActivity;
import com.qgfun.go.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * @author LLY
 */
public class TvScreenActivity extends BaseActivity implements IBrowseListener, IConnectListener {

    public ListView devices;
    private DevicesAdapter devicesAdapter;
    private Handler deliver = new Handler(Looper.getMainLooper());
    private LelinkPlayerImpl leLinkPlayer;
    private ILelinkServiceManager lelinkServiceManager;

    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_tv_screen;
    }

    @Override
    public void initView() {
        //StatusBarUtil.setTransparent(this);
        lelinkServiceManager = LelinkServiceManager.getInstance(this);
        lelinkServiceManager.browse(ILelinkServiceManager.TYPE_ALL);
        lelinkServiceManager.setOnBrowseListener(this);
        leLinkPlayer = new LelinkPlayerImpl(TvScreenActivity.this);
        leLinkPlayer.setConnectListener(this);
        devicesAdapter = new DevicesAdapter(new ArrayList<>(), TvScreenActivity.this);
        devices = findViewById(R.id.list);
        devices.setAdapter(devicesAdapter);
    }

    @Override
    public void initData() {

    }


    /**
     * 1 设置搜索监听
     */

    @Override
    public void onBrowse(int i, List<LelinkServiceInfo> list) {
        /*
        * 参数代表的意义：

        resultCode:
         - IBrowseListener.BROWSE_SUCCESS：搜索成功
         - IBrowseListener.BROWSE_ERROR_AUTH：搜索失败，**Auth错误，请检查您的网络设置或AppId和AppSecret**
        list：搜索出来的结果信息，请根据这个结果信息来显示搜索到的设备列表。
        * */
        deliver.post(() -> {
            switch (i) {
                case IBrowseListener.BROWSE_SUCCESS:
                    Log.i("devices", list.size() + "");
                    devicesAdapter.updateDatas(list);
                    devices.setOnItemClickListener((parent, view, position, id) -> leLinkPlayer.connect(list.get(position)));
                    break;
                case IBrowseListener.BROWSE_ERROR_AUTH:
                    Log.i("devices", "搜索失败，请检查您的网络设置");
                    Toast.makeText(this, "搜索失败，请检查您的网络设置", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Log.i("devices", "搜索失败，未知错误");
                    Toast.makeText(this, "搜索失败，未知错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    @Override
    public void onConnect(LelinkServiceInfo lelinkServiceInfo, int i) {

        deliver.post(() -> Toast.makeText(this, lelinkServiceInfo.getName() + "连接成功", Toast.LENGTH_SHORT).show());
        lelinkServiceManager.onBrowseListGone();
        finish();
    }

    /**
     * @param lelinkServiceInfo
     * @param what              what取值为:
     *                          IConnectListener.CONNECT_INFO_DISCONNECT：连接断开
     *                          IConnectListener.CONNECT_ERROR_FAILED：连接失败
     * @param i1
     */
    @Override
    public void onDisconnect(LelinkServiceInfo lelinkServiceInfo, int what, int i1) {
        deliver.post(() -> {
            switch (what) {
                case IConnectListener.CONNECT_INFO_DISCONNECT:
                    Toast.makeText(this, lelinkServiceInfo.getName() + "连接断开", Toast.LENGTH_SHORT).show();
                    break;
                case IConnectListener.CONNECT_ERROR_FAILED:
                    Toast.makeText(this, lelinkServiceInfo.getName() + "连接失败", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(this, lelinkServiceInfo.getName() + "连接失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }


    public void close(View v) {
        finish();
    }

    public void question(View v) {
        startActivity(new Intent(this, TvScreenHelperActivity.class));
    }
}
