package com.ficheralezzi.fantasygo.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MGpsObservableObserver;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by ASUS on 26/10/2017.
 */

public class LocationListeningServiceObservable extends Observable {

    private ArrayList<Observer> observers = null;

    private static final String TAG = "LocListServObservable";
    private static final long MIN_PERIOD = 2;
    private static final float MIN_DIST = (float) 0.001;

    private LocationManager locationManager = null;
    private String providerIdGps;
    private String providerIdNetwork;
    private Location location = null;
    public Context context;

    //vedere se rendere Singleton, sarebbe comodo
    public LocationListeningServiceObservable(Context context) {
        observers = new ArrayList<>();
        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        providerIdGps = LocationManager.GPS_PROVIDER;
        providerIdNetwork = LocationManager.NETWORK_PROVIDER;
        this.context = context;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean getGpsStatus() {
        boolean check = false;
        if (locationManager != null) {

            Log.i(TAG, "GpsProvider: " + locationManager.isProviderEnabled(locationManager.GPS_PROVIDER) +
            " NetworkProvider: " +  locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER));

            if (locationManager.isProviderEnabled(locationManager.GPS_PROVIDER) ||
                    locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER))
                check = true;
        } else check = false;
        return check;
    }

    @SuppressLint("MissingPermission")
    public boolean startLocation(Context context) {
        //ho inserito il try/catch perchè crasha se effettuo la requestLocationUpdates senza i permessi, quindi
        //con nel caso ridia errore ritorna false ma lo farebbe anche nel caso in cui sia qualcosa di diverso
        //alla mancanza di permessi a dare errore
        try{
            locationManager.requestLocationUpdates(providerIdGps, MIN_PERIOD, MIN_DIST, locationListener);
            return true;
        }catch (Exception e) {
            Log.i(TAG, "Errore requestLocationUpdates " + e.toString());
            return false;
        }
    }

    public void stopLocation() {
        locationManager.removeUpdates(locationListener);
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.i(TAG, "onLocationChanged: " + location.toString());

            setLocation(location);

            notifyObservers();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

    };

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
        observers.add(o);
    }

    @Override
    public synchronized void deleteObserver(Observer o) {
        super.deleteObserver(o);
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        super.notifyObservers();
        for(int i = this.observers.size(); i > 0; i--){
            this.observers.get(i-1).update(this, this);
        }
    }
}
