package com.example.teddywyly.instagramphotoviewer;

import android.content.Context;
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
 * Created by teddywyly on 5/8/15.
 */
public class InstagramCommentsAdapter extends ArrayAdapter<InstagramComment> {


    public InstagramCommentsAdapter(Context context, List<InstagramComment> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramComment comment = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
        }

        TextView tvUsername = (TextView)convertView.findViewById(R.id.tvUsername);
        TextView tvComment = (TextView)convertView.findViewById(R.id.tvComment);
        TextView tvTime = (TextView)convertView.findViewById(R.id.tvTime);
        ImageView ivProfile = (ImageView)convertView.findViewById(R.id.ivProfile);

        tvUsername.setText(comment.username);
        tvComment.setText(comment.text);
        tvTime.setText(comment.timestamp + "");

        ivProfile.setImageResource(0);
        Picasso.with(getContext()).load(comment.profileURL).fit().transform(circleTransformationForImageView(ivProfile)).into(ivProfile);

        return convertView;
    }

    private Transformation circleTransformationForImageView(ImageView view) {
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(25)
                .oval(false)
                .build();
        return transformation;
    }
}
