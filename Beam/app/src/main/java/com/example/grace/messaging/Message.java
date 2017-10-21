package com.example.grace.messaging;


/**
 * Created by haziq on 7/09/17.
 * Message information to be delivered to server
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


