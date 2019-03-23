package com.example.travelapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Stop extends AppCompatActivity {

    public static final int TAKE_PHOTO = 101;
    private Button camera ;
    private Button words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        camera = (Button)findViewById(R.id.btn_camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Stop.this, PhotoWithWord.class);//也可以这样写intent.setClass(MainActivity.this, OtherActivity.class);

                Bundle bundle = new Bundle();
                bundle.putInt("id", TAKE_PHOTO);//使用显式Intent传递参数，用以区分功能
                intent.putExtras(bundle);

                Stop.this.startActivity(intent);//启动新的Intent
            }
        });
        words = (Button)findViewById(R.id.btn_edit);
        words.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Stop.this, Words.class);
                Stop.this.startActivity(intent);//启动新的Intent
            }
        });
    }
}
