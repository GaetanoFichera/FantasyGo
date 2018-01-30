package com.ficheralezzi.fantasygo.ModalitaNearPvE.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.Modalità.MModalitàNearPvEObserver;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.RegoleDiSoddisfazione.MRegoleDiSoddisfazione;
import com.ficheralezzi.fantasygo.R;
import com.ficheralezzi.fantasygo.Utils.CustomFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gaetano on 13/07/17.
 */

public class RegoleDiSoddisfazioneFragment extends CustomFragment{

    private static int FRAGMENT_TITLE = R.string.title_regole_di_soddisfazione;
    private static HashMap<String, Integer> mParametri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setTitleActivity();
        setButtons();
        setGoButtonListener();
        return populateTable(inflater, container);
    }

    private View populateTable(LayoutInflater inflater, ViewGroup container){
        View viewFragment = inflater.inflate(R.layout.fragment_regole_di_soddisfazione, container, false);

        TableLayout viewTablev2 = (TableLayout) viewFragment.findViewById(R.id.table_regole_di_soddisfazione);

        mParametri = MRegoleDiSoddisfazione.getSingletoneInstance().getNomiParametriConMassimiRegoleDiSoddisfazione();

        /**
         * questo ciclo sostituisce le righe commentate qui sotto per rendere la creazione dinamica, basta che
         * le RegoleDiSoddisfazione utilizzate siano implementazioni dell'Interfaccia definita
         */

        List<String> nomiParametri = new ArrayList<>(mParametri.keySet());

        for (int i = 0; i < nomiParametri.size(); i++){
            viewTablev2.addView(createOneRow(inflater, container, nomiParametri.get(i), mParametri.get(nomiParametri.get(i))));
            setOneProgressBarListener(viewFragment, nomiParametri.get(i));
        }

        return viewFragment;
    }

    private TableRow createOneRow(LayoutInflater inflater, @Nullable ViewGroup container, String resText, int resMaxValueSeekBar){
        TableRow row = ((TableRow) inflater.inflate(R.layout.row_regola_di_soddisfazione, container, false));
        row.setTag(resText + "_row");
        TextView textView = ((TextView) row.findViewById(R.id.text_seekbar));
        textView.setText(resText);
        SeekBar seekBar = ((SeekBar) row.findViewById(R.id.seekbar));
        seekBar.setTag(resText);
        seekBar.setMax(resMaxValueSeekBar);
        TextView progress = ((TextView) row.findViewById(R.id.progress_seekbar));
        progress.setTag(resText + "_progress");

        return row;
    }

    @Override
    public void setTitleActivity() {
        super.setTitleActivity();
        getActivity().setTitle(FRAGMENT_TITLE);
    }

    private void setButtons(){
        Button backButton = ((Button) getActivity().findViewById(R.id.back_button));
        Button goButton = ((Button) getActivity().findViewById(R.id.go_button));

        goButton.setText(R.string.go_button_label);

        if(backButton.getVisibility() == View.INVISIBLE) backButton.setVisibility(View.VISIBLE);
        if(goButton.getVisibility() == View.INVISIBLE) goButton.setVisibility(View.VISIBLE);
    }

    private void setGoButtonListener(){
        Button goButton = ((Button) getActivity().findViewById(R.id.go_button));
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRegoleDiSoddisfazione();
                goToRiepilogoFragment();
            }
        });

    }

    private void setOneProgressBarListener(final View v, final String res){
        ((SeekBar) v.findViewWithTag(res + "_row").findViewById(R.id.seekbar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String progress = String.valueOf(seekBar.getProgress());
                ((TextView) getView().findViewWithTag(res + "_row").findViewById(R.id.progress_seekbar)).setText(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setRegoleDiSoddisfazione(){

        HashMap<String, Integer> parametri = MRegoleDiSoddisfazione.getSingletoneInstance().getNomiParametriConMassimiRegoleDiSoddisfazione();
        List<String> nomiParametri = new ArrayList<>(parametri.keySet());
        HashMap<String, Integer> valori = new HashMap<>();

        for (int i = 0; i < nomiParametri.size(); i++){
            valori.put(nomiParametri.get(i), ((SeekBar) getView().findViewWithTag(nomiParametri.get(i))).getProgress());
        }

        MModalitàNearPvEObserver.getSingletoneInstance().enterRegolediSoddisfazione(valori);
    }

    private void goToRiepilogoFragment(){
        System.out.println(MRegoleDiSoddisfazione.getSingletoneInstance().toString());
        RiepilogoFragment riepilogoFragment = new RiepilogoFragment();
        //setOneArgToNextFragment(riepilogoFragment, "idPersonaggioScelto", getOneArg("idPersonaggioScelto"));
        goToNextFragment(riepilogoFragment, R.id.fragment_container_modnearpve);
    }
}
