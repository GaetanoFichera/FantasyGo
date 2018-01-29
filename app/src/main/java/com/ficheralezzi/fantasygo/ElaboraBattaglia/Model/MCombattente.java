package com.ficheralezzi.fantasygo.ElaboraBattaglia.Model;

import android.util.Log;

import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MEquipaggiamento;

public class MCombattente {

    private static final String TAG = "MCombattente";
    private String id = null;
    private MCaratteristiche caratteristiche;
    private MEquipaggiamento equipaggiamento;

    public MCombattente(String id, MCaratteristiche caratteristiche, MEquipaggiamento equipaggiamento) {
        this.id = id;
        this.caratteristiche = caratteristiche;
        this.equipaggiamento = equipaggiamento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MCaratteristiche getCaratteristiche() {
        return caratteristiche;
    }

    public void setCaratteristiche(MCaratteristiche caratteristiche) {
        this.caratteristiche = caratteristiche;
    }

    public MEquipaggiamento getEquipaggiamento() {
        return equipaggiamento;
    }

    public void setEquipaggiamento(MEquipaggiamento equipaggiamento) {
        this.equipaggiamento = equipaggiamento;
    }

    public void eseguiAzione(){

        String azione;

        if (caratteristiche.getCaricaAbilità() == caratteristiche.getCaricaMaxAbilità()){
            azione = caratteristiche.getAbilità();
            caratteristiche.azzeraCaricaAbilità();
        } else {
            azione = "Att" + caratteristiche.getTipoAttBase();
            caratteristiche.incrementaCaricaAbilità();
        }
        Log.d(TAG, "Azione: " + azione);

        try{
            Log.d(TAG, "sono nel try");
            ICalcoloDannoStrategy calcoloDannoStrategy = (ICalcoloDannoStrategy)
                    Class.forName("com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.CalcoloDannoStrategy.CalcoloDannoStrategy" +
                    azione).newInstance();
            Log.d(TAG, "Io Combattente eseguo: " + calcoloDannoStrategy.getClass().getName());
            calcoloDannoStrategy.eseguiMossa(this.id);

        }catch (Exception e){
            new Exception("Classe " + "CalcoloDannoStrategy" + azione + " non trovata");

        }
    }

    @Override
    public String toString() {
        return "MCombattente{" +
                "id=" + id +
                ", caratteristiche=" + caratteristiche.toString() +
                '}';
    }
}
