package com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.CalcoloDannoStrategy;

import android.util.Log;

import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.ICalcoloDannoStrategy;
import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MBattaglia;
import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MCombattente;

import java.util.Random;

public class CalcoloDannoStrategyAttaccoPoderoso implements ICalcoloDannoStrategy {

    private static final String TAG = "CdS AttPod";
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

        int dannoParziale = 0;

        Log.d(TAG, "Azione: AttaccoPoderoso");

        if(attaccante.getCaratteristiche().getAttaccoFisico() > difensore.getCaratteristiche().getDifesaFisica()){
            int dannoBase = attaccante.getCaratteristiche().getAttaccoFisico() - difensore.getCaratteristiche().getDifesaFisica();
            Random random = new Random();
            int dannoBonus = random.nextInt(attaccante.getCaratteristiche().getAttaccoFisico() +
                    ((attaccante.getCaratteristiche().getLivello() + attaccante.getCaratteristiche().getAttaccoFisico()) / 8) + 1);
            dannoParziale = dannoBase * dannoBonus;
            int dannoParzialePoderoso = dannoParziale * 3;
            MBattaglia.getSingletoneInstance().getCombattenteById(difensore.getId()).getCaratteristiche().diminuisciPuntiFerita(dannoParzialePoderoso);
            Log.i(TAG, "Danno : " + ((Integer) dannoParzialePoderoso).toString());
        }
    }
}
