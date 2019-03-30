package com.example.travelapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.VideoView;
import android.os.Handler;

public class UserGuide extends AppCompatActivity {
    public static final String TAG = "Videoplayer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_guide);
        final VideoView vv = (VideoView) this.findViewById(R.id.videoView);
        final String uri = "android.resource://" + getPackageName() + "/" + R.raw.user;

        vv.setVideoURI(Uri.parse(uri));
        vv.start();
        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);

            }
        });

        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                vv.setVideoURI(Uri.parse(uri));
                vv.start();

            }
        });
        Button btnSkip=(Button)findViewById(R.id.btn_skip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(UserGuide.this,MainActivity.class);
                startActivity(i);
            }
        });
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
           public void run() {
                Intent in = new Intent(UserGuide.this, MainActivity.class);
                            startActivity(in);
                           }
                           }, 28000);
    }
}
