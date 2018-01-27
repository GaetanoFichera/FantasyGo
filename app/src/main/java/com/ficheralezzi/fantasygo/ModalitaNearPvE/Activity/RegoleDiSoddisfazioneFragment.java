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

        /*
        viewTablev2.addView(createOneRow(inflater, container, R.string.oro_minimo, getContext().getResources().getInteger(R.integer.oro_minimo_max)));
        viewTablev2.addView(createOneRow(inflater, container, R.string.punti_esperienza_minimi, getContext().getResources().getInteger(R.integer.punti_esperienza_minimi_max)));
        viewTablev2.addView(createOneRow(inflater, container, R.string.numero_di_battaglie, getContext().getResources().getInteger(R.integer.numero_di_battaglie_max)));
        String idPersonaggioSceltov2 = MModalitàNearPvEObserver.getSingletoneInstance().getIdPersonaggioScelto();
        //String idPersonaggioSceltov2 = getOneArg("idPersonaggioScelto");
        viewTablev2.addView(createOneRow(inflater, container, R.string.punti_ferita_minimi, MGiocatore.getSingletoneInstance().getOnePersonaggioById(idPersonaggioSceltov2).getCaratteristiche().getPuntiFeritaMax()));

        setOneProgressBarListener(viewFragment, R.string.oro_minimo);
        setOneProgressBarListener(viewFragment, R.string.punti_esperienza_minimi);
        setOneProgressBarListener(viewFragment, R.string.numero_di_battaglie);
        setOneProgressBarListener(viewFragment, R.string.punti_ferita_minimi);
        */

        //si potrebbe modificare il codice qui sopra e popolare la tabella e inserire gli ascoltatori sulle seekbar in modo dinamico a partire da un array di valori che indicano le row che servono

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

        /*
        int oroMinimoScelto = ((SeekBar) getView().findViewWithTag(R.string.oro_minimo)).getProgress();
        int puntiEsperienzaMinimiScelti = ((SeekBar) getView().findViewWithTag(R.string.punti_esperienza_minimi)).getProgress();
        int numeroDiBattaglieScelte = ((SeekBar) getView().findViewWithTag(R.string.numero_di_battaglie)).getProgress();
        int puntiFeritaMinimiScelti = ((SeekBar) getView().findViewWithTag(R.string.punti_ferita_minimi)).getProgress();

        MModalitàNearPvEObserver.getSingletoneInstance().enterRegolediSoddisfazione(oroMinimoScelto, puntiEsperienzaMinimiScelti, numeroDiBattaglieScelte, puntiFeritaMinimiScelti);
        */

        MModalitàNearPvEObserver.getSingletoneInstance().enterRegolediSoddisfazione(valori);
    }

    private void goToRiepilogoFragment(){
        System.out.println(MRegoleDiSoddisfazione.getSingletoneInstance().toString());
        RiepilogoFragment riepilogoFragment = new RiepilogoFragment();
        //setOneArgToNextFragment(riepilogoFragment, "idPersonaggioScelto", getOneArg("idPersonaggioScelto"));
        goToNextFragment(riepilogoFragment, R.id.fragment_container_modnearpve);
    }
}
