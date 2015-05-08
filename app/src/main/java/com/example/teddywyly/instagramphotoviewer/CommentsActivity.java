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
    private InstagramCommentsAdapter aComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        comments = new ArrayList<>();
        aComments = new InstagramCommentsAdapter(this, comments);

        ListView lvComments = (ListView)findViewById(R.id.lvComments);
        lvComments.setAdapter(aComments);

        String mediaID = getIntent().getStringExtra("mediaID");
        Log.d("Pass", mediaID);

        fetchComments(mediaID);
    }

    public void fetchComments(String mediaID) {
        String url = "https://api.instagram.com/v1/media/" + mediaID + "/comments?client_id=" + CLIENT_ID;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler(){

            //on success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("DEBUG", response.toString());
                aComments.clear();

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
                        comments.add(comment);
                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                }

                aComments.notifyDataSetChanged();
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
