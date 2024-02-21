package com.example.theme.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkStatusCallback extends ConnectivityManager.NetworkCallback {

    private static final String TAG = "NetworkStatusCallback";

    private Context context;
    private ConnectivityManager manager;

    public NetworkStatusCallback(Context context) {
        this.context = context;
        this.manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
        //调用判断
        isNetworkEnable();
    }

    @Override
    public void onLosing(Network network, int maxMsToLive) {
        super.onLosing(network, maxMsToLive);
    }

    @Override
    public void onLost(Network network) {
        super.onLost(network);
        //调用判断
        isNetworkEnable();
    }

    @Override
    public void onUnavailable() {
        super.onUnavailable();
    }

    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
    }

    @Override
    public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
        super.onLinkPropertiesChanged(network, linkProperties);
    }

    /**
     * 只判断当前可用的，无论是WIFI和蜂窝网络，原因是WIFI和蜂窝网络都可用时，如果只是断掉WIFI，也会调用onLoss方法
     */
    private void isNetworkEnable() {
        NetworkInfo active = manager.getActiveNetworkInfo();
        boolean result = null != active && active.isAvailable() &&
                active.getState() == NetworkInfo.State.CONNECTED;
        //下面根据结果，发送事件或者做业务操作
        Log.i(TAG, "result:" + result);
    }


}

