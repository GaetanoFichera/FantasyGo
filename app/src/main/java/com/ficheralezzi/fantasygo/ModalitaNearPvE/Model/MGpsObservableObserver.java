package com.ficheralezzi.fantasygo.ModalitaNearPvE.Model;

import android.content.ContentQueryMap;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ficheralezzi.fantasygo.Utils.LocationListeningServiceObservable;
import com.ficheralezzi.fantasygo.Utils.Messaggio;
import com.ficheralezzi.fantasygo.Utils.NetworkManager;
import com.ficheralezzi.fantasygo.Utils.VolleyResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class MGpsObservableObserver extends Observable implements Observer {

    private final static String TAG = "MGpsObservableObserver";

    private double latitude;
    private double longitude;
    private ArrayList<Observer> observers = null;

    public MGpsObservableObserver() {
        this.observers = new ArrayList<>();
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

    public void updateLocation(Context context, Location location) throws JSONException {
        try{
            this.setLatitude(location.getLatitude());
            this.setLongitude(location.getLongitude());

            Log.i(TAG, "Lat: " + String.valueOf(latitude) + " Long: " + String.valueOf(longitude));

            VolleyResponseListener volleyResponseListener = new VolleyResponseListener() {
                @Override
                public void requestStarted() {

                }

                @Override
                public void requestCompleted(Messaggio messaggioResponse) {
                    Log.i(TAG, "Richiesta Volley Completata in update Location");
                    String idNuovaZonaDiCaccia = ((String) messaggioResponse.getObject());

                    MZonaDiCaccia.getSingletoneInstance().update(idNuovaZonaDiCaccia);
                }

                @Override
                public void requestEndedWithError(VolleyError error) {

                }
            };

            NetworkManager.updateLocationOnServer(volleyResponseListener, context);
            notifyObservers();
        }catch (Exception e){
            Log.i(TAG, "Errore: " + e);
        }

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
        for(int i = this.observers.size(); i > 0; i--){
            this.observers.get(i-1).update(this, this);
        }
    }

    @Override
    public synchronized void deleteObservers() {
        super.deleteObservers();
        this.observers.clear();
    }

    @Override
    public void update(Observable o, Object arg) {
        Location location = ((LocationListeningServiceObservable) o).getLocation();
        try {
            updateLocation(((LocationListeningServiceObservable) o).context, location);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
