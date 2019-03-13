package com.example.travelapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null){
            actionbar.hide();
        }
        Button titleHistory = (Button)findViewById(R.id.title_history);
        Button titleNewRoute = (Button)findViewById(R.id.title_newroute);
        titleHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"History",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,TravelRecord.class);
                startActivity(intent);
                //finish();

            }
        });
        titleNewRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "NewRoute", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,Start.class);
                startActivity(intent);
            }
        });
    }
}

