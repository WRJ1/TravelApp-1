package com.example.travelapp;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.RandomAccessFile;

public class Words extends TitleTravelRecord {
    private EditText typewords;
    private String words;
    private String routefile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        showTitleTextView(false);
        showBackwardView(true);
        showUploadView(false);
        Intent intentroutefile = getIntent();
        routefile = intentroutefile.getStringExtra("starttime");
        setFilename(routefile);
        Log.e("Words",routefile);
        typewords = (EditText)findViewById(R.id.edit_words);
        typewords.addTextChangedListener(edit);

    }
    private TextWatcher edit = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            words = s.toString();
            setWords(words);
            if (words!="")
            {
                showUploadView(true);
            } else {
                showUploadView(false);
            }
        }
    };
}
