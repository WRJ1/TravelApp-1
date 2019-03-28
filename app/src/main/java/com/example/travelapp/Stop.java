package com.example.travelapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.OnMapScreenShotListener;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Stop extends AppCompatActivity implements LocationSource, AMapLocationListener,OnMapScreenShotListener {

    public static final int TAKE_PHOTO = 101;
    private Button camera;
    private Button words;
    private Button stop;
    private String routefile;
    private String stopLocation;
    private String MapsPath;
    private AMap aMap;
    private MapView mapView;

    //以前的定位点
    private LatLng oldLatLnog;
    private boolean isFirstLoc;
    private String starttime;
    private OnLocationChangedListener mListener = null;
    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption = null;
    private AMapLocation mAmapLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        //获取起始时间
        Intent intentroutefile = getIntent();
        routefile = intentroutefile.getStringExtra("routefiletocamera");
        Log.e("Stop","get"+routefile);
        starttime = routefile.substring(0,routefile.indexOf("_"));


        //显示地图
        mapView = (MapView) findViewById(R.id.map);
        //必须要写
        mapView.onCreate(savedInstanceState);
        //获取地图对象
        if (aMap == null) {
            aMap = mapView.getMap();
            //设置显示定位按钮 并且可以点击
            UiSettings settings = aMap.getUiSettings();
            aMap.setLocationSource(this);//设置了定位的监听

            // 是否显示定位按钮
            settings.setMyLocationButtonEnabled(true);
            aMap.setMyLocationEnabled(true);//显示定位层并且可以触发定位,默认是flase

        }
        isFirstLoc = true;
        location();

        camera = (Button)findViewById(R.id.btn_camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Stop.this, PhotoWithWord.class);
                intent.putExtra("starttime",routefile);
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
                intent.putExtra("starttime",routefile);
                /*try {
                    if (ExternalStorageUtil.isExternalStorageMounted()) {

                        // Check whether this app has write external storage permission or not.
                        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(Stop.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        // If do not grant write external storage permission.
                        if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                            // Request user to grant write external storage permission.
                            ActivityCompat.requestPermissions(Stop.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 119);
                        } else {

                            // Specify the directory to use
                            //String publicDirectory = ExternalStorageUtil.getPublicExternalStorageBaseDir(Environment.DIRECTORY_DCIM);
                            String Wordsfile = Environment.getExternalStorageDirectory().getPath() + "/Words";
                            File words = new File(Wordsfile);
                            if (words.exists()) {
                                String publicDirectory = ExternalStorageUtil.getPublicExternalStorageBaseDir(Environment.getRootDirectory().getPath());
                                Log.e("TAG", publicDirectory);
                                File fileAll = new File(publicDirectory);
                                //Get the list of the files in this directory
                                File[] files = fileAll.listFiles();
                                for (File file : files) {
                                    Log.e("TAG", "File found: " + file.getName());
                                }
                                //Write a new file in this directory
                                String fileName = "my_file.txt";
                                File newFile = new File(publicDirectory, fileName);
                                FileWriter fw = new FileWriter(newFile);
                                fw.write("Insert this text");
                                fw.flush();
                                fw.close();
                                Log.e("TAG", "Saved: " + newFile.getAbsolutePath());*/

                                /**
                                 * Read the file content
                                 */

                                // Get The Text file
                                /*File textFileToRead = new File(publicDirectory, fileName);

                                // Read the file Contents in a StringBuilder Object (handling long string content)
                                StringBuilder content = new StringBuilder();
                                try {
                                    BufferedReader reader = new BufferedReader(new FileReader(textFileToRead));
                                    String line;
                                    while ((line = reader.readLine()) != null) {
                                        content.append(line + '\n');
                                    }
                                    reader.close();
                                    Log.e("TAG", "Content: " + content);

                                } catch (IOException e) {
                                    Log.e("TAG", "Error reading the file requested.");

                                }
                            }else {
                                //若不存在，创建目录，可以在应用启动的时候创建
                                words.mkdirs();
                                setTitle("paht ok,path:"+Wordsfile);
                                Log.e("Stop","create file successful"+Wordsfile);
                            }
                        }
                    }

                    }catch(Exception ex)
                    {
                        Log.e("TAG", "Error: " + ex.getMessage(), ex);

                    }*/
                Stop.this.startActivity(intent);//启动新的Intent，
            }
        });
        stop = (Button)findViewById(R.id.btn_stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*//记录路程结束时间
                SimpleDateFormat timesdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String FileTime =timesdf.format(new Date()).toString();//获取系统时间
                String stoptime = FileTime.replace(":", "");
                Intent stoptimeintent = new Intent();
                stoptimeintent.putExtra("stoptime",stoptime);*/
                Intent intent=new Intent(Stop.this,UploadSuccessful.class);
                intent.putExtra("starttime",starttime);
               Log.e("StopL",stopLocation);
                MapsPath = Environment.getExternalStorageDirectory() + "/"+ routefile + "--"+ stopLocation  + ".png";
               // MapsPath = Environment.getExternalStorageDirectory()+"/maps/"+ routefile + "--"+ stopLocation + ".png";
                Log.e("Stoppath",MapsPath);
               screenShot(v);
               startActivity(intent);
               finish();
            }
        });

    }

   public void screenShot(View v){
      aMap.getMapScreenShot(this);
      Log.e("screenshot",MapsPath);
   }

    private void location() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
        mLocationClient.stopLocation();//停止定位
        mLocationClient.onDestroy();//销毁定位客户端。
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();//国家信息
                aMapLocation.getProvince();//省信息
                aMapLocation.getCity();//城市信息
                aMapLocation.getDistrict();//城区信息
                aMapLocation.getStreet();//街道信息
                aMapLocation.getStreetNum();//街道门牌号信息
                aMapLocation.getCityCode();//城市编码
                aMapLocation.getAdCode();//地区编码
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                LatLng newLatLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                MyLocationStyle myLocationStyle;
                myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
                myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
                aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style

                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {
                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener.onLocationChanged(aMapLocation);
                    //添加图钉
                    aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
                    //  aMap.addMarker(getMarkerOptions(amapLocation));
                    //获取定位信息
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(aMapLocation.getCountry() + ""
                            + aMapLocation.getProvince() + ""
                            + aMapLocation.getCity() + ""
                            + aMapLocation.getProvince() + ""
                            + aMapLocation.getDistrict() + ""
                            + aMapLocation.getStreet() + ""
                            + aMapLocation.getStreetNum());
                    Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();
                    isFirstLoc = false;
                }
               stopLocation = aMapLocation.getStreet();
                if (oldLatLnog != newLatLng) {
                    Log.e("Amap", aMapLocation.getLatitude() + "," + aMapLocation.getLongitude());
                    setUpMap(oldLatLnog, newLatLng);
                    oldLatLnog = newLatLng;
                }

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
                Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mLocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            mLocationOption.setOnceLocation(false);
            /**
             * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
             * 注意：只有在高精度模式下的单次定位有效，其他方式无效
             */
            mLocationOption.setGpsFirst(true);
            // 设置发送定位请求的时间间隔,最小值为1000ms,1秒更新一次定位信息
            mLocationOption.setInterval(1000);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mLocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
    }


    /**
     * 绘制两个坐标点之间的线段,从以前位置到现在位置
     */
    private void setUpMap(LatLng oldData, LatLng newData) {

        // 绘制一个大地曲线
        aMap.addPolyline((new PolylineOptions())
                .add(oldData, newData)
                .geodesic(true).color(Color.BLUE));

    }
    @Override
    public void onMapScreenShot(Bitmap bitmap) {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            // 保存在SD卡根目录下，图片为png格式。
            FileOutputStream fos = new FileOutputStream(MapsPath);
            Log.e("shotsuccessful",MapsPath);
            boolean b = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            try {
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (b)
                Toast.makeText(this, "Your trip is finished! Upload successful!", Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(this, "截屏失败", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
