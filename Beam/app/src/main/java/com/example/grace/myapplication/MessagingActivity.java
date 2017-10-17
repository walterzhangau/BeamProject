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
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import com.example.grace.myapplication.NavigationBarActivity;
import com.example.grace.myapplication.R;
import com.example.grace.messaging.ChatMessage;
import com.example.grace.messaging.MessageAdapter;
import com.example.grace.messaging.Message;

import java.util.ArrayList;
import java.util.List;

import com.example.grace.util.JsonUtil;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONObject;
import org.json.JSONException;

public class MessagingActivity extends AppCompatActivity {

    TextView message_text_view;
    Button message_button_view;
    Socket bSocket;
    private List<ChatMessage> chatMessages;
    private ArrayAdapter<ChatMessage> adapter;
    ListView MessagelistView;
    boolean isMine = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        chatMessages = new ArrayList<>();
        super.onCreate(savedInstanceState);
        {
            // Open the socket
            try {
                bSocket = IO.socket("http://10.13.151.202:5000");

                System.out.println("Connection established");
            } catch (java.net.URISyntaxException e) {
                System.out.println("Connection not established");
            }
            //Connect to socket
            bSocket.connect();
            bSocket.on("message", onReceiving);
        }


        setContentView(R.layout.activity_messaging);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MessagelistView = (ListView) findViewById(R.id.list_msg);
        adapter = new MessageAdapter(this, R.layout.item_chat_left, chatMessages);
        MessagelistView.setAdapter(adapter);


        message_button_view = (Button) findViewById(R.id.message_button);
        message_text_view = (TextView) findViewById(R.id.message_input);

        //Get message target
        String first_name = getIntent().getStringExtra("FIRST_NAME");


        //Change button to display who recipient is if it has already been selected
        if (first_name != null ) {
            message_button_view.setText("Send to " + first_name);
        }

        
        message_button_view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Get the text from the input and save in message
                String toPrint = message_text_view.getText().toString();
                Message msg = new Message("Grace", 2, toPrint);
                chatMessages.add(new ChatMessage(toPrint, isMine));
                adapter.notifyDataSetChanged();
                message_text_view.setText("");
                //      Turn the data in a JSON object
                String jsonMessage = JsonUtil.covertJavaToJson(msg);
                //      Send the message as a JSON object
                bSocket.send(jsonMessage);
            }
        });
        setupActionBar();
    }

    //Emitter code sourced from https://github.com/nkzawa/socket.io-android-chat, adapted for this project
    private Emitter.Listener onReceiving = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            System.out.println("Inside Emitter");
            JSONObject data;
            String message;

            try {
                //Get the message from JSON data and save in messages array
                data = new JSONObject((String) args[0]);
                message = data.getString("message");
                chatMessages.add(new ChatMessage(message, !isMine));
//                System.out.println(message);
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                return;
            }

        }
    };


    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the back button in the action bar.
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        bSocket.disconnect();
        bSocket.off("message", onReceiving);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(MessagingActivity.this, NavigationBarActivity.class);
        startActivity(intent);
        finish();
        return true;

    }


//Add button listening here


}