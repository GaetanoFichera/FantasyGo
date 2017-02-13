package com.ficheralezzi.fantasygo.Model;


import android.util.Log;

public class ICalcoloDannoStrategy {

    private static final String TAG = "ICalcoloDannoStrategy";
    protected MCombattente attaccante = null;
    protected MCombattente difensore = null;

    public ICalcoloDannoStrategy() { }

    public void esegui(int id){
        if(MBattaglia.getSingletoneInstance().getCombattenteA().getId() == id){
            attaccante = MBattaglia.getSingletoneInstance().getCombattenteA();
            difensore = MBattaglia.getSingletoneInstance().getCombattenteB();
        } else{
            attaccante = MBattaglia.getSingletoneInstance().getCombattenteB();
            difensore = MBattaglia.getSingletoneInstance().getCombattenteA();
        }
        Log.d(TAG, "Attaccante: " + attaccante.toString());
        Log.d(TAG, "Difensore: " + difensore.toString());
    }

    public void applicaDanno(int valore){
        MBattaglia.getSingletoneInstance().getCombattenteById(difensore.getId()).getCaratteristiche().diminuisciPuntiFerita(valore);
    }

    public void applicaCura (int valore){
        MBattaglia.getSingletoneInstance().getCombattenteById(attaccante.getId()).getCaratteristiche().aumentaPuntiFerita(valore);
    }

    public void log (String classe, String msg){
        Log.d(classe, msg);
    }
}
