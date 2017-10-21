package com.example.grace.myapplication;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RunWith(MockitoJUnitRunner.class)
public class ServerConnectionTest {
    /**
     * Test Connection with server
     */

    private static final String TAG = "ServerConnectionTest";
    private final String URL_START = "http://";
    private final String COLON = ":";
    private final String URL_END = "/";


    private final String IP = "10.13.152.154";
    private final String PORT = "4444";

    ArrayList<String> Keys;
    ArrayList<String> KeyTags;
    int len;

    private final String SERVER_URL = URL_START + IP + COLON + PORT + URL_END;


    @Mock
    Context context;


    @Test
    public void testServer() {

        int option = 1;
        Keys = new ArrayList<>();
        Keys.add("evan@gmail.com");
        KeyTags = new ArrayList<>();
        KeyTags.add("email");
        len = 1;



        final RequestQueue MyRequestQueue = Volley.newRequestQueue(context);


        String urlAffix;
        switch (option) {
            case 1:
                urlAffix = "GetUsername";
                break;
            case 2:
                urlAffix = "ListFriends";
                break;
            default:
                Log.e(TAG, "No option");
                return;


        }


        String url = SERVER_URL + urlAffix;


        Log.e(TAG, "The url is " + url);
        Log.e(TAG, "The url affix is " + urlAffix);

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Response is\n" + response);

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
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
                    Log.e(TAG, "Status Code is \n" + statusCode);
                }
            }


        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                Log.e(TAG, "Error in connecting to server");
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
                    params.put(KeyTags.get(i), Keys.get(i));
                    System.out.println(params);
                }

                return params;
            }
        };


        MyRequestQueue.add(MyStringRequest);
    }




}