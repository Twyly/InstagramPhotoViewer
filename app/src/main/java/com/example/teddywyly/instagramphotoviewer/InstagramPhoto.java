package com.example.teddywyly.instagramphotoviewer;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by teddywyly on 5/5/15.
 */
public class InstagramPhoto implements Serializable {

    private static final long serialVersionUID = 5177222050535318633L;
    public String username;
    public String caption;
    public String imageURL;
    public String videoURL;
    public String profileURL;
    public int imageHeight;
    public int imageWidth;
    public int likesCount;
    public int commentCount;
    public long timestamp;
    public String mediaID;
    public ArrayList<InstagramComment> comments;


    // Constructors
    public InstagramPhoto(JSONObject photoJSON) {

        try {
            username = photoJSON.getJSONObject("user").getString("username");
            profileURL = photoJSON.getJSONObject("user").getString("profile_picture");
            caption = photoJSON.getJSONObject("caption").getString("text");
            imageURL = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
            imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
            imageWidth = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("width");
            likesCount = photoJSON.getJSONObject("likes").getInt("count");
            timestamp = photoJSON.getLong("created_time");
            commentCount = photoJSON.getJSONObject("comments").getInt("count");
            mediaID = photoJSON.getString("id");
            // Possible Video
            JSONObject videoJSON = photoJSON.optJSONObject("videos");
            if (videoJSON != null) {
                videoURL = videoJSON.optJSONObject("standard_resolution").optString("url");
            }
            comments = new ArrayList<>();
            JSONArray commentsJSON = photoJSON.getJSONObject("comments").getJSONArray("data");
            for (int j = 0; j < commentsJSON.length(); j++) {
                JSONObject commentJSON = commentsJSON.getJSONObject(j);
                Log.i("JSON", commentJSON.toString());
                InstagramComment comment = new InstagramComment(commentJSON);
//                comment.username = commentJSON.getJSONObject("from").getString("username");
//                comment.profileURL = commentJSON.getJSONObject("from").getString("profile_picture");
//                comment.text = commentJSON.getString("text");
//                comment.timestamp = commentJSON.getLong("created_time");
                comments.add(comment);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
