package com.ficheralezzi.fantasygo.Utils;

import android.app.Fragment;

/**
 * Created by gaetano on 14/07/17.
 */

public interface ICustomFragment{
    void goToNextFragment(Fragment nextFragment, int resContainerNextFragment);
    void setOneArgToNextFragment(Fragment nextFragment, String key, String value);
    String getOneArg(String key);
}
