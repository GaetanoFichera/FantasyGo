package com.ficheralezzi.fantasygo.ModalitàNearPvE.Model.Modalità;

import com.ficheralezzi.fantasygo.ModalitàNearPvE.Model.IModalità;
import com.ficheralezzi.fantasygo.ModalitàNearPvE.Model.MGiocatore;
import com.ficheralezzi.fantasygo.ModalitàNearPvE.Model.MPersonaggio;
import com.ficheralezzi.fantasygo.ModalitàNearPvE.Model.MRegoleDiSoddisfazione;
import com.ficheralezzi.fantasygo.ModalitàNearPvE.Model.MZonaDiCaccia;


import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by ASUS on 09/03/2017.
 */

public class MModalitàNearPvE implements IModalità, Observer {

    private RisultatoFinale risultatoFinale = null;
    private int idPersonaggioScelto;

    public MModalitàNearPvE(int idPersonaggioScelto){
        this.idPersonaggioScelto = idPersonaggioScelto;
        this.risultatoFinale = new RisultatoFinale();
    }

    public void avviaModalità(){

        MPersonaggio personaggioScelto = MGiocatore.getSingletoneInstance().getOnePersonaggio(this.idPersonaggioScelto);
        MZonaDiCaccia.getSingletoneInstance().init();


        // il while controlla sul risultatoFinale gli avanzamenti dell'oro guadagnato fino a quel momento
        // e al giocatore le caratteristiche del combattente

        while(!MRegoleDiSoddisfazione.getSingletoneInstance().regoleSoddisfatte(this.risultatoFinale.getOro(),
            this.risultatoFinale.getPuntiEsperienza(), this.risultatoFinale.getNumeroDiBattaglie(),
                this.risultatoFinale.getPuntiFerita())){

            //cose

        }

    }

    public boolean terminaModalità(){ return true; }

    public void update(Observable observable, Object o) {

        MZonaDiCaccia.getSingletoneInstance().update(MGiocatore.getSingletoneInstance().getLatitude(),
                MGiocatore.getSingletoneInstance().getLongitude());

    }

    private void updateRisultatoFinale(int oro, int puntiEsperienza, int numeroDiBattaglie, int puntiFerita){

        this.risultatoFinale.setOro(this.risultatoFinale.getOro() + oro);
        this.risultatoFinale.setPuntiEsperienza(this.risultatoFinale.getPuntiEsperienza() + puntiEsperienza);
        this.risultatoFinale.setNumeroDiBattaglie(this.risultatoFinale.getNumeroDiBattaglie() + numeroDiBattaglie);
        this.risultatoFinale.setPuntiFerita(this.risultatoFinale.getPuntiFerita() + puntiFerita);
    }
}

class RisultatoFinale{

    private int oro = 0;
    private int puntiEsperienza = 0;
    private int numeroDiBattaglie = 0;
    private int puntiFerita = 0;

    public RisultatoFinale() {
    }

    public int getOro() {
        return oro;
    }

    public void setOro(int oro) {
        this.oro = oro;
    }

    public int getPuntiEsperienza() {
        return puntiEsperienza;
    }

    public void setPuntiEsperienza(int puntiEsperienza) {
        this.puntiEsperienza = puntiEsperienza;
    }

    public int getNumeroDiBattaglie() {
        return numeroDiBattaglie;
    }

    public void setNumeroDiBattaglie(int numeroDiBattaglie) {
        this.numeroDiBattaglie = numeroDiBattaglie;
    }

    public int getPuntiFerita() {
        return puntiFerita;
    }

    public void setPuntiFerita(int puntiFerita) {
        this.puntiFerita = puntiFerita;
    }
}
