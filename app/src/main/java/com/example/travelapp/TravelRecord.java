package com.example.travelapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TravelRecord extends TitleTravelRecord {

    private List<Record> recordList = new ArrayList<>();
    private List<String> mMaps = new ArrayList<String>();
    private List<String> mRecordDate = new ArrayList<String>();
    private List<String> mRecordLocation = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_record);

        setTitle("TRAVEL RECORD");
        showBackwardView(true);
        showTitleTextView(true);
        showUploadView(false);

        //初始化记录数据
        initRecords();
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recview_travelrecord);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        RecordAdapter adapter = new RecordAdapter(recordList);
        //添加Android自带的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickLitener(new RecordAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                position = recyclerView.getChildAdapterPosition(view);
                Intent intent;
                intent = new Intent(TravelRecord.this, TravelRecordDetail.class);
                String map = mMaps.get(position);
                Log.e("TR_route_press",map);
                String time = map.substring(20, map.indexOf("_"));
                intent.putExtra("routetime",time);
                startActivity(intent);
            }
        });
    }

    private void initRecords() {
        mMaps = getMapsPathFromSD();
        for (int i = 0; i < mMaps.size(); i++) {
            String map = mMaps.get(i);
            Log.e("map",map);
            String date = map.substring(20, map.indexOf("_"));
            Log.e("date",date);
            mRecordDate.add(date);
            String location = map.substring(38, map.indexOf("."));
            mRecordLocation.add(location);
        }
        String date;
        String location;
        for (int i = 0; i < mMaps.size(); i++) {
            date = mRecordDate.get(i);
            location = mRecordLocation.get(i);
            Record record = new Record(date, location);
            recordList.add(record);
        }
    }

    /**
     * 从sd卡获取地图截屏文件资源
     *
     * @return
     */
    public List<String> getMapsPathFromSD() {
        List<String> mapsPathList = new ArrayList<String>();
        String mapsPath = Environment.getExternalStorageDirectory().getPath();
        File fileAll=new File(mapsPath);
        File[] files=fileAll.listFiles();
        if (files == null){
            Log.e("error","空目录");return null;}
        // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (checkIsMapsFile(file.getPath())){
                mapsPathList.add(file.getPath());
            }
        }
        return mapsPathList;
    }

    /**
     * 检查扩展名，得到png格式的文件
     *
     * @param fName 文件名
     * @return
     */
    @SuppressLint("DefaultLocale")
    private boolean checkIsMapsFile(String fName) {
        boolean isMapFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("png")) {
            isMapFile = true;
        } else {
            isMapFile = false;
        }
        return isMapFile;
    }
}
