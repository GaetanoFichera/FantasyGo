package com.ficheralezzi.fantasygo.Model;


public class MBattaglia {
    private int risultato;
    private MCombattente combattenteA = null;
    private MCombattente combattenteB = null;
    private static MBattaglia singletoneinstance = null;

    public MBattaglia() {
    }

    public void init(MCombattente combattenteA,MCombattente combattenteB){

        if(combattenteA == null & combattenteB == null ) {
            this.combattenteA = combattenteA;
            this.combattenteB = combattenteB;
        }
    }

    public MBattaglia getSingletoneinstance() {

        if(singletoneinstance == null){
            singletoneinstance = new MBattaglia();
        }

        return singletoneinstance;
    }


    public void setRisultato(int risultato) {
        this.risultato = risultato;
    }

    public int getRisultato() {

        return risultato;
    }

    public MCombattente getCombattenteA() {

        return combattenteA;
    }

    public void setCombattenteA(MCombattente combattenteA) {
        this.combattenteA = combattenteA;
    }

    public MCombattente getCombattenteB() {
        return combattenteB;
    }

    public void setCombattenteB(MCombattente combattenteB) {
        this.combattenteB = combattenteB;
    }

    public void elaboraBattaglia(){

        while (combattenteA.getCaratteristiche().getPuntiFerita() > 0 && combattenteB.getCaratteristiche().getPuntiFerita() > 0){
            if (combattenteA.getCaratteristiche().getVelocitaAttacco() > 0 ){

            }
        }

    }
}
