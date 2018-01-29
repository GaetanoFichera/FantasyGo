package com.ficheralezzi.fantasygo.Utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ASUS on 26/10/2017.
 */

public class Volley {

    private final static String TAG = "Volley";

    //final String URL = "http://192.168.1.71:8080/ApiFantasyGo/ApiTest/TestConnection";
    private final static String URL = "http://172.20.10.2:8080/ApiFantasyGo/ApiTest/TestDb";

    public static void sendJSON(Context context, String Url, Object o) throws JSONException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(ow.writeValueAsString(o));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Log.i(TAG, jsonObject.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url, jsonObject,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    VolleyLog.v("Response:", response.toString(4));
                    Log.i("Response", response.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("Errore", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                Log.i("ErroreVolley", error.toString());
            }
        });

        com.android.volley.toolbox.Volley.newRequestQueue(context).getCache().clear();
        com.android.volley.toolbox.Volley.newRequestQueue(context).add(jsonObjectRequest);
    }
}
