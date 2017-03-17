package com.ficheralezzi.fantasygo.ElaboraBattaglia.Model;

public class Risultato {
    private  boolean risultato = false;
    private  int puntiFeritaA = 0;
    private  int puntiFeritaB = 0;
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

    public int getPuntiFeritaB() {
        return puntiFeritaB;
    }

    public int getPuntiferitaA() {
        return puntiFeritaA;
    }

    public void setPuntiferitaA(int puntiferitaA) {
        this.puntiFeritaA = puntiferitaA;
    }

    public void setPuntiferitaB(int puntiferitaB) {
        puntiFeritaB = puntiferitaB;
    }

    public void setNumeroturni(int numeroturni) {
        this.numeroturni = numeroturni;
    }

    @Override
    public String toString() {
        return "Risultato{" +
                "risultato=" + risultato +
                ", puntiFeritaA=" + puntiFeritaA +
                ", puntiFeritaB=" + puntiFeritaB +
                ", numeroturni=" + numeroturni +
                '}';
    }
}
