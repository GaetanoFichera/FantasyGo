package com.ficheralezzi.fantasygo.Home.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MCaratteristiche;
import com.ficheralezzi.fantasygo.Home.SwipeHomeCollectionAdapter;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Activity.ModalitaNearPvEActivity;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MGiocatore;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MPersonaggio;
import com.ficheralezzi.fantasygo.R;
import com.ficheralezzi.fantasygo.Utils.UserPreferencesManager;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by gaetano on 14/07/17.
 */

public class SwipeHomeActivity extends FragmentActivity {

    SwipeHomeCollectionAdapter mSwipeHomeCollectionAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //da fare in una splashScreen
        updateMineDb();
        updateInfoGiocatore();

        setContentView(R.layout.activity_swipe_home);

        mSwipeHomeCollectionAdapter = new SwipeHomeCollectionAdapter(getSupportFragmentManager());
        mSwipeHomeCollectionAdapter.setmSwipeOptions(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.swipe_options))));
        mSwipeHomeCollectionAdapter.setmTabClassNameFragments(new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.tab_class_name_fragments))));

        mViewPager = ((ViewPager) findViewById(R.id.pager));
        mViewPager.setAdapter(mSwipeHomeCollectionAdapter);

        System.out.println("numero di pagine: " + mViewPager.getAdapter().getCount());;

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);

    }

    public void goToModNearPve(){
        Intent i = new Intent(SwipeHomeActivity.this, ModalitaNearPvEActivity.class);
        startActivity(i);
    }

    private void makeShortToast(CharSequence msg){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();
    }

    private void updateMineDb(){
        MCaratteristiche caratteristicheA = new MCaratteristiche(2, 10000, 10000, 50, 21, 10, 20, 17, "AttaccoPoderoso", 0, 13, "Fis");
        ArrayList<String> inv = new ArrayList<>();
        MPersonaggio Gaetano = new MPersonaggio("P0001", "Gaetano", caratteristicheA, 0, "M", "Umano", "Tizio", 0, inv, 0);

        MCaratteristiche caratteristicheB = new MCaratteristiche(5, 10000, 10000, 50, 21, 10, 20, 17, "DardoIncantato", 0, 13, "Fis");
        ArrayList<String> inv2 = new ArrayList<>();
        MPersonaggio Giovanni = new MPersonaggio("P0002", "Giovanni", caratteristicheB, 0, "M", "Umano", "Tizios", 0, inv, 0);

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
        MGiocatore.getSingletoneInstance().init("0001", PersonaggiFromDb, "Demo");
    }
}
