package com.example.grace.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.grace.ARchitect.SampleCamActivity;
import com.example.grace.UserInformation.UserCredentials;
import com.example.grace.servercommunication.*;

import org.json.JSONObject;

import java.util.ArrayList;


public class FriendsActivity extends AppCompatActivity {

    private final static String TAG = "FriendsActivity";
    //final int PENDING = 2;
    final int FRIEND = 3;
    final String NOFRIENDSERROR = "1002";
    final String REQUESTSENT = "Friend Request Sent";
    final String AFFIX_LIST_FRIENDS = "ListFriends";
    final String AFFIX_SEND_FRIEND_REQUEST = "SendFriendRequest";
    final String AFFIX_ACCEPT_FRIEND_REQUEST = "AcceptFriendRequest";
    final String USER = "user";
    final String EMAIL = "email";
    final String INTENT_TAG_MESSAGING = "MESSAGE_AUDIENCE";
    final String RESPONSE_TAG_MESSAGE = "Message";


    Button add_friend_button;
    TextView add_friend_input;


    private View mProgressView;
    private View mFriendsListForm;
    private FriendsListTask friendsListTask;
    private AddFriendTask addFriendTask;
    public String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email = UserCredentials.email;

        setContentView(R.layout.activity_friends);


        mProgressView = findViewById(R.id.Friends_progress);
        mFriendsListForm = findViewById(R.id.friend_list_form);
        setupFriendsTable();

