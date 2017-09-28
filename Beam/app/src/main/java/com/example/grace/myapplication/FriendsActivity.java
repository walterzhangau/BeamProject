package com.example.grace.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class FriendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

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
        setupFriendsTable();
        setupActionBar();
    }

    private void setupFriendsTable(){

        TableLayout tableLayoutA;
        tableLayoutA= (TableLayout)findViewById(R.id.friends_table);


        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);

        TableRow header= new TableRow(this);
        header.setLayoutParams(lp);
        TextView first_name  = new TextView(this);                                                                                                                                                                        new TextView(this);
        TextView second_name = new TextView(this);
        first_name.setText("First name");
        second_name.setText("Second name");

        header.addView(first_name);
        header.addView(second_name);
        tableLayoutA.addView(header, 0);

        for (int i = 1; i <10; i++) {

            TableRow row= new TableRow(this);
            row.setLayoutParams(lp);

            TextView qty = new TextView(this);
            qty.setText("Johnson");

            TextView second = new TextView(this);
            second.setText(Integer.toString(i));

            row.addView(qty);
            row.addView(second);
            row.addView(messageButton());
            row.addView(beamButton());
            row.addView(blockButton());
            tableLayoutA.addView(row,i);
        }


    }

    private Button messageButton(){


        Button messageFriend = new Button(this);
        messageFriend.setText(R.string.message_button_text);
        messageFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return messageFriend;

    }

    private Button beamButton(){


        Button button = new Button(this);
        button.setText(R.string.beam_button_text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return button;

    }

    private Button blockButton(){


        Button button = new Button(this);
        button.setText(R.string.block_button_text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return button;

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
        Intent intent = new Intent(FriendsActivity.this, NavigationBarActivity.class);
        startActivity(intent);
        finish();
        return true;

    }

}