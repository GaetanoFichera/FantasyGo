package com.ficheralezzi.fantasygo.Utils;

/**
 * Created by gaetano on 10/07/17.
 */

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.internal.bind.MapTypeAdapterFactory;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Map;

/** stores the user object in SharedPreferences */
public class UserPrefs{

    /** This application's preferences label */
    private static final String PREFS_NAME = "com.ficheralezzi.package.FantasyGo";

    /** This application's preferences */
    private static SharedPreferences settings;

    /** This application's settings editor*/
    private static SharedPreferences.Editor editor;

    /** Constructor takes an android.content.Context argument*/
    public UserPrefs(Context ctx){
        if(settings == null){
            settings = ctx.getSharedPreferences(PREFS_NAME,
                    Context.MODE_PRIVATE );
        }
       /*
        * Get a SharedPreferences editor instance.
        * SharedPreferences ensures that updates are atomic
        * and non-concurrent
        */
        editor = settings.edit();
    }

    public boolean save(Object object, String key){
        Gson gson = new Gson();
        String object_json = gson.toJson(object);
        editor.putString(key, object_json);
        if(editor.commit()) return true;
        return false;
    }

    public Object load(Type classType, String key){
        Gson gson = new Gson();
        String object_json = settings.getString(key, "");
        return gson.fromJson(object_json, classType);
    }

    public int size(){
        return settings.getAll().size();
    }

    public int sizeOneType(String type){
        Map<String, ?> myMap = settings.getAll();
        int sizeOneType = 0;
        for (Map.Entry<String, ?> e : myMap.entrySet()) {
            if (e.getKey().endsWith(type)) {
                sizeOneType++;
            }
        }
        return sizeOneType;
    }

    public ArrayList<String> getIdsOneType(String type){
        Map<String, ?> myMap = settings.getAll();
        ArrayList<String> idsOneType = new ArrayList<>();
        for (Map.Entry<String, ?> e : myMap.entrySet()) {
            if (e.getKey().endsWith(type)) {
                idsOneType.add(e.getKey());
            }
        }
        return idsOneType;
    }
}
