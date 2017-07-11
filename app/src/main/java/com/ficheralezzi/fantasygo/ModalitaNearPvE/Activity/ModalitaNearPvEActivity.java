package com.ficheralezzi.fantasygo.ModalitaNearPvE.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MGiocatore;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MPersonaggio;
import com.ficheralezzi.fantasygo.R;
import com.ficheralezzi.fantasygo.Utils.CustomAdapter;
import com.ficheralezzi.fantasygo.Utils.UserPrefs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModalitaNearPvEActivity extends AppCompatActivity{
    private static final String TAG = "ModalitàNearPvEActivity";
    private ListView mListViewPersonaggi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modnearpve);
        createListaPersonaggi();

        //listener sulla lista per passare alle regole di soddisfazione

    }

    private void createListaPersonaggi(){
        mListViewPersonaggi = (ListView) findViewById(R.id.list_view_personaggi);

        updateInfoPersonaggiGiocatore();

        int lenghtArray = MGiocatore.getSingletoneInstance().getPersonaggi().size();

        ArrayList<String> idsPersonaggi = new ArrayList<>();
        for(int i=0; i < lenghtArray; i++){
            idsPersonaggi.add(MGiocatore.getSingletoneInstance().getPersonaggi().get(i).getId());
        }
        Map<String,String> myMap = new HashMap<String,String>();
        for(int i=0; i < idsPersonaggi.size(); i++){
            myMap.put(MGiocatore.getSingletoneInstance().getOnePersonaggio(idsPersonaggi.get(i)).getNome(), idsPersonaggi.get(i));
        }
        ArrayList<String> nomiPersonaggi = new ArrayList<>();
        for(int i=0; i < lenghtArray; i++){
            nomiPersonaggi.add(MGiocatore.getSingletoneInstance().getPersonaggi().get(i).getNome());
        }
        CustomAdapter customAdapter = new CustomAdapter(this, android.R.layout.simple_list_item_1, nomiPersonaggi, myMap);
        mListViewPersonaggi.setAdapter(customAdapter);
        System.out.println("ciao");
    }

    private void createListaRegolediSoddisfazione(){

    }

    private void avviaModalità(){

    }

    private void updateInfoPersonaggiGiocatore(){
        UserPrefs userPrefs = new UserPrefs(this);
        ArrayList<MPersonaggio> PersonaggiFromDb = new ArrayList<>();
        ArrayList<String> idsPersonaggiDb = userPrefs.getIdsOneType("_Personaggio");
        for(int i=0; i < idsPersonaggiDb.size(); i++){
            MPersonaggio onePersonaggio = (MPersonaggio) userPrefs.load(MPersonaggio.class, idsPersonaggiDb.get(i));
            PersonaggiFromDb.add(onePersonaggio);
        }
        MGiocatore.getSingletoneInstance().setPersonaggi(PersonaggiFromDb);
    }
}
