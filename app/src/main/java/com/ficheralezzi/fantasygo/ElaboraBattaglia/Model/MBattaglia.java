package com.ficheralezzi.fantasygo.ElaboraBattaglia.Model;

import android.os.Handler;
import android.util.Log;

import com.ficheralezzi.fantasygo.Utils.Risultato;

import java.util.concurrent.TimeUnit;

public class MBattaglia {

    private static final String TAG = "MBattaglia";
    private static final long TIMETOWAIT = 500;
    private Risultato risultato = null;
    private MCombattente combattenteA = null;
    private MCombattente combattenteB = null;
    private static MBattaglia singletoneinstance = null;
    private int turno = 0;


    public MBattaglia() {
    }

    public void init(MCombattente combattenteA,MCombattente combattenteB){

        if(this.combattenteA == null & this.combattenteB == null & this.risultato == null) {
            this.combattenteA = combattenteA;
            this.combattenteB = combattenteB;
            this.risultato = new Risultato();
            Log.i(TAG, "Inizializzato");
        }
    }

    public static MBattaglia getSingletoneInstance() {

        if(singletoneinstance == null){
            Log.d(TAG, "no");
            singletoneinstance = new MBattaglia();
        } else Log.d(TAG, "si");


        return singletoneinstance;
    }

     public MCombattente getCombattenteA() {

        return combattenteA;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
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

    public MCombattente getCombattenteById(String id){
        if (combattenteA.getId() == id) return combattenteA;
        else return combattenteB;
    }

    public Risultato getRisultato() {
        return risultato;
    }

    public void elaboraBattaglia(){
        Log.i(TAG, "elabora battaglia eseguito");
        Log.i(TAG, "combattenteA : " + combattenteA.toString());
        Log.i(TAG, "combattenteB : " + combattenteB.toString());
        while (combattenteA.getCaratteristiche().getPuntiFerita() > 0 && combattenteB.getCaratteristiche().getPuntiFerita() > 0){
            Log.i(TAG, "sono nel while");
            if ((combattenteA.getCaratteristiche().getVelocitàdAttacco() >= 0  & turno%2 == 0 )||
                    (combattenteA.getCaratteristiche().getVelocitàdAttacco()< combattenteB.getCaratteristiche().getVelocitàdAttacco()
                    & turno%2 != 0)) {
                Log.d(TAG, "Turno di A");
                combattenteA.eseguiAzione();

            }else {
                Log.d(TAG, "Turno di B");
                combattenteB.eseguiAzione();

            }
            Log.d(TAG, "Turno " + turno + " finito");
            Log.d(TAG, this.toString());
            turno++;

            try {
                Thread.sleep(TIMETOWAIT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(combattenteA.getCaratteristiche().getPuntiFerita() > combattenteB.getCaratteristiche().getPuntiFerita()){

            risultato.setRisultato(true);
        }

        risultato.setPuntiferitaA(combattenteA.getCaratteristiche().getPuntiFerita());
        risultato.setPuntiferitaB(combattenteB.getCaratteristiche().getPuntiFerita());
        risultato.setNumeroturni(this.turno);
    }


    public void resetBattaglia(){

        this.risultato = null;
        this.combattenteA = null;
        this.combattenteB = null;
    }


    @Override
    public String toString() {
        return "MBattaglia{" +
                "combattenteA=" + combattenteA.toString() +
                ", combattenteB=" + combattenteB.toString() +
                ", turno=" + turno +
                '}';
    }

    public void destroy(){
        if(singletoneinstance != null){
            singletoneinstance = null;
            combattenteA = null;
            combattenteB = null;
            risultato = null;
        }
    }
}

