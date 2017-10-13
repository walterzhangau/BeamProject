package com.example.grace.util;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.grace.UserInformation.UserCredentials;
import com.example.grace.servercommunication.ServerConnection;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

/**
 * Created by thomas on 9/10/2017.
 * Referencing: https://github.com/nickfox/GpsTracker/blob/master/phoneClients/android/app/src/main/java/com/websmithing/gpstracker/LocationService.java
 */

public class LocationService extends Service implements LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private FusedLocationProviderApi FusedLocationProviderApi;
    private GoogleApiClient googleApiClient;
    private static final String TAG = "LocationService";
    private boolean currentlyProcessingLocation = false;
    private LocationRequest locationRequest;


    @Override
    public void onCreate() {
        super.onCreate();




    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!currentlyProcessingLocation) {
            currentlyProcessingLocation = true;
            startTracking();
        }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.e(TAG, "onLocationChanged");
            //send new location to server
            ServerConnection serverConnection = new ServerConnection();
            ArrayList<String> keyTags = new ArrayList<String>();
            ArrayList<String> keys = new ArrayList<String>();
            keyTags.add("email");
            // what order shoud these be in
            keyTags.add("latitude");
            keyTags.add("longitude");
            keys.add(UserCredentials.email);
            keys.add(Double.toString(location.getLatitude()));
            keys.add(Double.toString(location.getLongitude()));
            serverConnection.makeServerRequest("UpdateLocation", keyTags, keys, 0, this, false);
        }
    }

    /*@Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected");

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "GoogleApi suspended.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed");

        stopLocationUpdates();
        stopSelf();

    }

    private void stopLocationUpdates() {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    private void startTracking() {
        Log.d(TAG, "startTracking");
        if (isGooglePlayServicesAvailable()) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();


            if (!googleApiClient.isConnected() || !googleApiClient.isConnecting()) {
                googleApiClient.connect();
            }

        } else {
            Log.e(TAG, "Cant connect");
        }
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            return false;
        }
    }


}
