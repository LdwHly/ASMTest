package com.example.theme.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;


public class NetworkUtils {
    /**
     * 判断当前网络是否可用(6.0以上版本)
     * 实时
     *
     * @param context
     * @return
     */
    public static boolean isNetSystemUsable(Context context) {
        boolean isNetUsable = false;
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            NetworkCapabilities networkCapabilities =
                    manager.getNetworkCapabilities(manager.getActiveNetwork());
            if (networkCapabilities != null) {
                isNetUsable = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
            }
        } else {
            isNetUsable = isNetConnection(context);
        }
        return isNetUsable;
    }

    /**
     * 判断当前是否有网络连接,但是如果该连接的网络无法上网，也会返回true
     *
     * @param mContext
     * @return
     */
    public static boolean isNetConnection(Context mContext) {
        if (mContext != null) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) mContext.
                            getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo!=null && networkInfo.isConnected() && networkInfo.isAvailable()) {
                return networkInfo.getState() == NetworkInfo.State.CONNECTED;
            }
        }
        return false;
    }

    public static void registerNetConnection(Context context, ConnectivityManager.NetworkCallback listener) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr != null) {
            //这里只监听了WIFI和蜂窝网络，如果有其他要求，可以增加
            NetworkRequest nr = new NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                    .build();
            connMgr.registerNetworkCallback(nr, listener);
        }

    }
}
