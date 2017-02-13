package com.ficheralezzi.fantasygo.Model.CalcoloDannoStrategy;

import com.ficheralezzi.fantasygo.Model.ICalcoloDannoStrategy;
import com.ficheralezzi.fantasygo.Model.MCaratteristiche;

/**
 * Created by ASUS on 10/02/2017.
 */

public class CalcoloDannoStrategyCura extends ICalcoloDannoStrategy {

    @Override
    public void esegui(int id) {

        super.esegui(id);

        log(this.getClass().toString(), "Azione: Cura");

        int curaDanno = attaccante.getCaratteristiche().getPuntiFeritaMax() / 5;
        applicaCura(curaDanno);
        log(this.getClass().toString(), ((Integer) curaDanno).toString());
    }
}
