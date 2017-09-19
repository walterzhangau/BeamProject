package com.example.grace.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AccountCreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);
        SetUpCreateAccountButton();
    }


    private void SetUpCreateAccountButton(){
    //On pressing submit for account creation

        Button CreateAccountButton   = (Button)   findViewById(R.id.accountCreation_Button);

        final TextView email_input = (TextView) findViewById(R.id.accountCreation_Email);
        TextView username_input = (TextView) findViewById(R.id.accountCreation_Username);
        TextView password1_input = (TextView) findViewById(R.id.accountCreation_Password1);
        TextView password2_input = (TextView) findViewById(R.id.accountCreation_Password2);

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Write code here (Use "email_input.gettext")

            }
        });
    }
}
