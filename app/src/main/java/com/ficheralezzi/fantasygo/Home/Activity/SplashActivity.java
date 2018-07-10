package com.ficheralezzi.fantasygo.Home.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MCaratteristiche;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MEquipaggiamento;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MGiocatore;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MPersonaggio;
import com.ficheralezzi.fantasygo.R;
import com.ficheralezzi.fantasygo.Utils.DbManager;
import com.ficheralezzi.fantasygo.Utils.UserPreferencesManager;

import java.util.ArrayList;

/**
 * Created by gaetano on 30/01/18.
 */

public class SplashActivity extends AppCompatActivity {
    private boolean updateEffettuato = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DbManager.getSingletoneInstance().init(this);

        updateMineDb();
        updateInfoGiocatore();
        updateEffettuato = true;

        startActivity(new Intent(this, SwipeHomeActivity.class));
        finish();
    }

    private void updateMineDb(){
        MCaratteristiche caratteristicheA = new MCaratteristiche(2, 10000, 10000, 50, 21, 10, 20, 17, "AttaccoPoderoso", 0, 13, "Fis");
        ArrayList<String> inv = new ArrayList<>();
        MEquipaggiamento equipaggiamento = new MEquipaggiamento("W001", "A01");
        MPersonaggio Gaetano = new MPersonaggio("P0001", "Gaetano", caratteristicheA, equipaggiamento, 0, "M", "Umano", "Tizio", 0, inv, 0);

        MCaratteristiche caratteristicheB = new MCaratteristiche(5, 10000, 10000, 50, 21, 10, 20, 17, "DardoIncantato", 0, 13, "Fis");
        ArrayList<String> inv2 = new ArrayList<>();
        MPersonaggio Giovanni = new MPersonaggio("P0002", "Giovanni", caratteristicheB, equipaggiamento, 0, "M", "Umano", "Tizios", 0, inv2, 0);

        UserPreferencesManager up = new UserPreferencesManager(this);

        if(up.save(Gaetano, Gaetano.getId() + "_Personaggio"));
        if(up.save(Giovanni, Giovanni.getId() + "_Personaggio")) makeShortToast("Update dal Server Riuscito con Successo!");
    }

    private void updateInfoGiocatore(){
        UserPreferencesManager userPreferencesManager = new UserPreferencesManager(this);
        ArrayList<MPersonaggio> PersonaggiFromDb = new ArrayList<>();
        ArrayList<String> idsPersonaggiDb = userPreferencesManager.getIdsOneType("_Personaggio");
        for(int i=0; i < idsPersonaggiDb.size(); i++){
            MPersonaggio onePersonaggio = (MPersonaggio) userPreferencesManager.load(MPersonaggio.class, idsPersonaggiDb.get(i));
            PersonaggiFromDb.add(onePersonaggio);
        }
        MGiocatore.getSingletoneInstance().init("G00001", PersonaggiFromDb, "Demo");
    }

    private void makeShortToast(CharSequence msg){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();
    }
}
