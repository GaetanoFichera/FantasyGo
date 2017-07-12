package com.ficheralezzi.fantasygo.ModalitaNearPvE.Activity;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MGiocatore;
import com.ficheralezzi.fantasygo.R;
import com.ficheralezzi.fantasygo.Utils.CustomAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaetano on 12/07/17.
 */

public class SceltaPersonaggioFragment extends ListFragment {
    private String idPersonaggioScelto = null;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("ciao");

        int lenghtArray = MGiocatore.getSingletoneInstance().getPersonaggi().size();

        ArrayList<String> idsPersonaggi = new ArrayList<>();
        for (int i = 0; i < lenghtArray; i++) {
            idsPersonaggi.add(MGiocatore.getSingletoneInstance().getPersonaggi().get(i).getId());
        }
        Map<String, String> myMap = new HashMap<String, String>();
        for (int i = 0; i < idsPersonaggi.size(); i++) {
            myMap.put(MGiocatore.getSingletoneInstance().getOnePersonaggio(idsPersonaggi.get(i)).getNome(), idsPersonaggi.get(i));
        }
        ArrayList<String> nomiPersonaggi = new ArrayList<>();
        for (int i = 0; i < lenghtArray; i++) {
            nomiPersonaggi.add(MGiocatore.getSingletoneInstance().getPersonaggi().get(i).getNome());
        }

        CustomAdapter customAdapter = new CustomAdapter(getActivity(), android.R.layout.simple_list_item_1, nomiPersonaggi, myMap);
        setListAdapter(customAdapter);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String nome =((TextView) v).getText().toString();
        idPersonaggioScelto = ((CustomAdapter) getListAdapter()).getId(nome);
        System.out.println(idPersonaggioScelto);
        //transiction();
    }

    /*private void transiction(){
        RegoleDiSoddisfazioneFragment regoleDiSoddisfazioneFragment = new RegoleDiSoddisfazioneFragment();
        android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, regoleDiSoddisfazioneFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }*/
}
