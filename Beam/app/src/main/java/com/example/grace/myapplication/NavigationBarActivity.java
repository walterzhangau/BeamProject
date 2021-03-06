package com.example.grace.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.Button;

import com.example.grace.UserInformation.UserCredentials;
import com.example.grace.util.LocationService;

import java.util.ArrayList;

//import com.example.grace.messaging.MessagingActivity;

public class NavigationBarActivity extends AppCompatActivity{


     ArrayList<String> needPermissions = new ArrayList<>();

    private ArrayList<String> permissions = new ArrayList<>();
    private static final String TAG = "NavigationBarActivity";
    private boolean alreadyCalled = false;
    private static final int LOCATION_PERMISSION = 1;
    final String INTENT_TAG_MESSAGING = "MESSAGE_AUDIENCE";
    final String CHAT_ROOM = "Chat Room";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);


        SetupActivityButtons();
        if (Build.VERSION.SDK_INT >= 23) {
            Log.e(TAG, "permissions check starting");
            needPermissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            needPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            needPermissions.add(Manifest.permission.CAMERA);
            for (String permission : needPermissions) {
                checkPermission(permission);
            }
            if (permissions.size() > 0) {
                ActivityCompat.requestPermissions(NavigationBarActivity.this, permissions.toArray(new String[permissions.size()]), LOCATION_PERMISSION);
                alreadyCalled = true;
            }
            if(!alreadyCalled) {
                Log.d(TAG, "service starting...");
                startService(new Intent(this, LocationService.class));
            }
        } else {
            Log.e(TAG, "service starting build version < 23");
            startService(new Intent(this, LocationService.class));
        }

    }

    private void checkPermission(String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(permission);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    startService(new Intent(this, LocationService.class));
                } else {
                    Log.e(TAG, "Permissions denied.");
                }
            }
        }
    }

    private void SetupActivityButtons(){

        Button SettingsActivityButton = (Button)findViewById(R.id.settings_activity_button);
        Button MessageActivityButton = (Button)findViewById(R.id.message_activity_button);
        Button FriendsActivityButton = (Button)findViewById(R.id.friends_activity_button);


        SettingsActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserCredentials.email = null;
                UserCredentials.username = null;
                Intent intent = new Intent(NavigationBarActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        MessageActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavigationBarActivity.this, MessagingActivity.class);
                intent.putExtra(INTENT_TAG_MESSAGING, CHAT_ROOM);
                startActivity(intent);

            }
        });

        FriendsActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavigationBarActivity.this, FriendsActivity.class);
                startActivity(intent);


            }
        });




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        getMenuInflater().inflate(R.menu.navigation_bar, menu);

        return true;
    }

}
