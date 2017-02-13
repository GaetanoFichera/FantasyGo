package com.ficheralezzi.fantasygo.Model.CalcoloDannoStrategy;

import com.ficheralezzi.fantasygo.Model.ICalcoloDannoStrategy;
import com.ficheralezzi.fantasygo.Model.MCaratteristiche;

import java.util.Random;

/**
 * Created by ASUS on 10/02/2017.
 */

public class CalcoloDannoStrategyDardoInfuocato extends ICalcoloDannoStrategy {

    @Override
    public void esegui(int id) {

        super.esegui(id);

        int dannoParziale = 0;
        if(attaccante.getCaratteristiche().getAttaccoMagico() > difensore.getCaratteristiche().getDifesaMagico()){
            int dannobase = attaccante.getCaratteristiche().getAttaccoMagico() - difensore.getCaratteristiche().getDifesaMagico();
            Random random= new Random();
            int bonus = random.nextInt(attaccante.getCaratteristiche().getAttaccoMagico() +
                    ((attaccante.getCaratteristiche().getLivello() + attaccante.getCaratteristiche().getAttaccoMagico())/8)+1);
            dannoParziale = dannobase*bonus;
            int dannoParzialeDardo = dannoParziale * 3;
            applicaDanno(dannoParzialeDardo);
        }
    }
}
