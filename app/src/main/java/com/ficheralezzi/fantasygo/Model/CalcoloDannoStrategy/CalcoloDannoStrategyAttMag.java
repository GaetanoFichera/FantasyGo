package com.ficheralezzi.fantasygo.Model.CalcoloDannoStrategy;

import com.ficheralezzi.fantasygo.Model.ICalcoloDannoStrategy;
import com.ficheralezzi.fantasygo.Model.MCaratteristiche;

import java.util.Random;

/**
 * Created by ASUS on 10/02/2017.
 */

public class CalcoloDannoStrategyAttMag extends ICalcoloDannoStrategy {

    @Override
    public void esegui(int id) {

        super.esegui( id);

        int dannoparziale = 0;
        if(attaccante.getCaratteristiche().getAttaccoMagico() > difensore.getCaratteristiche().getDifesaMagico()){
            int dannobase = attaccante.getCaratteristiche().getAttaccoMagico() - difensore.getCaratteristiche().getDifesaMagico();
            Random random= new Random();
            int bonus = random.nextInt(attaccante.getCaratteristiche().getAttaccoMagico() +
                    ((attaccante.getCaratteristiche().getLivello() + attaccante.getCaratteristiche().getAttaccoMagico())/8)+1);
            dannoparziale = dannobase*bonus;
            applicaDanno(dannoparziale);
        }
    }
}
