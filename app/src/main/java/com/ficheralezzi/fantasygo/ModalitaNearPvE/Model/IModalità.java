package com.ficheralezzi.fantasygo.ModalitaNearPvE.Model;

import com.ficheralezzi.fantasygo.Utils.RisultatoFinale;

/**
 * Created by ASUS on 09/03/2017.
 */

public interface IModalità {

    boolean createModalità();
    void selezionaPersonaggio(String idPersonaggioScelto);
    void avviaModalità();
    void terminaModalità();
}
