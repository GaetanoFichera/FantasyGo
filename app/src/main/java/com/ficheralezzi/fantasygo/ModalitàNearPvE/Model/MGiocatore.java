package com.ficheralezzi.fantasygo.ModalitàNearPvE.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by ASUS on 09/03/2017.
 */

public class MGiocatore extends MGps {

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
            Log.d("MGiocatore", "no");
            singletoneinstance = new MGiocatore();
        } else Log.d("MGiocatore", "si");

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

    public MPersonaggio getOnePersonaggio(int id){

        MPersonaggio personaggio = null;
        for(int i=0; i < personaggi.size()-1 && personaggio == null; i++){
            if(this.personaggi.get(i).getId() == id){
                personaggio = this.personaggi.get(i);
            }
        }
        if(personaggio == null){
            Log.d("MGiocatore", "Personaggio non Trovato");
        }
        return personaggio;
    }

}
