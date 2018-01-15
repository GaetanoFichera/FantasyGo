package com.ficheralezzi.fantasygo.Utils;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by gaetano on 24/08/17.
 */

public class NetworkManager {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    public static boolean isOnline(Activity activity) {
        ConnectivityManager cm =
                (ConnectivityManager) activity.getSystemService(activity.getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = NetworkManager.getConnectivityStatus(context);
        String status = null;
        if (conn == NetworkManager.TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == NetworkManager.TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == NetworkManager.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }

    public static void showToastOffline(Context context){
        Toast.makeText(context, "Connessione non Disponibile", Toast.LENGTH_SHORT).show();
    }

    public void updateLocationOnServer(Context context , Location location) throws JSONException {
        Volley volley = new Volley();
        Messaggio messaggio = new Messaggio();
        ArrayList<String> datiGiocatore = new ArrayList<>();
        datiGiocatore.add("G00001");
        datiGiocatore.add(String.valueOf(location.getLatitude()));
        datiGiocatore.add(String.valueOf(location.getLongitude()));
        messaggio.setMessaggio(1);
        messaggio.setObject(datiGiocatore);
        Log.i("network: ", messaggio.toString());
        volley.send(context, messaggio);
    }
}
