package com.ficheralezzi.fantasygo.ModalitaNearPvE.Model;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.ficheralezzi.fantasygo.Repository.GiocatoreRepo;

import java.util.ArrayList;

/**
 * Created by ASUS on 09/03/2017.
 */

public class MGiocatore extends MGpsObservableObserver {

    private static final String TAG = "MGiocatore";
    private String id;
    private ArrayList<MPersonaggio> personaggi;
    private String nome;
    private static MGiocatore singletoneinstance = null;

    public MGiocatore() {
        super();

    }

    public void init(String id, ArrayList<MPersonaggio> personaggi, String nome){

        if(this.id == null & this.personaggi == null & this.nome == null){
            this.id = id;
            this.personaggi = personaggi;
            this.nome = nome;
        }
    }

    public static MGiocatore getSingletoneInstance() {

        if(singletoneinstance == null){
            //Log.d("MGiocatore", "no");
            singletoneinstance = new MGiocatore();
        } //else //Log.d("MGiocatore", "si");

        return singletoneinstance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<MPersonaggio> getPersonaggi() {
        return personaggi;
    }

    public void setPersonaggi(ArrayList<MPersonaggio> personaggio) { this.personaggi = personaggio; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public MPersonaggio getOnePersonaggioById(String id){
        MPersonaggio personaggio = null;
        for(int i=0; i <= this.personaggi.size()-1 && personaggio == null; i++){
            if(this.personaggi.get(i).getId() == id){
                personaggio = this.personaggi.get(i);
                //Log.i("MGiocatore", "Personaggio con Id Trovato");
            }
        }
        if(personaggio == null){
            //Log.i("MGiocatore", "Personaggio con Id non Trovato");
        }
        return personaggio;
    }

    public MPersonaggio getOnePersonaggioByNome(String nome){
        MPersonaggio personaggio = null;
        for(int i=0; i <= this.personaggi.size()-1 && personaggio == null; i++){
            if(this.personaggi.get(i).getNome() == nome){
                personaggio = this.personaggi.get(i);
                Log.i(TAG, "Personaggio con Nome Trovato");
            }
        }
        if(personaggio == null){
            Log.i(TAG, "Personaggio con Nome non Trovato");
        }
        return personaggio;
    }

    public ArrayList<String> getNomiPersonaggi() {
        ArrayList<String> idsPersonaggi = new ArrayList<>();
        for (int i = 0; i < this.personaggi.size(); i++) {
            idsPersonaggi.add(this.personaggi.get(i).getNome());
        }
        return idsPersonaggi;
    }

    @Override
    public void updateLocation(Context context, Location location) {
        super.updateLocation(context, location);
        try{
            this.setLatitude(location.getLatitude());
            this.setLongitude(location.getLongitude());

            Log.i(TAG, "Nuova Posizione - Lat: " + String.valueOf(this.getLatitude()) + " Long: " + String.valueOf(this.getLongitude()));

            GiocatoreRepo.saveLocation(this.getId(), this.getLatitude(), this.getLongitude(), context);
            notifyObservers();
        }catch (Exception e){
            Log.i(TAG, "Errore: " + e);
        }
    }
}
