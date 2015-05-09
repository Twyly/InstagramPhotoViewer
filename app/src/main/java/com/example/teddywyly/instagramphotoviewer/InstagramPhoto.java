package com.example.teddywyly.instagramphotoviewer;

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
}
