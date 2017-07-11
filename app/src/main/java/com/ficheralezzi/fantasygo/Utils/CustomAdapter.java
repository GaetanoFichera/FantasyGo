package com.ficheralezzi.fantasygo.Utils;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gaetano on 11/07/17.
 */

public class CustomAdapter extends ArrayAdapter {

    //Mappa <Nome,Id>
    Map<String,String> myMap = new HashMap<String,String>();

    public CustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects, Map<String,String> myMap) {
        super(context, resource, objects);

        this.myMap = myMap;
    }

    public String getId(String name){
        return myMap.get(name);
    }
}
