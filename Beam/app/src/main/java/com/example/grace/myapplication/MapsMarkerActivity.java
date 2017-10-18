package com.example.grace.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.grace.ARchitect.AbstractArchitectCamActivity;
import com.example.grace.servercommunication.JSONResponse;
import com.example.grace.servercommunication.ServerConnection;
import com.example.grace.util.MyLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static com.example.grace.myapplication.R.id.architectView;

/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
public class MapsMarkerActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    public String friendsLatitude;
    public String friendsLongitude;
    public String friendsName;
    public String friendsNameToPrint;

    public MapFriendLocationTask friendLocationTask;
    public MapFriendLocationTask mFriendLocationTask = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle extras = getIntent().getExtras();
        friendsName = extras.getString("user");

        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    private boolean getFriendLocation() {

        System.out.println("conencitng to SERVER ------------------------------------------------------------------------------------------------");
        ServerConnection serverConnection = new ServerConnection();

        ArrayList<String> keyTags = new ArrayList<>();
        keyTags.add("user");
        ArrayList<String> keys = new ArrayList<>();
        keys.add(friendsName);
        //query server for friends data

        serverConnection.makeServerRequest("FriendLocation", keyTags, keys, 1, this, false);

        mFriendLocationTask = new MapFriendLocationTask();
        mFriendLocationTask.execute((Void) null);
        return true;
    }

    public class MapFriendLocationTask extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {
            System.out.println("AT 92");
            try {
                while (JSONResponse.response == null) {
                    System.out.println("AT 95");
                    Thread.sleep(20);
                }

                return true;


            } catch (Exception e) {
                System.out.println("AT 103");
                e.printStackTrace();
            }

            return false;
        }


        @Override
        protected void onPostExecute(final Boolean success) {

            mFriendLocationTask = null;
            System.out.println("TRYING IN POSTEXECUTE");
            try {
                System.out.println("IN POSTEXECUTE");
                //	System.out.print("=============World.loadPoisFromJsonData(" + JSONResponse.JSON + ");");\
                friendsNameToPrint = JSONResponse.response.getString("user");
                friendsLatitude = JSONResponse.response.getString("latitude");
                friendsLongitude = JSONResponse.response.getString("longitude");

                System.out.println("LATITUDE IS = " + friendsLatitude);
                System.out.println("LONGITUDE IS = " + friendsLongitude);
                //System.out.println("=================== LATITUDE AS STRING: " + latitude);
            } catch (Exception e) {

                System.out.println("NOT IN POSTEXECUTE");
                e.printStackTrace();
            }

            friendLocationTask = null;
            JSONResponse.response = null;
        }

        @Override
        protected void onCancelled() {
            friendLocationTask = null;

        }
    }
    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.

        boolean success = getFriendLocation();
        if (success) {
            System.out.println("LATITUDE IN ON MAP IS = " + friendsLatitude);
            System.out.println("LONGITUDE IS = " + friendsLongitude);
            LatLng sydney = new LatLng(-37.814, 144.96332);
            googleMap.addMarker(new MarkerOptions().position(sydney)
                    .title(friendsNameToPrint + "'s Location"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        }

    }
/*    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng sydney = new LatLng(Double.parseDouble(friendsLatitude),
                Double.parseDouble(friendsLongitude));
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title(friendsName + "'s Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }*/


}