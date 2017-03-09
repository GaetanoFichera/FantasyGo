package com.ficheralezzi.fantasygo.ElaboraBattaglia.Model;


import android.util.Log;

public interface ICalcoloDannoStrategy {

    public void eseguiMossa(int id);

    public void log (String classe, String msg);
}
