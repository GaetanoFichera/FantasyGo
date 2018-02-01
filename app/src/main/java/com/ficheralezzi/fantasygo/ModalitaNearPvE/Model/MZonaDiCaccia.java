package com.ficheralezzi.fantasygo.ModalitaNearPvE.Model;

import android.location.Location;
import android.util.Log;

import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MCaratteristiche;
import com.ficheralezzi.fantasygo.Utils.Posizione;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * Created by ASUS on 09/03/2017.
 */

public class MZonaDiCaccia implements Observer {

    private static final String TAG = "MZonaDiCaccia";
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

    private MArea getAreaFromDb(String idArea){
        //da implementare con interazione col db
        Posizione posizione = new Posizione(0, 0);
        ArrayList<Posizione> confini = new ArrayList<Posizione>();
        confini.add(posizione);
        String id = "Area51";
        MArea area = new MArea(confini, id);
        return area;
    }

    private ArrayList<MMostro> getMostriFromDb(String idArea){

        //da implementare con interazione col db
        MCaratteristiche caratteristichemostro = new MCaratteristiche(1, 500, 500, 10, 8, 5, 4, 6, "DardoInfuocato", 0, 10, "Mag");
        MEquipaggiamento equipaggiamentoMostro = new MEquipaggiamento("W001", "A01");
        ArrayList<MMostro> mostri= new ArrayList<>();
        MMostro mostro = new MMostro("M0001", caratteristichemostro, equipaggiamentoMostro,19);
        mostri.add(mostro);
        //aggiungere mostri manualmente nell'array
        return mostri;
    }

    public void update(double latitudine, double longitudine){
        Log.i(TAG, "A MZonaDiCaccia è stata mandata la nuova Posizione: " + latitudine + " - " + longitudine);

        if(this.area == null || this.mostri == null) {
            this.area = getAreaFromDb(latitudine, longitudine);
            this.mostri = getMostriFromDb(this.area.getId());
        } else{
            if (!IsInZonaDiCaccia(latitudine, longitudine)){
                this.area = getAreaFromDb(latitudine, longitudine);
                this.mostri = getMostriFromDb(this.area.getId());
            }
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

    public void reviveMostroById(String id){
        MMostro mostro = null;
        for (int i=0; i < this.mostri.size(); i++){
            if (this.mostri.get(i).getId() == id) mostro = this.mostri.get(i);
        }
        mostro.revive();
    }

    public boolean IsInZonaDiCaccia(double latitude, double longitude) {
        return this.area.checkPuntiInterni(latitude, longitude);
    }

    @Override
    public void update(Observable observable, Object o) {
        MGiocatore mGiocatore = ((MGiocatore) observable);
        update(mGiocatore.getLatitude(), mGiocatore.getLongitude());
    }
}
