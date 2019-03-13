package com.example.travelapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {
    private List<Record> mrecordList;

    private OnRecyclerViewClickListener listener;
    public void setItemClickListener(OnRecyclerViewClickListener itemClickListener) {
        listener = itemClickListener;
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        View recordView;
        TextView dateRecord;
        TextView locationRecord;
        public ViewHolder(View view){
            super(view);
            recordView = view;
            dateRecord = (TextView) view.findViewById(R.id.tv_date);
            locationRecord = (TextView)view.findViewById(R.id.tv_location);
        }
    }

    public RecordAdapter(List<Record> recordList){
        mrecordList = recordList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.record_item,
                                        viewGroup,false);
        ViewHolder holder = new ViewHolder(view);

        //接口回调
        if(listener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickListener(v);
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClickListener(v);
                    return true;
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Record record = mrecordList.get(i);
        viewHolder.dateRecord.setText(record.getDate());
        viewHolder.locationRecord.setText(record.getLocation());
    }

    @Override
    public int getItemCount() {
        return mrecordList.size();
    }
}
