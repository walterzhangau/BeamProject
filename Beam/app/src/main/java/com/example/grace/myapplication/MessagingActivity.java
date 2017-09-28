package com.example.grace.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.grace.messaging.ChatMessage;
import com.example.grace.messaging.MessageAdapter;

import java.util.ArrayList;
import java.util.List;


public class MessagingActivity extends AppCompatActivity {

    TextView message_text_view;
    Button message_button_view;
    private List<ChatMessage> chatMessages;
    private ArrayAdapter<ChatMessage> adapter;
    ListView MessagelistView;
    boolean isMine = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        chatMessages = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MessagelistView = (ListView) findViewById(R.id.list_msg);
        adapter = new MessageAdapter(this, R.layout.item_chat_left, chatMessages);
        MessagelistView.setAdapter(adapter);

        message_button_view   = (Button)   findViewById(R.id.message_button);
        message_text_view   = (TextView) findViewById(R.id.message_input);



        message_button_view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String toPrint = message_text_view.getText().toString();

                chatMessages.add(new ChatMessage(message_text_view.getText().toString(), isMine));
                adapter.notifyDataSetChanged();
                message_text_view.setText("");
                isMine =!isMine;


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





}