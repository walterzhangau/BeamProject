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

/**
 * Created by evanl on 2/10/2017.
 */

public class ServerConnection {



    public String makeServerRequest(String urlAffix, final ArrayList<String> keyTags, final ArrayList<String> Keys, final int len, android.content.Context context) {


        // Store values at the time of the login attempt.

        final RequestQueue MyRequestQueue = Volley.newRequestQueue(context);

        String url = "http://10.0.2.2:5000/" + urlAffix;

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
                    JSONResponse.SetResponse(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                String statusCode = null;
                try {
                    statusCode = obj.getString("StatusCode");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(statusCode);
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
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                for(int i = 0; i < len; i ++) {
                    params.put(keyTags.get(i), Keys.get(i));
                    System.out.println(params);
                }
                return params;
            }
        };


        MyRequestQueue.add(MyStringRequest);
        return "";
    }

}