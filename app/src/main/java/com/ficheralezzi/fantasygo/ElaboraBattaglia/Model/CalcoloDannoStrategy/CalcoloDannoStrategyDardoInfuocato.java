package com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.CalcoloDannoStrategy;

import android.util.Log;

import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.ICalcoloDannoStrategy;
import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MBattaglia;
import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MCombattente;

import java.util.Random;

public class CalcoloDannoStrategyDardoInfuocato implements ICalcoloDannoStrategy {

    private static final String TAG = "ICalcoloDannoStrategy";
    protected MCombattente attaccante = null;
    protected MCombattente difensore = null;

    public void eseguiMossa(int id) {

        int dannoParziale = 0;

        log(this.getClass().toString(), "Azione: DardoInfuocato");

        if(attaccante.getCaratteristiche().getAttaccoMagico() > difensore.getCaratteristiche().getDifesaMagica()){
            int dannobase = attaccante.getCaratteristiche().getAttaccoMagico() - difensore.getCaratteristiche().getDifesaMagica();
            Random random= new Random();
            int bonus = random.nextInt(attaccante.getCaratteristiche().getAttaccoMagico() +
                    ((attaccante.getCaratteristiche().getLivello() + attaccante.getCaratteristiche().getAttaccoMagico())/8)+1);
            dannoParziale = dannobase*bonus;
            int dannoParzialeDardo = dannoParziale * 3;
            MBattaglia.getSingletoneInstance().getCombattenteById(difensore.getId()).getCaratteristiche().diminuisciPuntiFerita(dannoParzialeDardo);
            log(this.getClass().toString(), ((Integer) dannoParzialeDardo).toString());
        }
    }

    public void log (String classe, String msg){
        Log.d(classe, msg);
    }

}
