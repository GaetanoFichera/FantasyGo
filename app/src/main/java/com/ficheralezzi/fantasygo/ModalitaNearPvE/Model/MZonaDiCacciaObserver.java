package com.ficheralezzi.fantasygo.ModalitaNearPvE.Model;

import android.util.Log;

import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MCaratteristiche;
import com.ficheralezzi.fantasygo.Repository.ZonaDiCacciaRepo;
import com.ficheralezzi.fantasygo.Utils.Posizione;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * Created by ASUS on 09/03/2017.
 */

public class MZonaDiCacciaObserver implements Observer {

    private static final String TAG = "MZonaDiCacciaObserver";
    private ArrayList<MMostro> mostri = null;
    private MArea area = null;
    private static MZonaDiCacciaObserver singletoneinstance = null;

    public MZonaDiCacciaObserver(){
    }

    public void init(){
        if(this.area == null && this.mostri == null){

        }
    }

    public static MZonaDiCacciaObserver getSingletoneInstance() {

        if(singletoneinstance == null){
            Log.d("ZonaDiCaccia", "no");
            singletoneinstance = new MZonaDiCacciaObserver();
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

    public void updateZonaDiCaccia(double latitudine, double longitudine){
        Log.i(TAG, "A MZonaDiCacciaObserver è stata mandata la nuova Posizione: " + latitudine + " - " + longitudine);

        if(this.area == null || this.mostri == null) {
            this.area = ZonaDiCacciaRepo.retrieveArea(latitudine, longitudine);
            this.mostri = ZonaDiCacciaRepo.retrieveMostri(this.area.getId());
        } else{
            if (!IsInZonaDiCaccia(latitudine, longitudine)){
                this.area = ZonaDiCacciaRepo.retrieveArea(latitudine, longitudine);
                this.mostri = ZonaDiCacciaRepo.retrieveMostri(this.area.getId());
            }
        }

        Log.i(TAG, "ZonaDiCaccia Aggiornata: " + this.area.toString() + "\n" + this.mostri.toString());
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
        Log.i(TAG, "Lat: " + String.valueOf(((MGiocatore) observable).getSingletoneInstance().getLatitude()) + " Long: " + String.valueOf(((MGiocatore) observable).getSingletoneInstance().getLongitude()));

        updateZonaDiCaccia(((MGiocatore) observable).getSingletoneInstance().getLatitude(), ((MGiocatore) observable).getSingletoneInstance().getLongitude());
    }
}
