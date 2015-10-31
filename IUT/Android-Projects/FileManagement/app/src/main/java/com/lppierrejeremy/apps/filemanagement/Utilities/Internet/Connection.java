package com.lppierrejeremy.apps.filemanagement.Utilities.Internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Connection Class
 */
public class Connection {

    /**
     * Check current connectivity
     * @param context Context
     * @return Boolean
     */
    public static boolean checkConnectivity(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
