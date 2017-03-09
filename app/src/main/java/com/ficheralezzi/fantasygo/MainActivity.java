package com.ficheralezzi.fantasygo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MBattaglia;
import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MCaratteristiche;
import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MCombattente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        MCombattente Gaetano = new MCombattente(1, caratteristicheA);

        MCaratteristiche caratteristicheB = new MCaratteristiche(3, 1500, 1500, 10, 22, 46, 31, 12, "DardoInfuocato", 0, 10, "Mag");
        MCombattente Giovanni = new MCombattente(2, caratteristicheB);

        Log.d(TAG, ((Integer) caratteristicheA.getPuntiFerita()).toString());
        Log.d(TAG, ((Integer) Gaetano.getCaratteristiche().getPuntiFerita()).toString());

        MBattaglia battaglia = new MBattaglia().getSingletoneInstance();
        battaglia.init(Gaetano, Giovanni);

        Log.d(TAG, ((Integer) battaglia.getCombattenteA().getCaratteristiche().getPuntiFerita()).toString());

        battaglia.elaboraBattaglia();

        Log.d(TAG, "battaglia terminata");
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
