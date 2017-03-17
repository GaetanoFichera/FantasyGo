package com.ficheralezzi.fantasygo.ModalitàNearPvE.Model;

public class RisultatoFinale{

    private int oro = 0;
    private int puntiEsperienza = 0;
    private int numeroDiBattaglie = 0;
    private int puntiFerita = 99999999;

    public RisultatoFinale() {
    }

    public int getOro() {
        return oro;
    }

    public void setOro(int oro) {
        this.oro = oro;
    }

    public int getPuntiEsperienza() {
        return puntiEsperienza;
    }

    public void setPuntiEsperienza(int puntiEsperienza) {
        this.puntiEsperienza = puntiEsperienza;
    }

    public int getNumeroDiBattaglie() {
        return numeroDiBattaglie;
    }

    public void setNumeroDiBattaglie(int numeroDiBattaglie) {
        this.numeroDiBattaglie = numeroDiBattaglie;
    }

    @Override
    public String toString() {
        return "RisultatoFinale{" +
                "oro=" + oro +
                ", puntiEsperienza=" + puntiEsperienza +
                ", numeroDiBattaglie=" + numeroDiBattaglie +
                ", puntiFerita=" + puntiFerita +
                '}';
    }

    public int getPuntiFerita() {
        return puntiFerita;
    }

    public void setPuntiFerita(int puntiFerita) {
        this.puntiFerita = puntiFerita;
    }
}
