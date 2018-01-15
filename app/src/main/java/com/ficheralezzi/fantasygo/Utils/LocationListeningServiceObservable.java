package com.ficheralezzi.fantasygo.Utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
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

public class LocationListeningServiceObservable extends Observable implements Observer {

    private ArrayList<Observer> observers = null;

    private static final String TAG = "LocationListener";
    private static final int MIN_PERIOD = 2;
    private static final int MIN_DIST = 2;

    private LocationManager locationManager = null;
    private String providerIdGps;
    private String providerIdNetwork;
    private Location location = null;
    private Context context;

    public LocationListeningServiceObservable(Context context) {
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
            if (locationManager.isProviderEnabled(locationManager.GPS_PROVIDER) ||
                    locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER))
                check = true;
        } else check = false;
        return check;
    }

    public void startLocation(Context context) {
        Log.i(TAG, "sono in start location di LocationLstener");
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }else{
            if(locationManager.getLastKnownLocation(providerIdGps) != null){
                Log.i(TAG, "GPSPROVIDER");
                location = locationManager.getLastKnownLocation(providerIdGps);
                locationManager.requestLocationUpdates(providerIdGps, MIN_PERIOD, MIN_DIST, locationListener);
            }else{
                location = locationManager.getLastKnownLocation(providerIdNetwork);
                Log.i(TAG, String.valueOf(location));
                locationManager.requestLocationUpdates(providerIdNetwork, MIN_PERIOD, MIN_DIST, locationListener);
            }
            notifyObservers();
        }
    }

    public void stopLocation() {
        locationManager.removeUpdates(locationListener);
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.i(TAG, "onLocationChanged");
            if (String.valueOf(location) == null) {
                Log.i("Location", "location null");
            } else {
                Log.i("Location", "location ok");
            }
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
    }

    @Override
    public synchronized void deleteObserver(Observer o) {
        super.deleteObserver(o);
    }

    @Override
    public void notifyObservers() {
        super.notifyObservers();
        MGpsObservableObserver mGpsObservableObserver = new MGpsObservableObserver();
        Log.i(TAG, "sono nel notify");
        try {
            mGpsObservableObserver.updateLocation(context, location);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*for(int i = this.observers.size(); i > 0; i--){
            this.observers.get(i).update(this, this);
        }*/

    }

    @Override
    public void update(Observable o, Object arg) {

    }

}
