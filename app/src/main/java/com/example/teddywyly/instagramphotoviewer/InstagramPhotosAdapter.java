package com.example.teddywyly.instagramphotoviewer;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.w3c.dom.Text;

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
        ImageView ivPhoto = (ImageView)convertView.findViewById(R.id.ivPhoto);
        ImageView ivProfile = (ImageView)convertView.findViewById(R.id.ivProfile);

        tvCaption.setText(photo.caption);
        tvUsername.setText(photo.username);
        tvLikes.setText(photo.likesCount + "likes");
        tvTimestamp.setText(timestampText(photo.timestamp));
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.imageURL).into(ivPhoto);

        Picasso.with(getContext()).load(photo.profileURL).fit().transform(circleTransformationForImageView(ivProfile)).into(ivProfile);

        return convertView;

    }

    private Transformation circleTransformationForImageView(ImageView view) {
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(25)
                .oval(false)
                .build();
        return transformation;
    }

    private String timestampText(int timestamp) {


        return Integer.toString(timestamp);
    }


}
