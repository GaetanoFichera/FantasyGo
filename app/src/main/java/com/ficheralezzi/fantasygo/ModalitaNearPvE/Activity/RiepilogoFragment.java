package com.ficheralezzi.fantasygo.ModalitaNearPvE.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MGiocatore;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MPersonaggio;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.RegoleDiSoddisfazione.MRegoleDiSoddisfazione;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.Modalità.MModalitàNearPvEObserver;
import com.ficheralezzi.fantasygo.R;
import com.ficheralezzi.fantasygo.Utils.CustomFragment;
import com.ficheralezzi.fantasygo.Utils.NetworkManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by root on 13/07/17.
 */

public class RiepilogoFragment extends CustomFragment{

    private static int FRAGMENT_TITLE = R.string.title_riepilogo;
    private static String TAG = "RiepilogoFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setTitleActivity();

        View view = inflater.inflate(R.layout.fragment_riepilogo, container, false);

        riempiTableCaratteristiche(view);

        riempiTabellaRegoleDiSoddisfazione(view, inflater, container);

        setButtons();
        setGoButtonListener();

        return view;
    }

    public void riempiTableCaratteristiche(View view){

        MPersonaggio mPersonaggio = MGiocatore.getSingletoneInstance().getOnePersonaggioById(MModalitàNearPvEObserver.getSingletoneInstance().getIdPersonaggioScelto());

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

    private TableRow createOneRow(LayoutInflater inflater, @Nullable ViewGroup container, String nome, int valore){
        TableRow row = ((TableRow) inflater.inflate(R.layout.row_riepilogo_regole, container, false));
        row.setTag(nome + "_row");
        TextView textView = ((TextView) row.findViewById(R.id.text_nome));
        textView.setText(nome);
        TextView textView2 = ((TextView) row.findViewById(R.id.text_valore));
        textView2.setText(String.valueOf(valore));

        return row;
    }

    public void riempiTabellaRegoleDiSoddisfazione(View view, LayoutInflater inflater, ViewGroup container){

        TableLayout viewTable = (TableLayout) view.findViewById(R.id.table_riepilogo_regole);

        HashMap<String, Integer> parametri = MRegoleDiSoddisfazione.getSingletoneInstance().getValoriRegoleDiSoddisfazione();

        List<String> nomiParametri = new ArrayList<>(parametri.keySet());

        for (int i = 0; i < nomiParametri.size(); i++){
            viewTable.addView(createOneRow(inflater, container, nomiParametri.get(i), parametri.get(nomiParametri.get(i))));
        }
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
                if (NetworkManager.isOnline(getActivity())) ((ModalitaNearPvEActivity) getActivity()).avviaModalità();
                    else NetworkManager.showToastOffline(getContext());
            }
        });

    }
}