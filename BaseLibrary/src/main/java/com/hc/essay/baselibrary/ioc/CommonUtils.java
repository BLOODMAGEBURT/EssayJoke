package com.hc.essay.baselibrary.ioc;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 常用工具类
 */
public class CommonUtils {

    // 联网判断，判断网络请求
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {       // 当前网络是连接的
                return info.getState() == NetworkInfo.State.CONNECTED;      // 当前所连接的网络可用
            }
        }
        return false;
    }
}
