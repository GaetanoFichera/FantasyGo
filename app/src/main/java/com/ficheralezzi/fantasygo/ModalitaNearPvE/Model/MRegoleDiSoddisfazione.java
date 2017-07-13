package com.ficheralezzi.fantasygo.ModalitaNearPvE.Model;

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
            Log.d("RegoleDiSoddisfazione", "no");
            singletoneinstance = new MRegoleDiSoddisfazione();
        } else Log.d("RegoleDiSoddisfazione", "si");

        return singletoneinstance;
    }

    public int getOroMinimo() {
        return oroMinimo;
    }

    public void setOroMinimo(int oroMinimo) {
        this.oroMinimo = oroMinimo;
    }

    public int getPuntiEsperienzaMinimi() {
        return puntiEsperienzaMinimi;
    }

    public void setPuntiEsperienzaMinimi(int puntiEsperienzaMinimi) {
        this.puntiEsperienzaMinimi = puntiEsperienzaMinimi;
    }

    public int getNumeroDiBattaglie() {
        return numeroDiBattaglie;
    }

    public void setNumeroDiBattaglie(int numeroDiBattaglie) {
        this.numeroDiBattaglie = numeroDiBattaglie;
    }

    public int getPuntiFeritaMinimi() {
        return puntiFeritaMinimi;
    }

    public void setPuntiFeritaMinimi(int puntiFeritaMinimi) {
        this.puntiFeritaMinimi = puntiFeritaMinimi;
    }

    @Override
    public String toString() {
        return "MRegoleDiSoddisfazione{" +
                "oroMinimo=" + oroMinimo +
                ", puntiEsperienzaMinimi=" + puntiEsperienzaMinimi +
                ", numeroDiBattaglie=" + numeroDiBattaglie +
                ", puntiFeritaMinimi=" + puntiFeritaMinimi +
                '}';
    }

    //controllo se almeno una delle regole di soddisfazione sono soddisfatte
    public boolean regoleSoddisfatte(int oro, int puntiEsperienza, int numeroDiBattaglie, int puntiFerita) {

        boolean controllo = false;
        if(this.oroMinimo <= oro || this.puntiEsperienzaMinimi <= puntiEsperienza || this.puntiFeritaMinimi >= puntiFerita ||
                this.numeroDiBattaglie <= numeroDiBattaglie){
            controllo = true;
            Log.i("sonoinregolesoddisfatte", "true");

        }

        return controllo;
    }

    public void destroy(){
        if(singletoneinstance != null) singletoneinstance = null;
    }
}
