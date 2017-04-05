package com.ficheralezzi.fantasygo.Utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ficheralezzi.fantasygo.R;

/**
 * Created by gaetano on 31/03/17.
 */

public class UDrawerItemClickListener implements ListView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectItem(position);
    }

    /**
     * Swaps fragments in the main content view
     */
    private void selectItem(int position) {

    }
}

