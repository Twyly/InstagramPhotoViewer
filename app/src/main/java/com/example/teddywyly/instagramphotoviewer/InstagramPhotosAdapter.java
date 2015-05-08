package com.example.teddywyly.instagramphotoviewer;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by teddywyly on 5/5/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        InstagramPhoto photo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        TextView tvCaption = (TextView)convertView.findViewById(R.id.tvCaption);
        TextView tvUsername = (TextView)convertView.findViewById(R.id.tvUsername);
        TextView tvLikes = (TextView)convertView.findViewById(R.id.tvLikes);
        TextView tvTimestamp = (TextView)convertView.findViewById(R.id.tvTimestamp);
        Button btnComment = (Button)convertView.findViewById(R.id.btnComment);
        ImageView ivPhoto = (ImageView)convertView.findViewById(R.id.ivPhoto);
        ImageView ivProfile = (ImageView)convertView.findViewById(R.id.ivProfile);
        LinearLayout llComments = (LinearLayout)convertView.findViewById(R.id.llComments);

        tvCaption.setText(Html.fromHtml(formattedUsernameText(photo.username) + " " + formattedCaptionText(photo.caption)));

        tvUsername.setText(photo.username);
        tvLikes.setText(photo.likesCount + " likes");
        tvTimestamp.setText(timestampText(photo.timestamp));
        btnComment.setText("view all " + photo.commentCount + " comments");

        llComments.removeAllViews();
        // Add comments here
        for (int i=0; i< Math.min(3, photo.commentCount); i++) {
            InstagramComment comment = photo.comments.get(i);
            View line = LayoutInflater.from(getContext()).inflate(R.layout.item_photo_comment, parent, false);
            TextView tvComment = (TextView)line.findViewById(R.id.tvComment);
            tvComment.setText(comment.text);
            llComments.addView(line);
        }

        ivPhoto.setImageResource(0);
        ivProfile.setImageResource(0);
        Picasso.with(getContext()).load(photo.imageURL).into(ivPhoto);
        Picasso.with(getContext()).load(photo.profileURL).fit().transform(circleTransformationForImageView(ivProfile)).into(ivProfile);

        return convertView;

    }

    private String formattedUsernameText(String username) {
        return "<font color=" + getContext().getResources().getColor(R.color.themeblue) + ">" + username + "</font>";
    }

    private String formattedCaptionText(String caption) {

        //caption.replaceAll("\\b#|@\\w*\\b", "<font color=" + getContext().getResources().getColor(R.color.themeblue) + ">" + "$1" + "</font>");
        return "<font color=" + getContext().getResources().getColor(R.color.themelightgray) + ">" + caption + "</font>";
    }

    private Transformation circleTransformationForImageView(ImageView view) {
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(25)
                .oval(false)
                .build();
        return transformation;
    }

    private String timestampText(long timestamp) {
        return DateUtils.getRelativeTimeSpanString(timestamp*1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
    }


}
