package com.ficheralezzi.fantasygo.ModalitaNearPvE.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MGiocatore;
import com.ficheralezzi.fantasygo.R;
import com.ficheralezzi.fantasygo.Utils.CustomListFragment;

import java.util.ArrayList;

/**
 * Created by gaetano on 12/07/17.
 */

public class SceltaPersonaggioFragment extends CustomListFragment{

    private String idPersonaggioScelto = null;
    private static int FRAGMENT_TITLE = R.string.title_scelta_personaggio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setTitleActivity();
        setVisibilityButtons();
        return inflater.inflate(R.layout.fragment_lista_scelta_personaggio, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<String> nomiPersonaggi = MGiocatore.getSingletoneInstance().getNomiPersonaggi();

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), R.layout.simple_list_item, nomiPersonaggi);
        setListAdapter(arrayAdapter);
    }

    @Override
    public void setTitleActivity() {
        super.setTitleActivity();
        getActivity().setTitle(FRAGMENT_TITLE);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String nomePersonaggioScelto =((TextView) v).getText().toString();
        idPersonaggioScelto = MGiocatore.getSingletoneInstance().getOnePersonaggioByNome(nomePersonaggioScelto).getId();
        goToRegoleDiSoddisfazioneFragment();
    }

    private void goToRegoleDiSoddisfazioneFragment(){
        RegoleDiSoddisfazioneFragment regoleDiSoddisfazioneFragment = new RegoleDiSoddisfazioneFragment();
        setOneArgToNextFragment(regoleDiSoddisfazioneFragment, "idPersonaggioScelto", idPersonaggioScelto);
        goToNextFragment(regoleDiSoddisfazioneFragment, R.id.fragment_container_modnearpve);
    }

    /* Rimpiazzato dal metodo qui sopra che utilizza la classe estesa CustomListFragment
    private void goToNextFragment(){

        RegoleDiSoddisfazioneFragment regoleDiSoddisfazioneFragment = new RegoleDiSoddisfazioneFragment();
        android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle args = new Bundle();
        args.putString("idPersonaggioScelto", idPersonaggioScelto);
        regoleDiSoddisfazioneFragment.setArguments(args);

        fragmentTransaction.replace(R.id.fragment_container_modnearpve, regoleDiSoddisfazioneFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    */

    private void setVisibilityButtons(){
        Button backButton = ((Button) getActivity().findViewById(R.id.back_button));
        Button startButton = ((Button) getActivity().findViewById(R.id.go_button));

        if(backButton.getVisibility() == View.INVISIBLE) backButton.setVisibility(View.VISIBLE);
        if(startButton.getVisibility() == View.VISIBLE) startButton.setVisibility(View.INVISIBLE);
    }


}
