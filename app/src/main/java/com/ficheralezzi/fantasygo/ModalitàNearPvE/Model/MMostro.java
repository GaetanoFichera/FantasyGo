package com.ficheralezzi.fantasygo.ModalitàNearPvE.Model;

import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MCaratteristiche;
import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MCombattente;

/**
 * Created by ASUS on 09/03/2017.
 */

public class MMostro extends MCombattente {

    private int ricompensa;

    public MMostro(int id, MCaratteristiche caratteristiche) {
        super(id, caratteristiche);
    }

    public int getRicompensa() {
        return ricompensa;
    }

    public void setRicompensa(int ricompensa) {
        this.ricompensa = ricompensa;
    }
}
