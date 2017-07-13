package com.ficheralezzi.fantasygo.ModalitaNearPvE.Activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MGiocatore;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MPersonaggio;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MRegoleDiSoddisfazione;
import com.ficheralezzi.fantasygo.R;


/**
 * Created by root on 13/07/17.
 */

public class RiepilogoFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_riepilogo, container, false);
        final String idPersonaggio = getArguments().getString("idPersonaggioScelto");

        MPersonaggio mPersonaggio = MGiocatore.getSingletoneInstance().getOnePersonaggioById(idPersonaggio);
        riempiTableCaratteristiche(mPersonaggio, view);

        MRegoleDiSoddisfazione mRegoleDiSoddisfazione = MRegoleDiSoddisfazione.getSingletoneInstance();
        riempiTabellaRegoleDiSoddisfazione(mRegoleDiSoddisfazione, view);

        Button button = ((Button) getActivity().findViewById(R.id.go_button));
        button.setText("Avvia");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ModalitaNearPvEActivity) getActivity()).avviaModalità(idPersonaggio);
            }
        });

        return view;
    }

    public void riempiTableCaratteristiche(MPersonaggio mPersonaggio, View view){

        TableRow tableRowNome = ((TableRow) view.findViewById(R.id.tableNome));
        TextView textViewNome = ((TextView) tableRowNome.findViewById(R.id.nome));
        textViewNome.setText(mPersonaggio.getNome());

        TableRow tableRowBottino = ((TableRow) view.findViewById(R.id.tableBottino));
        TextView textViewBottino = ((TextView) tableRowBottino.findViewById(R.id.bottino));
        textViewBottino.setText(String.valueOf(mPersonaggio.getBottino()));

        TableRow tableRowRazza = ((TableRow) view.findViewById(R.id.tableRazza));
        TextView textViewRazza = ((TextView) tableRowRazza.findViewById(R.id.razza));
        textViewRazza.setText(mPersonaggio.getRazza());

        TableRow tableRowClasse = ((TableRow) view.findViewById(R.id.tableClasse));
        TextView textViewClasse = ((TextView) tableRowClasse.findViewById(R.id.classe));
        textViewClasse.setText(mPersonaggio.getClasse());

        TableRow tableRowSesso = ((TableRow) view.findViewById(R.id.tableSesso));
        TextView textViewSesso = ((TextView) tableRowSesso.findViewById(R.id.sesso));
        textViewSesso.setText(mPersonaggio.getSesso());

        TableRow tableRowPuntiEsperienza = ((TableRow) view.findViewById(R.id.tablePuntiEsperienza));
        TextView textViewRPuntiEsperienza = ((TextView) tableRowPuntiEsperienza.findViewById(R.id.puntiEsperienza));
        textViewRPuntiEsperienza.setText(String.valueOf(mPersonaggio.getPuntiEsperienza()));

        TableRow tableRowOro = ((TableRow) view.findViewById(R.id.tableOro));
        TextView textViewOro = ((TextView) tableRowOro.findViewById(R.id.oro));
        textViewOro.setText(String.valueOf(mPersonaggio.getOro()));

        TableRow tableRowLivello = ((TableRow) view.findViewById(R.id.tableLivello));
        TextView textViewLivello = ((TextView) tableRowLivello.findViewById(R.id.livello));
        textViewLivello.setText(String.valueOf(mPersonaggio.getCaratteristiche().getLivello()));

        TableRow tableRowPuntiFerita = ((TableRow) view.findViewById(R.id.tablePuntiFerita));
        TextView textViewPuntiFerita = ((TextView) tableRowPuntiFerita.findViewById(R.id.puntiFerita));
        textViewPuntiFerita.setText(String.valueOf(mPersonaggio.getCaratteristiche().getPuntiFerita()));

    }

    public void riempiTabellaRegoleDiSoddisfazione(MRegoleDiSoddisfazione mRegoleDiSoddisfazione, View view){

        TableRow tableRowOroMinimo = ((TableRow) view.findViewById(R.id.tableOroMinimo));
        TextView textViewOroMinimo = ((TextView) tableRowOroMinimo.findViewById(R.id.oroMinimo));
        textViewOroMinimo.setText(String.valueOf(mRegoleDiSoddisfazione.getOroMinimo()));

        TableRow tableRowPuntiEsperienzaMinimi = ((TableRow) view.findViewById(R.id.tablePuntiEsperienzaMinimi));
        TextView textViewPuntiEsperienzaMinimi = ((TextView) tableRowPuntiEsperienzaMinimi.findViewById(R.id.puntiEsperienzaMinimi));
        textViewPuntiEsperienzaMinimi.setText(String.valueOf(mRegoleDiSoddisfazione.getPuntiEsperienzaMinimi()));

        TableRow tableRowNumeroDiBattaglie = ((TableRow) view.findViewById(R.id.tableNumeroDiBattaglie));
        TextView textViewNumeroDiBattaglie = ((TextView) tableRowNumeroDiBattaglie.findViewById(R.id.numeroDiBattaglie));
        textViewNumeroDiBattaglie.setText(String.valueOf(mRegoleDiSoddisfazione.getNumeroDiBattaglie()));

        TableRow tableRowPuntiFeritaMinimi = ((TableRow) view.findViewById(R.id.tablePuntiFeritaMinimi));
        TextView textViewPuntiFeritaMinimi = ((TextView) tableRowPuntiFeritaMinimi.findViewById(R.id.puntiFeritaMinimi));
        textViewPuntiFeritaMinimi.setText(String.valueOf(mRegoleDiSoddisfazione.getPuntiFeritaMinimi()));
    }
}