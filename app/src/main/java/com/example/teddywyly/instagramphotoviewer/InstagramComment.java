package com.example.teddywyly.instagramphotoviewer;

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
}
