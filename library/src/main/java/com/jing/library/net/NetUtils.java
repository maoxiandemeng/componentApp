package com.jing.library.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.jing.library.utils.Utils;

/**
 * Created by jon on 2016/12/20
 */

public class NetUtils {
    //没有网络
    public static final int NETWORKTYPE_INVALID = 0;
    //流量网络
    public static final int NETWORKTYPE_MOBILE = 1;
    //wifi网络
    public static final int NETWORKTYPE_WIFI = 2;

    /**
     * 判断网络是否连接
     * @return 已连接true 未连接false
     */
    public static boolean isNetworkConnected() {
        // 获取手机所有连接管理对象（包括wifi，net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) Utils.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            // 获取网络连接管理的对象
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 判断当前网络是否已经连接
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * get the network type (wifi,wap,2g,3g)
     *
     * @param context
     * @return
     */
    public static int getNetWorkType(Context context) {

        int mNetWorkType = NETWORKTYPE_INVALID;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();
            if (type.equalsIgnoreCase("WIFI")) {
                mNetWorkType = NETWORKTYPE_WIFI;
            } else if (type.equalsIgnoreCase("MOBILE")) {
                mNetWorkType = NETWORKTYPE_MOBILE;
            }
        } else {
            mNetWorkType = NETWORKTYPE_INVALID;
        }
        return mNetWorkType;
    }
}
