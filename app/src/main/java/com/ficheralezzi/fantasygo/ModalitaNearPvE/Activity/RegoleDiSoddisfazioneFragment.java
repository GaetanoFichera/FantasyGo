package com.ficheralezzi.fantasygo.ModalitaNearPvE.Activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MGiocatore;
import com.ficheralezzi.fantasygo.R;

import java.util.ArrayList;

/**
 * Created by gaetano on 13/07/17.
 */

public class RegoleDiSoddisfazioneFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return populateTable(inflater, container);
    }

    private View populateTable(LayoutInflater inflater, ViewGroup container){
        View viewFragment = inflater.inflate(R.layout.fragment_regole_di_soddisfazione, container, false);

        TableRow OroMinimoRow = ((TableRow) inflater.inflate(R.layout.row_regola_di_soddisfazione, container, false));
        TextView OroMinimoTextView = ((TextView) OroMinimoRow.findViewById(R.id.text_seek_bar));
        OroMinimoTextView.setText(R.string.oro_minimo);
        SeekBar OroMinimoSeekBar = ((SeekBar) OroMinimoRow.findViewById(R.id.seekBar));
        OroMinimoSeekBar.setMax(R.string.oro_minimo_max);

        TableRow PuntiEsperienzaMinimiRow = ((TableRow)inflater.inflate(R.layout.row_regola_di_soddisfazione, container, false));
        TextView PuntiEsperienzaMinimiTextView = ((TextView) PuntiEsperienzaMinimiRow.findViewById(R.id.text_seek_bar));
        PuntiEsperienzaMinimiTextView.setText(R.string.punti_esperienza_minimi);
        SeekBar PuntiEsperienzaSeekBar = ((SeekBar) PuntiEsperienzaMinimiRow.findViewById(R.id.seekBar));
        PuntiEsperienzaSeekBar.setMax(R.string.punti_esperienza_minimi_max);

        TableRow NumeroBattaglieRow = ((TableRow) inflater.inflate(R.layout.row_regola_di_soddisfazione, container, false));
        TextView NumeroBattaglieTextView = ((TextView) NumeroBattaglieRow.findViewById(R.id.text_seek_bar));
        NumeroBattaglieTextView.setText(R.string.numero_di_battaglie);
        SeekBar NumeroBattaglieSeekBar = ((SeekBar) NumeroBattaglieRow.findViewById(R.id.seekBar));
        NumeroBattaglieSeekBar.setMax(R.string.numero_di_battaglie_max);

        TableRow PuntiFeritaMinimiRow = ((TableRow) inflater.inflate(R.layout.row_regola_di_soddisfazione, container, false));
        TextView PuntiFeritaMinimiTextView = ((TextView) PuntiFeritaMinimiRow.findViewById(R.id.text_seek_bar));
        PuntiFeritaMinimiTextView.setText(R.string.punti_ferita_minimi);
        SeekBar PuntiFeritaMinimiSeekBar = ((SeekBar) PuntiFeritaMinimiRow.findViewById(R.id.seekBar));
        String idPersonaggioScelto = getArguments().getString("idPersonaggioScelto");
        PuntiFeritaMinimiSeekBar.setMax(MGiocatore.getSingletoneInstance().getOnePersonaggio(idPersonaggioScelto).getCaratteristiche().getPuntiFeritaMax());

        TableLayout viewTable = (TableLayout) viewFragment.findViewById(R.id.table_regole_di_soddisfazione);
        viewTable.addView(OroMinimoRow);
        viewTable.addView(PuntiEsperienzaMinimiRow);
        viewTable.addView(NumeroBattaglieRow);
        viewTable.addView(PuntiFeritaMinimiRow);

        return viewFragment;
    }
}
