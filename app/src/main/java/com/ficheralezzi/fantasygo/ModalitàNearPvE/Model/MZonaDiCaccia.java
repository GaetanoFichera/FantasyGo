package com.ficheralezzi.fantasygo.ModalitàNearPvE.Model;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MCaratteristiche;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ASUS on 09/03/2017.
 */

public class MZonaDiCaccia {

    private ArrayList<MMostro> mostri = null;
    private MArea area = null;
    private static MZonaDiCaccia singletoneinstance = null;

    public MZonaDiCaccia(){
    }

    public void init(){
        if(this.area == null && this.mostri == null){

        }
    }

    public static MZonaDiCaccia getSingletoneInstance() {

        if(singletoneinstance == null){
            Log.d("ZonaDiCaccia", "no");
            singletoneinstance = new MZonaDiCaccia();
        } else Log.d("ZonaDiCaccia", "si");

        return singletoneinstance;
    }

    public MArea getArea() {
        return area;
    }

    public void setArea(MArea area) {
        this.area = area;
    }

    public ArrayList<MMostro> getMostri() {
        return mostri;
    }

    public void setMostri(ArrayList<MMostro> mostri) {
        this.mostri = mostri;
    }

    public MMostro getOneMostro(){
        Random random = new Random();
        int index = random.nextInt(this.mostri.size());
        return this.mostri.get(index-1);
    }

    private MArea getAreaFromDb(double latitudine, double longitudine){

        //da implementare con interazione col db
        Posizione posizione = new Posizione(latitudine, longitudine);
        ArrayList<Posizione> confini = new ArrayList<Posizione>();
        confini.add(posizione);
        String id = "Area51";
        MArea area = new MArea(confini, id);
        return area;
    }

    private ArrayList<MMostro> getMostriFromDb(String idArea){

        //da implementare con interazione col db
        MCaratteristiche caratteristichemostro = new MCaratteristiche(3, 1500, 1500, 10, 22, 46, 31, 12, "DardoInfuocato", 0, 10, "Mag");
        ArrayList<MMostro> mostri= new ArrayList<>();
        MMostro mostro = new MMostro(10, caratteristichemostro);
        mostri.add(mostro);
        //aggiungere mostri manualmente nell'array
        return mostri;
    }

    public void update(double latitudine, double longitudine){

        // controlla se gli attributi sono stati gia istanziati e in caso positivo controlla se
        // latitudine e longitudine che gli vengono passate appartengo all'area gia istanziata
        // nel caso la if si verifichi viene fatta una richeista al db per l'area relativa alle coordinate passate
        if(!this.area.checkPuntiInterni(latitudine, longitudine)){
            this.area = getAreaFromDb(latitudine, longitudine);
            this.mostri = getMostriFromDb(this.area.getId());
        }
    }

    public int getRicompensa(){
        return this.getOneMostro().getRicompensa();
    }

}
