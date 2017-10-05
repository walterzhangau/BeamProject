package com.example.grace.servercommunication;

import org.json.JSONObject;

/**
 * Created by evanl on 3/10/2017.
 */

public class JSONResponse {
    public static JSONObject response = null;

   public static void SetResponse(String JSONresponse){
        try {


            response = new JSONObject(JSONresponse);
        }
            catch(Exception e){}

    }
}
