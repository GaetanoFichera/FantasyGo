package com.ficheralezzi.fantasygo.ModalitàNearPvE.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by ASUS on 09/03/2017.
 */

public class MGiocatore extends MGps {

    private String id;
    private ArrayList<MPersonaggio> personaggio;
    private String nome;
    private static MGiocatore singletoneinstance = null;

    public MGiocatore() {
        super();

    }

    public void init(String id, MPersonaggio personaggio, String nome){

        if(this.id == null & this.personaggio == null & this.nome == null){
            this.id = id;
            this.personaggio.add(personaggio);
            this.nome = nome;
        }
    }

    public static MGiocatore getSingletoneInstance() {

        if(singletoneinstance == null){
            Log.d("giocatore", "no");
            singletoneinstance = new MGiocatore();
        } else Log.d("giocatore", "si");

        return singletoneinstance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<MPersonaggio> getPersonaggio() {
        return personaggio;
    }

    public void setPersonaggio(ArrayList<MPersonaggio> personaggio) { this.personaggio = personaggio; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
