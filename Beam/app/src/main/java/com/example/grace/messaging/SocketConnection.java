package com.example.grace.messaging;
import com.example.grace.util.JsonUtil;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
/**
 * Created by haziq on 9/09/17.
 * Edited by Grace on 16/09/17
 */
public class SocketConnection {
    public Socket bSocket;
    SocketConnection(Socket bSocket){
        this.bSocket = bSocket;
    }

    {
//      Open the socket
        try {
            bSocket = IO.socket("http://127.0.0.1:5000");
        }
        catch (java.net.URISyntaxException e)
        {}
    }
}
