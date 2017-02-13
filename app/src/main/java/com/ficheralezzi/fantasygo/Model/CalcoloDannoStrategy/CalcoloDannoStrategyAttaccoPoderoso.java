package com.ficheralezzi.fantasygo.Model.CalcoloDannoStrategy;

import com.ficheralezzi.fantasygo.Model.ICalcoloDannoStrategy;
import com.ficheralezzi.fantasygo.Model.MCaratteristiche;

import java.util.Random;

public class CalcoloDannoStrategyAttaccoPoderoso extends ICalcoloDannoStrategy {

    @Override
    public void esegui(int id) {

        super.esegui(id);

        int dannoParziale = 0;

        log(this.getClass().toString(), "Azione: AttaccoPoderoso");

        if(attaccante.getCaratteristiche().getAttaccoFisico() > difensore.getCaratteristiche().getDifesaFisica()){
            int dannoBase = attaccante.getCaratteristiche().getAttaccoFisico() - difensore.getCaratteristiche().getDifesaFisica();
            Random random = new Random();
            int dannoBonus = random.nextInt(attaccante.getCaratteristiche().getAttaccoFisico() +
                    ((attaccante.getCaratteristiche().getLivello() + attaccante.getCaratteristiche().getAttaccoFisico()) / 8) + 1);
            dannoParziale = dannoBase * dannoBonus;
            int dannoParzialePoderoso = dannoParziale * 3;
            applicaDanno(dannoParzialePoderoso);
            log(this.getClass().toString(), ((Integer) dannoParzialePoderoso).toString());
        }
    }
}
