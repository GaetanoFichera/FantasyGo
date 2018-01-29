package com.ficheralezzi.fantasygo.ModalitaNearPvE.Model;

import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MCaratteristiche;
import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MCombattente;

/**
 * Created by ASUS on 09/03/2017.
 */

public class MMostro extends MCombattente implements Cloneable {

    private int ricompensa;

    public MMostro(String id, MCaratteristiche caratteristiche, MEquipaggiamento equipaggiamento, int ricompensa) {
        super(id, caratteristiche, equipaggiamento);
        this.ricompensa = ricompensa;
    }

    public int getRicompensa() {
        return ricompensa;
    }

    public void setRicompensa(int ricompensa) {
        this.ricompensa = ricompensa;
    }

    @Override
    protected Object clone() {
        try {
            final MMostro result = (MMostro) super.clone();
            // copy fields that need to be copied here!
            return result;
        } catch (final CloneNotSupportedException ex) {
            throw new AssertionError();
        }
    }

    public void revive(){
        this.getCaratteristiche().setPuntiFerita(this.getCaratteristiche().getPuntiFeritaMax());
    }
}
