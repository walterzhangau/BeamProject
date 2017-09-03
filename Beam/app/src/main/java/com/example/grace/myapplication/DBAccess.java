package com.example.grace.myapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by thomas on 3/09/2017.
 */

public class DBAccess {
    String hostName = "192.168.1.12";
    int portNumber = 8889;
    String fromServer, fromUser="sendMeData";
    String returnMsg;

    public DBAccess() {
        //do nothing
    }

    public DBAccess(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }

    //connect to server
    public void connect() {
        try {
            Socket echoSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));

            out.println(fromUser);
            while((fromServer = in.readLine()) != null){

                System.out.println(fromServer);
                //System.out.println("test");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
