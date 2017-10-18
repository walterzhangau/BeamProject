package com.example.grace.servercommunication;

import org.json.JSONObject;

/**
 * Created by evanl on 3/10/2017.
 */

public class JSONResponse {
    public static JSONObject response = null;
    public static String JSON = null;

   public static void SetResponse(String JSONresponse){
        try {

            JSON = JSONresponse;
            response = new JSONObject(JSONresponse);
        }
            catch(Exception e){}

    }
}
