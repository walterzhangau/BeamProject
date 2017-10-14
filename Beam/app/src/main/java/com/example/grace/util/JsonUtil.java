package com.example.grace.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by haziq on 9/09/17.
 */

public class JsonUtil implements Serializable{
    private static ObjectMapper mapper;
    static{
        mapper = new ObjectMapper();
    }
    public static String covertJavaToJson(Object object){
        String jsonResult = "";
        try{
            jsonResult = mapper.writeValueAsString(object);
        }
        catch(JsonGenerationException e){
            e.printStackTrace();
        }
        catch(JsonMappingException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return jsonResult;
    }

}
