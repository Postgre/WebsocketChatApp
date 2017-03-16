package com.calenaur.chat.websocket;

import android.support.v7.app.AppCompatActivity;

import com.calenaur.chat.activity.WebsocketActivity;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class Client extends WebSocketClient {

    public static final int DISCONNECTED = 0;
    public static final int CONNECTED = 1;

    private WebsocketActivity activity;

    public Client(URI serverURI) {
        super(serverURI);
    }

    public Client(URI serverURI, WebsocketActivity activity) {
        this(serverURI);
        this.activity = activity;
    }

    public void setActivity(WebsocketActivity activity){
        this.activity = activity;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        setState(CONNECTED);
    }

    @Override
    public void onMessage(String s) {

    }

    @Override
    public void onClose(int i, String s, boolean b) {
        setState(DISCONNECTED);
    }

    @Override
    public void onError(Exception e) {
        setState(DISCONNECTED);
    }

    public void setState(int state){
        if(activity != null){
            activity.setState(state);
        }
    }
}
