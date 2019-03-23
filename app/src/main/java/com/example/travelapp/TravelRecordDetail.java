package com.example.travelapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class TravelRecordDetail extends TitleTravelRecord {

    private ImageView imgMap;
    private List<RecordDetail> recordDetailList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_record_detail);
        setTitle("TRAVEL RECORD");
        showTitleTextView(true);
        showBackwardView(true);
        showUploadView(false);

        initRecordDetail();
         imgMap = (ImageView)findViewById(R.id.img_map);
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        RecordDetailAdapter adapter=new RecordDetailAdapter(recordDetailList);
        recyclerView.setAdapter(adapter);
    }


    private void initRecordDetail(){
        RecordDetail view =new RecordDetail("好看好看好看！",R.mipmap.view);
        recordDetailList.add(view);
        RecordDetail view1 =new RecordDetail("好看好看好看好看好看好看好看好看好看好看好看好看好看好看！",R.mipmap.view1);
        recordDetailList.add(view1);
        RecordDetail view2 =new RecordDetail("好看！",R.mipmap.view2);
        recordDetailList.add(view2);
        RecordDetail view3 =new RecordDetail("好看好看好看好看好看好看好看好看！",R.mipmap.view3);
        recordDetailList.add(view3);
        RecordDetail view4 =new RecordDetail("好看好看好看好看好看好看好看好看好看好看好看好看好看好看好看好看好看好看好看好看！",R.mipmap.view4);
        recordDetailList.add(view4);
        RecordDetail view5 =new RecordDetail("好看好看好看！",R.mipmap.view5);
        recordDetailList.add(view5);
        RecordDetail view6 =new RecordDetail("好看好看！",R.mipmap.view6);
        recordDetailList.add(view6);
        RecordDetail view7 =new RecordDetail("好看好看好看好看好看好看好看好看好看好看！",R.mipmap.view7);
        recordDetailList.add(view7);
    }
}
