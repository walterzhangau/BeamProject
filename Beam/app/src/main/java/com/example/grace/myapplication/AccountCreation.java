package com.example.grace.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.grace.servercommunication.*;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AccountCreation extends AppCompatActivity {

    final String USERCREATED = "User creation success";
    final String PASSWORDMISMATCH = "Passwords must match";

    TextView email_input;
    AccountCreationTask ACtask;

    //Main view
    View ACform;

    // View for loading screen
    View ACprogress;


    //Tells user account was created
    View AccountCreatedDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setupActionBar();
        setContentView(R.layout.activity_account_creation);
        setViews();
        SetUpCreateAccountButton();

    }

    private void setViews(){
        ACform = findViewById(R.id.AccountCreation_form);
        ACprogress = findViewById(R.id.AccountCreation_progress);

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
        Intent intent = new Intent(AccountCreation.this, LoginActivity.class);
        startActivity(intent);
        finish();
        return true;

    }


    private void SetUpCreateAccountButton(){
        //On pressing submit for account creation

        final Button CreateAccountButton   = (Button)   findViewById(R.id.accountCreation_Button);

        email_input = (TextView) findViewById(R.id.accountCreation_Email);
        final TextView username_input = (TextView) findViewById(R.id.accountCreation_Username);
        final TextView password1_input = (TextView) findViewById(R.id.accountCreation_Password1);
        final TextView password2_input = (TextView) findViewById(R.id.accountCreation_Password2);

        AccountCreatedDisplay = findViewById(R.id.AccountCreation_display);

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = username_input.getText().toString();
                String email = email_input.getText().toString();
                String password1 = password1_input.getText().toString();
                String password2 = password2_input.getText().toString();


                if (password1.equals(password2)) {
                    ArrayList<String> KeyTags = new ArrayList<String>();
                    ArrayList<String> Keys = new ArrayList<String>();

                    KeyTags.add("username");
                    KeyTags.add("email");
                    KeyTags.add("password");

                    Keys.add(username);
                    Keys.add(email);
                    Keys.add(password1);


                    CreateAccount(KeyTags, Keys);
                }else{
                    password1_input.setText("");
                    password2_input.setText("");

                    password1_input.setHint(PASSWORDMISMATCH);
                    password2_input.setHint(PASSWORDMISMATCH);

                }


            }
        });
    }



    public void CreateAccount(ArrayList<String> KeyTags, ArrayList<String> Keys){


        ServerConnection serverConnection = new ServerConnection();
        serverConnection.makeServerRequest("CreateUser", KeyTags, Keys, 3, this, false);
        ACtask = new AccountCreationTask();
        ACtask.execute();

    }




    public class AccountCreationTask extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                while(JSONResponse.response == null) {
                    Thread.sleep(20);
                    Log.e("Wating", "waiting");
                }

                if (JSONResponse.response.getString("Message").equals(USERCREATED)){
                    return true;

                }
                else{
                    return false;
                }



            }catch(Exception e){e.printStackTrace();}

            return false;
        }


        @Override
        protected void onPostExecute(final Boolean success) {

            if(success){
                AccountCreatedDisplay.setVisibility(View.VISIBLE);

            }
            else{
                email_input.setText("USERNAME TAKEN");

            }

            ACtask = null;
            showProgress(false);
            JSONResponse.response = null;
        }

        @Override
        protected void onCancelled() {
            ACtask = null;
            showProgress(false);
        }
    }


    /**
     * Shows the progress UI and hides the friendslist form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            ACform.setVisibility(show ? View.GONE : View.VISIBLE);
            ACform.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    ACform.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            ACprogress.setVisibility(show ? View.VISIBLE : View.GONE);
            ACprogress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    ACprogress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });




        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            ACprogress.setVisibility(show ? View.VISIBLE : View.GONE);
            ACform.setVisibility(show ? View.GONE : View.VISIBLE);

        }
    }
}