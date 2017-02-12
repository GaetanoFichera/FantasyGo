package com.ficheralezzi.fantasygo.Model.CalcoloDannoStrategy;

import com.ficheralezzi.fantasygo.Model.ICalcoloDannoStrategy;
import com.ficheralezzi.fantasygo.Model.MBattaglia;
import com.ficheralezzi.fantasygo.Model.MCaratteristiche;

import java.util.Random;


public class CalcoloDannoStrategyAttFis extends ICalcoloDannoStrategy {

    @Override
    public void esegui(int id){

        super.esegui(id);

        int dannoParziale = 0;

        if(attaccante.getCaratteristiche().getAttaccoFisico() > difensore.getCaratteristiche().getDifesaFisico()){
            int dannoBase = attaccante.getCaratteristiche().getAttaccoFisico() - difensore.getCaratteristiche().getDifesaFisico();
            Random random = new Random();
            int dannoBonus = random.nextInt(attaccante.getCaratteristiche().getAttaccoFisico() +
                    ((attaccante.getCaratteristiche().getLivello() + attaccante.getCaratteristiche().getAttaccoFisico()) / 8) + 1);
            dannoParziale = dannoBase * dannoBonus;
            super.applicaDanno(dannoParziale);
        }
    }
}
