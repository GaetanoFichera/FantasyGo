package com.ficheralezzi.fantasygo.ModalitaNearPvE.Activity;

import android.app.Fragment;
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
        TextView OroMinimoTextView = ((TextView) OroMinimoRow.findViewById(R.id.text_seek_bar));
        OroMinimoTextView.setText(R.string.oro_minimo);
        SeekBar OroMinimoSeekBar = ((SeekBar) OroMinimoRow.findViewById(R.id.seekBar));
        OroMinimoSeekBar.setTag(R.string.oro_minimo);
        OroMinimoSeekBar.setMax(getContext().getResources().getInteger(R.integer.oro_minimo_max));

        TableRow PuntiEsperienzaMinimiRow = ((TableRow)inflater.inflate(R.layout.row_regola_di_soddisfazione, container, false));
        TextView PuntiEsperienzaMinimiTextView = ((TextView) PuntiEsperienzaMinimiRow.findViewById(R.id.text_seek_bar));
        PuntiEsperienzaMinimiTextView.setText(R.string.punti_esperienza_minimi);
        SeekBar PuntiEsperienzaMinimiSeekBar = ((SeekBar) PuntiEsperienzaMinimiRow.findViewById(R.id.seekBar));
        PuntiEsperienzaMinimiSeekBar.setTag(R.string.punti_esperienza_minimi);
        PuntiEsperienzaMinimiSeekBar.setMax(getContext().getResources().getInteger(R.integer.punti_esperienza_minimi_max));

        TableRow NumeroBattaglieRow = ((TableRow) inflater.inflate(R.layout.row_regola_di_soddisfazione, container, false));
        TextView NumeroBattaglieTextView = ((TextView) NumeroBattaglieRow.findViewById(R.id.text_seek_bar));
        NumeroBattaglieTextView.setText(R.string.numero_di_battaglie);
        SeekBar NumeroBattaglieSeekBar = ((SeekBar) NumeroBattaglieRow.findViewById(R.id.seekBar));
        NumeroBattaglieSeekBar.setTag(R.string.numero_di_battaglie);
        NumeroBattaglieSeekBar.setMax(getContext().getResources().getInteger(R.integer.numero_di_battaglie_max));

        TableRow PuntiFeritaMinimiRow = ((TableRow) inflater.inflate(R.layout.row_regola_di_soddisfazione, container, false));
        TextView PuntiFeritaMinimiTextView = ((TextView) PuntiFeritaMinimiRow.findViewById(R.id.text_seek_bar));
        PuntiFeritaMinimiTextView.setText(R.string.punti_ferita_minimi);
        SeekBar PuntiFeritaMinimiSeekBar = ((SeekBar) PuntiFeritaMinimiRow.findViewById(R.id.seekBar));
        PuntiFeritaMinimiSeekBar.setTag(R.string.punti_ferita_minimi);
        String idPersonaggioScelto = getArguments().getString("idPersonaggioScelto");
        PuntiFeritaMinimiSeekBar.setMax(MGiocatore.getSingletoneInstance().getOnePersonaggioById(idPersonaggioScelto).getCaratteristiche().getPuntiFeritaMax());
        TableLayout viewTable = (TableLayout) viewFragment.findViewById(R.id.table_regole_di_soddisfazione);
        viewTable.addView(OroMinimoRow);
        viewTable.addView(PuntiEsperienzaMinimiRow);
        viewTable.addView(NumeroBattaglieRow);
        viewTable.addView(PuntiFeritaMinimiRow);

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

    private void setRegoleDiSoddisfazione(){

        int oroMinimoScelto = ((SeekBar) getView().findViewWithTag(R.string.oro_minimo)).getProgress();
        int puntiEsperienzaMinimiScelti = ((SeekBar) getView().findViewWithTag(R.string.punti_esperienza_minimi)).getProgress();
        int numeroDiBattaglieScelte = ((SeekBar) getView().findViewWithTag(R.string.numero_di_battaglie)).getProgress();
        int puntiFeritaMinimiScelti = ((SeekBar) getView().findViewWithTag(R.string.punti_ferita_minimi)).getProgress();

        MRegoleDiSoddisfazione.getSingletoneInstance().destroy();
        MRegoleDiSoddisfazione.getSingletoneInstance().init(oroMinimoScelto, puntiEsperienzaMinimiScelti, numeroDiBattaglieScelte, puntiFeritaMinimiScelti);
    }

    private void goToNextFragment(){

        RiepilogoFragment riepilogoFragment = new RiepilogoFragment();
        android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        String idPersonaggioScelto = getArguments().getString("idPersonaggioScelto");
        Bundle args = new Bundle();
        args.putString("idPersonaggioScelto", idPersonaggioScelto);
        riepilogoFragment.setArguments(args);

        fragmentTransaction.replace(R.id.fragment_container_modnearpve, riepilogoFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
