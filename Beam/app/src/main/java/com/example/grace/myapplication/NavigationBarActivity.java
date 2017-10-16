package com.example.grace.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.audiofx.BassBoost;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.grace.util.LocationService;

import java.util.ArrayList;

//import com.example.grace.messaging.MessagingActivity;

public class NavigationBarActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

     ArrayList<String> needPermissions = new ArrayList<>();

    private ArrayList<String> permissions = new ArrayList<>();
    private static final String TAG = "NavigationBarActivity";
    private boolean alreadyCalled = false;
    private static final int LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        SetupActivityButtons();
        if (Build.VERSION.SDK_INT >= 23) {
            needPermissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            needPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            for (String permission : needPermissions) {
                checkPermission(permission);
            }
            if (permissions.size() > 0) {
                ActivityCompat.requestPermissions(NavigationBarActivity.this, permissions.toArray(new String[permissions.size()]), LOCATION_PERMISSION);
                alreadyCalled = true;
            }
            if(!alreadyCalled) {
                startService(new Intent(this, LocationService.class));
            }
        }

    }

    private void checkPermission(String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(permission);
        }
        return;
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
                Intent intent = new Intent(NavigationBarActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        MessageActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavigationBarActivity.this, MessagingActivity.class);
                intent.putExtra("MESSAGE_AUDIENCE", "Chat Room");
                startActivity(intent);
                finish();

            }
        });

        FriendsActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavigationBarActivity.this, FriendsActivity.class);
                startActivity(intent);
                finish();

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

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            Intent intent = new Intent(NavigationBarActivity.this, SettingsActivity.class);
//            startActivity(intent);
//            //finish();
//            return true;
//        }
//
//        if (id == R.id.title_activity_friends) {
//            Intent intent = new Intent(NavigationBarActivity.this, FriendsActivity.class);
//
//            startActivity(intent);
//            finish();
//            return true;
//        }
//
//
//        if (id == R.id.title_activity_messaging) {
//            Intent intent = new Intent(NavigationBarActivity.this, MessagingActivity.class);
//            startActivity(intent);
//            finish();
//            return true;
//        }
//
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
