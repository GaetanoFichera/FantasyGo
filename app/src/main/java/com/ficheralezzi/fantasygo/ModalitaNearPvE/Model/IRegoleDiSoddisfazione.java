package com.ficheralezzi.fantasygo.ModalitaNearPvE.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaetano on 12/09/17.
 */

public interface IRegoleDiSoddisfazione {

    /**
     * Una Map in cui le chiavi contengono i Nomi dei Parametri e nel Valore associato il valore massimo
     * che può essere scelto
     * @return
     */
    HashMap<String, Integer> getNomiParametriConMassimiRegoleDiSoddisfazione();
    HashMap<String, Integer> getValoriRegoleDiSoddisfazione();
    void setRegoleDiSoddisfazione(Map<String, Integer> parametri);
    boolean regoleSoddisfatte();
}