        setupAddFriends();
        setupActionBar();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void setupFriendsTable() {


        //Request friends data


        ServerConnection serverConnection = new ServerConnection();


        ArrayList<String> Keys = new ArrayList<>();
        ArrayList<String> KeyTags = new ArrayList<>();
        Keys.add(email);
        KeyTags.add(EMAIL);

        serverConnection.makeServerRequest(AFFIX_LIST_FRIENDS, KeyTags, Keys, 1, this, false);


        showProgress(true);
        friendsListTask = new FriendsListTask();
        friendsListTask.execute();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void setupAddFriends() {
        add_friend_button = (Button) findViewById(R.id.add_friends_button);
        add_friend_input = (TextView) findViewById(R.id.add_friends_input);
        add_friend_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String add_friend_username = add_friend_input.getText().toString();

                add_friend_input.setText("");

                ArrayList<String> Keys = new ArrayList<>();
                ArrayList<String> KeyTags = new ArrayList<>();

                Keys.add(email);
                KeyTags.add(EMAIL);

                Keys.add(add_friend_username);
                KeyTags.add(USER);

                ServerConnection serverConnection = new ServerConnection();
                serverConnection.makeServerRequest(AFFIX_SEND_FRIEND_REQUEST, KeyTags, Keys, 2, FriendsActivity.this, false);

                showProgress(true);
                addFriendTask = new AddFriendTask();
                addFriendTask.execute();


            }
        });
    }


    public void drawFriendsTable() {
        String FirstName;
        int Status;

        ArrayList<String> friends = new ArrayList<>();
        String[] values;
        ArrayList<Integer> statuses = new ArrayList<>();

        JSONObject response = JSONResponse.response;
        try {
            String responseString = response.toString();
            responseString = responseString.replace("{", "");
            responseString = responseString.replace("}", "");
            responseString = responseString.replace("\"", "");
            responseString = responseString.replace(":", ",");
            responseString = responseString.replace("[", "");
            responseString = responseString.replace("]", "");
            values = responseString.split(",");
            int j;

            boolean good = true;
            if (values.length > 3) {
                if (values[3].equals(NOFRIENDSERROR)) {
                    good = false;

                }

            }

            if (good) {

                for (j = 0; j < values.length; j += 3) {
                    friends.add(values[j + 1]);
                    statuses.add(Integer.parseInt(values[j + 2]));
                }
            }
            System.out.println("The response for friends list is " + responseString);

        } catch (Exception e) {
            e.printStackTrace();

        }

        TableLayout tableLayoutA;
        tableLayoutA = (TableLayout) findViewById(R.id.friends_table);


        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);


        int i;
        for (i = 0; i < friends.size(); i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(lp);

            TextView qty = new TextView(this);

            FirstName = friends.get(i);
            Status = statuses.get(i);

            qty.setText(FirstName);


            row.addView(qty);
            row.addView(messageButton(FirstName, Status));
            row.addView(beamButton(FirstName, Status));
            row.addView(blockButton(FirstName, Status));
            tableLayoutA.addView(row, i);

        }

        TableRow header = new TableRow(this);
        header.setLayoutParams(lp);

        TextView username = new TextView(this);
        new TextView(this);
        TextView message = new TextView(this);
        TextView beam = new TextView(this);
        TextView map = new TextView(this);

        username.setText(getText(R.string.friends_list_header_name));
        message.setText(getText(R.string.friends_list_header_message));
        beam.setText(getText(R.string.friends_list_header_beam));
        map.setText(getText(R.string.friends_list_header_map));

        header.addView(username);
        header.addView(message);
        header.addView(beam);
        header.addView(map);
        tableLayoutA.addView(header, 0);


        JSONResponse.response = null;
    }


    //Button to open messaging for each friend
    private Button messageButton(final String FirstName, final int Status) {

        Button messageFriend;

        if (Status == FRIEND) {
            messageFriend = new Button(this);
            messageFriend.setText(R.string.message_button_text);
            messageFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(FriendsActivity.this, MessagingActivity.class);

                    // Inform messaging activity of recipients name
                    intent.putExtra(INTENT_TAG_MESSAGING, (FirstName));
                    startActivity(intent);

                }
            });
        } else {
            messageFriend = new Button(this);
            messageFriend.setText(R.string.friendRequest_button_text);
            messageFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ArrayList<String> Keys = new ArrayList<>();
                    ArrayList<String> KeyTags = new ArrayList<>();

                    Keys.add(email);
                    KeyTags.add(EMAIL);

                    Keys.add(FirstName);
                    KeyTags.add(USER);

                    ServerConnection serverConnection = new ServerConnection();
                    serverConnection.makeServerRequest(AFFIX_ACCEPT_FRIEND_REQUEST, KeyTags, Keys, 2, FriendsActivity.this, true);
                    finish();
                    startActivity(getIntent());

                }
            });

        }

        return messageFriend;

    }


    private Button beamButton(final String friend_username, int Status) {


        Button button = new Button(this);

        if (Status == FRIEND) {
            //button.setBackgroundResource(R.drawable.ic_beam_button);
            button.setText(R.string.beam_button_text);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(FriendsActivity.this, SampleCamActivity.class);
                    intent.putExtra(USER, friend_username);
                    startActivity(intent);
                }
            });

        }

        return button;

    }

    private Button blockButton(final String friend_username, int status) {


        Button button = new Button(this);
        if (status == FRIEND) {
            button.setText(R.string.block_button_text);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(FriendsActivity.this, MapsMarkerActivity.class);

                    intent.putExtra(USER, friend_username);
                    startActivity(intent);

                }
            });
        }
        return button;

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e(TAG, "onKeyDown");
        Log.e(TAG, Integer.toString(KeyEvent.KEYCODE_BACK));
        Log.e(TAG, "keyCode = " + Integer.toString(keyCode));
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return false;
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

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(FriendsActivity.this, NavigationBarActivity.class);
        startActivity(intent);
        finish();
        return true;

    }

    /**
     * Represents an asynchronous friendslist task used to authenticate
     * the user.
     */
    private class FriendsListTask extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                while (JSONResponse.response == null) {
                    Thread.sleep(20);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return true;

        }

        @Override
        protected void onPostExecute(final Boolean success) {

            drawFriendsTable();
            friendsListTask = null;
            showProgress(false);
        }

        @Override
        protected void onCancelled() {
            friendsListTask = null;
            showProgress(false);
        }
    }

    /**
     * Represents an asynchronous addfriend task used to authenticate
     * the user.
     */
    private class AddFriendTask extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                while (JSONResponse.response == null) {
                    Thread.sleep(20);
                    Log.e("Waiting", "waiting");
                }

                return (JSONResponse.response.getString(RESPONSE_TAG_MESSAGE).equals(REQUESTSENT));


            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }


        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {
                add_friend_input.setText(getText(R.string.friend_request_success));

            } else {
                add_friend_input.setText(getText(R.string.friend_request_failure));

            }

            addFriendTask = null;
            showProgress(false);
            JSONResponse.response = null;
        }

        @Override
        protected void onCancelled() {
            addFriendTask = null;
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
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mFriendsListForm.setVisibility(show ? View.GONE : View.VISIBLE);
        mFriendsListForm.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mFriendsListForm.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });

    }


}