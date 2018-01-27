package com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.RegoleDiSoddisfazione;

import android.util.Log;

import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.IRegoleDiSoddisfazione;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MGiocatore;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.Modalità.MModalitàNearPvEObserver;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by ASUS on 09/03/2017.
 */
public class MRegoleDiSoddisfazione implements IRegoleDiSoddisfazione{

    private int oroMinimo = 0;
    private int puntiEsperienzaMinimi = 0;
    private int numeroDiBattaglie = 0;
    private int puntiFeritaMinimi = 0;

    private static MRegoleDiSoddisfazione singletoneinstance = null;

    MRegoleDiSoddisfazione(){}

    public void init(){
        if(this.oroMinimo == 0 & this.puntiEsperienzaMinimi == 0 & this.numeroDiBattaglie == 0 & this.puntiFeritaMinimi == 0){
            this.oroMinimo = 1;
            this.puntiEsperienzaMinimi = 1;
            this.numeroDiBattaglie = 1;
            this.puntiFeritaMinimi = 1;
        }
    }

    /*
    public void init(int oroMinimo, int puntiEsperienzaMinimi, int numeroDiBattaglie, int puntiFeritaMinimi){
        if(this.oroMinimo == 0 & this.puntiEsperienzaMinimi == 0 & this.numeroDiBattaglie == 0 & this.puntiFeritaMinimi == 0){
            this.oroMinimo = oroMinimo;
            this.puntiEsperienzaMinimi = puntiEsperienzaMinimi;
            this.numeroDiBattaglie = numeroDiBattaglie;
            this.puntiFeritaMinimi = puntiFeritaMinimi;
        }
    }
    */

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

    @Override
    public HashMap<String, Integer> getNomiParametriConMassimiRegoleDiSoddisfazione() {

        HashMap<String, Integer> parametri = new HashMap<>();

        parametri.put("Oro Minimo", 9999);
        parametri.put("Punti Esperienza Minimi", 9999);
        parametri.put("Numero di Battaglie", 999);
        parametri.put("Punti Ferita Minimi", MGiocatore.getSingletoneInstance().getOnePersonaggioById(MModalitàNearPvEObserver.getSingletoneInstance().getIdPersonaggioScelto()).getCaratteristiche().getPuntiFeritaMax());

        return parametri;
    }

    @Override
    public HashMap<String, Integer> getValoriRegoleDiSoddisfazione() {

        HashMap<String, Integer> parametri = new HashMap<>();

        parametri.put("Oro Minimo", this.oroMinimo);
        parametri.put("Punti Esperienza Minimi", this.puntiEsperienzaMinimi);
        parametri.put("Numero di Battaglie", this.numeroDiBattaglie);
        parametri.put("Punti Ferita Minimi", this.puntiFeritaMinimi);

        return parametri;
    }

    @Override
    public void setRegoleDiSoddisfazione(Map<String, Integer> parametri) {
        if (parametri.containsKey("Oro Minimo"))
            this.oroMinimo = parametri.get("Oro Minimo");
        else
            this.oroMinimo = 0;

        if (parametri.containsKey("Punti Esperienza Minimi"))
            this.puntiEsperienzaMinimi = parametri.get("Punti Esperienza Minimi");
        else
            this.puntiEsperienzaMinimi = 0;

        if (parametri.containsKey("Numero di Battaglie"))
            this.numeroDiBattaglie = parametri.get("Numero di Battaglie");
        else
            this.numeroDiBattaglie = 0;

        if (parametri.containsKey("Punti Ferita Minimi"))
            this.puntiFeritaMinimi = parametri.get("Punti Ferita Minimi");
        else
            this.puntiFeritaMinimi = 0;
    }

    @Override
    public boolean regoleSoddisfatte(){
        boolean controllo = false;

        int oro = MModalitàNearPvEObserver.getSingletoneInstance().getRisultatoFinale().getOro();
        int puntiEsperienza = MModalitàNearPvEObserver.getSingletoneInstance().getRisultatoFinale().getPuntiEsperienza();
        int puntiFerita = MModalitàNearPvEObserver.getSingletoneInstance().getRisultatoFinale().getPuntiFerita();
        int numeroDiBattaglie = MModalitàNearPvEObserver.getSingletoneInstance().getRisultatoFinale().getNumeroDiBattaglie();

        if(this.oroMinimo <= oro || this.puntiEsperienzaMinimi <= puntiEsperienza || this.puntiFeritaMinimi >= puntiFerita ||
                this.numeroDiBattaglie <= numeroDiBattaglie){
            controllo = true;
            Log.i("sonoinregolesoddisfatte", "true");

        }

        return controllo;
    }

    /*

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
    */

    public void destroy(){
        if(singletoneinstance != null) singletoneinstance = null;
    }
}
