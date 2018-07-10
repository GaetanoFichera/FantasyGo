package com.ficheralezzi.fantasygo.Utils;

import android.content.Context;
import android.util.Log;

import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MCaratteristiche;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MArea;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MEquipaggiamento;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MGiocatore;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MMostro;

import java.util.ArrayList;

/**
 * Created by gaetano on 11/07/17.
 */

public class DbManager {
    private static final String TAG = "DbManager";
    private UserPreferencesManager myUserPrefsManager;
    private static DbManager singletoneinstance = null;

    public DbManager() {
    }

    public void init(Context context) {
        if (myUserPrefsManager == null){
            this.myUserPrefsManager = new UserPreferencesManager(context);
        } else this.myUserPrefsManager = new UserPreferencesManager(context);
    }

    public static DbManager getSingletoneInstance() {
        if (singletoneinstance == null) {
            singletoneinstance = new DbManager();
        }
        return singletoneinstance;

    }

    /**i dati sono memorizzati in 3 punti diversi:
     * 1- nelle variabili al momento esistenti in memoria
     * 2- nel db interno all'app (all'interno di UserPreferences)
     * 3- nel db remoto
     *
     * questa fuonzione dovrebbe essere capace di capire chi tra le tre è la più aggiornata e chi
     * deve essere aggiornata
     *
     * sarebbe ancora meglio se si riuscisse a riconoscere quali parti sarebbero da aggiornare per ogni singolo
     * punto così da aggiungere aggiornamenti mirati
     */

    public void updateDb(){}

    public MArea getAreaByLocation(double latitudine, double longitudine){
        //da implementare con interazione col db
        Posizione posizione = new Posizione(latitudine, longitudine);
        ArrayList<Posizione> confini = new ArrayList<Posizione>();
        confini.add(posizione);
        String id = "Area51";
        MArea area = new MArea(confini, id);



        return area;
    }

    public ArrayList<MMostro> getMostriByIdArea(String idArea){
        //da implementare con interazione col db
        MCaratteristiche caratteristichemostro = new MCaratteristiche(1, 500, 500, 10, 8, 5, 4, 6, "DardoInfuocato", 0, 10, "Mag");
        MEquipaggiamento equipaggiamentoMostro = new MEquipaggiamento("W001", "A01");
        ArrayList<MMostro> mostri= new ArrayList<>();
        MMostro mostro = new MMostro("M0001", caratteristichemostro, equipaggiamentoMostro,19);
        mostri.add(mostro);
        //aggiungere mostri manualmente nell'array
        return mostri;
    }
}
