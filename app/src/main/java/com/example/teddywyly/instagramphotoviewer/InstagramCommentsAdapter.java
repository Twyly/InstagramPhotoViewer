package com.example.teddywyly.instagramphotoviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by teddywyly on 5/8/15.
 */
public class InstagramCommentsAdapter extends BaseAdapter {

    private static final int TYPE_COMMENT = 0;
    private static final int TYPE_LOAD_MORE = 1;

    private HTMLTextFormatter formatter;
    private Context context;
    private List<InstagramComment> comments;
    private InstagramPhoto photo;

    private static class ViewHolder {
        // For Comments
        TextView username;
        TextView comment;
        TextView time;
        ImageView profile;
        // For Load More
//        TextView load;
    }


    public InstagramCommentsAdapter(Context newContext, List<InstagramComment> objects, InstagramPhoto newPhoto) {
        photo = newPhoto;
        comments = objects;
        context = newContext;
        formatter = new HTMLTextFormatter(context.getResources());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int type = getItemViewType(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            switch (type) {
                case TYPE_COMMENT:
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
                    viewHolder.username = (TextView)convertView.findViewById(R.id.tvUsername);
                    viewHolder.comment = (TextView)convertView.findViewById(R.id.tvComment);
                    viewHolder.time = (TextView)convertView.findViewById(R.id.tvTime);
                    viewHolder.profile = (ImageView)convertView.findViewById(R.id.ivProfile);
                    break;
                case TYPE_LOAD_MORE:
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_load_comments, parent, false);
                    TextView tvLoad = (TextView)convertView.findViewById(R.id.tvLoad);
                    tvLoad.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Load More Comments
                            ((CommentsActivity)context).releaseComments(false);
                        }
                    });
                    break;

            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        switch (type) {
            case TYPE_COMMENT:
                if (position == 0) {
                    viewHolder.username.setText(formatter.usernameSpanned(photo.username));
                    viewHolder.comment.setText(formatter.captionSpanned(photo.caption));
                    viewHolder.time.setText(formatter.timestampText(photo.timestamp));
                    viewHolder.profile.setImageResource(0);
                    Picasso.with(context).load(photo.profileURL).fit().transform(circleTransformationForImageView(viewHolder.profile)).into(viewHolder.profile);
                } else {
                    InstagramComment comment = (InstagramComment)getItem(position);
                    viewHolder.username.setText(formatter.usernameSpanned(comment.username));
                    viewHolder.comment.setText(formatter.captionSpanned(comment.text));
                    viewHolder.time.setText(formatter.timestampText(comment.timestamp));
                    viewHolder.profile.setImageResource(0);
                    Picasso.with(context).load(comment.profileURL).fit().transform(circleTransformationForImageView(viewHolder.profile)).into(viewHolder.profile);
                }
                break;
            case TYPE_LOAD_MORE:

                break;

        }
        return convertView;
    }

    @Override
    public int getCount() {
        return comments.size()+2;
    }

    @Override
    public Object getItem(int i) {
        if (i == 0) {
            return photo;
        } else if (i == 1) {
            return R.string.loadComments;
        } else {
            return comments.get(i-2);
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 1) {
            return TYPE_LOAD_MORE;
        } else {
            return TYPE_COMMENT;
        }
    }

    private Transformation circleTransformationForImageView(ImageView view) {
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(25)
                .oval(false)
                .build();
        return transformation;
    }
}
