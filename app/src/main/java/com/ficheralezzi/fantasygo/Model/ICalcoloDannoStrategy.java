package com.ficheralezzi.fantasygo.Model;


public class ICalcoloDannoStrategy {

    protected MCombattente attaccante = null;
    protected MCombattente difensore = null;
    protected MBattaglia battaglia = null;

    public ICalcoloDannoStrategy() {
        battaglia = MBattaglia.getSingletoneInstance();
    }

    public void esegui(int id){
        if(battaglia.getCombattenteA().getId() == id){
            attaccante = battaglia.getCombattenteA();
            difensore = battaglia.getCombattenteB();
        } else{
            attaccante = battaglia.getCombattenteB();
            difensore = battaglia.getCombattenteA();
        }
    }

    public void applicaDanno(int danno){
        difensore.getCaratteristiche().setPuntiFerita(danno);
    }
}
