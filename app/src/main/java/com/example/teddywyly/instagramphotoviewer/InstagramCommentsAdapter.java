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

    private HTMLTextFormatter formatter;
    private static class ViewHolder {
        TextView username;
        TextView comment;
        TextView time;
        ImageView profile;
    }


    public InstagramCommentsAdapter(Context context, List<InstagramComment> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        formatter = new HTMLTextFormatter(context.getResources());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        InstagramComment comment = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.username = (TextView)convertView.findViewById(R.id.tvUsername);
            viewHolder.comment = (TextView)convertView.findViewById(R.id.tvComment);
            viewHolder.time = (TextView)convertView.findViewById(R.id.tvTime);
            viewHolder.profile = (ImageView)convertView.findViewById(R.id.ivProfile);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.username.setText(formatter.usernameSpanned(comment.username));
        viewHolder.comment.setText(formatter.captionSpanned(comment.text));
        viewHolder.time.setText(formatter.timestampText(comment.timestamp));

        viewHolder.profile.setImageResource(0);
        Picasso.with(getContext()).load(comment.profileURL).fit().transform(circleTransformationForImageView(viewHolder.profile)).into(viewHolder.profile);

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
