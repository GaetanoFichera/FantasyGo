package com.ficheralezzi.fantasygo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MBattaglia;
import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MCaratteristiche;
import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MCombattente;
import com.ficheralezzi.fantasygo.ModalitàNearPvE.Model.MGiocatore;
import com.ficheralezzi.fantasygo.ModalitàNearPvE.Model.MMostro;
import com.ficheralezzi.fantasygo.ModalitàNearPvE.Model.MPersonaggio;
import com.ficheralezzi.fantasygo.ModalitàNearPvE.Model.MRegoleDiSoddisfazione;
import com.ficheralezzi.fantasygo.ModalitàNearPvE.Model.MZonaDiCaccia;
import com.ficheralezzi.fantasygo.ModalitàNearPvE.Model.Modalità.MModalitàNearPvE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String  TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayLog();
        battaglia();
    }

    private void battaglia(){

        /*
        MCaratteristiche(int livello, int puntiFerita, int puntiFeritaMax, int attaccoFisico, int difesaFisico, int attaccoMagico,
                     int difesaMagico, String abilità, int caricaAbilità, int caricaMaxAbilità, String tipoAttBase, int velocitadAttacco )
         */
        MCaratteristiche caratteristicheA = new MCaratteristiche(2, 1000, 1000, 50, 21, 10, 20, 17, "AttaccoPoderoso", 0, 13, "Fis");
        ArrayList<String> inv = new ArrayList<>();
        MPersonaggio Gaetano = new MPersonaggio(1, caratteristicheA, 0, "F", "Umano", "Tizio", 0, inv, 0);

        ArrayList<MPersonaggio> personaggios = new ArrayList<>();
        personaggios.add(Gaetano);
        MGiocatore.getSingletoneInstance().init("P0", personaggios, "Gaetano");

        MRegoleDiSoddisfazione.getSingletoneInstance().init(100, 200, 30, 10);
        MModalitàNearPvE modalitàNearPvE = new MModalitàNearPvE(1);
        modalitàNearPvE.avviaModalità();
        Log.i("Risultato", modalitàNearPvE.terminaModalità().toString());
    }

    private void displayLog(){
        try {

            Process process = Runtime.getRuntime().exec("logcat -d");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            StringBuilder log=new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                log.append(line);
                log.append("\n");
            }

            TextView textViewLog = (TextView)findViewById(R.id.textViewLog);

            textViewLog.setText(log.toString());

        } catch (IOException e) {
        }
    }
}
