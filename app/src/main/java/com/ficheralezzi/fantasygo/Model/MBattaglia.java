package com.ficheralezzi.fantasygo.Model;


import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MBattaglia {
    private Risultato risultato = null;
    private MCombattente combattenteA = null;
    private MCombattente combattenteB = null;
    private static MBattaglia singletoneinstance = null;
    private int turno = 0;

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public MBattaglia() {
    }

    public void init(MCombattente combattenteA,MCombattente combattenteB){

        if(combattenteA == null & combattenteB == null & risultato == null) {
            this.combattenteA = combattenteA;
            this.combattenteB = combattenteB;
            this.risultato = new Risultato();
        }
    }

    public MBattaglia getSingletoneinstance() {

        if(singletoneinstance == null){
            singletoneinstance = new MBattaglia();
        }

        return singletoneinstance;
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

    public Risultato getRisultato() {
        return risultato;
    }

    public void elaboraBattaglia(){

        while (combattenteA.getCaratteristiche().getPuntiFerita() > 0 && combattenteB.getCaratteristiche().getPuntiFerita() > 0){
            if ((combattenteA.getCaratteristiche().getVelocitadAttacco() > 0  & turno%2 == 0 )||
                    (combattenteA.getCaratteristiche().getVelocitadAttacco()< combattenteB.getCaratteristiche().getVelocitadAttacco()
                    & turno%2 != 0)) {
                combattenteA.eseguiAzione();

            }else {
                combattenteB.eseguiAzione();

            }
            turno++;
        }

        if(combattenteA.getCaratteristiche().getPuntiFerita() > combattenteB.getCaratteristiche().getPuntiFerita()){

            risultato.setRisultato(true);
        }

        risultato.setPuntiferitaA(combattenteA.getCaratteristiche().getPuntiFerita());
        risultato.setPuntiferitaB(combattenteB.getCaratteristiche().getPuntiFerita());
        risultato.setNumeroturni(this.turno);
    }
}

class Risultato {
    private  boolean risultato = false;
    private  int puntiferitaA = 0;
    private  int PuntiferitaB = 0;
    private  int numeroturni = 0;

    public Risultato (){}

    public void setRisultato(boolean risultato) {
        this.risultato = risultato;
    }

    public boolean isRisultato() {
        return risultato;
    }

    public int getNumeroturni() {
        return numeroturni;
    }

    public int getPuntiferitaB() {
        return PuntiferitaB;
    }

    public int getPuntiferitaA() {
        return puntiferitaA;
    }

    public void setPuntiferitaA(int puntiferitaA) {
        this.puntiferitaA = puntiferitaA;
    }

    public void setPuntiferitaB(int puntiferitaB) {
        PuntiferitaB = puntiferitaB;
    }

    public void setNumeroturni(int numeroturni) {
        this.numeroturni = numeroturni;
    }
}
