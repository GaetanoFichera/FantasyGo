package com.ficheralezzi.fantasygo.Model.CalcoloDannoStrategy;

import com.ficheralezzi.fantasygo.Model.ICalcoloDannoStrategy;
import com.ficheralezzi.fantasygo.Model.MCaratteristiche;

import java.util.Random;

/**
 * Created by ASUS on 10/02/2017.
 */

public class CalcoloDannoStrategyAttMag extends ICalcoloDannoStrategy {

    private static final String TAG = "AttMag";

    @Override
    public void esegui(int id) {

        super.esegui( id);

        int dannoParziale = 0;

        log(TAG, "Azione: AttMag");

        if(attaccante.getCaratteristiche().getAttaccoMagico() > difensore.getCaratteristiche().getDifesaMagica()){
            int dannobase = attaccante.getCaratteristiche().getAttaccoMagico() - difensore.getCaratteristiche().getDifesaMagica();
            Random random= new Random();
            int bonus = random.nextInt(attaccante.getCaratteristiche().getAttaccoMagico() +
                    ((attaccante.getCaratteristiche().getLivello() + attaccante.getCaratteristiche().getAttaccoMagico())/8)+1);
            dannoParziale = dannobase*bonus;
            applicaDanno(dannoParziale);
            log(TAG, "Danno: " + dannoParziale);
        } else log(TAG, "Else Danno: " + dannoParziale);
    }
}
