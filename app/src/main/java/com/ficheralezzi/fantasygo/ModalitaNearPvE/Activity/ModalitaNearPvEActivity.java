package com.ficheralezzi.fantasygo.ModalitaNearPvE.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MGiocatore;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MPersonaggio;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.Modalità.MModalitàNearPvE;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.RegoleDiSoddisfazione.MRegoleDiSoddisfazione;
import com.ficheralezzi.fantasygo.R;
import com.ficheralezzi.fantasygo.Utils.UserPreferencesManager;

import java.util.ArrayList;

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

        setBackButtonListener();

        createListaPersonaggi();
    }

    private void createListaPersonaggi() {
        updateInfoPersonaggiGiocatore();

        mFragmentTransaction.add(R.id.fragment_container_modnearpve, new SceltaPersonaggioFragment());

        mFragmentTransaction.commit();

    }

    public void avviaModalità() {

        Log.i(TAG, "Modalità Avviata con il personaggio: " + MGiocatore.getSingletoneInstance().getOnePersonaggioById(MModalitàNearPvE.getSingletoneInstance().getIdPersonaggioScelto()).getNome());
        Log.i(TAG, "Le Regole di Soddisfazione sono " + MRegoleDiSoddisfazione.getSingletoneInstance().toString());

        MModalitàNearPvE.getSingletoneInstance().avviaModalità();

        Intent returnIntent = new Intent();
        returnIntent.putExtra("avviare mod near pve", "avvia");
        setResult(AppCompatActivity.RESULT_OK, returnIntent);
        finish();
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

    private void setBackButtonListener(){

        Button backButton = ((Button) findViewById(R.id.back_button));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
