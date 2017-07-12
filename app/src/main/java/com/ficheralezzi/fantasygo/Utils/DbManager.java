package com.ficheralezzi.fantasygo.Utils;

import android.content.Context;
import android.util.Log;

import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MGiocatore;

/**
 * Created by gaetano on 11/07/17.
 */

public class DbManager {
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
     * 2- nel db intento dell'app (all'interno di UserPreferences
     * 3- nel db remoto
     *
     * questa fuonzione dovrebbe essere capace di capire chi tra le tre è la più aggiornata e chi
     * deve essere aggiornata
     *
     * sarebbe ancora meglio se si riesce a riconoscere quali parti sono da aggiornare per ogni singolo
     * punto e fare così degli update mirati
     */

    public void updateDb(){}

    /**mi stavo chiedendo anche se questa classe DbManager sia responsabile della conoscenza dei model
     * presenti nel gioco e quindi va ad aggiornare i vari db sapendo che sta aggiornando ad esempio i
     * personaggi e il giocatore OPPURE aggiorna senza sapere cosa sta aggiornando
     */
}
