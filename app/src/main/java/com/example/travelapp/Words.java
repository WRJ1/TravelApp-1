package com.example.travelapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Words extends TitleTravelRecord {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        showTitleTextView(false);
        showBackwardView(true);
        showUploadView(true);
    }
}
