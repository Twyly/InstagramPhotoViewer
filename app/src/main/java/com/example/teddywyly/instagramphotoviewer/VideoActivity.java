package com.example.teddywyly.instagramphotoviewer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

import java.net.URI;


public class VideoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        VideoView vvInstagram = (VideoView)findViewById(R.id.vvInstagram);
        String url = null;

        if (getIntent().getExtras() != null) {
            url = getIntent().getExtras().getString("url");
            if (url != null) {
                vvInstagram.setVideoPath(url);
                vvInstagram.setMediaController(new MediaController(this));
                vvInstagram.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        finish();
                    }
                });
                vvInstagram.start();
            }
        }

        if (url == null) {
            throw new IllegalArgumentException("Must set url parameter in intent");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_video, menu);
        return true;
    }

    public static void showRemoteVideo(Context context, String url) {
        Intent i = new Intent(context, VideoActivity.class);
        i.putExtra("url", url);
        context.startActivity(i);
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
