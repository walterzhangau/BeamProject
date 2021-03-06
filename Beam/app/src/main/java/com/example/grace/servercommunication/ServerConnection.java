package com.example.grace.servercommunication;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ServerConnection {
    /**
     * The app's communication with server
     * Sends request with given values to given address
     * Receives response and stores in JSONResponse
     */

    private static final String TAG = "ServerConnection";
    private final String URL_START = "http://";
    private final String COLON = ":";
    private final String URL_END = "/";


    private final String IP = "10.13.152.154";
    private final String PORT = "4444";


    private final String SERVER_URL = URL_START + IP + COLON + PORT + URL_END;

    // Sends a post to the server
    //url affix, appened to the url of the server, e.g "Login", "CreateUser"
    //KeyTags, unorderered array of keys for server e.g. "email", "friendstatus", location
    //Keys, values of keys e.g. "evan@gmail.com", "pending", "123.1.24.2."
    //len, length of keytags and keys arrays
    //ignore_response, if false the response is stored in JSONResponse
    public void makeServerRequest(String urlAffix, final ArrayList<String> keyTags, final ArrayList<String> Keys, final int len, android.content.Context context, final Boolean ignore_response) {

        // Store values at the time of the login attempt.

        final RequestQueue MyRequestQueue = Volley.newRequestQueue(context);


        String url = SERVER_URL + urlAffix;

        System.out.println("The url is " + url);
        System.out.println("the affix is " + urlAffix);

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Response Received");
                System.out.println(response);

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    if (!ignore_response) {
                        JSONResponse.SetResponse(response);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String statusCode = null;
                try {
                    statusCode = obj.getString("StatusCode");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (statusCode != null) {
                    System.out.println(statusCode);
                }
            }


        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                System.out.println("Error in connecting to server");
                if (error.getMessage() != null) {
                    Log.e("VOLLEY", error.getMessage());
                }
                if (error.networkResponse != null) {
                    Log.e("VOLLEY", error.networkResponse.toString());
                }
            }
        })

        {
            @Override
            protected Map<String, String> getParams() {
                Log.e(TAG, "getParams");
                Map<String, String> params = new HashMap<>();
                for (int i = 0; i < len; i++) {
                    params.put(keyTags.get(i), Keys.get(i));
                    System.out.println(params);
                }

                return params;
            }
        };


        MyRequestQueue.add(MyStringRequest);
    }

}