package com.ficheralezzi.fantasygo.ModalitàNearPvE.Model;

import java.util.ArrayList;

/**
 * Created by ASUS on 09/03/2017.
 */
public class MArea {

    public ArrayList<Posizione> confini;

    public MArea(ArrayList<Posizione> confini) {
        this.confini = confini;
    }

    public ArrayList<Posizione> getConfini() {
        return confini;
    }

    public void setConfini(ArrayList<Posizione> confini) {
        this.confini = confini;
    }

    public boolean checkPuntiInterni(double latitudine, double longitudine){
        //da implementare
        return true;
    }
}


class Posizione{
    double latitudine;
    double longitudine;

    public Posizione(double latitudine, double longitudine) {
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }
}