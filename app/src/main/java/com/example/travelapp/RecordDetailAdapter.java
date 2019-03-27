package com.example.travelapp;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;
import java.util.Random;

public class RecordDetailAdapter extends RecyclerView.Adapter<RecordDetailAdapter.ViewHolder> {
    private List<RecordDetail> mRecordDetailList;
    private Context mContext;
    private LayoutInflater inflater;
    private SparseArray<Integer> heightArray;
    private List<String> mPhotos;
    //private static int SCREE_WIDTH = 0;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView viewImage;
        TextView viewdescribe;

        public ViewHolder(View view){
            super(view);
            viewImage=(ImageView)view.findViewById(R.id.view_image);
            viewdescribe=(TextView)view.findViewById(R.id.view_describe);
        }
    }
    public RecordDetailAdapter(Context context,List<RecordDetail> recordDetailList){
        mRecordDetailList=recordDetailList;
        mContext = context;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        heightArray = new SparseArray<Integer>();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.record_detail_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        //view.setOnClickListener(this);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        RecordDetail recordDetail =mRecordDetailList.get(position);
        //用Glide去加载图片
        Glide.with(mContext).load(recordDetail.getPhotoid()).into(holder.viewImage);
        holder.viewdescribe.setText(recordDetail.getPhotodescrible());
    }
    @Override
    public int getItemCount(){
        return mRecordDetailList.size();
    }
}
