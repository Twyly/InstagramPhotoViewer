package com.example.teddywyly.instagramphotoviewer;

import java.util.ArrayList;

/**
 * Created by teddywyly on 5/5/15.
 */
public class InstagramPhoto {
    public String username;
    public String caption;
    public String imageURL;
    public String profileURL;
    public int imageHeight;
    public int likesCount;
    public int commentCount;
    public long timestamp;
    public String mediaID;
    public ArrayList<InstagramComment> comments;
}
