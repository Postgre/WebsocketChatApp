package com.calenaur.chat.websocket;

import com.calenaur.chat.activity.WebsocketActivity;
import com.calenaur.chat.json.Message;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

public class Client extends WebSocketClient {

    public static final int DISCONNECTED = 0;
    public static final int CONNECTED = 1;
    public static final int AUTHORIZED = 2;

    private WebsocketActivity activity;
    private boolean authorized = false;

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

    public void sendMessage(String msg){
        try {
            Message message = new Message("message");
            message.addField("message", msg);
            send(message.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(String s) {
        try {
            JSONObject json = new JSONObject(s);
            if(json.has("method") && json.has("data") && json.has("status") && json.has("status_code")){
                if(authorized){
                    if(json.getString("method").equalsIgnoreCase("message") && json.getInt("status_code") == 1){
                        JSONObject data = json.getJSONObject("data");
                        if(data.has("message")){
                            activity.addToLog(data.getString("message"));
                        }
                    }
                }else{
                    if(json.getString("method").equalsIgnoreCase("auth") && json.getInt("status_code") == 1){
                        authorized = true;
                        setState(AUTHORIZED);
                    }
                }
            }
        } catch (JSONException e) {
        }
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

    public void authorize(String username, String password){
        try {
            Message message = new Message("auth");
            message.addField("username", username);
            message.addField("password", password);
            send(message.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
