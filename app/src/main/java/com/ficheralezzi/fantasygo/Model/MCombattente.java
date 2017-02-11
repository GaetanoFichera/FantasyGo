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
        try{
            ICalcoloDannoStrategy calcoloDannoStrategy = (ICalcoloDannoStrategy) Class.forName("CalcoloDannoStrategy" +
                    this.caratteristiche.getAbilità()).newInstance();
            calcoloDannoStrategy.esegui(this.caratteristiche);

        }catch (Exception e){
            new Exception("Error occurred");

        }

    }



}
