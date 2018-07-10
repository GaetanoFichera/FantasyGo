package com.ficheralezzi.fantasygo.Home.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MZonaDiCacciaObserver;
import com.ficheralezzi.fantasygo.Utils.LocationListeningServiceObservable;
import com.ficheralezzi.fantasygo.Utils.PlayAudio;
import com.ficheralezzi.fantasygo.Utils.SwipeHomeCollectionAdapter;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Activity.ModalitaNearPvEActivity;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MGiocatore;
import com.ficheralezzi.fantasygo.R;
import com.ficheralezzi.fantasygo.Utils.PermissionManager;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by gaetano on 14/07/17.
 */

public class SwipeHomeActivity extends FragmentActivity {

    private static final String TAG = "SwipeHomeActivity";
    private static final int REQUEST_CODE = 50;
    SwipeHomeCollectionAdapter mSwipeHomeCollectionAdapter;
    ViewPager mViewPager;
    TabLayout mTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startLocation();

        ArrayList<String> swipeOptions = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.swipe_options)));
        ArrayList<String> tabClassNameFragments = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.tab_class_name_fragments)));

        mSwipeHomeCollectionAdapter = new SwipeHomeCollectionAdapter(getSupportFragmentManager(), swipeOptions, tabClassNameFragments, getApplicationContext());

        setContentView(R.layout.activity_swipe_home);

        mViewPager = ((ViewPager) findViewById(R.id.pager));
        mViewPager.setAdapter(mSwipeHomeCollectionAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);
        initTabsLayout(tabLayout);
        mTabLayout = tabLayout;

        playAudio();
    }

    //funzione per settare icone alle tab... sarebbe da migliorare
    public void initTabsLayout(final TabLayout tabLayout){
        for (int i=0; i < tabLayout.getTabCount(); i++){
            tabLayout.getTabAt(i).setCustomView(R.layout.tab_layout);
            if (i==0){
                tabLayout.getTabAt(i).setIcon(R.drawable.gioca);
            } else if (i==1){
                tabLayout.getTabAt(i).setIcon(R.drawable.i_tuoi_personaggi);
            } else if (i==2){
                tabLayout.getTabAt(i).setIcon(R.drawable.il_tuo_profilo);
            } else if (i==3){
                tabLayout.getTabAt(i).setIcon(R.drawable.impostazioni);
            }
            ((TextView) tabLayout.getTabAt(i).getCustomView().findViewById(R.id.tab_text)).setText(mViewPager.getAdapter().getPageTitle(i));
        }

        mSwipeHomeCollectionAdapter.initializeFirstPageTextColor(tabLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int c = tab.getPosition();
                mSwipeHomeCollectionAdapter.setOnSelectView(tabLayout,c);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int c = tab.getPosition();
                mSwipeHomeCollectionAdapter.setUnSelectView(tabLayout,c);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void goToModNearPve(){
        Intent i = new Intent(SwipeHomeActivity.this, ModalitaNearPvEActivity.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    private void makeShortToast(CharSequence msg){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if(resultCode == AppCompatActivity.RESULT_OK){
                Log.i(TAG, data.getStringExtra("avviare mod near pve"));
                Log.i(TAG, String.valueOf((data.getStringExtra("avviare mod near pve") == "avvia")));
                if (true){
                    startAvanzamentoModNearPvE();
                }
            } else if (resultCode == AppCompatActivity.RESULT_CANCELED) {
                //ci andrebbe qualcosa
            }
        }
    }

    public void startAvanzamentoModNearPvE(){
        Log.i(TAG, "Avanzamento ModNearPvE in caricamento");
        mSwipeHomeCollectionAdapter.replaceFragment("Gioca", getString(R.string.tab_class_name_fragment_avan_mod_near_pve), 0);
        mViewPager.setCurrentItem(2);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewPager.setCurrentItem(0);
            }
        }, 400);
    }

    public void stopAvanzamentoModNearPvE(){
        mViewPager.setCurrentItem(0);
        Log.i(TAG, "Avanzamento ModNearPvE Terminato");
        mSwipeHomeCollectionAdapter.replaceFragment("Gioca", getString(R.string.tab_class_name_fragment_avvia_mod), 0);
        mViewPager.setCurrentItem(2);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewPager.setCurrentItem(0);
            }
        }, 400);
    }

    private void checkLocationPermission(){
        PermissionManager.askPermissionsLocation(this);
    }

    public void moveToFirstPage(){
        if (mViewPager.getCurrentItem() != 0) mViewPager.setCurrentItem(0);
    }

    public void playAudio() {
        Intent objIntent = new Intent(this, PlayAudio.class);
        startService(objIntent);
    }

    public void stopAudio() {
        Intent objIntent = new Intent(this, PlayAudio.class);
        stopService(objIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAudio();
    }

    @Override
    protected void onResume() {
        super.onResume();
        playAudio();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopAudio();
    }

    //start location prova ad avviare la raccolta dei dati gps, nel caso non sia possibile
    //(consideriamo solo il caso in cui non siano stati dati i permessi prima) avvia la richiesta dei permessi
    //che una volta ottenuti avvieranno onRequestPermissionsResult che ritenterà l'avvio di startLocation
    public void startLocation(){
        Log.i(TAG, "sono in start location");
        LocationListeningServiceObservable locationListeningServiceObservable = new LocationListeningServiceObservable(this);
        //aggiungo MGiocatore agli Osservatori di Location Listening
        locationListeningServiceObservable.addObserver(MGiocatore.getSingletoneInstance());
        //aggiungo MZonaDiCacciaObserver agli Osservatori di MGiocatore
        MGiocatore.getSingletoneInstance().addObserver(MZonaDiCacciaObserver.getSingletoneInstance());
        if (!locationListeningServiceObservable.startLocation(this)) {
            Log.i(TAG, "Non ho i permessi di Location, dammeli per favore!");
            checkLocationPermission();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        startLocation();
    }
}
