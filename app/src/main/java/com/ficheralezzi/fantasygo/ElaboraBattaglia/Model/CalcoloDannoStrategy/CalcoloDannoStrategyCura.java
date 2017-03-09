package com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.CalcoloDannoStrategy;

import android.util.Log;

import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.ICalcoloDannoStrategy;
import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MBattaglia;
import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MCombattente;

public class CalcoloDannoStrategyCura implements ICalcoloDannoStrategy {

    private static final String TAG = "ICalcoloDannoStrategy";
    protected MCombattente attaccante = null;
    protected MCombattente difensore = null;

    public void eseguiMossa(int id) {

        log(this.getClass().toString(), "Azione: Cura");

        int curaDanno = attaccante.getCaratteristiche().getPuntiFeritaMax() / 5;
        MBattaglia.getSingletoneInstance().getCombattenteById(attaccante.getId()).getCaratteristiche().aumentaPuntiFerita(curaDanno);
        log(this.getClass().toString(), ((Integer) curaDanno).toString());
    }

    public void log (String classe, String msg){
        Log.d(classe, msg);
    }

}
