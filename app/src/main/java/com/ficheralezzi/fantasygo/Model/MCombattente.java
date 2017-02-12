package com.ficheralezzi.fantasygo.Model;


public class MCombattente {

    int id;
    MCaratteristiche caratteristiche;

    public MCombattente(int id, MCaratteristiche caratteristiche) {
        this.id = id;
        this.caratteristiche = caratteristiche;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MCaratteristiche getCaratteristiche() {
        return caratteristiche;
    }

    public void setCaratteristiche(MCaratteristiche caratteristiche) {
        this.caratteristiche = caratteristiche;
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

        try{
            ICalcoloDannoStrategy calcoloDannoStrategy = (ICalcoloDannoStrategy) Class.forName("CalcoloDannoStrategy" +
                    azione).newInstance();
            calcoloDannoStrategy.esegui(this.id);

        }catch (Exception e){
            new Exception("Classe " + "CalcoloDannoStrategy" + azione + " non trovata");

        }
    }
}
