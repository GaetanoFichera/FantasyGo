package com.ficheralezzi.fantasygo.ModalitàNearPvE.Model;

import android.util.Log;


/**
 * Created by ASUS on 09/03/2017.
 */
public class MRegoleDiSoddisfazione {

    private int oroMinimo = 0;
    private int puntiEsperienzaMinimi = 0;
    private int numeroDiBattaglie = 0;
    private int puntiFeritaMinimi = 0;
    private static MRegoleDiSoddisfazione singletoneinstance = null;

    MRegoleDiSoddisfazione(){}

    public void init(int oroMinimo, int puntiEsperienzaMinimi, int numeroDiBattaglie, int puntiFeritaMinimi){
        if(this.oroMinimo == 0 & this.puntiEsperienzaMinimi == 0 & this.numeroDiBattaglie == 0 & this.puntiFeritaMinimi == 0){
            this.oroMinimo = oroMinimo;
            this.puntiEsperienzaMinimi = puntiEsperienzaMinimi;
            this.numeroDiBattaglie = numeroDiBattaglie;
            this.puntiFeritaMinimi = puntiFeritaMinimi;
        }
    }

    public static MRegoleDiSoddisfazione getSingletoneInstance() {

        if(singletoneinstance == null){
            Log.d("regolediSOddisfazione", "no");
            singletoneinstance = new MRegoleDiSoddisfazione();
        } else Log.d("regolediSOddisfazione", "si");

        return singletoneinstance;
    }

    //controllo se almeno una delle regole di soddisfazione sono soddisfatte
    public boolean regoleSoddisfatte(int oro, int puntiEsperienza, int numeroDiBattaglie, int puntiFerita) {

        boolean controllo = false;
        if(this.oroMinimo >= oro || this.puntiEsperienzaMinimi >= puntiEsperienza || this.puntiFeritaMinimi >= puntiFerita ||
                this.numeroDiBattaglie >= numeroDiBattaglie || this.puntiFeritaMinimi >= puntiFerita){
            controllo = true;
        }

        return controllo;
    }
}
