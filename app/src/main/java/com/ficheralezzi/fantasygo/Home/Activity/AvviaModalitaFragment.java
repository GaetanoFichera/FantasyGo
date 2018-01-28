package com.ficheralezzi.fantasygo.Home.Activity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MGiocatore;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.Modalità.MModalitàNearPvEObserver;
import com.ficheralezzi.fantasygo.R;
import com.ficheralezzi.fantasygo.Utils.NetworkManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.maps.android.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by gaetano on 14/07/17.
 */

public class AvviaModalitaFragment extends Fragment implements OnMapReadyCallback {

    /*
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_avvia_modalita, container, false);
        setButtonListener(view);
        return view;
    }

    private void setButtonListener(View v){
        Button button = ((Button) v.findViewById(R.id.button_avvia_modNearPvE));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NetworkManager.isOnline(getActivity())){

                    MModalitàNearPvEObserver.getSingletoneInstance().destroy();
                    MModalitàNearPvEObserver.getSingletoneInstance().init();
                    MModalitàNearPvEObserver.getSingletoneInstance().createModalità();

                    ((com.ficheralezzi.fantasygo.Home.Activity.SwipeHomeActivity) getActivity()).goToModNearPve();
                }else{
                    NetworkManager.showToastOffline(getContext());
                }

            }
        });
    }
    */

    private static final String TAG = "AvviaModalitaFragment";
    private GoogleMap mMap;

    private SensorManager mSensorManager;
    private Sensor sensor;

    private boolean startApp = false;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_avvia_modalita_con_mappa, container, false);
        setButtonListener(view);

        setMap();
        return view;
    }

    private void setButtonListener(View v) {
        Button button = ((Button) v.findViewById(R.id.button_avvia_modNearPvE));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkManager.isOnline(getActivity())) {

                    MModalitàNearPvEObserver.getSingletoneInstance().destroy();
                    MModalitàNearPvEObserver.getSingletoneInstance().init();
                    MModalitàNearPvEObserver.getSingletoneInstance().createModalità();

                    ((com.ficheralezzi.fantasygo.Home.Activity.SwipeHomeActivity) getActivity()).goToModNearPve();
                } else {
                    NetworkManager.showToastOffline(getContext());
                }

            }
        });
    }

    private void setMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);
    }

    private void setSensor() {
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        if (sensor != null) {
            mSensorManager.registerListener(mySensorEventListener, sensor,
                    SensorManager.SENSOR_DELAY_UI);
            Log.i(TAG, "Compass: Registerered for ORIENTATION Sensor");
        } else {
            Log.e(TAG, "Compass: Not Registerered for ORIENTATION Sensor");
            Toast.makeText(getActivity().getApplicationContext(), "ORIENTATION Sensor not found",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i(TAG, "Map Ready!");
        mMap = googleMap;
        try {
            setUpMap(getActivity().getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public void setUpMap(Context context) throws IOException, XmlPullParserException {

        mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        context, R.raw.style_map_json));
        //mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //commentato perchè troppo pesante per il tablet
        /*
        KmlLayer layer = new KmlLayer(mMap, R.raw.kml_laquila, context);
        layer.addLayerToMap();
        */

        mMap.setTrafficEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        LatLng LAQUILA = new LatLng(42.354008,13.391992);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(LAQUILA)             // Sets the center of the map to Mountain View
                .zoom(100)                   // Sets the zoom
                .bearing(90)                 // Sets the orientation of the camera to east
                .tilt(75)                    // Sets the tilt of the camera to 30 degrees
                .build();                    // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        setSensor();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sensor != null) {
            mSensorManager.unregisterListener(mySensorEventListener);
        }
    }

    private SensorEventListener mySensorEventListener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            // angle between the magnetic north direction
            // 0=North, 90=East, 180=South, 270=West
            float azimuth = event.values[0];
            if (azimuth != 0.0){
                Log.i(TAG, "Sensing: " + azimuth);
                updateCamera(azimuth);
            }
        }
    };

    private void updateCamera(float bearing) {

        CameraPosition camera;

        LatLng position = new LatLng(MGiocatore.getSingletoneInstance().getLatitude(), MGiocatore.getSingletoneInstance().getLongitude());

        if(!startApp){
            //LatLng LAQUILA = new LatLng(42.354008,13.391992);

            camera = new CameraPosition.Builder()
                    .target(position)            // Sets the center of the map to Mountain View
                    .zoom(100)                   // Sets the zoom
                    .bearing(90)                 // Sets the orientation of the camera to east
                    .tilt(75)                    // Sets the tilt of the camera to 30 degrees
                    .build();                    // Creates a CameraPosition from the builder

            startApp = true;
        } else camera = mMap.getCameraPosition();

        CameraPosition newCamera = CameraPosition.builder(camera)
                .bearing(bearing)
                .target(position)
                .build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(newCamera));
    }
}
