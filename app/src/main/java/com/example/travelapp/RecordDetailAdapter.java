package com.example.travelapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class RecordDetailAdapter extends RecyclerView.Adapter<RecordDetailAdapter.ViewHolder> {
    private List<RecordDetail> mRecordDetailList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView viewImage;
        TextView viewdescribe;

        public ViewHolder(View view){
            super(view);
            viewImage=(ImageView)view.findViewById(R.id.view_image);
            viewdescribe=(TextView)view.findViewById(R.id.view_describe);
        }
    }
    public RecordDetailAdapter(List<RecordDetail> recordDetailList){
        mRecordDetailList=recordDetailList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.record_detail_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        RecordDetail recordDetail =mRecordDetailList.get(position);
        holder.viewImage.setImageResource(recordDetail.getPhotoid());
        holder.viewdescribe.setText(recordDetail.getPhotodescrible());
    }
    @Override
    public int getItemCount(){
        return mRecordDetailList.size();
    }
}
