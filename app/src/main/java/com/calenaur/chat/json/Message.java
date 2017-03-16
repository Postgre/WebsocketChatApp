package com.calenaur.chat.json;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {

    private JSONObject json;
    private JSONObject data;

    public Message(String method) throws JSONException {
        json = new JSONObject();
        data = new JSONObject();
        json.put("method", method);
    }

    public void addField(String k, int v) throws JSONException {
        data.put(k, v);
    }

    public void addField(String k, String v) throws JSONException {
        data.put(k, v);
    }

    public void addField(String k, JSONObject v) throws JSONException {
        data.put(k, v);
    }

    @Override
    public String toString(){
        try {
            json.put("data", data);
            return json.toString();
        } catch (JSONException e) {
            return null;
        }
    }

}
