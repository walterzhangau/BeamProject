package com.example.grace.messaging;

import com.example.grace.util.JsonUtil;

/**
 * Created by haziq on 9/09/17.
 */

public class createAndSendMessage {


    public static void main(String[] args){
        Message msg = new Message(200, 300, "hello");
        String jsonMessage = JsonUtil.covertJavaToJson(msg);
        System.out.println(jsonMessage);
    }
}