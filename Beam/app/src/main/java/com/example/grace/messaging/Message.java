package com.example.grace.messaging;


/**
 * Created by haziq on 7/09/17.
 * this is a template for the message class to be used in our in-app chat
 */

public class Message {

    private String senderID;
    private String receiverID;
    private String messageBody;


    public Message(String senderID, String receiverID, String messageBody){
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.messageBody = messageBody;
    }

    public static void SendMessage(String messageBody, int userID){
        //SEND THIS MESSAGING SHIT TO
        //THIS USER ID

    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public String getMessage() {
        return messageBody;
    }

    public void setMessage(String message) {
        this.messageBody = messageBody;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

}


