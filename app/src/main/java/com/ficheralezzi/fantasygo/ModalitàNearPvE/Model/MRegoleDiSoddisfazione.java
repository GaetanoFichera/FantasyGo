package com.ficheralezzi.fantasygo.ModalitàNearPvE.Model;

import android.util.Log;


/**
 * Created by ASUS on 09/03/2017.
 */
public class MRegoleDiSoddisfazione {

    private int oroMinimo;
    private int puntiEsperienzaMinimi;
    private int numeroDiBattaglie;
    private int puntiFeritaMinimi;
    private static MRegoleDiSoddisfazione singletoneinstance = null;

    MRegoleDiSoddisfazione(){}

    public void init(int oroMinimo, int puntiEsperienzaMinimi, int numeroDiBattaglie, int puntiFeritaMinimi){
        if(this.oroMinimo == 0 & this.puntiEsperienzaMinimi == 0 & this.numeroDiBattaglie == 0 & this.puntiFeritaMinimi == 0){

        }
    }

    static MRegoleDiSoddisfazione getSingletoneInstance() {

        if(singletoneinstance == null){
            Log.d("regolediSOddisfazione", "no");
            singletoneinstance = new MRegoleDiSoddisfazione();
        } else Log.d("regolediSOddisfazione", "si");

        return singletoneinstance;
    }
}
