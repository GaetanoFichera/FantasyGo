package com.ficheralezzi.fantasygo.ModalitàNearPvE.Model;

import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MCaratteristiche;
import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MCombattente;

import java.util.ArrayList;

/**
 * Created by ASUS on 09/03/2017.
 */

public class MPersonaggio extends MCombattente {

    private int bottino;
    private String sesso;
    private String razza;
    private String classe;
    private int puntiEsperienza;
    private ArrayList<String> inventario;
    private int oro;

    public MPersonaggio(int id, MCaratteristiche caratteristiche) {
        super(id, caratteristiche);
    }

    public int getBottino() {
        return bottino;
    }

    public void setBottino(int bottino) {
        this.bottino = bottino;
    }

    public int getOro() {
        return oro;
    }

    public void setOro(int oro) {
        this.oro = oro;
    }

    public ArrayList<String> getInventario() {
        return inventario;
    }

    public void setInventario(ArrayList<String> inventario) {
        this.inventario = inventario;
    }

    public int getPuntiEsperienza() {
        return puntiEsperienza;
    }

    public void setPuntiEsperienza(int puntiEsperienza) {
        this.puntiEsperienza = puntiEsperienza;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getRazza() {
        return razza;
    }

    public void setRazza(String razza) {
        this.razza = razza;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public void addOnetoInventory(String id){
        this.inventario.add(id);
    }

    public void deleteOneFromInventory(String id){ this.inventario.remove(id); }
}
