package com.example.travelapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class TravelRecord extends TitleTravelRecord {

    private List<Record> recordList = new ArrayList<>();

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
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickLitener(new RecordAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                //String id = infoBeen.get(position).getId();
                //String id = infoBeen.get(position).getId();
                position = recyclerView.getChildAdapterPosition(view);
                Intent intent;
                switch (position) {
                    case 0:
                        //setContentView(R.layout.activity_travel_record_detail);
                        intent = new Intent(TravelRecord.this, TravelRecordDetail.class);
                        startActivity(intent);
                        break;
                    case 1:
                        //setContentView(R.layout.activity_travel_record_detail);
                        intent = new Intent(TravelRecord.this, TravelRecordDetail.class);
                        startActivity(intent);
                        break;
                }
            }
        });
        /*adapter.setItemClickListener(new OnRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(View view) {
                int position = recyclerView.getChildAdapterPosition(view);
                switch (position) {
                    case 0:
                        setContentView(R.layout.activity_travel_record_detail);
                        break;
                    case 1:
                        setContentView(R.layout.activity_travel_record_detail);
                        break;
                }
            }

            @Override
            public void onItemLongClickListener(View view) {

            }
        });*/
        }

    private void initRecords(){
            Record r1 = new Record("2018/03/19", "CQU-HongyaCave");
            recordList.add(r1);
            Record r2 = new Record("2018/03/20", "CQU-SFAI");
            recordList.add(r2);
    }

}
