package com.ficheralezzi.fantasygo.Utils;

import android.app.Fragment;
import android.os.Bundle;

import com.ficheralezzi.fantasygo.ModalitaNearPvE.Activity.RegoleDiSoddisfazioneFragment;
import com.ficheralezzi.fantasygo.R;

/**
 * Created by gaetano on 14/07/17.
 */

public class CustomFragment extends Fragment implements ICustomFragment{

    @Override
    public void goToNextFragment(Fragment nextFragment, int resContainerNextFragment){

        android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(resContainerNextFragment, nextFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void setOneArgToNextFragment(Fragment nextFragment, String key, String value){
        Bundle args = new Bundle();
        args.putString(key, value);
        nextFragment.setArguments(args);
    }

    @Override
    public String getOneArg(String key) {
        return getArguments().getString(key);
    }

    @Override
    public void setTitleActivity() {

    }
}
