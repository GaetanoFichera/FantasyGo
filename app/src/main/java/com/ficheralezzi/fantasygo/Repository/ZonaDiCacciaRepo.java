package com.ficheralezzi.fantasygo.Repository;

import android.util.Log;

import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MArea;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MMostro;
import com.ficheralezzi.fantasygo.Utils.DbManager;
import com.ficheralezzi.fantasygo.Utils.NetworkManager;

import java.util.ArrayList;

public class ZonaDiCacciaRepo {

    private final static String TAG = "ZonaDiCacciaRepo";

    public static MArea retrieveArea(double latitudine, double longitudine){
        MArea newArea = DbManager.getSingletoneInstance().getAreaByLocation(latitudine, longitudine);

        Log.d(TAG, "Nuova ZonaDiCaccia: " + newArea);

        return newArea;
    }

    public static ArrayList<MMostro> retrieveMostri(String idArea){
        ArrayList<MMostro> newMostri = DbManager.getSingletoneInstance().getMostriByIdArea(idArea);

        Log.d(TAG, "Nuovi Mostri: " + newMostri);

        return newMostri;
    }
}
