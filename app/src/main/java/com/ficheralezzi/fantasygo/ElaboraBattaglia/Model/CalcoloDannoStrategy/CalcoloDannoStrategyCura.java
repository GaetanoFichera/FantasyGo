package com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.CalcoloDannoStrategy;

import android.util.Log;

import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.ICalcoloDannoStrategy;
import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MBattaglia;
import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MCombattente;

public class CalcoloDannoStrategyCura implements ICalcoloDannoStrategy {

    private static final String TAG = "CdS Cura";
    protected MCombattente attaccante = null;
    protected MCombattente difensore = null;

    public void eseguiMossa(String id) {

        if(MBattaglia.getSingletoneInstance().getCombattenteA().getId() == id){
            attaccante = MBattaglia.getSingletoneInstance().getCombattenteA();
            difensore = MBattaglia.getSingletoneInstance().getCombattenteB();
        } else{
            attaccante = MBattaglia.getSingletoneInstance().getCombattenteB();
            difensore = MBattaglia.getSingletoneInstance().getCombattenteA();
        }
        Log.d(TAG, "Attaccante: " + attaccante.toString());
        Log.d(TAG, "Difensore: " + difensore.toString());

        Log.d(this.getClass().toString(), "Azione: Cura");

        int curaDanno = attaccante.getCaratteristiche().getPuntiFeritaMax() / 5;
        MBattaglia.getSingletoneInstance().getCombattenteById(attaccante.getId()).getCaratteristiche().aumentaPuntiFerita(curaDanno);
        Log.d(this.getClass().toString(), ((Integer) curaDanno).toString());
    }
}
