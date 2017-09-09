package main.java.com.example.grace.myapplication;
import java.sql.Timestamp;
import java.util.Date;
/**
 * Created by haziq on 7/09/17.
 * this is a template for the message class to be used in our in-app chat
 */

public class Message {

    private int senderID;
    private int receiverID;
    private String message;
    private Timestamp timestamp;


    public Message(int senderID, int receiverID, String message, Timestamp timestamp){
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.message = message;
        this.timestamp = timestamp;
    }

    public static void SendMessage(String message, int userID){
        //SEND THIS MESSAGING SHIT TO
        //THIS USER ID

    }
}


