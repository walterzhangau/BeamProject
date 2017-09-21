package com.example.grace.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MessagingActivity extends AppCompatActivity {

    TextView message_text_view;
    Button message_button_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
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


        message_button_view   = (Button)   findViewById(R.id.message_button);
        message_text_view   = (TextView) findViewById(R.id.message_input);

        message_button_view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String toPrint = message_text_view.getText().toString();

                Log.v("Text Message",toPrint);
            }
        });
        setupActionBar();
    }





    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the back button in the action bar.
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(MessagingActivity.this, NavigationBarActivity.class);
        startActivity(intent);
        finish();
        return true;

    }



    //Add button listening here



}