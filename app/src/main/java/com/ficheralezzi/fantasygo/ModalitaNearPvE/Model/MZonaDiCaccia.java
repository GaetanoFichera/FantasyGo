package com.ficheralezzi.fantasygo.ModalitaNearPvE.Model;

import android.util.Log;

import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MCaratteristiche;
import com.ficheralezzi.fantasygo.Utils.Posizione;

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

        MMostro mostro = null;
        if (this.mostri.size() == 0){

        } else if (this.mostri.size() == 1){
            mostro = this.mostri.get(0);
        } else{
            Random random = new Random();
            int index = random.nextInt(this.mostri.size() - 1);
            mostro = this.mostri.get(index);
        }
        return mostro;
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
        MCaratteristiche caratteristichemostro = new MCaratteristiche(1, 70, 70, 3, 5, 5, 4, 6, "DardoInfuocato", 0, 10, "Mag");
        ArrayList<MMostro> mostri= new ArrayList<>();
        MMostro mostro = new MMostro("M0001", caratteristichemostro, 10);
        mostri.add(mostro);
        //aggiungere mostri manualmente nell'array
        return mostri;
    }

    public void update(double latitudine, double longitudine){

        // controlla se gli attributi sono stati gia istanziati e in caso positivo controlla se
        // latitudine e longitudine che gli vengono passate appartengo all'area gia istanziata
        // nel caso la if si verifichi viene fatta una richeista al db per l'area relativa alle coordinate passate
        if(this.area != null || this.mostri != null) {
            if (!this.area.checkPuntiInterni(latitudine, longitudine)) {
                this.area = getAreaFromDb(latitudine, longitudine);
                this.mostri = getMostriFromDb(this.area.getId());
            }
        } else{
            this.area = getAreaFromDb(latitudine, longitudine);
            this.mostri = getMostriFromDb(this.area.getId());
        }
        Log.i("mostri", this.mostri.toString());

    }

    public int getRicompensa(String id){
        int ricompensa = 0;
        for (int i=0; i < this.mostri.size(); i++){
            if (this.mostri.get(i).getId() == id) ricompensa = this.mostri.get(i).getRicompensa();
        }
        return ricompensa;
    }

    public MMostro getOneMostroById(String id){
        MMostro mostro = null;
        for (int i=0; i < this.mostri.size(); i++){
            if (this.mostri.get(i).getId() == id) mostro = this.mostri.get(i);
        }
        return mostro;
    }
}
