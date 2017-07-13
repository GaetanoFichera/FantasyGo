package com.ficheralezzi.fantasygo.ModalitaNearPvE.Activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.ficheralezzi.fantasygo.R;

/**
 * Created by gaetano on 13/07/17.
 */

public class RegoleDiSoddisfazioneFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_regole_di_soddisfazione, container, false);
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekBar_oro_minimo);
        int value = seekBar.getProgress();

        return view;


    }
}
