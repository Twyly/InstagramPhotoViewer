package com.example.teddywyly.instagramphotoviewer;

import android.content.res.Resources;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.format.DateUtils;

/**
 * Created by teddywyly on 5/8/15.
 */
public class HTMLTextFormatter {

    private Resources resources;

    public HTMLTextFormatter(Resources newResources) {
        resources = newResources;
    }

    public Spanned usernameSpanned(String username) {
        return Html.fromHtml("<font color=" + resources.getColor(R.color.themeblue) + ">" + username + "</font>");
    }

    public Spanned captionSpanned(String caption) {
        // On the look out for a better regex
        String regex = "(@\\S*|#\\S*)";
        return Html.fromHtml(caption.replaceAll(regex, "<font color=" + resources.getColor(R.color.themeblue) + ">" + "$1" + "</font>"));
    }

    public String timestampText(long timestamp) {
        String systemDateString = DateUtils.getRelativeTimeSpanString(timestamp * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        return systemDateString;
        //return systemDateString.replaceAll("ago|\\s+","").replaceAll();
    }
}
