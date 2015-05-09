package com.example.teddywyly.instagramphotoviewer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by teddywyly on 5/8/15.
 */
public class InstagramComment implements Serializable {
    private static final long serialVersionUID = 5177222050535313633L;
    public String username;
    public String text;
    public String profileURL;
    public long timestamp;

    //Constructors
    public InstagramComment(JSONObject commentJSON) {
        try {
            username = commentJSON.getJSONObject("from").getString("username");
            profileURL = commentJSON.getJSONObject("from").getString("profile_picture");
            text = commentJSON.getString("text");
            timestamp = commentJSON.getLong("created_time");
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
}
