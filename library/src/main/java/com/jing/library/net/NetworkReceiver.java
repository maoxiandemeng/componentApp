package com.jing.library.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.jing.library.utils.ToastUtils;

/**
 * 使用：NetworkReceiver networkReceiver = new NetworkReceiver();
 IntentFilter filter = new IntentFilter();
 filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
 filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
 filter.addAction("android.net.wifi.STATE_CHANGE");
 registerReceiver(networkReceiver, filter);

 unregisterReceiver(networkReceiver);

 添加对应的权限

 跳转到设置系统设置网络
 Intent intent = new Intent(
 android.provider.Settings.ACTION_WIRELESS_SETTINGS);
 startActivity(intent);
 */

public class NetworkReceiver extends BroadcastReceiver {
    public static final int NET_NO = 0;
    public static final int NET_WIFI = 1;
    public static final int NET_MOBILE = 2;
    private int net_state = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            // 获取网络连接管理的对象
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null && info.isConnected() && info.isAvailable()) {
                // 判断当前网络是否已经连接
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    String type = info.getTypeName();
                    if (type.equalsIgnoreCase("WIFI")) {
                        if (net_state != NET_WIFI) {
                            ToastUtils.showShortToast("已切换WIFI连接");
                        }
                        net_state = NET_WIFI;
                    } else if (type.equalsIgnoreCase("MOBILE")) {
                        if (net_state != NET_MOBILE) {
                            ToastUtils.showShortToast("已切换2G/3G/4G,请注意流量使用");
                        }
                        net_state = NET_MOBILE;
                    }
                } else {
                    if (net_state != NET_NO) {
                        ToastUtils.showShortToast("网络连接不可用");
                    }
                    net_state = NET_NO;
                }
            } else {
                if (net_state != NET_NO) {
                    ToastUtils.showShortToast("网络连接不可用");
                }
                net_state = NET_NO;
            }
        }
    }

}
