package com.ficheralezzi.fantasygo.Home.Activity;

import android.app.ActivityManager;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MCaratteristiche;
import com.ficheralezzi.fantasygo.Utils.Messaggio;
import com.ficheralezzi.fantasygo.Utils.PlayAudio;
import com.ficheralezzi.fantasygo.Utils.SwipeHomeCollectionAdapter;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Activity.ModalitaNearPvEActivity;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MGiocatore;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MPersonaggio;
import com.ficheralezzi.fantasygo.R;
import com.ficheralezzi.fantasygo.Utils.NetworkManager;
import com.ficheralezzi.fantasygo.Utils.PermissionManager;
import com.ficheralezzi.fantasygo.Utils.UserPreferencesManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by gaetano on 14/07/17.
 */

public class SwipeHomeActivity extends FragmentActivity {

    private static final String TAG = "SwipeHomeActivity";
    private static final int REQUEST_CODE = 50;
    SwipeHomeCollectionAdapter mSwipeHomeCollectionAdapter;
    ViewPager mViewPager;
    TabLayout mTabLayout;

    private boolean updateEffettuato = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkLocationPermission();

        //da fare in una splashScreen
        if (!updateEffettuato){
            updateMineDb();
            updateInfoGiocatore();
            updateEffettuato = true;
        }

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
        send();
    }

    //funzione per settare icone alle tab ma fa cagare => da rifare
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

    private void updateMineDb(){
        MCaratteristiche caratteristicheA = new MCaratteristiche(2, 10000, 10000, 50, 21, 10, 20, 17, "AttaccoPoderoso", 0, 13, "Fis");
        ArrayList<String> inv = new ArrayList<>();
        MPersonaggio Gaetano = new MPersonaggio("P0001", "Gaetano", caratteristicheA, 0, "M", "Umano", "Tizio", 0, inv, 0);

        MCaratteristiche caratteristicheB = new MCaratteristiche(5, 10000, 10000, 50, 21, 10, 20, 17, "DardoIncantato", 0, 13, "Fis");
        ArrayList<String> inv2 = new ArrayList<>();
        MPersonaggio Giovanni = new MPersonaggio("P0002", "Giovanni", caratteristicheB, 0, "M", "Umano", "Tizios", 0, inv2, 0);

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

        boolean isOnline = NetworkManager.isOnline(this);

        Log.i(TAG, "Connessione: " + String.valueOf(isOnline));
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

    public void send(){
        Messaggio messaggio = new Messaggio();
        final String URL = "http://192.168.1.107:8080/ApiFantasyGo/ApiTest";
        HashMap<String, String> params = new HashMap<>();
        params.put("id", "00");
        messaggio.setMessaggio(1);
        messaggio.setObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL, new JSONObject(params),  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    VolleyLog.v("Response:", response.toString(4));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("Errore", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        Volley.newRequestQueue(this).add(jsonObjectRequest);
        Log.i("Json da inviare", new JSONObject(params).toString());


    }
}
