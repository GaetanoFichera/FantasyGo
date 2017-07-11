package com.ficheralezzi.fantasygo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MCaratteristiche;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MGiocatore;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MPersonaggio;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MRegoleDiSoddisfazione;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.Modalità.MModalitàNearPvE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    private static final String  TAG = "TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayLog();
        modalità();
    }

    private void modalità(){

        /*
        MCaratteristiche(int livello, int puntiFerita, int puntiFeritaMax, int attaccoFisico, int difesaFisico, int attaccoMagico,
                     int difesaMagico, String abilità, int caricaAbilità, int caricaMaxAbilità, String tipoAttBase, int velocitadAttacco )
         */
        MCaratteristiche caratteristicheA = new MCaratteristiche(2, 10000, 1000, 50, 21, 10, 20, 17, "AttaccoPoderoso", 0, 13, "Fis");
        ArrayList<String> inv = new ArrayList<>();
        MPersonaggio Gaetano = new MPersonaggio("P0001", "Gaetano", caratteristicheA, 0, "F", "Umano", "Tizio", 0, inv, 0);
        ArrayList<MPersonaggio> personaggios = new ArrayList<>();
        personaggios.add(Gaetano);

        MGiocatore.getSingletoneInstance().init("G0001", personaggios, "Gaetano");

        MRegoleDiSoddisfazione.getSingletoneInstance().init(10000, 20000, 30000, 10);
        MModalitàNearPvE modalitàNearPvE = new MModalitàNearPvE("P0001");
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
