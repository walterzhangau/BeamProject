package com.example.grace.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.grace.servercommunication.*;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AccountCreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
        setContentView(R.layout.activity_account_creation);
        SetUpCreateAccountButton();

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
        Intent intent = new Intent(AccountCreation.this, NavigationBarActivity.class);
        startActivity(intent);
        finish();
        return true;

    }


    private void SetUpCreateAccountButton(){
        //On pressing submit for account creation

        final Button CreateAccountButton   = (Button)   findViewById(R.id.accountCreation_Button);

        final TextView email_input = (TextView) findViewById(R.id.accountCreation_Email);
        final TextView username_input = (TextView) findViewById(R.id.accountCreation_Username);
        final TextView password1_input = (TextView) findViewById(R.id.accountCreation_Password1);
        TextView password2_input = (TextView) findViewById(R.id.accountCreation_Password2);

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = username_input.getText().toString();
                String email = email_input.getText().toString();
                String password = password1_input.getText().toString();


                ArrayList<String> KeyTags = new ArrayList<String>();
                ArrayList<String> Keys = new ArrayList<String>();

                KeyTags.add("username");
                KeyTags.add("email");
                KeyTags.add("password");

                Keys.add(username);
                Keys.add(email);
                Keys.add(password);


                CreateAccount(KeyTags, Keys);



                //Write code here (Use "email_input.gettext")

            }
        });
    }



    public void CreateAccount(ArrayList<String> KeyTags, ArrayList<String> Keys){


        ServerConnection serverConnection = new ServerConnection();
        serverConnection.makeServerRequest("CreateUser", KeyTags, Keys, 3, this);

    }

}