package com.ficheralezzi.fantasygo.ModalitàNearPvE.Model;

import java.util.ArrayList;

/**
 * Created by ASUS on 09/03/2017.
 */
public class MArea {

    private ArrayList<Posizione> confini;
    private String id;

    public MArea(ArrayList<Posizione> confini, String id) {
        this.confini = confini;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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