package com.example.teddywyly.instagramphotoviewer;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
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

import java.util.List;

/**
 * Created by teddywyly on 5/5/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    private HTMLTextFormatter formatter;
    private static class ViewHolder {
        TextView caption;
        TextView username;
        TextView likes;
        TextView timestamp;
        TextView seeComments;
        ImageView photo;
        ImageView profile;
        LinearLayout comments;
    }

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        formatter = new HTMLTextFormatter(context.getResources());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final InstagramPhoto photo = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);

            viewHolder.caption = (TextView)convertView.findViewById(R.id.tvCaption);
            viewHolder.username = (TextView)convertView.findViewById(R.id.tvUsername);
            viewHolder.likes = (TextView)convertView.findViewById(R.id.tvLikes);
            viewHolder.timestamp = (TextView)convertView.findViewById(R.id.tvTime);
            viewHolder.seeComments = (TextView)convertView.findViewById(R.id.tvSeeComments);
            viewHolder.photo = (ImageView)convertView.findViewById(R.id.ivPhoto);
            viewHolder.profile = (ImageView)convertView.findViewById(R.id.ivProfile);
            viewHolder.comments = (LinearLayout)convertView.findViewById(R.id.llComments);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }


        viewHolder.caption.setText(TextUtils.concat(formatter.usernameSpanned(photo.username), " ", formatter.captionSpanned(photo.caption)));
        viewHolder.username.setText(photo.username);
        viewHolder.likes.setText("\u2665 " + photo.likesCount + " likes");
        viewHolder.timestamp.setText(formatter.timestampText(photo.timestamp));
        viewHolder.seeComments.setText("view all " + photo.commentCount + " comments");
        viewHolder.seeComments.setOnClickListener(null);
        viewHolder.seeComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("###", "Clicked");
                launchCommentsView(photo);
            }
        });
        viewHolder.comments.removeAllViews();
        // Add comments here
        for (int i=0; i< Math.min(3, photo.commentCount); i++) {
            InstagramComment comment = photo.comments.get(i);
            View line = LayoutInflater.from(getContext()).inflate(R.layout.item_photo_comment, parent, false);
            TextView tvComment = (TextView)line.findViewById(R.id.tvComment);
            tvComment.setText(TextUtils.concat(formatter.usernameSpanned(comment.username),  " ", formatter.captionSpanned(comment.text)));
            viewHolder.comments.addView(line);
        }

        viewHolder.photo.setImageResource(0);
        viewHolder.profile.setImageResource(0);
//        float ratio = (float)photo.imageWidth/photo.imageHeight;
//        Picasso.with(getContext()).load(photo.imageURL).resize(0., 0).into(ivPhoto);
        Picasso.with(getContext()).load(photo.imageURL).placeholder(R.drawable.lightgrayinstagram).into(viewHolder.photo);
        Picasso.with(getContext()).load(photo.profileURL).fit().transform(circleTransformationForImageView(viewHolder.profile)).into(viewHolder.profile);

        return convertView;

    }

    private void launchCommentsView(InstagramPhoto photo) {
        Intent i = new Intent(getContext(), CommentsActivity.class);
//        i.putExtra("mediaID", photo.mediaID);
        i.putExtra("photo", photo);
        getContext().startActivity(i);
    }

    private Transformation circleTransformationForImageView(ImageView view) {
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(25)
                .oval(false)
                .build();
        return transformation;
    }



}
