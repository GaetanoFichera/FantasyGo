package com.ficheralezzi.fantasygo.Repository;

import android.content.Context;
import android.util.Log;

import com.ficheralezzi.fantasygo.Utils.NetworkManager;

import org.json.JSONException;

public class GiocatoreRepo {

    private static final String TAG = "GiocatoreRepo";

    public static void saveLocation(String idGiocatore, double latitudine, double longitudine, Context context) throws JSONException {
        NetworkManager.updateLocationOnServer(context, idGiocatore, latitudine, longitudine);

        Log.d(TAG, "Posizione salvata!");
    }
}
