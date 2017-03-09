package com.ficheralezzi.fantasygo.ModalitàNearPvE.Model;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by ASUS on 09/03/2017.
 */

public class MGps extends Observable {

    private float latitude;
    private float longitude;

    public MGps() {}

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

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
