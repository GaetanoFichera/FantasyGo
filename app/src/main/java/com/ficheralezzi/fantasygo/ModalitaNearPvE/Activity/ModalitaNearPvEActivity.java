package com.ficheralezzi.fantasygo.ModalitaNearPvE.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MGiocatore;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MPersonaggio;
import com.ficheralezzi.fantasygo.R;
import com.ficheralezzi.fantasygo.Utils.CustomAdapter;
import com.ficheralezzi.fantasygo.Utils.UserPreferencesManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModalitaNearPvEActivity extends AppCompatActivity {
    private static final String TAG = "ModalitàNearPvEActivity";
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        setContentView(R.layout.activity_modnearpve);
        createListaPersonaggi();

        //listener sulla lista per passare alle regole di soddisfazione

    }

    private void createListaPersonaggi() {
        updateInfoPersonaggiGiocatore();



        SceltaPersonaggioFragment sceltaPersonaggioFragment = new SceltaPersonaggioFragment();
        mFragmentTransaction.add(R.id.fragment, sceltaPersonaggioFragment);
        mFragmentTransaction.commit();

    }

    private void createListaRegolediSoddisfazione() {


    }

    private void avviaModalità() {

    }

    private void updateInfoPersonaggiGiocatore() {
        UserPreferencesManager userPreferencesManager = new UserPreferencesManager(this);
        ArrayList<MPersonaggio> PersonaggiFromDb = new ArrayList<>();
        ArrayList<String> idsPersonaggiDb = userPreferencesManager.getIdsOneType("_Personaggio");
        for (int i = 0; i < idsPersonaggiDb.size(); i++) {
            MPersonaggio onePersonaggio = (MPersonaggio) userPreferencesManager.load(MPersonaggio.class, idsPersonaggiDb.get(i));
            PersonaggiFromDb.add(onePersonaggio);
        }
        MGiocatore.getSingletoneInstance().setPersonaggi(PersonaggiFromDb);
    }
}
