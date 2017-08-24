package com.ficheralezzi.fantasygo.Utils;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by gaetano on 24/08/17.
 */

public class NetworkManager {

    public static boolean isOnline(Activity activity) {
        ConnectivityManager cm =
                (ConnectivityManager) activity.getSystemService(activity.getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
