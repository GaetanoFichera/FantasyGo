package com.ficheralezzi.fantasygo.Home.Activity;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MBattaglia;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MGiocatore;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.Modalità.MModalitàNearPvE;
import com.ficheralezzi.fantasygo.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by gaetano on 31/07/17.
 */

public class AvanzamentoModalitaNearPvEFragment extends Fragment {

    private static final String TAG = "AvanzModNearPvEFragment";
    private Timer mTimer;
    private TimerTask mTimerTask;
    private String mIdPersonaggioCorrente;
    private TextView mTextViewPuntiFeritaCorrenti;
    private TextView mTextViewPuntiFeritaMassimi;
    private TextView mTextViewNumeroDiBattaglieAffrontate;
    private TextView mTextViewOroPosseduto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_avvia_modalita_mod_near_pve_in_corso, container, false);
        ViewGroup avanzamentoModNearPvE =  (ViewGroup) inflater.inflate(R.layout.fragment_avanzamento_mod_near_pve, null);
        ((FrameLayout) view.findViewById(R.id.avanzamento_mod_near_pve_container)).addView(avanzamentoModNearPvE);

        //set id nella variabile e nome nella view del personaggio corrente
        //sarebbe da fare in modo che esista una classe che gira in background per i caso d'uso mod near pve
        mIdPersonaggioCorrente = MGiocatore.getSingletoneInstance().getPersonaggi().get(0).getId();
        System.out.println(MGiocatore.getSingletoneInstance().getPersonaggi().get(0).getId());
        ((TextView) view.findViewById(R.id.nomePersonaggio)).setText(MGiocatore.getSingletoneInstance().getOnePersonaggioById(mIdPersonaggioCorrente).getNome());
    
        mTextViewPuntiFeritaCorrenti = ((TextView) view.findViewById(R.id.puntiFeritaCorrenti));
        mTextViewPuntiFeritaMassimi = ((TextView) view.findViewById(R.id.puntiFeritaMassimi));
        mTextViewNumeroDiBattaglieAffrontate = ((TextView) view.findViewById(R.id.numeroDiBattaglieAffrontate));
        mTextViewOroPosseduto = ((TextView) view.findViewById(R.id.oroPosseduto));
        setButtonListener(view);
        modNearPvETerminata();

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        mTimer.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            mTimer = new Timer();
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    updateView();
                }
            };
            mTimer.schedule(mTimerTask, 500, 500);
        } catch (IllegalStateException e){
            android.util.Log.i(TAG, "Resume error");
        }
    }

    private void updateView(){
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if (MModalitàNearPvE.getSingletoneInstance().getRisultatoFinale() != null) {
                    mTextViewPuntiFeritaCorrenti.setText(String.valueOf(MModalitàNearPvE.getSingletoneInstance().getRisultatoFinale().getPuntiFerita()));
                    mTextViewPuntiFeritaMassimi.setText(String.valueOf(MGiocatore.getSingletoneInstance().getOnePersonaggioById(mIdPersonaggioCorrente).getCaratteristiche().getPuntiFeritaMax()));
                    mTextViewNumeroDiBattaglieAffrontate.setText(String.valueOf(MModalitàNearPvE.getSingletoneInstance().getRisultatoFinale().getNumeroDiBattaglie()));
                    mTextViewOroPosseduto.setText(String.valueOf(MModalitàNearPvE.getSingletoneInstance().getRisultatoFinale().getOro()));
                }
            }

        });
    }

    private void setButtonListener(View v){
        Button endModButton = ((Button) v.findViewById(R.id.button_end_mod));
        endModButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                terminaModalitaNearPvE();
            }
        });
    }

    private void terminaModalitaNearPvE(){
        MModalitàNearPvE.getSingletoneInstance().terminaModalità();

        Log.i(TAG, "Mod Terminata");

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog();
            }
        });
    }

    private void modNearPvETerminata(){

        Thread battagliaTerminata = new Thread(new Runnable() {
            @Override
            public void run() {
                Boolean isFinished = true;
                while (isFinished){
                    if(MModalitàNearPvE.getSingletoneInstance().getRisultatoFinale() != null){
                        if(!MModalitàNearPvE.getSingletoneInstance().isRunning()){
                            isFinished = false;
                        }
                    }else isFinished = false;
                }

                Log.i(TAG, "Mod Terminata in automatico");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog();
                    }
                });
            }
        });

        battagliaTerminata.start();
    }

    private void dialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_titolo)
                .setMessage(R.string.dialog_testo_mod_in_corso)
                .setPositiveButton(R.string.string_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((SwipeHomeActivity) getActivity()).stopAvanzamentoModNearPvE();
                    }
                });

        final AlertDialog dialog = builder.create();

        dialog.show();


        dialog.getButton(Dialog.BUTTON_POSITIVE).setClickable(false);
        dialog.getButton(Dialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.FGcolorAccentGreyfy));

        Thread controlloTermineUltimaBattaglia = new Thread(new Runnable() {
            @Override
            public void run() {

                while(MModalitàNearPvE.getSingletoneInstance().isBattagliaInCorso()){

                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setMessage(getResources().getString(R.string.dialog_testo));
                        dialog.getButton(Dialog.BUTTON_POSITIVE).setClickable(true);
                        dialog.getButton(Dialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.FGcolorAccent));
                    }
                });
            }
        });

        controlloTermineUltimaBattaglia.start();

    }
}
