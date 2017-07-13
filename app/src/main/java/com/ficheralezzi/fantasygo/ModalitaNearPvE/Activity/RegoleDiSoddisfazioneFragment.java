package com.ficheralezzi.fantasygo.ModalitaNearPvE.Activity;

import android.app.Fragment;
import android.location.GpsStatus;
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

import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MGiocatore;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MRegoleDiSoddisfazione;
import com.ficheralezzi.fantasygo.R;

import java.util.zip.Inflater;

/**
 * Created by gaetano on 13/07/17.
 */

public class RegoleDiSoddisfazioneFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setButtons();
        setGoButtonListener();
        return populateTable(inflater, container);
    }

    private View populateTable(LayoutInflater inflater, ViewGroup container){
        View viewFragment = inflater.inflate(R.layout.fragment_regole_di_soddisfazione, container, false);

        TableRow OroMinimoRow = ((TableRow) inflater.inflate(R.layout.row_regola_di_soddisfazione, container, false));
        OroMinimoRow.setTag(R.string.oro_minimo + "_row");
        TextView OroMinimoTextView = ((TextView) OroMinimoRow.findViewById(R.id.text_seekbar));
        OroMinimoTextView.setText(R.string.oro_minimo);
        SeekBar OroMinimoSeekBar = ((SeekBar) OroMinimoRow.findViewById(R.id.seekbar));
        OroMinimoSeekBar.setTag(R.string.oro_minimo);
        OroMinimoSeekBar.setMax(getContext().getResources().getInteger(R.integer.oro_minimo_max));
        TextView OroMinimoProgress = ((TextView) OroMinimoRow.findViewById(R.id.progress_seekbar));
        OroMinimoProgress.setTag(R.string.oro_minimo + "_progress");

        TableRow PuntiEsperienzaMinimiRow = ((TableRow)inflater.inflate(R.layout.row_regola_di_soddisfazione, container, false));
        PuntiEsperienzaMinimiRow.setTag(R.string.punti_esperienza_minimi + "_row");
        TextView PuntiEsperienzaMinimiTextView = ((TextView) PuntiEsperienzaMinimiRow.findViewById(R.id.text_seekbar));
        PuntiEsperienzaMinimiTextView.setText(R.string.punti_esperienza_minimi);
        SeekBar PuntiEsperienzaMinimiSeekBar = ((SeekBar) PuntiEsperienzaMinimiRow.findViewById(R.id.seekbar));
        PuntiEsperienzaMinimiSeekBar.setTag(R.string.punti_esperienza_minimi);
        PuntiEsperienzaMinimiSeekBar.setMax(getContext().getResources().getInteger(R.integer.punti_esperienza_minimi_max));
        TextView PuntiEsperienzaMinimiProgress = ((TextView) PuntiEsperienzaMinimiRow.findViewById(R.id.progress_seekbar));
        PuntiEsperienzaMinimiProgress.setTag(R.string.punti_esperienza_minimi + "_progress");

        TableRow NumeroBattaglieRow = ((TableRow) inflater.inflate(R.layout.row_regola_di_soddisfazione, container, false));
        NumeroBattaglieRow.setTag(R.string.numero_di_battaglie + "_row");
        TextView NumeroBattaglieTextView = ((TextView) NumeroBattaglieRow.findViewById(R.id.text_seekbar));
        NumeroBattaglieTextView.setText(R.string.numero_di_battaglie);
        SeekBar NumeroBattaglieSeekBar = ((SeekBar) NumeroBattaglieRow.findViewById(R.id.seekbar));
        NumeroBattaglieSeekBar.setTag(R.string.numero_di_battaglie);
        NumeroBattaglieSeekBar.setMax(getContext().getResources().getInteger(R.integer.numero_di_battaglie_max));
        TextView NumeroBattaglieProgress = ((TextView) NumeroBattaglieRow.findViewById(R.id.progress_seekbar));
        NumeroBattaglieProgress.setTag(R.string.numero_di_battaglie + "_progress");

        TableRow PuntiFeritaMinimiRow = ((TableRow) inflater.inflate(R.layout.row_regola_di_soddisfazione, container, false));
        PuntiFeritaMinimiRow.setTag(R.string.punti_ferita_minimi + "_row");
        TextView PuntiFeritaMinimiTextView = ((TextView) PuntiFeritaMinimiRow.findViewById(R.id.text_seekbar));
        PuntiFeritaMinimiTextView.setText(R.string.punti_ferita_minimi);
        SeekBar PuntiFeritaMinimiSeekBar = ((SeekBar) PuntiFeritaMinimiRow.findViewById(R.id.seekbar));
        PuntiFeritaMinimiSeekBar.setTag(R.string.punti_ferita_minimi);
        String idPersonaggioScelto = getArguments().getString("idPersonaggioScelto");
        PuntiFeritaMinimiSeekBar.setMax(MGiocatore.getSingletoneInstance().getOnePersonaggioById(idPersonaggioScelto).getCaratteristiche().getPuntiFeritaMax());
        TextView PuntiFeritaMinimiProgress = ((TextView) PuntiFeritaMinimiRow.findViewById(R.id.progress_seekbar));
        PuntiFeritaMinimiProgress.setTag(R.string.punti_ferita_minimi + "_progress");

        TableLayout viewTable = (TableLayout) viewFragment.findViewById(R.id.table_regole_di_soddisfazione);
        viewTable.addView(OroMinimoRow);
        viewTable.addView(PuntiEsperienzaMinimiRow);
        viewTable.addView(NumeroBattaglieRow);
        viewTable.addView(PuntiFeritaMinimiRow);

        setProgressBarListener(viewFragment);

        return viewFragment;
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
                goToNextFragment();
            }
        });

    }

    private void setProgressBarListener(final View v){
        ((SeekBar) v.findViewWithTag(R.string.oro_minimo + "_row").findViewById(R.id.seekbar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String progress = String.valueOf(seekBar.getProgress());
                ((TextView) getView().findViewWithTag(R.string.oro_minimo + "_row").findViewById(R.id.progress_seekbar)).setText(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

       ((SeekBar) v.findViewWithTag(R.string.punti_esperienza_minimi + "_row").findViewById(R.id.seekbar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String progress = String.valueOf(seekBar.getProgress());
                ((TextView) getView().findViewWithTag(R.string.punti_esperienza_minimi + "_row").findViewById(R.id.progress_seekbar)).setText(progress);            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ((SeekBar) v.findViewWithTag(R.string.numero_di_battaglie + "_row").findViewById(R.id.seekbar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String progress = String.valueOf(seekBar.getProgress());
                ((TextView) getView().findViewWithTag(R.string.numero_di_battaglie + "_row").findViewById(R.id.progress_seekbar)).setText(progress);            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ((SeekBar) v.findViewWithTag(R.string.punti_ferita_minimi + "_row").findViewById(R.id.seekbar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String progress = String.valueOf(seekBar.getProgress());
                ((TextView) getView().findViewWithTag(R.string.punti_ferita_minimi + "_row").findViewById(R.id.progress_seekbar)).setText(progress);            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setRegoleDiSoddisfazione(){
        int oroMinimoScelto = ((SeekBar) getView().findViewWithTag(R.string.oro_minimo)).getProgress();
        int puntiEsperienzaMinimiScelti = ((SeekBar) getView().findViewWithTag(R.string.punti_esperienza_minimi)).getProgress();
        int numeroDiBattaglieScelte = ((SeekBar) getView().findViewWithTag(R.string.numero_di_battaglie)).getProgress();
        int puntiFeritaMinimiScelti = ((SeekBar) getView().findViewWithTag(R.string.punti_ferita_minimi)).getProgress();

        MRegoleDiSoddisfazione.getSingletoneInstance().destroy();
        MRegoleDiSoddisfazione.getSingletoneInstance().init(oroMinimoScelto, puntiEsperienzaMinimiScelti, numeroDiBattaglieScelte, puntiFeritaMinimiScelti);
    }

    private void goToNextFragment(){
        System.out.println(MRegoleDiSoddisfazione.getSingletoneInstance().toString());

        RiepilogoFragment riepilogoFragment = new RiepilogoFragment();

        android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle args = new Bundle();
        args.putString("idPersonaggioScelto", getArguments().getString("idPersonaggioScelto"));
        riepilogoFragment.setArguments(args);

        fragmentTransaction.replace(R.id.fragment_container_modnearpve, riepilogoFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
