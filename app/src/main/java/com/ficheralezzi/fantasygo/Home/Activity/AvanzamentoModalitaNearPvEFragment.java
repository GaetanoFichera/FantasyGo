package com.ficheralezzi.fantasygo.Home.Activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MBattaglia;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MGiocatore;
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
        System.out.println(MGiocatore.getSingletoneInstance().getNome());

        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                mTextViewPuntiFeritaCorrenti.setText(String.valueOf(MGiocatore.getSingletoneInstance().getOnePersonaggioById(mIdPersonaggioCorrente).getCaratteristiche().getPuntiFerita()));
                mTextViewPuntiFeritaMassimi.setText(String.valueOf(MGiocatore.getSingletoneInstance().getOnePersonaggioById(mIdPersonaggioCorrente).getCaratteristiche().getPuntiFeritaMax()));
                mTextViewNumeroDiBattaglieAffrontate.setText("0");
                mTextViewOroPosseduto.setText(String.valueOf(MGiocatore.getSingletoneInstance().getOnePersonaggioById(mIdPersonaggioCorrente).getOro()));

            }

        });
    }
}
