package com.qgfun.go.util;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import pub.devrel.easypermissions.EasyPermissions;

public class PermissionsUtils {

    /**
     * @param context 上下文
     * @param perms   需要校验的权限
     * @return true:已经获取权限  false: 未获取权限，主动请求权限
     */
    public static boolean checkPermission(Context context, String... perms) {
        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 请求权限
     *
     * @param activity    Activity
     * @param rationale   描述
     * @param requestCode 请求码
     * @param perms       请求的权限
     */
    public static void requestPermission(AppCompatActivity activity, String rationale, int requestCode, String... perms) {
        EasyPermissions.requestPermissions(activity, rationale, requestCode, perms);
    }

    /**
     * 请求权限
     *
     * @param host        Fragment
     * @param rationale   描述
     * @param requestCode 请求码
     * @param perms       请求的权限
     */
    public static void requestPermission(Fragment host, String rationale, int requestCode, String... perms) {
        EasyPermissions.requestPermissions(host, rationale, requestCode, perms);
    }

}
