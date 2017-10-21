package com.example.grace.servercommunication;

import org.json.JSONObject;

/**
 * Created by evanl on 3/10/2017.
 */

public class JSONResponse {
    /***
     *
     * ***/
    public static JSONObject response = null;

    static void SetResponse(String JSONResponse) {
        try {
            response = new JSONObject(JSONResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
