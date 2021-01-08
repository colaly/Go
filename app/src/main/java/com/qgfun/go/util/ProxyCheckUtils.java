package com.qgfun.go.util;

import android.text.TextUtils;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import static com.qgfun.go.App.isDebug;


/**
 * @author LLY
 */
public class ProxyCheckUtils {


    public static boolean isProxyOrVpn() {

        if (isDebug()) {
            return false;
        } else {
            return isWifiProxy() || isVpnUsed();
        }

    }

    /**
     * 是否使用代理(WiFi状态下的,避免被抓包)
     */
    private static boolean isWifiProxy() {
        String proxyAddress;
        int proxyPort;
        proxyAddress = System.getProperty("http.proxyHost");
        String portstr = System.getProperty("http.proxyPort");
        proxyPort = Integer.parseInt((portstr != null ? portstr : "-1"));
        Log.i("isWifiProxy",proxyAddress + "~");
        Log.i("isWifiProxy","port = " + proxyPort);
        return (!TextUtils.isEmpty(proxyAddress)) && (proxyPort != -1);
    }

    /**
     * 是否正在使用VPN
     */
    private static boolean isVpnUsed() {
        try {

            List<NetworkInterface> nilist = Collections.list(NetworkInterface.getNetworkInterfaces());
            if (nilist != null) {
                for (NetworkInterface intf : nilist) {
                    if (!intf.isUp() || intf.getInterfaceAddresses().size() == 0) {
                        continue;
                    }
                    Log.i("isVpnUsed", "isVpnUsed() NetworkInterface Name: " + intf.getName());
                    if ("tun0".equals(intf.getName()) || "ppp0".equals(intf.getName())) {
                        // The VPN is up
                        return true;
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }
}
