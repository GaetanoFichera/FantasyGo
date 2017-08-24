package com.ficheralezzi.fantasygo.Home.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.Modalità.MModalitàNearPvE;
import com.ficheralezzi.fantasygo.R;
import com.ficheralezzi.fantasygo.Utils.NetworkManager;

/**
 * Created by gaetano on 14/07/17.
 */

public class AvviaModalitaFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_avvia_modalita, container, false);
        setButtonListener(view);
        return view;
    }

    private void setButtonListener(View v){
        Button button = ((Button) v.findViewById(R.id.button_avvia_modNearPvE));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NetworkManager.isOnline(getActivity())){
                    MModalitàNearPvE.getSingletoneInstance().destroy();
                    ((com.ficheralezzi.fantasygo.Home.Activity.SwipeHomeActivity) getActivity()).goToModNearPve();
                }else{
                    NetworkManager.showToastOffline(getContext());
                }

            }
        });
    }
}
