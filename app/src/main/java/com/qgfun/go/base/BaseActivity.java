package com.qgfun.go.base;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.lahm.library.EasyProtectorLib;
import com.lahm.library.VirtualApkCheckUtil;
import com.qgfun.go.util.NetTool;
import com.qgfun.go.util.PermissionsUtils;
import com.qgfun.go.util.ProxyCheckUtils;
import com.tencent.bugly.beta.Beta;
import com.xuexiang.xui.widget.toast.XToast;

import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author LLY
 * date: 2020/4/7 10:09
 * package name: com.qgfun.beauty
 * description：TODO
 */
public abstract class BaseActivity extends SupportActivity implements ICallback, EasyPermissions.PermissionCallbacks {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(this);
        if (getLayoutId(savedInstanceState) > 0) {
            setContentView(getLayoutId(savedInstanceState));
        }
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Beta.checkUpgrade(false, true);
       /* if (EasyProtectorLib.checkIsRunningInEmulator(this, null)) {
            XToast.error(this,"本应用不支持虚拟机环境使用", XToast.LENGTH_LONG).show();
            kill();
            return;
        }

        if (VirtualApkCheckUtil.getSingleInstance().checkByCreateLocalServerSocket(getPackageName(), null) ||
                VirtualApkCheckUtil.getSingleInstance().checkByPrivateFilePath(this, null) ||
                VirtualApkCheckUtil.getSingleInstance().checkByOriginApkPackageName(this, null) ||
                VirtualApkCheckUtil.getSingleInstance().checkByHasSameUid(null) ||
                VirtualApkCheckUtil.getSingleInstance().checkByMultiApkPackageName(null)) {
            XToast.error(this,"本应用不支持分身环境使用", XToast.LENGTH_LONG).show();
            kill();
            return;
        }

        if (ProxyCheckUtils.isProxyOrVpn()) {
            XToast.error(this,"请关闭VPN/抓包/网络加速器等软件", XToast.LENGTH_LONG).show();
            kill();
            return;
        }

        if (!NetTool.isAvailable(this)) {
            XToast.error(this, "当前网络不可用").show();
        }*/

    }

    /**
     * 检查权限
     */

    public void checkPermission(String... perms) {
        boolean result = PermissionsUtils.checkPermission(this, perms);
        if (!result) {
            PermissionsUtils.requestPermission(this, "应用没有获得运行所必须的权限，是否设置权限!", 1, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        //获得权限
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle("应用权限设置")
                    .setRationale("你没有授予应用运行所必须的权限，请到应用设置中授予相应的权限")
                    .build().show();
        }
    }

    private void kill() {
        new Handler().postDelayed(() -> {
            ActivityStack.getInstance().finishActivity();
            System.exit(0);
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStack.getInstance().finishActivity();
    }
}
