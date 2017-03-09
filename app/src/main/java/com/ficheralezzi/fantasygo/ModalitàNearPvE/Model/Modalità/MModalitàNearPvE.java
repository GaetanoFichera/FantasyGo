package com.ficheralezzi.fantasygo.ModalitàNearPvE.Model.Modalità;

import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MBattaglia;
import com.ficheralezzi.fantasygo.ModalitàNearPvE.Model.IModalità;
import com.ficheralezzi.fantasygo.ModalitàNearPvE.Model.MGiocatore;
import com.ficheralezzi.fantasygo.ModalitàNearPvE.Model.MRegoleDiSoddisfazione;
import com.ficheralezzi.fantasygo.ModalitàNearPvE.Model.MZonaDiCaccia;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by ASUS on 09/03/2017.
 */

public class MModalitàNearPvE implements IModalità, Observer {

    private ArrayList<String> risultatoFinale;

    public ArrayList<String> getRisultatoFinale() {
        return risultatoFinale;
    }

    public void setRisultatoFinale(ArrayList<String> risultatoFinale) {
        this.risultatoFinale = risultatoFinale;
    }

    public void avviaModalità(){

    }

    public boolean terminaModalità(){ return true; }

    public void update(Observable observable, Object o) {}
}
