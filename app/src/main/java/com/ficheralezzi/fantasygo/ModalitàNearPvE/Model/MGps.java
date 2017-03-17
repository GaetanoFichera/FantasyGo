package com.ficheralezzi.fantasygo.ModalitàNearPvE.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MGps extends Observable {

    private double latitude;
    private double longitude;
    private ArrayList<Observer> observers = null;

    public MGps() {
        this.observers = new ArrayList<>();
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

    //da implementare
    public void updateLocation(){
        this.randomPosition();
    }

    @Override
    public synchronized void deleteObserver(Observer o) {
        super.deleteObserver(o);
        this.observers.remove(o);

    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
        this.observers.add(o);
    }

    @Override
    public void notifyObservers() {
        super.notifyObservers();
        this.updateLocation(); //non va fatto così
        for(int i = this.observers.size(); i > 0; i--){
            this.observers.get(i).update(this, this);
        }
    }

    @Override
    public synchronized void deleteObservers() {
        super.deleteObservers();
        this.observers.clear();
    }
}
