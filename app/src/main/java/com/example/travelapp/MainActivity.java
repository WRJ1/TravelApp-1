package com.example.travelapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;

public class MainActivity extends AppCompatActivity{
    private Button btnStart;
    private MapView mapView;//地图控件
    private AMap aMap;//地图对象


    //定位需要的声明
    private AMapLocationClient mLocationClient = null;//定位发起端
    private AMapLocationClientOption mLocationOption = null;//定位参数
    private AMapLocationListener mListener = null;//定位监听器
    private boolean isFirstLoc = true;//用于判断是否重新定位
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null){
            actionbar.hide();
        }
        //Todo: load the map
        //显示地图
        mapView = (MapView) findViewById(R.id.map);
        //必须要写
       mapView.onCreate(savedInstanceState);
        //获取地图对象

        //aMap = mapView.getMap();
        Button titleHistory = (Button)findViewById(R.id.title_history);
        Button titleNewRoute = (Button)findViewById(R.id.title_newroute);

        titleHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"History",Toast.LENGTH_SHORT).show();
                try {
                    if (ExternalStorageUtil.isExternalStorageMounted()) {

                        // Check whether this app has write external storage permission or not.
                        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        // If do not grant write external storage permission.
                        if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                            // Request user to grant write external storage permission.
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 119);
                        } else {
                            Intent intent=new Intent(MainActivity.this,TravelRecord.class);
                            startActivity(intent);
                        } }

                }catch(Exception ex)
                {
                    Log.e("TAG", "Error: " + ex.getMessage(), ex);

                }
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

