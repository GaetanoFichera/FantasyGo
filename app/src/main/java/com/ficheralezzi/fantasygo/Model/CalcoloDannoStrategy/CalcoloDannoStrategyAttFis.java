package com.ficheralezzi.fantasygo.Model.CalcoloDannoStrategy;

import android.nfc.Tag;
import android.util.Log;

import com.ficheralezzi.fantasygo.Model.ICalcoloDannoStrategy;
import com.ficheralezzi.fantasygo.Model.MBattaglia;
import com.ficheralezzi.fantasygo.Model.MCaratteristiche;

import java.util.Random;

public class CalcoloDannoStrategyAttFis extends ICalcoloDannoStrategy {

    private static final String TAG = "AttFis";

    @Override
    public void esegui(int id){

        super.esegui(id);

        int dannoParziale = 0;

        log(TAG, "Azione: AttFis");
        log(TAG, "Check: " + "ATT attacco: " + attaccante.getCaratteristiche().getAttaccoFisico() + "DIF difesa: " +  difensore.getCaratteristiche().getDifesaFisica());

        if(attaccante.getCaratteristiche().getAttaccoFisico() > difensore.getCaratteristiche().getDifesaFisica()){
            int dannoBase = attaccante.getCaratteristiche().getAttaccoFisico() - difensore.getCaratteristiche().getDifesaFisica();
            Random random = new Random();
            int dannoBonus = random.nextInt(attaccante.getCaratteristiche().getAttaccoFisico() +
                    ((attaccante.getCaratteristiche().getLivello() + attaccante.getCaratteristiche().getAttaccoFisico()) / 8) + 1);
            dannoParziale = dannoBase * dannoBonus;
            applicaDanno(dannoParziale);
            log(TAG, "Danno: " + dannoParziale);
        } else log(TAG, "Else Danno: " + dannoParziale);
    }
}
