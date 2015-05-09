package com.example.teddywyly.instagramphotoviewer;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CommentsActivity extends ActionBarActivity {

    public static final String CLIENT_ID = "fcbc8aead3684d019846b1040e4ed6bc";
    private ArrayList<InstagramComment> comments;
    private ArrayList<InstagramComment> holdComments;
    private InstagramCommentsAdapter aComments;
    private ListView lvComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        InstagramPhoto photo = (InstagramPhoto)getIntent().getSerializableExtra("photo");
        holdComments = new ArrayList<>();
        comments = new ArrayList<>();
        aComments = new InstagramCommentsAdapter(this, comments, photo);

        lvComments = (ListView)findViewById(R.id.lvComments);
        lvComments.setAdapter(aComments);

        fetchComments(photo.mediaID);
    }

    public void releaseComments(Boolean scrollBottom) {

        ArrayList<InstagramComment> batch = new ArrayList<>();

        int finalIndex = holdComments.size()-1;
        for (int i=finalIndex; i>=Math.max(0, finalIndex-20); i--) {
            comments.add(0, holdComments.get(i));
            holdComments.remove(i);
        }

        aComments.notifyDataSetChanged();

        if (scrollBottom) {
            lvComments.setSelection(aComments.getCount() - 1);
        }
    }

    public void fetchComments(String mediaID) {
        String url = "https://api.instagram.com/v1/media/" + mediaID + "/comments?client_id=" + CLIENT_ID;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler(){

            //on success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("DEBUG", response.toString());
                //aComments.clear();

                JSONArray commentsJSON = null;
                try {
                    commentsJSON = response.getJSONArray("data");
                    for (int i=0; i<commentsJSON.length(); i++) {
                        JSONObject commentJSON = commentsJSON.getJSONObject(i);
                        InstagramComment comment = new InstagramComment();
                        comment.text = commentJSON.getString("text");
                        comment.timestamp = commentJSON.getLong("created_time");
                        comment.username = commentJSON.getJSONObject("from").getString("username");
                        comment.profileURL = commentJSON.getJSONObject("from").getString("profile_picture");
                        holdComments.add(comment);
                    }

                    releaseComments(true);

                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }


            //on failure
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //swipeContainer.setRefreshing(false);
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comments, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
