package com.ficheralezzi.fantasygo.ModalitàNearPvE.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by ASUS on 09/03/2017.
 */

public class MGps extends Observable {

    private double latitude;
    private double longitude;

    public MGps() {

        this.randomPosition();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void randomPosition (){

        this.setLatitude(42.368089);
        this.setLongitude(13.352198);
    }

    public void updateLocation(){

        this.randomPosition();
    } //da implementare

    @Override
    public synchronized void deleteObserver(Observer o) {
        super.deleteObserver(o);

    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    @Override
    public void notifyObservers(Object arg) {
        super.notifyObservers(arg);
    }

    @Override
    public synchronized void deleteObservers() {
        super.deleteObservers();
    }
}
