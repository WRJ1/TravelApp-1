package com.example.travelapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;


public class Start extends AppCompatActivity{
    private Button btnStart;
    private MapView mapView;//地图控件
    private AMap aMap;//地图对象


    //定位需要的声明
    private AMapLocationClient mLocationClient = null;//定位发起端
    private AMapLocationClientOption mLocationOption = null;//定位参数
    private AMapLocationListener mListener = null;//定位监听器

    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        //显示地图
        mapView = (MapView) findViewById(R.id.map);
        //必须要写
        mapView.onCreate(savedInstanceState);
        //获取地图对象
        aMap = mapView.getMap();


        btnStart = (Button)findViewById(R.id.btn_Start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Start.this,"Start route",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Start.this,Stop.class);
                startActivity(intent);
                //finish();
            }
        });
    }
}
